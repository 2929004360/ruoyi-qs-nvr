package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 获取区域状态 输出参数。此时CLIENT_GetAlarmRegionInfo的emType参数为NET_EM_GET_ALARMREGION_INFO_AREASTATUS
*/
public class NET_OUT_GET_AREAS_STATUS extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 区域个数
    */
    public int              nAreaRet;
    /**
     * 区域防区异常状态信息,参见结构体定义 {@link NET_AREA_STATUS}
    */
    public NET_AREA_STATUS[] stuAreaStatus = new NET_AREA_STATUS[8];
    /**
     * 区域个数扩展
    */
    public int              nAreaRetEx;
    /**
     * 区域防区异常状态信息扩展，区域个数超过8个使用这个字段,参见结构体定义 {@link NET_AREA_STATUS_EX}
    */
    public NET_AREA_STATUS_EX[] stuAreaStatusEx = new NET_AREA_STATUS_EX[64];

    public NET_OUT_GET_AREAS_STATUS() {
        this.dwSize = this.size();
        for(int i = 0; i < stuAreaStatus.length; i++){
            stuAreaStatus[i] = new NET_AREA_STATUS();
        }
        for(int i = 0; i < stuAreaStatusEx.length; i++){
            stuAreaStatusEx[i] = new NET_AREA_STATUS_EX();
        }
    }
}

