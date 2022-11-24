package org.gram.concurrent_test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {
    public void t1(){
        int num=10;
        final int[] count = {0};
        CyclicBarrier cb=new CyclicBarrier(num);//在await调用数等于num后就会解除await阻塞，最好配合线程池使用因为线程池天生阻塞
        ExecutorService exec= Executors.newFixedThreadPool(num);
        for(int i=0;i<num;i++){
            exec.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());//这里会完全输出
                    count[0]++;//这里的数字差距很大，应该和线程池的线程调度有关
                    try{
                        cb.await();
                    }catch (InterruptedException | BrokenBarrierException e){
                        e.printStackTrace();
                    }
                }
            });
        }
        System.out.println(count[0]);
        exec.shutdown();
    }
}
