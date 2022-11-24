package org.gram.type;

import org.gram.type.interfaces.TestInterface1;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class ClassLoaderTest {
    //编译并加载java文件，只能通过反射来调用代码
    public void c1(){
        String name="code/Test.java";
        JavaCompiler javaCompiler= ToolProvider.getSystemJavaCompiler();
        try{
            //先编译后加载
            StandardJavaFileManager standardJavaFileManager=javaCompiler.getStandardFileManager(null,null,null);
            Iterable<? extends JavaFileObject> iterable=standardJavaFileManager.getJavaFileObjects(name);
            JavaCompiler.CompilationTask task = javaCompiler.getTask(null,standardJavaFileManager,null,null,null,iterable);
            task.call();
            standardJavaFileManager.close();
            ClassLoader cl=new ClassLoader1("code/");
            Class<?> c=cl.loadClass(".Test");
            Object o=c.newInstance();
            System.out.println(o);
            c.getMethod("t1").invoke(o);
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void c2(){
        String name="code/Test1.java";
        JavaCompiler javaCompiler= ToolProvider.getSystemJavaCompiler();
        try{
            //先编译后加载
            StandardJavaFileManager standardJavaFileManager=javaCompiler.getStandardFileManager(null,null,null);
            Iterable<? extends JavaFileObject> iterable=standardJavaFileManager.getJavaFileObjects(name);
            JavaCompiler.CompilationTask task = javaCompiler.getTask(null,standardJavaFileManager,null,null,null,iterable);
            task.call();
            standardJavaFileManager.close();
            ClassLoader cl=new ClassLoader1("code/");
            Class<? extends TestInterface1> c= (Class<? extends TestInterface1>) cl.loadClass(".Test1");
            TestInterface1 o=c.newInstance();
            System.out.println(o);
            c.getMethod("op1").invoke(o);
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    //自定义类加载器
    static class ClassLoader1 extends ClassLoader{
        private final String rootPath;
        ClassLoader1(String root){
            this.rootPath=root;
        }

        //不想打破双亲委派机制就重写findClass，否则重写整个loadClass
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            Class<?> c=findLoadedClass(name);//先去jvm里面寻找该类
            //找不到再去用类加载器加载类
            if(c==null){
                c=findClass1(name);
            }
            return c;
        }

        private Class<?> findClass1(String name){
            try{
                byte[] bs=getData(name);
                return this.defineClass(null,bs,0,bs.length);//字节数组转换为class实例
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        private byte[] getData(String name){
            String path=rootPath+ File.separatorChar+name.replace('.',File.separatorChar)+".class";
            InputStream is = null;
            try{
                is=new FileInputStream(path);
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                byte[] buffer=new byte[2048];
                int num;
                while((num=is.read(buffer))!=-1){
                    baos.write(buffer,0,num);
                }
                return baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(is!=null){
                    try{
                        is.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }
}
