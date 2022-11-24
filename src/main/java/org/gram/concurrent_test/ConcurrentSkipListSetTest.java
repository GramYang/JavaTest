package org.gram.concurrent_test;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;

//可伸缩并发安全set，其中的元素被排序
//取值方法在set为空时返回null，不会阻塞
public class ConcurrentSkipListSetTest {
    public void c1(){
        ConcurrentSkipListSet<Integer> s=new ConcurrentSkipListSet<>();
        int concurrentNum=10;
        CountDownLatch cdl=new CountDownLatch(concurrentNum);
        for(int i=0;i<concurrentNum;i++){
            int tmp=i;
            Thread t=new Thread(()->{
                s.add(tmp);
                cdl.countDown();
            });
            t.start();
        }
        try{
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(s);
    }
}
