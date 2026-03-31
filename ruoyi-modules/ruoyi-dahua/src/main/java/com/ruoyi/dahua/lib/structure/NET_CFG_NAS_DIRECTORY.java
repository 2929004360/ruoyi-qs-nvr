package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/** 
* @author 291189
* @description  存储组件全局配置 
* @date 2022/09/22 17:25:41
*/
public class NET_CFG_NAS_DIRECTORY extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
字节对齐
*/
    public			byte[]         szReserved = new byte[4];
/** 
需要获取到的NAS配置数量
*/
    public			int            nNeedNASDirectoryInfoNum;
/** 
实际获取到的NAS共享目录配置数量
*/
    public			int            nNASCfgRealNum;
/** 
获取到的配置信息指针, 需用户申请内存   {@link NAS_DIRECTORY_CFG_INFO}
*/
    public Pointer          pNASDirectoryInfo;

public NET_CFG_NAS_DIRECTORY(){
		this.dwSize=this.size();
}
}

