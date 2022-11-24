package org.gram.concurrent_test;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;

//无边界阻塞队列，不允许null元素
//add、offer，按元素值插入到特定位置。元素如不能和其他元素比较或者为空则抛异常
//take，队列为空时会阻塞。
public class PriorityBlockingQueueTest {
    public void t1(){
        PriorityBlockingQueue<Integer> q=new PriorityBlockingQueue<>();
        int concurrentNum=11;
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
