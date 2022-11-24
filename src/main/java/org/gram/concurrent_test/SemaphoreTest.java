package org.gram.concurrent_test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public void t1(){
        int num=10;
        final int[] count = {0};
        Semaphore semaphore=new Semaphore(1);//1就和synchronized一样了
        CountDownLatch cdl=new CountDownLatch(num);
        for(int i=0;i<num;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                   try{
                       semaphore.acquire();
                    for(int i=0;i<1000;i++){
                        count[0] ++;
                    }
                   }catch (InterruptedException e){
                       e.printStackTrace();
                   }finally {
                       semaphore.release();
                   }
                   cdl.countDown();
                }
            }).start();
        }
        try{
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println(count[0]);
    }

}
