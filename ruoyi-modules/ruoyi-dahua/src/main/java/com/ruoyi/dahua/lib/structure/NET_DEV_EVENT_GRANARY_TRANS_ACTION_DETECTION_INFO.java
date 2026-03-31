package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 事件类型 EVENT_IVS_GRANARY_TRANS_ACTION_DETECTION(粮面异动检测事件上报)对应的数据块描述信息
 * @origin autoTool
 * @date 2023/12/13 09:51:51
 */
public class NET_DEV_EVENT_GRANARY_TRANS_ACTION_DETECTION_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 通道号
     */
    public int              nChannelID;
    /**
     * / 0:脉冲,1:开始, 2:停止
     */
    public int              nAction;
    /**
     * / 扩展协议字段
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * / 事件发生的时间
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
    /**
     * / 事件编号，用来唯一标志一个事件
     */
    public int              nEventID;
    /**
     * / 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * / 智能事件所属大类
     */
    public byte[]           szClass = new byte[16];
    /**
     * / 事件触发的预置点号，从1开始
     */
    public int              nPresetID;
    /**
     * / 物体信息列表实际数量
     */
    public int              nObjectsCount;
    /**
     * / 物体信息列表
     */
    public NetSDKLib.NET_MSG_OBJECT[] stuObjects = new NetSDKLib.NET_MSG_OBJECT[128];
    /**
     * / 相对事件时间戳,单位毫秒
     */
    public double           dbPTS;
    /**
     * / 全景图
     */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
    /**
     * / 检测区域
     */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
    /**
     * / 检测区域个数
     */
    public int              nDetectRegionCount;
    /**
     * / 预留字节
     */
    public byte[]           szReserverd = new byte[1024];

    public NET_DEV_EVENT_GRANARY_TRANS_ACTION_DETECTION_INFO() {
        for (int i = 0; i < stuObjects.length; i++) {
            stuObjects[i] = new NetSDKLib.NET_MSG_OBJECT();
        }
        for (int i = 0; i < stuDetectRegion.length; i++) {
            stuDetectRegion[i] = new NET_POINT_EX();
        }
    }
}

