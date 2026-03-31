package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 事件类型 EVENT_IVS_COLLISION_CONFLICT (碰撞冲突事件)对应的数据块描述信息
 * @date 2023/02/21 14:39:29
 */
public class NET_DEV_EVENT_COLLISION_CONFLICT_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 0:脉冲,1:开始, 2:停止
     */
    public int              nAction;
    /**
     * 扩展协议字段
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
    /**
     * 事件编号，用来唯一标志一个事件
     */
    public int              nEventID;
    /**
     * 碰撞位置
     */
    public NET_3DFLOAT_POINT stuPosition = new NET_3DFLOAT_POINT();
    /**
     * 碰撞点GPS坐标
     */
    public NET_GPS_POSITION stuGPSPosition = new NET_GPS_POSITION();
    /**
     * 参与碰撞的目标信息
     */
    public NET_VEHICLE_FUSED_OBJECT[] stuObjects = new NET_VEHICLE_FUSED_OBJECT[4];
    /**
     * 参与碰撞的目标个数
     */
    public int              nObjectsCount;
    /**
     * 碰撞剩余时间TTC，单位s,0表示已经碰撞
     */
    public int              nLeftTime;
    /**
     * 碰撞类型: 0-未知 1-交叉 2-追尾 3-合流
     */
    public int              nType;
    /**
     * 冲突程度，取值范围0-1，值越大越严重，<=0.5一般冲突， >0.5严重冲突
     */
    public float            fConflictLevel;
    /**
     * 置信度
     */
    public float            fConfidence;
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[1020];

    public NET_DEV_EVENT_COLLISION_CONFLICT_INFO() {
        for (int i = 0; i < stuObjects.length; i++) {
            stuObjects[i] = new NET_VEHICLE_FUSED_OBJECT();
        }
    }
}

