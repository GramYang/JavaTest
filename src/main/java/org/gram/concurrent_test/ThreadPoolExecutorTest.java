package org.gram.concurrent_test;

import java.util.concurrent.*;

public class ThreadPoolExecutorTest {
    public void t1(){
        int num=5;
        ExecutorService exec= Executors.newFixedThreadPool(num);
        System.out.println(Thread.currentThread().getName());

        //执行简单的runnable
        Future<?> f1=exec.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"执行runnable");
            }
        });
        try{
            System.out.println(f1.get());//null，因为runnable不返回结果
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        //执行callable
        Future<?> f2=exec.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                return 10;
            }
        });
        try{
            System.out.println(f2.get());//10
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        //这个接口很奇怪，返回的值是个假的
        Integer res=10;
        Future<?> f3=exec.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        },res);
        try{
            System.out.println(f3.get());//10
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        exec.shutdown();//线程池必须手动关闭
        try{
            System.out.println(exec.awaitTermination(5, TimeUnit.SECONDS));//true，因为线程池关闭了
        }catch (InterruptedException e){
            e.printStackTrace();
        }
//        exec.shutdownNow();//直接关闭，会返回未开始执行的任务
    }
}
