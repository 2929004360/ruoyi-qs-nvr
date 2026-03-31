package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 事件类型 EVENT_IVS_DRIVE_ACTION_ANAYLSE 驾驶行为分析 对应的规则配置
 * @date 2023/03/31 11:19:02
 */
public class CFG_DRIVE_ACIONANAYLSE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 规则名称,不同规则不能重名
     */
    public byte[]           szRuleName = new byte[128];
    /**
     * 规则使能
     */
    public int              bRuleEnable;
    /**
     * 相应物体类型个数
     */
    public int              nObjectTypeNum;
    /**
     * 相应物体类型列表
     */
    public byte[]           szObjectTypes = new byte[16 * 128];
    /**
     * 报警联动
     */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
    /**
     * 事件响应时间段
     */
    public NetSDKLib.CFG_TIME_SECTION[] stuTimeSection = new NetSDKLib.CFG_TIME_SECTION[7 * 10];
    /**
     * 云台预置点编号  0~65535
     */
    public int              nPtzPresetId;
    /**
     * 灵敏度  取值1-10，默认值5 值越小灵敏度越低
     */
    public int              nSensitivity;
    /**
     * 工作模式  0：正常模式 1：测试模式
     */
    public int              nWorkMode;
    /**
     * 报警触发速度
     */
    public int              nAlarmSpeed;
    /**
     * 报警分级速度
     */
    public int              nGradeSpeed;
    /**
     * 疲劳驾驶报警配置
     */
    public CFG_FATIGUE_DRIVING stuFatigue = new CFG_FATIGUE_DRIVING();
    /**
     * 分心驾驶报警
     */
    public CFG_DISTRACT_DRIVING stuDistract = new CFG_DISTRACT_DRIVING();
    /**
     * 打电话报警配置
     */
    public CFG_DRIVEING_CALLING stuCalling = new CFG_DRIVEING_CALLING();
    /**
     * 离岗报警配置
     */
    public CFG_DRIVE_LEAVE_INFO stuLeave = new CFG_DRIVE_LEAVE_INFO();
    /**
     * 红外阻断报警配置
     */
    public CFG_INFRARED_BLOCKING stuInfraredBlocking = new CFG_INFRARED_BLOCKING();
    /**
     * 抽烟报警配置
     */
    public CFG_DRIVE_SMOKING stuSmoking = new CFG_DRIVE_SMOKING();
    /**
     * 相机遮挡持续时间
     */
    public CFG_CAMERA_OCCLUSION stuCameraOcclusionTime = new CFG_CAMERA_OCCLUSION();
    /**
     * 身份异常报警配置
     */
    public CFG_IDENTITY_ANOMALY stuIdentityAnomaly = new CFG_IDENTITY_ANOMALY();
    /**
     * 换人驾驶报警配置
     */
    public CFG_SUBSTITUTION_DRIVING stuSubstitution = new CFG_SUBSTITUTION_DRIVING();
    /**
     * 未系安全带报警配置
     */
    public CFG_DRIVER_NOBELT stuNobelt = new CFG_DRIVER_NOBELT();
    /**
     * 报警多抓张数 取值范围[0, 10]，默认3
     */
    public int              nPicNums;
    /**
     * 报警多抓时间间隔，单位：ms 取值范围[100, 10000]，默认：3000
     */
    public int              nPicInterval;
    public byte[]           byReserved = new byte[4092];

    public CFG_DRIVE_ACIONANAYLSE_INFO() {
        for (int i = 0; i < stuTimeSection.length; i++) {
            stuTimeSection[i] = new NetSDKLib.CFG_TIME_SECTION();
        }
    }
}

