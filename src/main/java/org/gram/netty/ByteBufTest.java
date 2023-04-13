package org.gram.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;

public class ByteBufTest {
    /**
     * 扩容测试
     */
    public void b1(){
        ByteBuf heapBuf= Unpooled.buffer(10);
        ByteBuf directBuf= ByteBufAllocator.DEFAULT.buffer(10);
        System.out.println(heapBuf.capacity()+":"+directBuf.capacity());
        System.out.println(heapBuf.getClass()+":"+directBuf.getClass());

        StringBuilder builder=new StringBuilder();
        for(int i=0;i<300;i++){
            builder.append("a");
        }
        heapBuf.writeBytes(builder.toString().getBytes());
        System.out.println(heapBuf.capacity());//扩容为16倍数
        log(heapBuf);
        for(int i=0;i<10;i++){
            directBuf.writeByte(i);
        }
        System.out.println(directBuf.capacity());
        log(directBuf);
    }

    /**
     * ByteBuf读取
     */
    public void b2(){
        ByteBuf buf=Unpooled.copiedBuffer("hello,ByteBuf!", CharsetUtil.UTF_8);
        if(buf.hasArray()){
            byte[] content=buf.array();
            System.out.println(new String(content,CharsetUtil.UTF_8));
            //基本属性
            System.out.println(buf.arrayOffset()+":"+buf.readerIndex()+":"+buf.writerIndex()+":"+buf.capacity());
            //标记读取
            System.out.println(buf.readableBytes());
            System.out.println((char)buf.readByte());
            System.out.println(buf.readableBytes());
            //不标记范围读取
            System.out.println("读取范围0~5的数据"+buf.getCharSequence(0,5,CharsetUtil.UTF_8));
            System.out.println("读取范围5~7的数据"+buf.getCharSequence(5,2,CharsetUtil.UTF_8));
            //记录后标记读取，再重置
            byte[] bcon = new byte[buf.readableBytes()];
            buf.markReaderIndex();
            buf.readBytes(bcon);
            System.out.println("当前可读字节数: "+buf.readableBytes());
            buf.resetReaderIndex();
            System.out.println("还原读取标记位后当前可读字节数: "+buf.readableBytes());
            System.out.println(new String(bcon));
        }
    }

    /**
     * 零拷贝slice+duplicate
     */
    public void b3(){
        ByteBuf buf=ByteBufAllocator.DEFAULT.buffer(20);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        log(buf);
        //切片方法
        ByteBuf slice1=buf.slice(0,5);
        log(slice1);
        ByteBuf slice2=buf.slice(5,5);
        log(slice2);
        //切片和原始数据同源
        slice1.setByte(0,'p');
        log(slice1);
        log(buf);
        //duplicate
        ByteBuf duplicate=buf.duplicate();
        log(duplicate);
    }

    /**
     * 打印ByteBuf
     */
    private void log(ByteBuf bb){
        int length=bb.readableBytes();
        int rows=length/16 +(length%15==0?0:1)+4;
        StringBuilder sb=new StringBuilder(rows*80*2)
                .append("read index:")
                .append(bb.readerIndex())
                .append("write index:")
                .append(bb.writerIndex())
                .append(" capacity:")
                .append(bb.capacity())
                .append("\n");
        appendPrettyHexDump(sb,bb);
        System.out.println(sb);
    }
}
