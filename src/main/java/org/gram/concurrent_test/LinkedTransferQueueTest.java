package org.gram.concurrent_test;

import java.util.Date;
import java.util.concurrent.*;

//无界链表TransferQueue，TransferQueue是一个阻塞队列，且生产者会等待消费者接受元素。
//可以看出该数据结构是专门用于实现消息队里的。
public class LinkedTransferQueueTest {
    public void l1(){
        LinkedTransferQueue<Integer> q=new LinkedTransferQueue<>();
        ScheduledExecutorService ses= Executors.newScheduledThreadPool(5);
        Thread producer=new Thread(()->{
            ses.scheduleAtFixedRate(()->{
                try{
                    q.transfer(10);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            },1,2, TimeUnit.SECONDS);
        });
        producer.start();
        int concurrentNum=20;
        CountDownLatch cdl=new CountDownLatch(concurrentNum);
        for(int i=0;i<concurrentNum;i++){
            Thread consumer=new Thread(()->{
                try{
                    System.out.println("consumer "+Thread.currentThread().getName()+" value "+q.take());
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                cdl.countDown();
            });
            consumer.start();
        }
        try{
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        ses.shutdown();
    }
}
