package org.gram.concurrent_test;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

//ArrayBlockingQueue并发安全，先进先出，容量固定。
//add，成功返回true，无可用空间返回IllegalStateException。
//offer，成功返回true，无可用空间返回false。
//put，无返回，无空间时会阻塞
//take，无元素时阻塞
//remove、element，无元素抛NoSuchElementException
//peek，无元素返回null
public class ArrayBlockingQueueTest {
    public void a1(){
        ArrayBlockingQueue<Integer> q=new ArrayBlockingQueue<>(10);
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
