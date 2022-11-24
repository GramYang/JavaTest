package org.gram.std;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

//参考https://zhuanlan.zhihu.com/p/99609337
public class IOTest {
    public void byteArrayStreamTest(){
        byte[] bs="asdasdasd".getBytes();
//        ByteArrayInputStream bais =new ByteArrayInputStream(bs,1,3);//可以切片范围
        ByteArrayInputStream bais =new ByteArrayInputStream(bs);
        System.out.println((char)bais.read());//a
        System.out.println(bais.available());//8，读成一个，还剩8个
        System.out.printf("%d %d\n",bais.skip(2),bais.available());//2 6
        bais.reset();//你没有设置mark，那么mark初始值就是0
        System.out.println(bais.available());//9，reset后全部重置为初始状态
        System.out.printf("%d %d\n",bais.skip(2),bais.available());//2 7
        bais.mark(3);//mark传入的参数是没有意义的，这里实际上mark的是当前位置
        bais.reset();
        System.out.println(bais.available());//7
//        System.out.println(Arrays.toString(bais.readAllBytes()));//这个方法有问题，必然报错
        ByteArrayOutputStream baos=new ByteArrayOutputStream(5);//这个尺寸是可以超的
        System.out.printf("%d %s\n",baos.size(),baos);//0 ""
        try{
            baos.write(bs);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.printf("%d %s\n",baos.size(),baos);//9 asdasdasd
    }

    //File用来操作目录和文件属性，不能进行文件内容io
    public void fileTest(){
        String p ="iot/iot1/iotest.txt";
        File file=createOrGetFile(p);
//        System.out.println(file.mkdir());//因为p带不存在的父路径，因此返回false创建失败
//        if(!file.exists()){
//            System.out.println(file.mkdirs());//返回true创建成功，在项目根目录
//        }
        //注意：上面两个mkdir项目都是创建的目录，不是文件！！
        System.out.printf("%b %b %b %b %b %b %b %b\n",file.canRead(),file.canWrite(),file.canExecute(),file.exists(),
                file.isAbsolute(),file.isDirectory(),file.isFile(),file.isHidden());
        //true true true true false true false false
        System.out.printf("%s %s %s %s %s\n",file.getAbsolutePath(),file.getPath(),file.getName(),file.getParent(),file);
        //C:\Users\gramyang\IdeaProjects\JavaTest\iot\iotest.txt iot\iotest.txt iotest.txt iot iot\iotest.txt
        System.out.printf("%d \n",file.lastModified());//1621299152789
        file.deleteOnExit();//这里只会删除文件不会删除目录
    }

    //单文件io
    public void fileStreamTest1(){
        String p ="iot/iot1/iotest.txt";
        File file=createOrGetFile(p);
        System.out.println(file.exists());//true
        FileOutputStream fos;//用于写
        FileInputStream fis;//用于读
        try{
            fos=new FileOutputStream(file,true);//该文件不存在也没关系，会创建新文件，但是目录必须有
            fos.write("abcd".getBytes(StandardCharsets.UTF_8));
            System.out.println(fos.getFD().valid());//true
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            fis=new FileInputStream(file);
            System.out.printf("%d %c\n",fis.available(),(char)fis.read());//4 a
            fis.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //文件之间io
    public void fileStreamTest2(){
        String p1="iot/iot1/iotest.txt";
        String p2="iot/iot1/iotest1.txt";
        File file1=createOrGetFile(p1);
        File file2=createOrGetFile(p2);
        FileInputStream fis = null;
        FileOutputStream fos=null;
        FileChannel fci=null;
        FileChannel fco=null;
        try{
            fis=new FileInputStream(file1);
            fos=new FileOutputStream(file2);
            fci=fis.getChannel();
            fco=fos.getChannel();
            fci.transferTo(0,fci.size(),fco);
            fis=new FileInputStream(file2);
            System.out.println(fis.available());//8，只是复制，不是剪切
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.printf("%d %d\n",file1.length(),file2.length());//8 8
        try{
            assert fis != null;
            fis.close();
            assert fci != null;
            fci.close();
            fos.close();
            fco.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private File createOrGetFile(String filePath){
        File file=new File(filePath);
        try{
            if(!file.exists()){
                File fileParent=file.getParentFile();
                if(!fileParent.exists()){
                    System.out.println(fileParent.mkdirs());//true
                }
                System.out.println(file.createNewFile());//true
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }

    public void pipedStreamTest(){
        Sender sender= new Sender();
        Receiver receiver= new Receiver();
        try{
            receiver.pis.connect(sender.pos);
            sender.start();
            receiver.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    static class Receiver extends Thread{
        PipedInputStream pis=new PipedInputStream();
        @Override
        public void run(){
            readOnce();
            readLimited();
        }

        //管道流数据读到字节数组
        private void readOnce(){
            byte[] buf=new byte[2048];
            try{
                int len=pis.read(buf);//PipedInputStream的缓冲区默认大小只有1024
                System.out.println(new String(buf,0,len));
                pis.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        private void readLimited(){
            int total=0;
            while(true){
                byte[] buf=new byte[1024];
                try{
                    int len=pis.read(buf);
                    total+=len;
                    System.out.println(new String(buf,0,len));
                    if(total>1024) break;
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    static class Sender extends Thread{
        PipedOutputStream pos=new PipedOutputStream();

        @Override
        public void run() {
            writeShort();
            writeLong();
        }

        private void writeShort(){
            String msg="123466789999";
            try{
                pos.write(msg.getBytes(StandardCharsets.UTF_8));
                pos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        private void writeLong(){
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<1046;i++){
                sb.append("6");
            }
            try{
                pos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
                pos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //FilterInputStream主要是用于读操作，使用的都是其子类：BufferedInputStream用于读大数据，DataInputStream用于读java基本数据，LineNumberInputStream跟踪行号（已废弃），
    //PushbackInputStream增加推回取消读取功能
    public void filterStreamTest(){
    }

    public void byteBufferTest(){
        System.out.println(Runtime.getRuntime().freeMemory());//252055008
        ByteBuffer bf=ByteBuffer.allocate(102400);
        System.out.println(bf);//java.nio.HeapByteBuffer[pos=0 lim=102400 cap=102400]
        System.out.println(Runtime.getRuntime().freeMemory());//252055008
        ByteBuffer bf1=ByteBuffer.allocateDirect(102400);
        System.out.println(bf1);//java.nio.DirectByteBuffer[pos=0 lim=102400 cap=102400]
        System.out.println(Runtime.getRuntime().freeMemory());//252055008
        byte[] bs=new byte[32];
        ByteBuffer bf2=ByteBuffer.wrap(bs);
        System.out.println(bf2);//java.nio.HeapByteBuffer[pos=0 lim=32 cap=32]
        bf2=ByteBuffer.wrap(bs,10,10);
        System.out.println(bf2);//java.nio.HeapByteBuffer[pos=10 lim=20 cap=32]
        ByteBuffer bf3=ByteBuffer.wrap("123456789".getBytes(StandardCharsets.UTF_8));
        System.out.printf("%d %d %d %d\n",bf3.capacity(),bf3.position(),bf3.limit(),bf3.remaining());//9 0 9 9，注意这里的remaining是limit-position
        bf3.position(5);//position不能超过capacity
        bf3.mark();//这里mark等于5
        System.out.printf("%d %d %d %d\n",bf3.capacity(),bf3.position(),bf3.limit(),bf3.remaining());//9 5 9 4
        bf3.position(7);
        bf3.reset();//将position还原到mark
        System.out.printf("%d %d %d %d\n",bf3.capacity(),bf3.position(),bf3.limit(),bf3.remaining());//9 5 9 4
        bf3.clear();//重置position、limit、mark
        System.out.printf("%d %d %d %d\n",bf3.capacity(),bf3.position(),bf3.limit(),bf3.remaining());//9 0 9 9
        bf3.position(7).limit(8);//limit也不能比capacity高
        System.out.printf("%d %d %d %d\n",bf3.capacity(),bf3.position(),bf3.limit(),bf3.remaining());//9 7 8 1
        bf3.rewind();//还原position和mark
        System.out.printf("%d %d %d %d\n",bf3.capacity(),bf3.position(),bf3.limit(),bf3.remaining());//9 0 8 8
        bf3.position(5).flip();
        System.out.printf("%d %d %d %d\n",bf3.capacity(),bf3.position(),bf3.limit(),bf3.remaining());//9 0 5 5
        bf3.position(3);
        bf3.compact();
        System.out.printf("%d %d %d %d\n",bf3.capacity(),bf3.position(),bf3.limit(),bf3.remaining());//9 2 9 7
    }
}
