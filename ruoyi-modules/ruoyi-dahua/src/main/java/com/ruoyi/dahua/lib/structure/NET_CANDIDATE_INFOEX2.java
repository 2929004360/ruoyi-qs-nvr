package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 候选人员信息列表
 * @date 2022/11/24 17:12:01
 */
public class NET_CANDIDATE_INFOEX2 extends NetSDKLib.SdkStructure {
    /**
     * 当前人脸匹配到的候选对象信息扩展
     */
    public NetSDKLib.CANDIDATE_INFOEX stuCandidatesEx = new NetSDKLib.CANDIDATE_INFOEX();
    /**
     * 以图搜图业务,输入图片的相似度,万分比 1~10000
     */
    public int              nSimilarity2;
    /**
     * 任务流ID, 任务流以图搜图时, 表示候选人来自哪个分析任务
     */
    public int              nTaskID;
    /**
     * 非机动车信息, 非机动车以图搜图时返回的非机动车属性
     */
    public NET_HISTORY_NON_MOTOR_INFO stuHistoryNonMotorInfo = new NET_HISTORY_NON_MOTOR_INFO();
    /**
     * 机动车信息, 机动车以图搜图时返回的机动车属性
     */
    public NET_HISTORY_TRAFFIC_CAR_INFO stuHistoryTrafficCarInfo = new NET_HISTORY_TRAFFIC_CAR_INFO();
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[256];

    public NET_CANDIDATE_INFOEX2() {
    }
}

