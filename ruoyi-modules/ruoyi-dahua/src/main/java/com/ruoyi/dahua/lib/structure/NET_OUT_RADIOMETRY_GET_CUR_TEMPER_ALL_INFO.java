package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_RadiometryGetCurTemperAll 接口出参
*/
public class NET_OUT_RADIOMETRY_GET_CUR_TEMPER_ALL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 规则温度信息数组，内存由用户申请释放,参见结构体定义 NET_RADIOMETRY_TEMP_INFO
    */
    public Pointer          pstTempInfo;
    /**
     * 最大规则温度信息数量，由用户填充
    */
    public int              nMaxTempInfoNum;
    /**
     * 实际返回的规则温度信息数量，由SDK填充
    */
    public int              nRetTempInfoNum;

    public NET_OUT_RADIOMETRY_GET_CUR_TEMPER_ALL_INFO() {
        this.dwSize = this.size();
    }
}

