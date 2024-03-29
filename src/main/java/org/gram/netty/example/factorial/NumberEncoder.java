package org.gram.netty.example.factorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.math.BigInteger;

/**
 * 消息加密成字节数组，消息是数字
 */
public class NumberEncoder extends MessageToByteEncoder<Number> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Number msg, ByteBuf out) throws Exception {
        BigInteger v;
        if(msg instanceof BigInteger){
            v=(BigInteger) msg;
        }else{
            v=new BigInteger(String.valueOf(msg));
        }
        byte[] data=v.toByteArray();
        int dataLength=data.length;
        out.writeByte((byte)'F');
        out.writeInt(dataLength);
        out.writeBytes(data);
    }
}
