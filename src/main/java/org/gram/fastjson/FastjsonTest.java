package org.gram.fastjson;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;


public class FastjsonTest {
    public void t1(){
        JSONObject obj=new JSONObject();
        obj.put("a",1);
        obj.put("b","2");
        JSONObject obj1=new JSONObject();
        obj1.put("c",3);
        obj1.put("a","a");
        obj.put("d",obj1);
        String json= JSON.toJSONString(obj);
        System.out.println(json);
        JSONObject obj2=JSON.parseObject(json);
        System.out.println(obj2.get("a"));
    }
}
