package org.gram.java8;

public class LambdaTest {
    //定义lambda表达式，这个@FunctionalInterface不加也可以
    @FunctionalInterface
    private interface Function1{
        void say(String s1,String s2,String s3,String s4);
    }

    //自定义lambda回调，仍然要你定义接口。至于那些没有定义接口的lambda回调，都是function包里面提前定义好的接口，都是1到2个参数。
    public void l1(){
        op1((s1,s2,s3,s4)->System.out.println(s1+s2+s3+s4),"a","b","c","d");
    }

    private void op1(Function1 f,String s1,String s2,String s3,String s4){
        f.say(s1,s2,s3,s4);
    }
}
