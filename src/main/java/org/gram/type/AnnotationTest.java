package org.gram.type;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnnotationTest {
    //自定义注解添加元数据
    public void at1(){
        User u=new User();
        u.name="孙笑川";
        u.phone="0931234 3819";
        u.mobile="13575309630";
        u.email="test@gmail.com";
        u.website="fttp://test.com";
        try{
            parser(u);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Retention(RetentionPolicy.RUNTIME)//全局有效
    @Target(ElementType.FIELD)//用于域注解
    @Documented//可以用javadoc类似工具进行文档化
    private @interface ContactValidator{
        ContactType type();
    }

    private enum ContactType{
        EMAIL,PHONE,MOBILE,WEBSITE
    }

    private class User{
        String name;
        @ContactValidator(type=ContactType.EMAIL)
        String email;
        @ContactValidator(type=ContactType.PHONE)
        String phone;
        @ContactValidator(type=ContactType.MOBILE)
        String mobile;
        @ContactValidator(type=ContactType.WEBSITE)
        String website;
    }

    private boolean isValidEmail(String e){
        Pattern p=Pattern.compile(".+@.+\\.[a-z]+");//\.对应的是.，这里必须用\\才行
        Matcher m=p.matcher(e);
        return m.matches();
    }

    private boolean isValidPhone(String phone) {
        Pattern p =Pattern.compile("\\d\\d([,\\s])?\\d\\d\\d\\d([,\\s])?\\d\\d\\d\\d");
        Matcher m =p.matcher(phone);
        return m.matches();
    }

    private boolean isValidMobile(String mobile) {
        Pattern p =Pattern.compile("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$");
        Matcher m =p.matcher(mobile);
        return m.matches();
    }

    private boolean isValidWebsite(String website){
        Pattern p =Pattern.compile("^(https?|ftp|file)://.+$");
        Matcher m =p.matcher(website);
        return m.matches();
    }

    private void parser(User user)throws IllegalArgumentException, IllegalAccessException {
        Field[]fields = user.getClass().getDeclaredFields();
        for(Field field : fields){
            Annotation[] annotations = field.getAnnotations();//反射获取域上的注解
            for(Annotation annotation : annotations){
                if(annotation instanceof ContactValidator){
                    ContactValidator contactValidator = (ContactValidator) annotation;
                    if(field.getModifiers() == Modifier.PRIVATE){
                        field.setAccessible(true);
                    }
                    boolean result =false;
                    String fieldValue = (String) field.get(user);
                    switch (contactValidator.type()) {
                        case EMAIL:
                            result =isValidEmail(fieldValue);
                            break;
                        case PHONE:
                            result =isValidPhone(fieldValue);
                            break;
                        case MOBILE:
                            result =isValidMobile(fieldValue);
                            break;
                        case WEBSITE:
                            result =isValidWebsite(fieldValue);
                            break;
                    }
                    if(!result){
                        System.out.println("Invalid " + field.getName() + ": " +fieldValue);
                    }
                }
            }
        }
    }

    public void at2(){
        Test test=new Test();
        Method[] methods=test.getClass().getDeclaredMethods();
        for(Method method:methods){
            Test1 test1=method.getAnnotation(Test1.class);
            System.out.println(Arrays.toString(test1.value()));
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    private @interface Test1{
        String[] value() default {};

    }

    private static class Test{
        @Test1
        private void op1(){}

        @Test1({"a","b"})
        private void op2(){}
    }



}
