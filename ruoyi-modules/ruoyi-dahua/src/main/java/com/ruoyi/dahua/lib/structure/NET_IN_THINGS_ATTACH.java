package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description CLIENT_AttachThingsInfo接口输入参数
 * @date 2022/04/20 10:16:57
 */
public class NET_IN_THINGS_ATTACH extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 订阅类型 {@link com.ruoyi.dahua.lib.enumeration.EM_ATTACH_TOPICS}
     */
    public int              emTopics;
    /**
     * 产品ID，全网唯一
     */
    public byte[]           szProductID = new byte[64];
    /**
     * 设备ID，产品下唯一
     */
    public byte[]           szDeviceID = new byte[64];
    /**
     * 物模型属性信息回调
     */
    public NetSDKLib.fThingsCallBack cbThingsInfo;
    /**
     * 用户数据
     */
    public Pointer          dwUser;

    public NET_IN_THINGS_ATTACH() {
        this.dwSize = this.size();
    }
}

