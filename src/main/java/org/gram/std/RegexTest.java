package org.gram.std;

import java.util.regex.Pattern;

public class RegexTest {

    public void t1(){
        String topic1="0670f9b9-d723fc2c/device/message/up/ivs_result";
        String topic2="0670f9b9-d723fc2c/中文/1";
        String topic3="abc/123/3";
        String pattern="^[0-9 a-z]{8}-[0-9 a-z]{8}.*$";
        System.out.println(Pattern.matches(pattern,topic1));
        System.out.println(Pattern.matches(pattern,topic2));
        System.out.println(Pattern.matches(pattern,topic3));
    }
}
