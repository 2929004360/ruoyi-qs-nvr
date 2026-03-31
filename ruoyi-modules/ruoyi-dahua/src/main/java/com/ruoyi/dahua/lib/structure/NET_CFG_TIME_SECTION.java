package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 时间段信息
*/
public class NET_CFG_TIME_SECTION extends NetSDKLib.SdkStructure
{
    /**
     * 录像掩码，按位分别为Bit0-动态检测录像、Bit1-报警录像、Bit2-定时录像、Bit3-动检和报警同时触发时才录像、Bit4-卡号录像、Bit5-智能录像、Bit6-POS录像、Bit7~Bit15保留
    */
    public int              dwRecordMask;
    public int              nBeginHour;
    public int              nBeginMin;
    public int              nBeginSec;
    public int              nEndHour;
    public int              nEndMin;
    public int              nEndSec;

    public NET_CFG_TIME_SECTION() {
    }
}

