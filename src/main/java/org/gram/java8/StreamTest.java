package org.gram.java8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public void st1(){
        //foreach
        List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        numberList.forEach(number -> System.out.print(number+","));
        System.out.println();
        //map
        List<Integer> collect = numberList.stream()
                .map(number -> number * 2)
                .collect(Collectors.toList());
        collect.forEach(number -> System.out.print(number + ","));
        System.out.println();
        numberList.stream()
                .map(number -> "数字 " + number + ",")
                .forEach(System.out::print);
        System.out.println();
        //flatmap
        Stream<List<Integer>> inputStream = Stream.of(
                Collections.singletonList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        List<Integer> collect1 = inputStream
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        collect1.forEach(number -> System.out.print(number + ","));
        System.out.println();
        //filter
        List<Integer> collect2 = numberList.stream()
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());
        collect2.forEach(number -> System.out.print(number + ","));
        System.out.println();
        //findFirst，这里的Optional可以处理空引用
        Optional<Integer> firstNumber = numberList.stream()
                .findFirst();
        System.out.println(firstNumber.orElse(-1));
        //limit和skip
        List<Integer> ageList = Arrays.asList(11, 22, 13, 14, 25, 26);
        ageList.stream()
                .limit(3)
                .forEach(age -> System.out.print(age+","));
        System.out.println();
        ageList.stream()
                .skip(3)
                .forEach(age -> System.out.print(age+","));
        System.out.println();
        //groupingBy
        Map<String, List<Integer>> ageGrouyByMap = ageList.stream()
                .collect(Collectors.groupingBy(age -> String.valueOf(age / 10)));
        ageGrouyByMap.forEach((k, v) -> {
            System.out.println("年龄" + k + "0多岁的有：" + v);
        });
        //partitioningBy
        Map<Boolean, List<Integer>> ageMap = ageList.stream()
                .collect(Collectors.partitioningBy(age -> age > 18));
        System.out.println("未成年人：" + ageMap.get(false));
        System.out.println("成年人：" + ageMap.get(true));
    }
}
