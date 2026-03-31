package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 首卡开门信息（拓展）与 CFG_ACCESS_FIRSTENTER_INFO 配合使用，是其拓展
*/
public class CFG_ACCESS_FIRSTENTER_INFO_EX extends NetSDKLib.SdkStructure
{
    /**
     * 验证假日计划,需要首卡验证的假日计划
    */
    public int              nHolidayScheduleID;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[124];

    public CFG_ACCESS_FIRSTENTER_INFO_EX() {
    }
}

