 package com.ruoyi.dahua.lib.structure;


 import com.ruoyi.dahua.lib.NetSDKLib;

public class NET_CFG_VIOLATIONTYPE_NORMAL extends NetSDKLib.SdkStructure {
/** 使能*/
    public			int            bEnable;
/** 文本颜色 NET_EM_LXSJ_FONTCOLOR */
    public			int            emTextColor;
/** 违法内容*/
    public			byte[]         szText = new byte[128];
/** 预留*/
    public			byte[]         byReserved = new byte[376];
}

