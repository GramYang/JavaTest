package org.gram.concurrent_test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

//一个线程安全的arraylist，所有的修改操作都是由内存拷贝实现
public class CopyOnWriteArrayListTest {
    public void c1(){
        CopyOnWriteArrayList<Integer> list=new CopyOnWriteArrayList<>();
        int concurrentNum=11;
        CountDownLatch cdl=new CountDownLatch(concurrentNum);
        for(int i=0;i<concurrentNum;i++){
            int finalI = i;
            Thread t=new Thread(()->{
                list.add(finalI);
                cdl.countDown();
            });
            t.start();
        }
        try{
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(list);
    }
}
