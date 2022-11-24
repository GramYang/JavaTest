package org.gram.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortTest {
    //Comparable测试
    public void t1(){
        ArrayList<bag1> arrayList=new ArrayList<>();
        arrayList.add(new bag1("孙笑川",514));
        arrayList.add(new bag1("蔡徐坤",114));
        arrayList.add(new bag1("药水哥",810));
        System.out.println(arrayList);
        Collections.sort(arrayList);
        System.out.println(arrayList);
    }

    //Comparator测试
    public void t2(){
        ArrayList<bag1> arrayList=new ArrayList<>();
        arrayList.add(new bag1("孙笑川",514));
        arrayList.add(new bag1("蔡徐坤",114));
        arrayList.add(new bag1("药水哥",810));
        System.out.println(arrayList);
        arrayList.sort(Comparator.comparingInt(o -> o.b));
        System.out.println(arrayList);
    }

    static class bag1 implements Comparable<bag1>{
        String a;
        int b;
        bag1(String a,int b){
            this.a=a;
            this.b=b;
        }

        @Override
        public int compareTo(bag1 o) {
            return this.b-o.b;
        }

        @Override
        public String toString() {
            return "bag1{" +
                    "a='" + a + '\'' +
                    ", b=" + b +
                    '}';
        }
    }
}
