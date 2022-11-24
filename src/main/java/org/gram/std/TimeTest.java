package org.gram.std;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeTest {
    //Date使用
    public void tt1(){
        System.out.println(System.currentTimeMillis());//1621235063248
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd'-'HH:mm:ss", Locale.CHINA);
        Date date=new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));//2021-05-17-15:13:43
        try{
            System.out.println(formatter.parse("2021-05-17-15:13:43"));//Mon May 17 15:13:43 CST 2021
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    //Calendar使用
    public void tt2(){
        //Calendar输出当前时间的详细信息
        Calendar calendar=Calendar.getInstance();
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));//5，星期四
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//26，26号
        System.out.println(calendar.get(Calendar.YEAR));//2022
        System.out.println(calendar.get(Calendar.MONTH));//4，五月份
        System.out.println(calendar.get(Calendar.DATE));//26
        System.out.println(calendar.get(Calendar.AM_PM));//1，下午
        System.out.println(calendar.get(Calendar.HOUR));//4，下午4点
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));//16，下午4点
        System.out.println(calendar.get(Calendar.MINUTE));//23，分钟
        System.out.println(calendar.get(Calendar.SECOND));//22，秒
        //Calendar和Date互换
        Date now=calendar.getTime();
        calendar.setTime(now);
        //Calendar操作时间
        Calendar calendar1=new GregorianCalendar();
        calendar1.set(Calendar.YEAR,2000);
        System.out.println(calendar.after(calendar1));//true
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(calendar.getTime()));
        //2022-05-26 16:23:22 501
        //Calendar和timestamp相互转换
        Calendar calendar2=Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH,1);
        calendar2.set(Calendar.HOUR_OF_DAY,1);
        calendar2.set(Calendar.MINUTE,1);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(calendar2.getTime()));
        //2022-05-01 01:01:22 530
        long timestamp2=calendar2.getTimeInMillis();
        System.out.println(timestamp2);
        //1651338082530
        Calendar calendar3=Calendar.getInstance();
        calendar3.setTime(new Date(timestamp2));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(calendar3.getTime()));
        //2022-05-01 01:01:22 530
    }

    //TimerTask
    //schedule和scheduleAtFixedRate的区别：scheduleAtFixedRate会把过去的时间也算上作为周期执行，schedule则不会，会立即执行一次
    public void t3(){
        final int[] num = {0};
        Timer t=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                System.out.println("time: "+System.currentTimeMillis());
                num[0]++;
                if(num[0]==10){
                    t.cancel();
                }
//                t.cancel();//单次执行必须要调用cancel才能解除线程阻塞
            }
        };
        //单次执行
//        t.schedule(task,1000);
        //循环执行
        t.schedule(task,1000,500);
    }
}
