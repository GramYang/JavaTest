package org.gram.concurrent_test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {
    //用法和go的waitgroup一样
    public void t1(){
        int num=10;
        CountDownLatch cdl=new CountDownLatch(num);
        for(int i=0;i<10;i++){
            int tmp = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(tmp +"thread start");
                    try{
                        Thread.sleep(2000);
                        System.out.println(tmp +"thread end");
                        cdl.countDown();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        try{
            cdl.await();
            if(!cdl.await(10, TimeUnit.SECONDS)){
                System.out.println("countdownlatch阻塞超时");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
