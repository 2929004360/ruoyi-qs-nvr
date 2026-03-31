package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 日志信息里的时间定义
 * @author 47081
 */
public class SDK_DEV_TIME extends NetSDKLib.SdkStructure {
    /**
     * 秒    1-60
     *//*
    public int                second;
    *//**
     * 分    1-60
     *//*
    public int                minute;
    *//**
     * 时    1-24
     *//*
    public int                hour;
    *//**
     * 日    1-31
     *//*
    public int                day;
    *//**
     * 月    1-12
     *//*
    public int                month;
    *//**
     * 年    2000-2063
     *//*
    public int                year;*/
    public int              date;
    public int getHour(){return getAnd(date,6+6,6+6+5);           }
    public int getDay(){return getAnd(date,6+6+5,6+6+5+5);        }
    public int getMonth(){return getAnd(date,6+6+5+5,6+6+5+5+4);  }
    public int getYear(){return 2000+getAnd(date,6+6+5+5+4,6+6+5+5+4+6); }
    /**
     * c层处理后的原始年份信息
     * @return
     */
    public int getOriginalYear(){return getAnd(date,6+6+5+5+4,6+6+5+5+4+6); };

public int getSecond(){
    return getAnd(date,0,6);
}

public int getMinute(){
    return getAnd(date,6,6+6);
}

public String getDate(){
    return getYear()+"-"+getMonth()+"-"+getDay()+" "+getHour()+":"+getMinute()+":"+getSecond();
}

    private int getAnd(int date,int start,int end){
        return date>>start&getOr(start,end);
    }

    private int getOr(int start,int end){
        int result=0;
        for (int i = 0; i < end-start; i++) {
            result|=(1<<i);
        }
        return result;
    }
}

