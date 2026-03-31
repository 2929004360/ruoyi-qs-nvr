package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * EVENT_IVS_DOOR_STATE_DETECTION(开关门检测事件)对应的规则配置
*/
public class NET_CFG_DOOR_STATE_DETECTION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小
    */
    public int              dwSize;
    /**
     * 规则名称,不同规则不能重名
    */
    public byte[]           szName = new byte[128];
    /**
     * 规则使能
    */
    public int              bEnable;
    /**
     * 相应物体类型个数
    */
    public int              nObjectTypeNum;
    /**
     * 相应物体类型列表
    */
    public BYTE_ARRAY_128[] szObjectTypes = new BYTE_ARRAY_128[16];
    /**
     * 报警联动,参见结构体定义 {@link NetSDKLib.NET_ALARM_MSG_HANDLE}
    */
    public NetSDKLib.NET_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.NET_ALARM_MSG_HANDLE();
    /**
     * 云台预置点编号  0~65535
    */
    public int              nPtzPresetId;
    /**
     * 联动业务个数
    */
    public int              nLink;
    /**
     * 联动业务大类选项,参见结构体定义 {@link NET_LINK_CLASS_TYPE}
    */
    public NET_LINK_CLASS_TYPE[] stuLinkClassType = new NET_LINK_CLASS_TYPE[16];
    /**
     * 跟踪条件规则使能
    */
    public int              bTrackEnable;
    /**
     * 检测区顶点数
    */
    public int              nDetectRegionNum;
    /**
     * 检测区,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
    /**
     * 辅助线（两点),参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuDetectLine = new NET_POINT_EX[2];
    /**
     * 规则特定的尺寸过滤器,参见结构体定义 {@link NET_CFG_SIZEFILTER_INFO}
    */
    public NET_CFG_SIZEFILTER_INFO stuSizeFilter = new NET_CFG_SIZEFILTER_INFO();
    /**
     * 是否开启辅助线。对应DetectLine。
    */
    public int              bDetectLineEnable;
    /**
     * 是否开启开关门异常检测
    */
    public int              bDSEnable;
    /**
     * 是否开启开门时间过长检测
    */
    public int              bODEnable;
    /**
     * 最短持续时间, 单位:秒(5s-60s)
    */
    public int              nMinDuration;
    /**
     * 重复报警时间，单位：秒，范围0-3600
    */
    public int              nRepeatAlarmTime;
    /**
     * 灵敏度, 值越小灵敏度越低, 取值1-10
    */
    public int              nSensitivity;
    /**
     * 检测门的状态，开门状态1表示，关门状态0表示
    */
    public int              nDoorState;
    /**
     * 开门过长时间/s,最小10s
    */
    public int              nLongTime;
    /**
     * 门状态的报警指定时间段，在指定时间段开门或关门为异常，产生报警,参见结构体定义 {@link NetSDKLib.NET_CFG_TIME_SCHEDULE}
    */
    public NetSDKLib.NET_CFG_TIME_SCHEDULE stuDSTimeSection = new NetSDKLib.NET_CFG_TIME_SCHEDULE();
    /**
     * 开门状态的报警指定时间段，在指定时间段开门达到LongTime，产生报警,参见结构体定义 {@link NetSDKLib.NET_CFG_TIME_SCHEDULE}
    */
    public NetSDKLib.NET_CFG_TIME_SCHEDULE stuODTimeSection = new NetSDKLib.NET_CFG_TIME_SCHEDULE();
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[512];

    public NET_CFG_DOOR_STATE_DETECTION_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuLinkClassType.length; i++){
            stuLinkClassType[i] = new NET_LINK_CLASS_TYPE();
        }
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NET_POINT_EX();
        }
        for(int i = 0; i < stuDetectLine.length; i++){
            stuDetectLine[i] = new NET_POINT_EX();
        }
    }
}

