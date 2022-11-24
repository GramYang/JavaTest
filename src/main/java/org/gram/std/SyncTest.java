package org.gram.std;

public class SyncTest {

    //同步代码块测试
    public void st1(){
        syncBlock sb=new syncBlock();
        for(int i=0;i<3;i++){
            Thread t=new Thread(sb,"thread"+i);
            t.start();
        }
    }
    static class syncBlock implements Runnable{
        private int counter=1;

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis());//同步代码块外部是并行输出的
            synchronized (this){//同步代码块内部是串联输出的
                for(int i=0;i<5;i++){
                    counter++;
                    System.out.println(Thread.currentThread().getName()+counter);
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //同步方法测试，全部串联输出
    public void st2(){
        syncBlock1 sb1=new syncBlock1();
        for(int i=0;i<3;i++){
            Thread t=new Thread(sb1,"thread"+i);
            t.start();
        }
    }

    static class syncBlock1 implements Runnable{
        private int counter=1;

        @Override
        public synchronized void run() {
            System.out.println(System.currentTimeMillis());

            synchronized (this){//同步代码块内部是串联输出的
                for(int i=0;i<5;i++){
                    counter++;
                    System.out.println(Thread.currentThread().getName()+counter);
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //静态同步方法测试，效果同同步方法
    public void st3(){
        syncBlock2 sb2=new syncBlock2();
        for(int i=0;i<3;i++){
            Thread t=new Thread(sb2,"thread"+i);
            t.start();
        }
    }

    static class syncBlock2 implements Runnable{
        private static int counter=1;

        private synchronized static void run1() {
            System.out.println(System.currentTimeMillis());
            for(int i=0;i<5;i++){
                counter++;
                System.out.println(Thread.currentThread().getName()+counter);
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            run1();
        }
    }

    static class syncBlock3 implements Runnable{
        private static int counter=1;

        private synchronized static void run1() {
            for(int i=0;i<5;i++){
                counter++;
                System.out.println(Thread.currentThread().getName()+counter);
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                run2();
            }
        }

        private synchronized static void run2(){
            counter++;
        }

        @Override
        public void run() {
            run1();
        }
    }


    //synchronized的可重入性和中断性测试: 是可重入且不可中断的。run1可以调用run2表示其可重入，在synchronized释放锁之前其他线程不能获得锁
    public void st4(){
        syncBlock3 sb3=new syncBlock3();
        for(int i=0;i<3;i++){
            Thread t=new Thread(sb3,"thread"+i);
            t.start();
        }
    }
}
