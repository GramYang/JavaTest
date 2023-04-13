package org.gram.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

//        modifiers的含义列表如下：
//        PUBLIC: 1
//        PRIVATE: 2
//        PROTECTED: 4
//        STATIC: 8
//        FINAL: 16
//        SYNCHRONIZED: 32
//        VOLATILE: 64
//        TRANSIENT: 128
//        NATIVE: 256
//        INTERFACE: 512
//        ABSTRACT: 1024
//        STRICT: 2048
public class ReflectionTest {
    static interface BagInterface {
    }

    static class BagFather {
        private String attr1="bagfather attr1";
        private String attr5="bagfather attr5";
    }

    static class Bag extends BagFather implements BagInterface {
        private String attr1="private attr";
        protected String attr2="protected attr";
        public String attr3="public attr";
        static String attr4="static attr";

        public Bag(){}

        private Bag(String i){this.attr1=i;}

        private void op1(){
            System.out.println("private func");
        }

        protected void op2(){
            System.out.println("protected func");
        }

        public void op3(){
            System.out.println("public func");
        }

        static void op4(){
            System.out.println("static func");
        }
    }


    //反射获取域、方法、构造器
    public void t1(){
        //获取实例class对象方法测试
        Bag b1=new Bag();
        Class<? extends Bag> bc1=b1.getClass();
        Class<Bag> bc2=Bag.class;
        Class<? extends Bag> bc3=Bag.class;
        try{
            //内部类用$
            Class<?> bc4= Class.forName("org.example.reflect.ReflectionTest$Bag");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        //class获取属性集合
        System.out.println(bc1.getName());//org.example.reflect.Bag
        Field[] fields=bc1.getFields();//这里只能获取public的域
        for(Field f:fields){
            System.out.printf("%s %d %s\n",f.getName(),f.getModifiers(),f.toGenericString());//attr3 1 public java.lang.String org.example.reflect.Bag.attr3
        }
        Field[] fields1=bc1.getDeclaredFields();//获取所有的域
        for(Field f:fields1){
            System.out.printf("%s %d ",f.getName(),f.getModifiers());//attr1 2 attr2 4 attr3 1 attr4 8
        }
        System.out.println();
        //class获取指定属性
        try{
            Field f1=bc1.getField("attr3");//只能获取public域，其他类型都不行
            System.out.println(f1);//public java.lang.String org.example.reflect.Bag.attr3
            System.out.println((String)f1.get(b1));//public attr
            Field f2=bc1.getDeclaredField("attr1");//所有类型的域都能获取
            System.out.println(f2);//private java.lang.String org.example.reflect.Bag.attr1
            f2.setAccessible(true);//必须设置成true，获取private值才不会报IllegalAccessException
            System.out.println((String)f2.get(b1));//private attr
        }catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
        //class获取方法集合
        Method[] methods=bc1.getMethods();//获取所有public方法，包括object方法
        for(Method m:methods){
            System.out.printf("%s %d ",m.getName(),m.getModifiers());//op3 1 wait 17 wait 17 wait 273 equals 1 toString 1 hashCode 257 getClass 273 notify 273 notifyAll 273
        }
        System.out.println();
        Method[] methods1=bc1.getDeclaredMethods();//获取所有方法，但是不包括object方法
        for(Method m:methods1){
            System.out.printf("%s %d ",m.getName(),m.getModifiers());//op1 2 op2 4 op3 1 op4 8
        }
        System.out.println();
        //class获取指定方法
        try{
            Method m1=bc1.getMethod("op3");//只能获取public方法，其他都不行
            System.out.println(m1);//public void org.example.reflect.Bag.op3()
            Method m2=bc1.getDeclaredMethod("op1");//所有类型都能获取
            System.out.println(m2);//private void org.example.reflect.Bag.op1()
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }
        //获取构造器
        Constructor<?>[] constructors=bc1.getConstructors();//只能获取public构造器
        for(Constructor<?> c:constructors){
            System.out.println(c);//public org.example.reflect.Bag()
        }
        Constructor<?>[] constructors1=bc1.getDeclaredConstructors();//获取所有构造器
        for(Constructor<?> c:constructors1){
            System.out.println(c);
        }
//        public org.example.reflect.Bag()
//        private org.example.reflect.Bag(java.lang.String)
    }

    //反射获取父类属性，不能获取子类属性（当然）
    public void t2(){
        Class<Bag> c1=Bag.class;
        Class<? super Bag> c2=c1.getSuperclass();
        Field[] fs=c2.getDeclaredFields();
        for(Field f:fs){
            System.out.println(f);
        }
    }

    //反射调用方法
    public void t3(){
        Bag b1=new Bag();
        Class<Bag> c1=Bag.class;
        try{
            Method m1=c1.getMethod("op3");
            m1.invoke(b1);//public func
        }catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    static class BigBag{}
    static class bag1 extends BigBag implements BagInterface{
        private int v1;
        private String v2;
        bag1(int a,String b){
            v1=a;
            v2=b;
        }
        bag1(){
            v1=1;
            v2="abc";
        }

        @Override
        public String toString() {
            return "bag1{" +
                    "v1=" + v1 +
                    ", v2='" + v2 + '\'' +
                    '}';
        }
    }

    static class bag2 extends BigBag implements BagInterface{
        private int v3=2;
        private String v4="def";

        @Override
        public String toString() {
            return "bag2{" +
                    "v3=" + v3 +
                    ", v4='" + v4 + '\'' +
                    '}';
        }
    }

    //由类文件生成类实例，容器写法+参数传递
    public void t4(){
        HashMap<Integer,Class<?>> beanMap=new HashMap<Integer,Class<?>>(){
            {
                put(1,bag1.class);
                put(2,bag2.class);
            }
        };
        System.out.println(NewBean1(beanMap.get(1)));
        System.out.println(NewBean1(beanMap.get(2)));
        NewBean2(beanMap.get(1));
        NewBean2(beanMap.get(2));
        //下面三种都输出正常
        Pass1(new bag1());
        Pass1(new bag2());
        Pass2(new bag1());
        Pass2(new bag2());
        Pass3(new bag1());
        Pass3(new bag2());
    }

    //class生成实例处理，泛型
    private <T> T NewBean1(Class<T> cls){
        T t;
        try{
           t=cls.newInstance();
           return t;
        }catch (IllegalAccessException |  InstantiationException e){
            e.printStackTrace();
        }
        return null;
    }

    //class生成实例处理，Object
    private void NewBean2(Class<?> c){
        try{
            Object o=c.newInstance();
            if(o instanceof bag1){
                System.out.println("this is bag1");
            }else if(o instanceof bag2){
                System.out.println("this is bag2");
            }
        }catch (IllegalAccessException |  InstantiationException e){
            e.printStackTrace();
        }
    }

    //直接传递class，Object

    //测试Object实例的class获取
    private void Pass1(Object o){
        Class<?> c=o.getClass();
        if(c==bag1.class){
            System.out.println("this is bag1");
        }else if(c==bag2.class){
            System.out.println("this is bag2");
        }
    }

    //测试父类实例的class获取
    private void Pass2(BigBag bb){
        Class<?> c=bb.getClass();
        if(c==bag1.class){
            System.out.println("this is bag1");
        }else if(c==bag2.class){
            System.out.println("this is bag2");
        }
    }

    //测试接口实例的class获取
    private void Pass3(BagInterface bf){
        Class<?> c=bf.getClass();
        if(c==bag1.class){
            System.out.println("this is bag1");
        }else if(c==bag2.class){
            System.out.println("this is bag2");
        }
    }

    //反射生成内部类实例，非静态内部类实例化需要传入外部类实例
    public void t5(){
        try{
            Class<?> innerClass=Class.forName("org.gram.reflect.ReflectionTest$Outer$Inner1");
            Class<?> outerClass=innerClass.getEnclosingClass();
            Object o=innerClass.getDeclaredConstructors()[0].newInstance(outerClass.newInstance());
            System.out.println(o);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    static class Outer{
        class Inner1{}
    }
}
