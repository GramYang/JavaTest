package org.gram.gson;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class GsonTest {
    static class bag1{
        private int v1=1;
        private String v2="abc";
        private transient int v3=3;
        bag1(){}

        @Override
        public String toString() {
            return "bag1{" +
                    "v1=" + v1 +
                    ", v2='" + v2 + '\'' +
                    ", v3=" + v3 +
                    '}';
        }
    }

    static class bag2{
        private Integer v1=1;
        private String v2="abc";
        private innerBag2 ib2=new innerBag2();
        bag2(){}
        static class innerBag2{
            private Integer v1=3;
            private String v3="def";
            innerBag2(){}
        }

        @Override
        public String toString() {
            return "bag2{" +
                    "v1=" + v1 +
                    ", v2='" + v2 + '\'' +
                    ", ib2.v1=" + ib2.v1 +
                    ", ib2.v3=" + ib2.v3 +
                    '}';
        }
    }

    static class bag3{
        Integer v1;
        String v2;
        bag3(int a,String b){
            v1=a;
            v2=b;
        }

        @Override
        public String toString() {
            return "bag3{" +
                    "v1=" + v1 +
                    ", v2='" + v2 + '\'' +
                    '}';
        }
    }

    static class bag4{
        int v1=10;
        ArrayList<bag5> v2=new ArrayList<bag5>(){
            {add(new bag5());}
        };
    }

    static class bag5{
        Integer v1=100;
        String v2="200";

        @Override
        public String toString() {
            return "bag5{" +
                    "v1=" + v1 +
                    ", v2='" + v2 + '\'' +
                    '}';
        }
    }

    //基本使用
    //注意：go的json如果序列化的bean的域和json对不上，则对不上的域只会是默认值。但是gson不同，gson会报空指针！
    public void t1(){
        //基本类型
        Gson gson = new Gson();
        String s1=gson.toJson(1);            // ==> 1
        String s2=gson.toJson("abcd");       // ==> "abcd"
        String s3=gson.toJson(new Long(10)); // ==> 10
        int[] values = { 1 };
        String s4=gson.toJson(values);       // ==> [1]
        int s5 = gson.fromJson("1", int.class);
        Integer s6 = gson.fromJson("1", Integer.class);
        Long s7 = gson.fromJson("1", Long.class);
        Boolean s8 = gson.fromJson("false", Boolean.class);
        String s9 = gson.fromJson("\"abc\"", String.class);
        String[] s10 = gson.fromJson("[\"abc\"]", String[].class);
        System.out.printf("%s %s %s %s %d %d %d %b %s %s\n",s1,s2,s3,s4,s5,s6,s7,s8,s9,Arrays.toString(s10));
        //bean
        bag1 b1=new bag1();
        String b1s=gson.toJson(b1);
        System.out.println(b1s);
        //{"v1":1,"v2":"abc"}
        bag1 b11=gson.fromJson(b1s,bag1.class);
        System.out.println(b11);
        //bag1{v1=1, v2='abc', v3=3}
        //静态内嵌bean
        bag2 b2=new bag2();
        String b2s=gson.toJson(b2);
        System.out.println(b2s);
        //{"v1":1,"v2":"abc","ib2":{"v1":3,"v3":"def"}}
        bag2 b22=gson.fromJson(b2s,bag2.class);
        System.out.println(b22);
        //{"v1":1,"v2":"abc","ib2":{"v1":3,"v3":"def"}}
        //go风格的bean，我懒得写那么多get、set了
        bag3 b3=new bag3(10,"abc");
        String b3s=gson.toJson(b3);
        System.out.println(b3s);
        //{"v1":10,"v2":"abc"}
        bag3 b33=gson.fromJson(b3s,bag3.class);
        System.out.println(b33);
        //bag3{v1=10, v2='abc'}
        //ArrayList之类的泛型容器
        ArrayList<String> list=new ArrayList<>();
        list.add("123");
        list.add("456");
        String listString=gson.toJson(list);
        System.out.println(listString);
        //["123","456"]
        Type arraylistType=new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> list1=gson.fromJson(listString,arraylistType);
        System.out.println(list1);
        //[123, 456]
        //内嵌容器类
        bag4 b4=new bag4();
        String b4s=gson.toJson(b4);
        System.out.println(b4s);
        //{"v1":10,"v2":[{"v1":100,"v2":"200"}]}
        bag4 b44=gson.fromJson(b4s,bag4.class);
        System.out.println(b44.v2.get(0).toString());
        //bag5{v1=100, v2='200'}
    }

    static class Foo {
        private final String s;
        private final int i;

        public Foo() {
            this(null, 5);
        }

        public Foo(String s, int i) {
            this.s = s;
            this.i = i;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface GsonInterface1 {}

    static class SampleObjectForTest {
        @GsonInterface1 private final int annotatedField;
        private final String stringField;
        private final long longField;

        public SampleObjectForTest() {
            annotatedField = 5;
            stringField = "someDefaultValue";
            longField = 1234;
        }
    }

    static class MyExclusionStrategy implements ExclusionStrategy {
        private final Class<?> typeToSkip;

        private MyExclusionStrategy(Class<?> typeToSkip) {
            this.typeToSkip = typeToSkip;
        }

        //指定跳过的类型，这里是String
        public boolean shouldSkipClass(Class<?> clazz) {
            return (clazz == typeToSkip);
        }

        //指定跳过的域，这里是有GsonInterface1注解的
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(GsonInterface1.class) != null;
        }
    }

    static class SomeObject {
        //重命名
        @SerializedName("custom_naming") private final String someField;
        private final String someOtherField;

        public SomeObject(String a, String b) {
            this.someField = a;
            this.someOtherField = b;
        }
    }

    static class Point{
        private int x;
        private int y;
        Point(int a,int b){
            x=a;
            y=b;
        }
    }
    //复杂用法
    public void t2(){
        //bean的域可为空
        Gson gson = new GsonBuilder().serializeNulls().create();
        Foo foo = new Foo();
        String json = gson.toJson(foo);
        System.out.println(json);
        json = gson.toJson(null);
        System.out.println(json);
        //不序列化指定关键字修饰的域
        Gson gson1 = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .create();
        //自定义注释修饰跳过序列化的域
        Gson gson2 = new GsonBuilder()
                .setExclusionStrategies(new MyExclusionStrategy(String.class))
                .serializeNulls()
                .create();
        SampleObjectForTest src = new SampleObjectForTest();
        String srcJson = gson2.toJson(src);
        System.out.println(srcJson);
        //{"longField":1234}
        //指定序列化域名规则
        SomeObject someObject = new SomeObject("first", "second");
        Gson gson3 = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        //这里还指定了驼峰格式
        String jsonRepresentation = gson3.toJson(someObject);
        System.out.println(jsonRepresentation);
        //{"custom_naming":"first","SomeOtherField":"second"}

        //复杂的mapkey序列化，enableComplexMapKeySerialization这个功能太复杂了别用
    }

    static class BigBox{
        int Type;

        @Override
        public String toString() {
            return "BigBox{" +
                    "Type=" + Type +
                    '}';
        }
    }

    static class box1 extends BigBox{
        int v1;
        boolean v2;
        String v3;

        public box1(int v1, boolean v2, String v3,int type) {
            super.Type=type;
            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
        }

        @Override
        public String toString() {
            return "box1{" +
                    "v1=" + v1 +
                    ", v2=" + v2 +
                    ", v3='" + v3 + '\'' +
                    '}';
        }
    }

    static class box2 extends BigBox{
        int v1;
        boolean v2;

        public box2(int v1, boolean v2,int type) {
            super.Type=type;
            this.v1 = v1;
            this.v2 = v2;
        }

        @Override
        public String toString() {
            return "box2{" +
                    "v1=" + v1 +
                    ", v2=" + v2 +
                    '}';
        }
    }

    static class box3 extends BigBox{
        int v1;
        boolean v2;

        public box3(int v1, boolean v2,int type) {
            super.Type=type;
            this.v1 = v1;
            this.v2 = v2;
        }

        @Override
        public String toString() {
            return "box2{" +
                    "v1=" + v1 +
                    ", v2=" + v2 +
                    '}';
        }
    }

    static class boxSet{
        ArrayList<BigBox> set;

        public boxSet(ArrayList<BigBox> set) {
            this.set = set;
        }
    }

    static class BoxDeserializer implements JsonDeserializer<boxSet>{
        @Override
        public boxSet deserialize(JsonElement jsonElement, Type type,
                                  JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject obj=jsonElement.getAsJsonObject();
            JsonArray jsonArray=obj.get("set").getAsJsonArray();
            ArrayList<BigBox> list=new ArrayList<>();
            for(JsonElement je:jsonArray){
                JsonObject jo=je.getAsJsonObject();
                switch (jo.get("Type").getAsInt()){
                    case 10:
                        list.add(new box1(jo.get("v1").getAsInt(),jo.get("v2").getAsBoolean(),jo.get("v3").getAsString(),10));
                        break;
                    case 20:
                        list.add(new box2(jo.get("v1").getAsInt(),jo.get("v2").getAsBoolean(),20));
                        break;
                    case 30:
                        list.add(new box3(jo.get("v1").getAsInt(),jo.get("v2").getAsBoolean(),30));
                    break;
                }
            }
            return new boxSet(list);
        }
    }

    //容器内的动态类型
    public void t3(){
        Gson gson=new GsonBuilder().registerTypeAdapter(boxSet.class,new BoxDeserializer()).create();
        ArrayList<BigBox> list=new ArrayList<>();
        list.add(new box1(1,true,"a",10));
        list.add(new box2(2,false,20));
        list.add(new box3(3,true,30));
        boxSet bs=new boxSet(list);
        String bsJson=gson.toJson(bs);
        System.out.println(bsJson);
        //{"set":[{"v1":1,"v2":true,"v3":"a","Type":10},{"v1":2,"v2":false,"Type":20},{"v1":3,"v2":true,"Type":30}]}
        boxSet bs1=gson.fromJson(bsJson,boxSet.class);
        for (BigBox bb:bs.set){
            System.out.println(bb);
        }
//        box1{v1=1, v2=true, v3='a'}
//        box2{v1=2, v2=false}
//        box2{v1=3, v2=true}
        //这种写法虽然精准，但是太太太他妈复杂了，如果动态类的种类和域多一点，我等于说全部都得写一遍！！
        //测试一种偷懒的写法
        ArrayList<Object> list1=new ArrayList<>();
        list1.add(new box1(1,true,"a",10));
        list1.add(new box2(2,false,20));
        list1.add(new box3(3,true,30));
        String list1Json=gson.toJson(list1);
        System.out.println(list1Json);
        //[{"v1":1,"v2":true,"v3":"a","Type":10},{"v1":2,"v2":false,"Type":20},{"v1":3,"v2":true,"Type":30}]
        ArrayList<Object> list2=gson.fromJson(list1Json,new TypeToken<ArrayList<Object>>(){}.getType());
        box1 b1=gson.fromJson(list2.get(0).toString(),box1.class);
        System.out.println(b1);
        //box1{v1=1, v2=true, v3='a'}
        box2 b2=gson.fromJson(list2.get(1).toString(),box2.class);
        System.out.println(b2);
        //box2{v1=2, v2=false}
        box3 b3=gson.fromJson(list2.get(2).toString(),box3.class);
        System.out.println(b3);
        //box2{v1=3, v2=true}
    }
}
