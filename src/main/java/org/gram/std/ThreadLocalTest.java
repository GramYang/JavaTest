package org.gram.std;

public class ThreadLocalTest {
    //threadlocal使用
    public void tlt1(){
        Thread1 t1=new Thread1();
        Thread1 t2=new Thread1();
        t1.setName("A");
        t2.setName("B");
        t1.start();
        t2.start();
        //虽然没有加锁，但是输出正确
    }

    static class Thread1 extends Thread{
        private final ThreadLocal<Integer> threadLocal=new ThreadLocal<>();

        @Override
        public void run() {
            for(int i=0;i<3;i++){
                threadLocal.set(i);
                System.out.println(getName()+threadLocal.get());
            }
        }
    }
}
