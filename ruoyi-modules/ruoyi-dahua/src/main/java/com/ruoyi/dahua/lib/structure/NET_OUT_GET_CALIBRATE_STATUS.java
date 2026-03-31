package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetCalibrateStatus 接口输出参数
*/
public class NET_OUT_GET_CALIBRATE_STATUS extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 获取标定状态,1：启动后从未一键自动标定过 2：正在一键标定中 3：上次一键标定的结果是成功 4：上次一键标定的结果是失败
    */
    public int              nAutoCalibrateResult;

    public NET_OUT_GET_CALIBRATE_STATUS() {
        this.dwSize = this.size();
    }
}

