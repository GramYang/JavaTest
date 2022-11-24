package org.gram.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsTest {
    //泛型使用
    public void t1(){
        //泛型类
        GClass<Integer> gt1=new GClass<>(100);
        System.out.println(gt1.getKey());//100
        //泛型接口
        GInterface<String> gi1=new GClass<>("a");
        System.out.println(gi1.next());//a
        //泛型方法
        System.out.println(gOpt1(gt1));//100
        //泛型通配符？
        //不能写不能读只能用Object读
        System.out.println(gOpt2(gt1));//100
        GClass<String> gt2=new GClass<>("b");
        System.out.println(gOpt2(gt2));//b
        ArrayList<String> stringList=new ArrayList<>();
        stringList.add("a");
        ArrayList<?> list=stringList;
//        list.add("b");//报错，禁止写入
//        String s=list.get(0);//报错，不能读
        Object o=list.get(0);
        System.out.println((String)o);//a
    }

    static class GClass<T> implements GInterface<T>{
        private T key;

        GClass(T t){
            this.key=t;
        }

        public T getKey(){
            return key;
        }

        @Override
        public T next() {
            return key;
        }
    }

    interface GInterface<T>{
        public T next();
    }

    //泛型方法返回泛型值
    private <U> U gOpt1(GClass<U> gc){
        return gc.getKey();
    }

    private Object gOpt2(GClass<?> gc){
        return gc.getKey();
    }


    //泛型数组
    public void t2(){
        //型变
        ArrayList<Son> sonList1=new ArrayList<>();
        sonList1.add(new Son(1,2));
//        ArrayList<Father> fatherList1=sonList1;//报错，如果不报错你就可以在sonList1中加入Son1的实例了

        ArrayList<Father> fatherList0=new ArrayList<>();
        fatherList0.add(new Father(1));

        //协变，只能读不能写（任何类型都不行）
        ArrayList<? extends Father> fatherList01=fatherList0;//不报错
        ArrayList<? extends Father> fatherList1=sonList1;//不报错
//        fatherList1.add(new Son(1,2));//报错，不能写入
//        fatherList01.add(new Father(3));//报错，不能写入
        //这里就可以看出来为什么协变不能写入了，上面的Father和Son集合都可以作为<? extends Father>约束，
        //但是Father是可以存Son1的，如果写入那就可能是Son1和Son都在<? extends Father>里，那就错了
        //总结：协变禁止写入的原因，extends的父类可能有多个子类
        Father f=fatherList1.get(0);//可行
        System.out.println(((Son)f).b);//2
//        Son s=fatherList1.get(0);//报错
        ArrayList<? extends Son> sonList2=sonList1;//不报错
//        sonList2.add(new Son(3,4));//报错，不能写入
        Son s1=sonList2.get(0);
        Father f1=sonList2.get(0);
        System.out.println(((Son)f1).b);//2
        System.out.println(s1.b);//2

        //逆变，可以写不能读（只能用Object读）
        ArrayList<Father> fatherList2=new ArrayList<>();
        fatherList2.add(new Father(1));
        fatherList2.add(new Son(2,3));
        fatherList2.add(new Son1(4,5));
        //上面的添加都没有问题
        ArrayList<? super Father> fatherList3=fatherList2;//不报错，满足逆变条件
//        ArrayList<? super Father> fatherList31=sonList1;//报错
        ArrayList<? super Father> fatherList4=new ArrayList<>();
        fatherList4.add(new Father(1));
        fatherList4.add(new Son(2,3));
        fatherList4.add(new Son1(4,5));
        //上面的添加都没有问题
//        Son s2=fatherList4.get(0);//报错
//        Father f2=fatherList4.get(0);//报错
        Object o=fatherList4.get(0);
        //可以看出，逆变和协变相反，允许父类的多个子类存入同一个集合。
        //但是子类和子类之间不能转换没有关系，因此类型是不定的，因此不能读。
        //只能用Object读出来，在你知道是Son还是Son1时强行转换
    }

    static class Father{
        int a;

        public Father(int a) {
            this.a = a;
        }
    }

    static class Son extends Father{
        int b;

        public Son(int a, int b) {
            super(a);
            this.b = b;
        }
    }

    static class Son1 extends Father{
        int c;

        public Son1(int a, int c) {
            super(a);
            this.c = c;
        }
    }

}
