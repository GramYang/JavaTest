package org.gram.concurrent_test;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

public class CopyOnWriteArraySetTest {
    public void c1(){
        CopyOnWriteArraySet<Integer> set=new CopyOnWriteArraySet<>();
        int concurrentNum=11;
        CountDownLatch cdl=new CountDownLatch(concurrentNum);
        for(int i=0;i<concurrentNum;i++){
            int finalI = i;
            Thread t=new Thread(()->{
                set.add(finalI);
                cdl.countDown();
            });
            t.start();
        }
        try{
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(set);
    }
}
