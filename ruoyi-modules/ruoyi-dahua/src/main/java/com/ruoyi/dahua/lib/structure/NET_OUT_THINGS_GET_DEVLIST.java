package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description CLIENT_GetThingsDevList接口输出参数
 * @date 2022/04/20 10:16:56
 */
public class NET_OUT_THINGS_GET_DEVLIST extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 用户分配的pstuDevListInfo个数
     */
    public int              nMaxDevListCount;
    /**
     * 实际返回的pstuDevListInfo个数
     */
    public int              nRetDevListCount;
    /**
     * 设备列表信息，用户分配内存,大小为sizeof{@link NET_THINGS_DEVLIST_INFO}*nMaxDevListCount
     */
    public Pointer          pstuDevListInfo;

    public NET_OUT_THINGS_GET_DEVLIST() {
        this.dwSize = this.size();
    }
}

