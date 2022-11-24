package org.gram.concurrent_test;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

//无边界并发安全链表queue，FIFO。
//add，添加成功返回true，元素为空抛NullPointerException。
//element、peek，queue为空只会返回null不会阻塞。
public class ConcurrentLinkedQueueTest {
    //并发写
    public void c1(){
        ConcurrentLinkedQueue<Integer> q=new ConcurrentLinkedQueue<>();
        int concurrentNum=10;
        CountDownLatch cdl=new CountDownLatch(concurrentNum);
        for(int i=0;i<concurrentNum;i++){
            int finalI = i;
            Thread t=new Thread(()->{
                q.add(finalI);
                cdl.countDown();
            });
            t.start();
        }
        try{
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(q.toArray()));
    }


}
