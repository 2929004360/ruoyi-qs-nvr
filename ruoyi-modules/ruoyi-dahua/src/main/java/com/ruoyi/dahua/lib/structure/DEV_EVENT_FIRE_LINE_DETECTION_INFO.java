package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 事件类型 EVENT_IVS_FIRE_LINE_DETECTION 进入消防通道检测 对应的数据块描述信息
 * @origin autoTool
 * @date 2023/12/08 16:20:18
 */
public class DEV_EVENT_FIRE_LINE_DETECTION_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 通道号
     */
    public int              nChannelID;
    /**
     * / 1:开始 2:停止
     */
    public int              nAction;
    /**
     * / 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * / 时间戳(单位是毫秒)
     */
    public double           PTS;
    /**
     * / 智能事件所属大类 {@link com.ruoyi.dahua.lib.enumeration.EM_CLASS_TYPE}
     */
    public int              emClassType;
    /**
     * / 事件发生的时间
     */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
    /**
     * / 事件ID
     */
    public int              nEventID;
    /**
     * / 帧序号
     */
    public int              nSequence;
    /**
     * / 规则编号
     */
    public int              nRuleID;
    /**
     * / 检测到的物体个数
     */
    public int              nObjectNum;
    /**
     * / 检测到的物体
     */
    public NetSDKLib.NET_MSG_OBJECT[] stuObjects = new NetSDKLib.NET_MSG_OBJECT[32];
    /**
     * / 全景广角图
     */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
    /**
     * / 保留字节
     */
    public byte[]           byReserved = new byte[1024];

    public DEV_EVENT_FIRE_LINE_DETECTION_INFO() {
        for (int i = 0; i < stuObjects.length; i++) {
            stuObjects[i] = new NetSDKLib.NET_MSG_OBJECT();
        }
    }
}

