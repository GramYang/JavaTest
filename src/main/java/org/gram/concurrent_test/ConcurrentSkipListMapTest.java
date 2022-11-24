package org.gram.concurrent_test;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

//并发安全map，key是有序的，通过skiplist实现。ConcurrentSkipListMap是并发安全版的treemap，treemap则是用红黑树实现的。
//skiplist和redblacktree的对比：
//skiplist的复杂度和redblacktree相同但是实现更简单。
//并发环境下skiplist有优势
public class ConcurrentSkipListMapTest {
    public void c1(){
        ConcurrentSkipListMap<Integer,Integer> m=new ConcurrentSkipListMap<>();
        int concurrentNum=10;
        CountDownLatch cdl=new CountDownLatch(concurrentNum);
        for(int i=0;i<concurrentNum;i++){
            int tmp=i;
            Thread t=new Thread(()->{
                m.put(tmp,tmp);
                cdl.countDown();
            });
            t.start();
        }
        try{
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(m);
    }
}
