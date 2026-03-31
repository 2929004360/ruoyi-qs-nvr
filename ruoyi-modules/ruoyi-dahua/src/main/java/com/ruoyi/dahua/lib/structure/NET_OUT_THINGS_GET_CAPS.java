package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description CLIENT_GetThingsCaps接口输出参数
 * @date 2022/04/20 10:16:56
 */
public class NET_OUT_THINGS_GET_CAPS extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 用户分配的pstuGetCapsProperName个数
     */
    public int              nMaxProperCount;
    /**
     * 实际返回的pstuGetCapsProperName个数
     */
    public int              nRetProperCount;
    /**
     * 物模型标识符，用户分配内存,大小为sizeof{@link NET_PROPERTIES_NAME}*nMaxProperCount
     */
    public Pointer          pstuGetCapsProperName;

    public NET_OUT_THINGS_GET_CAPS() {
        this.dwSize = this.size();
    }
}

