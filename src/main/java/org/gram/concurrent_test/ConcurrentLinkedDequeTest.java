package org.gram.concurrent_test;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;

//无边界并发安全链表deque
//add，插入尾部。成功返回true永不返回false，元素为空报NullPointerException。
//element，获取头元素。deque为空则抛NoSuchElementException。
//getFirst、getLast，deque为空则抛NoSuchElementException。
//取值时是要deque为空就会抛异常，没有阻塞接口。
public class ConcurrentLinkedDequeTest {
    public void c1(){
        ConcurrentLinkedDeque<Integer> q=new ConcurrentLinkedDeque<>();
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
