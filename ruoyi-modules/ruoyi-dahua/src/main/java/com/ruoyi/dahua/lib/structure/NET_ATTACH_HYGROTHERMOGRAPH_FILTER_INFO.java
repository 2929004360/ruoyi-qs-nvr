package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  过滤器 
* @origin autoTool
* @date 2023/12/02 10:25:56
*/
public class NET_ATTACH_HYGROTHERMOGRAPH_FILTER_INFO extends NetSDKLib.SdkStructure {
/** 
/ 订阅通道号；-1代表订阅全通道；
*/
    public			int            nChannel;
/** 
/ 保留字节
*/
    public			byte[]         szResvered = new byte[252];

public			NET_ATTACH_HYGROTHERMOGRAPH_FILTER_INFO(){
}
}

