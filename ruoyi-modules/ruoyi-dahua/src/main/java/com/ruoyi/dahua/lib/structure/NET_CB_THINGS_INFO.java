package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 物模型属性订阅回调信息
 * @date 2022/04/20 11:36:49
 */
public class NET_CB_THINGS_INFO extends NetSDKLib.SdkStructure {
    /**
     * 订阅类型 {@link com.ruoyi.dahua.lib.enumeration.EM_ATTACH_TOPICS}
     */
    public int              emTopics;
    /**
     * sid
     */
    public int              nSID;
    /**
     * 产品ID，全网唯一
     */
    public byte[]           szProductID = new byte[64];
    /**
     * 设备ID，产品下唯一
     */
    public byte[]           szDeviceID = new byte[128];
    /**
     * 当Topics为events时，必须携带, 具体内容由物模型定义决定
     */
    public byte[]           szEventID = new byte[64];
    /**
     * 事件触发时间
     */
    public NET_TIME_EX      stuEventTime = new NET_TIME_EX();
    /**
     * 物模型属性信息或事件参数数组, 内存由sdk 内部申请和释放,参考{@link NET_CB_THINGS_PROPER_INFO}数组
     */
    public Pointer          pstuProperInfo;
    /**
     * 实际返回的物模型属性信息数量, 即pstuProperInfo 数组中有效的元素个数
     */
    public int              nRetProperInfoNum;
    /**
     * 保留字节
     */
    public byte[]           szReserve = new byte[256];
}

