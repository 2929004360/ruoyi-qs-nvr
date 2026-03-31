package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @version 1.0
 * @description CLIENT_AttachCrowdDistriMap 接口输入参数
 * @date 2022/01/07
 */
public class NET_IN_ATTACH_CROWDDISTRI_MAP_INFO extends NetSDKLib.SdkStructure {
    /**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  通道号
     */
    public int              nChannelID;
    /**
     *  订阅人群分布图实时统计信息回调函数
     */
    public NetSDKLib.fCrowdDistriStream cbCrowdDistriStream;
    /**
     *  用户数据
     */
    public Pointer          dwUser;

    public NET_IN_ATTACH_CROWDDISTRI_MAP_INFO(){
        this.dwSize = this.size();
    }
}

