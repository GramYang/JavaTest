package org.gram.std;

public class ThreadTest {
    public void joinTest(){
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        });
        t1.start();
        try{
            t1.join();//阻塞主线程等待t1结束
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void yieldTest(){
        YieldThread t1=new YieldThread("A");
        YieldThread t2=new YieldThread("B");
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    static class YieldThread extends Thread{
        YieldThread(String n){
            super(n);
        }

        @Override
        public void run() {
            for(int i=0;i<5;i++){
                System.out.println(Thread.currentThread().getName()+"----"+i);
                if(i==3){
                    Thread.yield();//这是静态方法，会让出线程cpu时间让同等级的其他线程抢占
                }
            }
        }
    }
}
