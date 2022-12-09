package org.gram.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

    //动态代理测试
//    动态代理对比静态代理：
//    静态代理和代理对象必须实现同一个接口，动态代理不需要；动态代理是在运行时创建的。
//    动态代理原理：
//    运行时动态生成代理类以class形式，然后用类加载器加载编译（类加载器加载的是byte[]，所以class类并不一定需要存在磁盘）
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
