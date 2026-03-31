package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_RadiometryGetTemper 接口入参
*/
public class NET_IN_RADIOMETRY_GET_TEMPER_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 获取测温项温度的条件,参见结构体定义 NET_RADIOMETRY_GET_TEMPER_CONDITION_INFO
    */
    public NET_RADIOMETRY_GET_TEMPER_CONDITION_INFO stCondition = new NET_RADIOMETRY_GET_TEMPER_CONDITION_INFO();

    public NET_IN_RADIOMETRY_GET_TEMPER_INFO() {
        this.dwSize = this.size();
    }
}

