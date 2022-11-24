package org.gram.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://github.com/FasterXML/jackson-databind/
 */
public class JacksonTest {

    static class MyValue{
        public String name;
        public int age;

        MyValue(){}

        MyValue(String n,int a){
            this.name=n;
            this.age=a;
        }

        @Override
        public String toString() {
            return "MyValue{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public void t1(){
        ObjectMapper om=new ObjectMapper();
        MyValue v1=new MyValue("a",1);
        byte[] bs;
        MyValue v2;
        try{
            //对象
            bs=om.writeValueAsBytes(v1);
            System.out.println(Arrays.toString(bs));
            v2=om.readValue(bs,MyValue.class);
            System.out.println(v2.toString());
            Map<String,Object> m1=new HashMap<>();
            m1.put("a",v1);
            m1.put("b",v2);
            //map
            bs=om.writeValueAsBytes(m1);
            Map<String,Object> m2=om.readValue(bs,Map.class);
            System.out.println(m2);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
