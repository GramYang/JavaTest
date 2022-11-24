package org.gram.java8;

import java.util.function.Function;

public class FunctionTest {

    public void f1(){
        Function<String, Integer> lengthFunction = str -> str.length();
        Integer length = lengthFunction.apply("www.wdbyte.com");
        System.out.println(length);
        Function<Integer, Integer> doubleFunction = length1 -> length1 * 2;
        Integer doubleLength = lengthFunction.andThen(doubleFunction).apply("www.wdbyte.com");
        System.out.println(doubleLength);
    }
}
