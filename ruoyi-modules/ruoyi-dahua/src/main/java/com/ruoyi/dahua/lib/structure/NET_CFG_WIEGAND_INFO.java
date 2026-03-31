package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description 韦根配置,   对应配置项 NET_EM_CFG_WIEGAND
* @date 2022/03/08 09:56:03
*/
public class NET_CFG_WIEGAND_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
stuWiegandInfos的有效元素
*/
    public			int            nCount;
/** 
韦根配置信息
*/
    public			NET_WIEGAND_INFO[] stuWiegandInfos = new NET_WIEGAND_INFO[8];

public NET_CFG_WIEGAND_INFO(){

    for(int i=0;i<stuWiegandInfos.length;i++){
        stuWiegandInfos[i]=new NET_WIEGAND_INFO();
    }

   this.dwSize=this.size();
}
}

