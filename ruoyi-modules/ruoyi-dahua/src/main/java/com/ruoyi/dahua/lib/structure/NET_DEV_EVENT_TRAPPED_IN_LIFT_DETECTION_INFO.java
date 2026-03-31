package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 事件类型 EVENT_IVS_TRAPPED_IN_LIFT_DETECTION (电梯困人检测)对应的数据块描述信息
*/
public class NET_DEV_EVENT_TRAPPED_IN_LIFT_DETECTION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 0:脉冲,1:开始, 2:停止
    */
    public int              nAction;
    /**
     * 扩展协议字段,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 事件发生时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 智能事件规则编号
    */
    public int              nRuleID;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类
    */
    public byte[]           szClass = new byte[16];
    /**
     * 相对事件时间戳,单位毫秒
    */
    public double           dbPTS;
    /**
     * GroupID事件组ID
    */
    public int              nGroupID;
    /**
     * CountInGroup一个事件组内的抓拍张数
    */
    public int              nCountInGroup;
    /**
     * IndexInGroup一个事件组内的抓拍序号
    */
    public int              nIndexInGroup;
    /**
     * 事件编号
    */
    public int              nEventID;
    /**
     * 事件触发的预置点号, 从1开始, 0表示预置点未知
    */
    public int              nPresetID;
    /**
     * 困人时间, 单位s ,最小10s
    */
    public int              nTrappedTime;
    /**
     * 当前电梯内人数
    */
    public int              nCurrentNumber;
    /**
     * 电梯运行状态,0:未知, 1:电梯停止, 2:电梯上行, 3:电梯下行
    */
    public int              nElevatorWorkState;
    /**
     * 电梯所在的楼层,最大值255
    */
    public byte[]           szElevatorFloor = new byte[32];
    /**
     * 规则区域,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
    /**
     * 规则区域个数
    */
    public int              nDetectRegionNum;
    /**
     * 检测目标的物体信息个数
    */
    public int              nObjectsNum;
    /**
     * 检测目标的物体信息,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT}
    */
    public Pointer          pstuObjects;
    /**
     * 全景图,参见结构体定义 {@link NetSDKLib.SCENE_IMAGE_INFO}
    */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_DEV_EVENT_TRAPPED_IN_LIFT_DETECTION_INFO() {
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NET_POINT_EX();
        }
    }
}

