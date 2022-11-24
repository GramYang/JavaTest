package org.gram.std;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueTest {
    public void t1(){
        Queue<Integer> queue1=new PriorityQueue<>();//内置默认的比较器
        queue1.add(4);
        queue1.add(2);
        queue1.add(-4);
        System.out.println(queue1);//[-4, 4, 2]，这个排列看起来就像一个二叉搜索树
        while(!queue1.isEmpty()){
            System.out.print(queue1.poll()+" ");
        }
        //-4 2 4
        System.out.println();
        Queue<bag1> queue2=new PriorityQueue<>(Comparator.comparingInt(o -> o.a));//必须写上比较器
        queue2.add(new bag1(30,"a",LocalDate.now()));
        queue2.add(new bag1(14,"b",LocalDate.now()));
        queue2.add(new bag1(20,"c",LocalDate.now()));
        queue2.add(new bag1(50,"d",LocalDate.now()));
        queue2.add(new bag1(9,"e",LocalDate.now()));
        System.out.println(queue2);
        //[bag1{a=9, b='e', ld=2021-05-31}, bag1{a=14, b='b', ld=2021-05-31}, bag1{a=20, b='c', ld=2021-05-31}, bag1{a=50, b='d', ld=2021-05-31}, bag1{a=30, b='a', ld=2021-05-31}]
        while(!queue2.isEmpty()){
            System.out.print(queue2.poll()+" ");
        }
        //bag1{a=9, b='e', ld=2021-05-31} bag1{a=14, b='b', ld=2021-05-31} bag1{a=20, b='c', ld=2021-05-31} bag1{a=30, b='a', ld=2021-05-31} bag1{a=50, b='d', ld=2021-05-31}
    }

    static class bag1{
        int a;
        String b;
        LocalDate ld;

        bag1(int a,String b,LocalDate ld){
            this.a=a;
            this.b=b;
            this.ld=ld;
        }

        @Override
        public String toString() {
            return "bag1{" +
                    "a=" + a +
                    ", b='" + b + '\'' +
                    ", ld=" + ld +
                    '}';
        }
    }
}
