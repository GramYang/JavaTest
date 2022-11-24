package org.gram.std;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteBufferTest {
    //大小端序列化操作
    public void t1(){
        short a1=1234;
        ByteBuffer bb=ByteBuffer.allocate(2);
        bb.putShort(a1);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println(bb.order());//小端
        bb.clear();
        System.out.println(bb.order());//小端，clear后仍然是小端
        byte[] bs1=bb.array();
        ByteBuffer bb1=ByteBuffer.wrap(bs1);
        System.out.println(bb1.order());//大端
        System.out.println(bb1.getShort());//虽然小端编码用大端解析，但是仍然解析正确
    }

    //put+get
    public void t2(){
        byte[] bs="欧内的手好汉".getBytes(StandardCharsets.UTF_8);
        ByteBuffer bb=ByteBuffer.allocate(bs.length+2).order(ByteOrder.LITTLE_ENDIAN);
        bb.putShort((short)1234);
        bb.put(bs);
        System.out.println(Arrays.toString(bb.array()));
        bb.rewind();
        System.out.println(Arrays.toString(bb.array()));
        byte[] v2=new byte[bb.limit()-2];
        short v1=bb.getShort();
        bb.get(v2);
        System.out.println(v1);
        System.out.println(new String(v2));
    }
}
