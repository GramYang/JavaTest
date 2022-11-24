package org.gram.java8;

import java.util.Optional;

public class OptionalTest {
    public void o1(){
        //创建Optional
        Optional<String> helloOption = Optional.of("hello");
        Optional<String> emptyOptional = Optional.empty();
        Optional<String> nullOptional = Optional.ofNullable(null);//这种写法同empty
        System.out.printf("%b %b %b\n",helloOption.isPresent(),emptyOptional.isPresent(),nullOptional.isPresent());//true false false
        //ifPresent
        helloOption.ifPresent(s->System.out.println(s.length()));//5
        //直接get不安全，要用orElse、orElseGet
        System.out.printf("%s %s\n",helloOption.orElse("null"),emptyOptional.orElse("null"));
        System.out.printf("%s %s\n",helloOption.orElseGet(()->"null"),emptyOptional.orElseGet(()->"null"));
        //orElseThrow
        try{
            System.out.println(emptyOptional.orElseThrow(()->new Exception("null value")));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
