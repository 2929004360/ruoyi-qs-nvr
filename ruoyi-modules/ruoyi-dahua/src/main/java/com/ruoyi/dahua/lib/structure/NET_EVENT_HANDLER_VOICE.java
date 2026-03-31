package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  联动语音配置 
* @date 2022/12/01 16:27:09
*/
public class NET_EVENT_HANDLER_VOICE extends NetSDKLib.SdkStructure {
/** 
联动语音文件绝对路径，为空播放系统默认报警联动音
*/
    public			byte[]         szAudioFileName = new byte[256];
/** 
预留
*/
    public			byte[]         szReserved = new byte[256];

public NET_EVENT_HANDLER_VOICE(){
}
}

