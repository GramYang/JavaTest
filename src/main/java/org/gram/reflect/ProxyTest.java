package org.gram.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

    //动态代理测试
    public void p1(){
        B b1=new B("a");
        A a=(A) Proxy.newProxyInstance(B.class.getClassLoader(),B.class.getInterfaces(),new C(b1));
        a.op1();//B op1 a
        b1.s="b";
        a.op1();//B op1 b，动态代理没有实现深拷贝
    }

    interface A{
        void op1();
    }

    static class B implements A{
        String s;

        B(String s){
            this.s=s;
        }
        @Override
        public void op1() {
            System.out.println("B op1 "+s);
        }
    }

    static class C implements InvocationHandler{
        private final Object o;
        C(Object object){
            o=object;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("this is invoke of class C");
            return method.invoke(o,args);
        }
    }
}
