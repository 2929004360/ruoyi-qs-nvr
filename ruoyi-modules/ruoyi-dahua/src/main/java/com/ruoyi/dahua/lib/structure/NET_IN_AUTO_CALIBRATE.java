package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_AutoCalibrate 接口输入参数
*/
public class NET_IN_AUTO_CALIBRATE extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 标定的模式，1 ，2， 3 分别代表“从下到上”，“从上到下”，“先上后下”
    */
    public int              nMode;

    public NET_IN_AUTO_CALIBRATE() {
        this.dwSize = this.size();
    }
}

