package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 291189
 * @创建时间 2021/5/28
 * @描述
 */
public class CFG_PTZ_AUTOMOVE_INFO extends NetSDKLib.SdkStructure {
    /**
     *  云台的配置信息 CFG_PTZ_ALL_AUTOMOVE_INFO
     */
    public Pointer          pstPTZAutoConfig;
    /**
     * 结构体申请的云台个数(对于多通道查询，申请不小于设备通数，对于单通道查询，一个就够了)
     */
    public  int             nMaxPTZNum;
    /**
     * 设备返回的云台个数(一般为设备通道数)
     */
    public   int            nReturnPTZNum;
}

