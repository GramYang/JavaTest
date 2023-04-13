package org.gram.netty.example.factorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.math.BigInteger;
import java.util.List;

/**
 * 将字节流解析成Object，tlv格式，v数字
 */
public class BigIntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //消息太短，退出重新读
        if(in.readableBytes()<5){
            return;
        }
        in.markReaderIndex();
        //读1个字节为short
        int magicNumber = in.readUnsignedByte();
        //头字节不对，重置并抛异常
        if(magicNumber!='F'){
            in.resetReaderIndex();
            throw new CorruptedFrameException("Invalid magic number: " + magicNumber);
        }
        //读4个字节为int，这是报文长度
        int dataLength=in.readInt();
        //报文长度不对，重置后退出
        if(in.readableBytes()<dataLength){
            in.resetReaderIndex();
            return;
        }
        //读取指定长度的数据报文，将其从in中标记为已读
        byte[] decoded=new byte[dataLength];
        in.readBytes(decoded);
        out.add(new BigInteger(decoded));
    }
}
