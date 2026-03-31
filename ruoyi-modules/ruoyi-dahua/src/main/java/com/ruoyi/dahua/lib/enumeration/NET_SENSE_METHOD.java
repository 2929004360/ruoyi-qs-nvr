package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  传感器感应方式枚举类型 
* @date 2022/08/31 14:44:16
*/
public enum NET_SENSE_METHOD {
/**
未知类型
*/
    NET_SENSE_UNKNOWN(-1, "未知类型"),
/**
门磁
*/
    NET_SENSE_DOOR(0, "门磁"),
/**
被动红外
*/
    NET_SENSE_PASSIVEINFRA(1, "被动红外"),
/**
气感
*/
    NET_SENSE_GAS(2, "气感"),
/**
烟感
*/
    NET_SENSE_SMOKING(3, "烟感"),
/**
水感
*/
    NET_SENSE_WATER(4, "水感"),
/**
主动红外
*/
    NET_SENSE_ACTIVEFRA(5, "主动红外"),
/**
玻璃破碎
*/
    NET_SENSE_GLASS(6, "玻璃破碎"),
/**
紧急开关
*/
    NET_SENSE_EMERGENCYSWITCH(7, "紧急开关"),
/**
震动
*/
    NET_SENSE_SHOCK(8, "震动"),
/**
双鉴(红外+微波)
*/
    NET_SENSE_DOUBLEMETHOD(9, "双鉴(红外+微波)"),
/**
三技术
*/
    NET_SENSE_THREEMETHOD(10, "三技术"),
/**
温度
*/
    NET_SENSE_TEMP(11, "温度"),
/**
湿度
*/
    NET_SENSE_HUMIDITY(12, "湿度"),
/**
风速
*/
    NET_SENSE_WIND(13, "风速"),
/**
呼叫按钮
*/
    NET_SENSE_CALLBUTTON(14, "呼叫按钮"),
/**
气体压力
*/
    NET_SENSE_GASPRESSURE(15, "气体压力"),
/**
燃气浓度
*/
    NET_SENSE_GASCONCENTRATION(16, "燃气浓度"),
/**
气体流量
*/
    NET_SENSE_GASFLOW(17, "气体流量"),
/**
其他
*/
    NET_SENSE_OTHER(18, "其他"),
/**
油量检测,汽油、柴油等车辆用油检测
*/
    NET_SENSE_OIL(19, "油量检测,汽油、柴油等车辆用油检测"),
/**
里程数检测
*/
    NET_SENSE_MILEAGE(20, "里程数检测"),
/**
紧急按钮
*/
    NET_SENSE_URGENCYBUTTON(21, "紧急按钮"),
/**
盗窃
*/
    NET_SENSE_STEAL(22, "盗窃"),
/**
周界
*/
    NET_SENSE_PERIMETER(23, "周界"),
/**
防拆
*/
    NET_SENSE_PREVENTREMOVE(24, "防拆"),
/**
门铃
*/
    NET_SENSE_DOORBELL(25, "门铃"),
/**
交流电压传感器
*/
    NET_SENSE_ALTERVOLT(26, "交流电压传感器"),
/**
直流电压传感器
*/
    NET_SENSE_DIRECTVOLT(27, "直流电压传感器"),
/**
交流电流传感器
*/
    NET_SENSE_ALTERCUR(28, "交流电流传感器"),
/**
直流电流传感器
*/
    NET_SENSE_DIRECTCUR(29, "直流电流传感器"),
/**
高新兴通用模拟量	4~20mA或0~5V
*/
    NET_SENSE_RSUGENERAL(30, "高新兴通用模拟量	4~20mA或0~5V"),
/**
高新兴门禁感应
*/
    NET_SENSE_RSUDOOR(31, "高新兴门禁感应"),
/**
高新兴断电感应
*/
    NET_SENSE_RSUPOWEROFF(32, "高新兴断电感应"),
/**
1500温度传感器
*/
    NET_SENSE_TEMP1500(33, "1500温度传感器"),
/**
DS18B20温度传感器
*/
    NET_SENSE_TEMPDS18B20(34, "DS18B20温度传感器"),
/**
1500湿度传感器
*/
    NET_SENSE_HUMIDITY1500(35, "1500湿度传感器"),
/**
红外报警
*/
    NET_SENSE_INFRARED(36, "红外报警"),
/**
火警
*/
    NET_SENSE_FIREALARM(37, "火警"),
/**
CO2浓度检测,典型值:0~5000ppm
*/
    NET_SENSE_CO2(38, "CO2浓度检测,典型值:0~5000ppm"),
/**
噪音检测,典型值:30~130dB
*/
    NET_SNESE_SOUND(39, "噪音检测,典型值:30~130dB"),
/**
PM2.5检测,典型值:0~1000ug/m3
*/
    NET_SENSE_PM25(40, "PM2.5检测,典型值:0~1000ug/m3"),
/**
SF6浓度检测,典型值:0~3000ppm
*/
    NET_SENSE_SF6(41, "SF6浓度检测,典型值:0~3000ppm"),
/**
臭氧浓度检测,典型值:0~100ppm
*/
    NET_SENSE_O3(42, "臭氧浓度检测,典型值:0~100ppm"),
/**
环境光照检测,典型值:0~20000Lux
*/
    NET_SENSE_AMBIENTLIGHT(43, "环境光照检测,典型值:0~20000Lux"),
/**
签入按钮
*/
    NET_SENSE_SIGNINBUTTON(44, "签入按钮"),
/**
液位
*/
    NET_SENSE_LIQUIDLEVEL(45, "液位"),
/**
测距
*/
    NET_SENSE_DISTANCE(46, "测距"),
/**
水流量
*/
    NET_SENSE_WATERFLOW(47, "水流量"),
/**
按键传感器
*/
    NET_SENSE_KEYPRESSS(48, "按键传感器"),
/**
SM7820B温湿度传感器
*/
    NET_SENSE_TEMP_AND_HUMI_SM7820B(49, "SM7820B温湿度传感器"),
/**
DSU666单相电子式电能表D
*/
    NET_SENSE_WATT_HUR_DDSU666(50, "DSU666单相电子式电能表D"),
/**
幕帘传感器
*/
    NET_SENSE_CURTAIN_SENSOR(51, "幕帘传感器"),
/**
机壳防拆
*/
    NET_SENSE_CASEPREVENTREMOVE(52, "机壳防拆"),
/**
枚举类型总数
*/
    NET_SENSE_NU(53, "枚举类型总数");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_SENSE_METHOD(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_SENSE_METHOD enumType : NET_SENSE_METHOD.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_SENSE_METHOD enumType : NET_SENSE_METHOD.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static NET_SENSE_METHOD getEnum(int value) {
        for (NET_SENSE_METHOD e : NET_SENSE_METHOD.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_SENSE_METHOD.NET_SENSE_UNKNOWN;
    }
}

