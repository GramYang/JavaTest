package org.gram.std;

import java.util.concurrent.CountDownLatch;

public class VolatileTest {
    //volatile基本原理
    public void vt1(){
        Work w=new Work();
        new Thread(w::open).start();
        new Thread(w::close).start();
        //参考https://blog.csdn.net/ly0724ok/article/details/116568640
        //上面的代码，如果isClosed不加volatile，可能存在几率w::open无法被中断。因为w::close中修改了线程工作内存中isClosed的值但是并不一定会回写到主存中去
        //如果不回写isClosed，那么w::open就会一直循环下去。但是加了volatile就不一样了，volatile会强制将这个值回写到主存中。原理：
        //使用volatile，当w::close修改isClosed时会强制回写到驻村中并使得w::open的isClosed缓存行无效（反映到硬件层的话，就是CPU的L1或者L2缓存中对应的缓存行无效）。
        //由于w::open的isClosed缓存行无效，所以w::open会去主存再次读取isClosed。
    }

    static class Work{
        boolean isClosed=false;
        void close(){
            isClosed=true;
            System.out.println("closed");
        }

        void open(){
            while (!isClosed){
                System.out.println("open");
            }
        }
    }

    private volatile int inc=0;

    //volatile原子性测试
    public void vt2(){
        int num=10;
        CountDownLatch cdl=new CountDownLatch(num);
        for(int i=0;i<num;i++){
            new Thread(){
                @Override
                public void run() {
                    for(int j=0;j<1000;j++)inc++;
                    cdl.countDown();
                }
            }.start();
        }
        try{
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(inc);//输出值随机，始终小于10000
    }

}
