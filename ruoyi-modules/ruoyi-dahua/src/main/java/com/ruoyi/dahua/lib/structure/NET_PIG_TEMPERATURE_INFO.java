package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description 猪体温信息数组
* @date 2022/03/31 14:06:49
*/
public class NET_PIG_TEMPERATURE_INFO extends NetSDKLib.SdkStructure {
/** 
猪的数量
*/
    public			int            nPigNum;
/** 
猪的信息
*/
    public			NET_PIG_TEMPERATURE_DATA[] stuPigInfo = new NET_PIG_TEMPERATURE_DATA[48];
/** 
保留字节
*/
    public			byte[]         byReserve = new byte[1024];

    public NET_PIG_TEMPERATURE_INFO(){
            for(int i=0;i<stuPigInfo.length;i++){
                stuPigInfo[i]=new NET_PIG_TEMPERATURE_DATA();
            }

    }
}

