package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  合规报警规则 
* @origin autoTool
* @date 2023/05/29 11:40:47
*/
public class NET_COMPLIANT_INFO extends NetSDKLib.SdkStructure {
/** 
合规报警报警类型，0：不合规，1-合规， 2-不限（IVSS专用）
*/
    public			int            nCompliantType;
/** 
合规条件使能, TRUE: 开启, FALSE-关闭,如果使能为TRUE上报合规相关属性
*/
    public			int            bCompliantEnable;
/** 
合规报警属性过滤规则
*/
    public			NET_COMPLIANT_FORMAT_INFO stuCompliantFormat = new NET_COMPLIANT_FORMAT_INFO();
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[1024];

public NET_COMPLIANT_INFO(){
}
}

