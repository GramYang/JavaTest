package org.gram.concurrent_test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

//有界无界可选（不指定就是无界）的并发安全阻塞队列。FIFO。
//add（调用的offer），插入元素成功返回true，没有空间则抛异常。
//offer，插入元素到队列尾部，队列满了则返回false
//remove，移除并返回元素头，队列为空则抛异常
//take，队列为空则阻塞
public class LinkedBlockingQueueTest {

    //并发写，测试并发安全
    public void l1(){
        LinkedBlockingQueue<Integer> q=new LinkedBlockingQueue<>();
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

    //并发读，测试并发安全和空阻塞
    public void l2(){
        LinkedBlockingQueue<Integer> q=new LinkedBlockingQueue<>(Arrays.asList(1,2,3,4));
        int concurrentNum=5;
        CountDownLatch cdl=new CountDownLatch(concurrentNum);
        for(int i=0;i<concurrentNum;i++){
            Thread t=new Thread(()->{
//                try{
////                    System.out.println("并发读线程 "+Thread.currentThread().getName()+" take "+q.take());//这里q为空会阻塞
////                }catch (InterruptedException e){
////                    e.printStackTrace();
////                }
                ArrayList<Integer> tmp=new ArrayList<>();
                q.drainTo(tmp);//q为空不会阻塞
                System.out.println("并发读线程 "+Thread.currentThread().getName()+" drainTo "+tmp);
                cdl.countDown();
            });
            t.start();
        }
        try{
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
