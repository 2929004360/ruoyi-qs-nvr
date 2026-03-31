package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 智能分析结果的信息
 * @date 2023/02/21 14:39:21
 */
public class NET_VIDEO_ANALYSE_ANALYSE_PROC extends NetSDKLib.SdkStructure {
    /**
     * 通道号
     */
    public int              nChannelId;
    /**
     * 时间戳(单位是毫秒)
     */
    public double           dbPTS;
    /**
     * 发生的时间
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
    /**
     * 时间毫秒数，只表示毫秒
     */
    public int              nUTCMS;
    /**
     * 包序号,用于校验是否丢包
     */
    public int              nSequence;
    /**
     * 物体列表,{@link NET_VEHICLE_FUSED_OBJECT}的数组
     */
    public Pointer          pstuObjectList;
    /**
     * 物体列表个数
     */
    public int              nObjectListNum;
    /**
     * 事件列表个数
     */
    public int              nEventListCount;
    /**
     * 事件列表, 仅支持: 碰撞冲突事件CollisionConflict(EVENT_IVS_COLLISION_CONFLICT)
     */
    public int[]            dwEventCode = new int[32];
    /**
     * 事件信息(只解析Event Data中的字段)
     * 碰撞冲突事件CollisionConflict(EVENT_IVS_COLLISION_CONFLICT) - {@link NET_DEV_EVENT_COLLISION_CONFLICT_INFO}
     */
    public Pointer[]        pstuEventList = new Pointer[32];
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[1916 - NetSDKLib.POINTERSIZE * 32];
}

