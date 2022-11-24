package org.gram.concurrent_test;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorTest {
    //只执行一次
    public void s1(){
        ScheduledExecutorService ses= Executors.newScheduledThreadPool(5);
        //只执行一次
//        ses.schedule(()->{
//            System.out.println("start "+new Date());
//            System.out.println("once!");
//            System.out.println("end "+new Date());
//        },1, TimeUnit.SECONDS);
        //循环执行
        ses.scheduleAtFixedRate(()->{
            System.out.println("start "+new Date());
            System.out.println("once!");
            System.out.println("end "+new Date());
        },1,2,TimeUnit.SECONDS);
        try{
            Thread.sleep(20000);
            System.out.println("关闭 "+new Date());
            ses.shutdown();
            boolean isClosed;
            do{
                isClosed=ses.awaitTermination(1,TimeUnit.DAYS);
                System.out.println("关闭中");
            }while (!isClosed);
            System.out.println("已关闭");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
