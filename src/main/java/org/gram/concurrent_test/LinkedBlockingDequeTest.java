package org.gram.concurrent_test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

//和LinkedBlockingQueue的区别就是Deque，双头的，非FIFO
public class LinkedBlockingDequeTest {
    public void l1(){
        LinkedBlockingDeque<Integer> d=new LinkedBlockingDeque<>();
        int concurrentNum=11;
        CountDownLatch cdl=new CountDownLatch(concurrentNum);
        for(int i=0;i<concurrentNum;i++){
            int finalI = i;
            Thread t=new Thread(()->{
                d.add(finalI);
                cdl.countDown();
            });
            t.start();
        }
        try{
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(d);
    }
}
