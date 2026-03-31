package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 传感器所拥有的检测属性
*/
public class NET_IOTBOX_TAGS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 获取的第三方传感器检测属性点名称
    */
    public byte[]           szTagName = new byte[64];
    /**
     * 第三方传感器检测属性点名称的描述
    */
    public byte[]           szDescribe = new byte[64];
    /**
     * 第三方传感器属性点名称的值
    */
    public double           dbValue;
    /**
     * 第三方传感器属性点名值的单位
    */
    public byte[]           szUnity = new byte[32];
    /**
     * 第三方传感器属性点名称的值数据类型,0 - 无效型  ,1 - 布尔,2 - 浮点数 ,3 - 整形  ,4 - 字符串
    */
    public int              nValueType;
    /**
     * 第三方传感器测点报警值，0表示无报警，非0表示有报警,32位整形，其数据位的含义如下：bit1：低低限报警 bit2：低限报警 bit3：高限报警 bit4：高高限报警 bit13：开关量报警,其它位暂未定义
    */
    public int              nAlarmValue;
    /**
     * 第三方传感器测点质量戳,正常值范围：[192, 195]，[216, 219],默认值一般为192
    */
    public int              nQualityStamp;
    /**
     * 第三方传感器测点报警集合，两个报警名称之间使用'|'分隔开来,报警字段如下：LLNAME：低低限报警名,LONAME:  低限报警名,HINAME: 高限报警名,HHNAME：高高限报警名,ALMNAME：开关量报警名,报警组合方式：LLNAME|LONAME|HINAME|HHNAME|ALMNAME
    */
    public byte[]           szAlarmName = new byte[128];
    /**
     * 保留字节
    */
    public byte[]           szResvered = new byte[52];

    public NET_IOTBOX_TAGS_INFO() {
    }
}

