package org.gram.type;

import java.io.Serializable;

public class ClassTest {
    //类型判断instanceof
    public void t1(){
        Bag bag=new Bag();
        System.out.println(bag instanceof BagFather1);//true
        System.out.println(bag instanceof BagFather);//true
    }

    static class Bag extends BagFather implements BagFather1{}

    static class BagFather{}

    interface BagFather1{}

    //默认值测试
    public void t2(){
        A1 a1=new A1();
        System.out.println(a1);//a: 0, b: null, c: false, d: null, e: null
        //可以看出，基本类型是默认值，装箱类是null
        System.out.println(a1.b);//null
        System.out.printf("%s\n",a1.b);//null
    }

    static class A1{
        int a;
        String b;
        boolean c;
        Integer d;
        Boolean e;

        @Override
        public String toString(){
            return String.format("a: %s, b: %s, c: %s, d: %s, e: %s",a,b,c,d,e);
        }
    }

    static abstract class Human{}

    static class Man extends Human{}

    static class Woman extends Human{}

    //只能匹配到这个方法，即只能使用静态类型
    void op1(Human h){
        System.out.println("this is Human");
    }

    void op1(Man m){
        System.out.println("this is Man");
    }

    void op1(Woman w){
        System.out.println("this is Woman");
    }

    void op2(Object o){
        System.out.println("this is Object");
    }

    void op2(int i){
        System.out.println("this is int");
    }

    void op2(long i){
        System.out.println("this is long");
    }

    void op2(Character i){
        System.out.println("this is Character");
    }

    void op2(char i){
        System.out.println("this is char");
    }

    void op2(char... i){
        System.out.println("this is char...");
    }

    void op2(Serializable i){
        System.out.println("this is Serializable");
    }

    //静态分派+重载测试
    public void t3(){
        Human m1=new Man();
        Human m2=new Woman();
        op1(m1);
        op1(m2);
        op2('a');
        //顺序：char-int-long-Character-Serializer-Object-char...
        //首先基本类型自动转换，顺序为char-int-long-float-double
        //然后装箱为Character，向上转换为Object，最后才是char[]
    }

    static class Father{
        int money=1;
        Father(){
            money=2;
            op3();
        }
        void op3(){
            System.out.println("Father has "+money);
        }
    }

    static class Son extends Father{
        int money=3;
        Son(){
            money=4;
            op3();
        }
        void op3(){
            System.out.println("Son has "+money);
        }
    }

    //动态分派+重写测试
    public void t4(){
        Father f=new Son();
        System.out.println("f has "+f.money);
//        Son has 0，Son构造器隐式调用Father构造器，其中Father的op3重写为Son的op3，此时money还未初始化
//        Son has 4，Son构造器中op3，此时money已经初始化
//        f has 2，这里访问的是Father的money域，f的静态类型是Father
    }

    static class _QQ{}
    static class _360{}

    static class Father1{
        void op4(_QQ o){
            System.out.println("father1 op4 qq");
        }

        void op4(_360 o){
            System.out.println("father1 op4 360");
        }
    }

    static class Son1 extends Father1{
        void op4(_QQ o){
            System.out.println("Son1 op4 qq");
        }

        void op4(_360 o){
            System.out.println("Son1 op4 360");
        }
    }

    //单分派+多分派测试
    //为什么java的动态分派是单分派？感觉JVM这本书上并没有讲清楚。
    public void t5(){
        Father1 f1=new Father1();
        Father1 f2=new Son1();
        f1.op4(new _360());//father1 op4 360，静态分派根据两个宗量（实例类型，参数类型）来选择，因此java的静态分派是多分派类型
        f2.op4(new _QQ());//Son1 op4 qq，动态分派只有一个宗量（实例类型）来选择，因此java的动态分派属于单分派类型
    }

    static class Fruit{}
    static class Apple extends Fruit{}
    static class Banana extends Fruit{}
    static class People{
        void eat(Fruit f){
            System.out.println("people eat Fruit");
        }
        void eat(Apple a){
            System.out.println("people eat Apple");
        }
        void eat(Banana b){
            System.out.println("people eat Banana");
        }
    }
    static class Boy extends People{
        void eat(Fruit f){
            System.out.println("boy eat Fruit");
        }
        void eat(Apple f){
            System.out.println("boy eat Apple");
        }
        void eat(Banana f){
            System.out.println("boy eat Banana");
        }
    }
    //java的动态分派是单分派的测试
    //https://www.zhihu.com/question/28462483/answer/40919696
    public void t6(){
        People p1=new Boy();
        Fruit f1=new Apple();
        Fruit f2=new Banana();
        p1.eat(f1);//boy eat Fruit，这里并没有考察参数的动态类型，说明是单分派
        p1.eat(f2);//boy eat Fruit
    }
}
