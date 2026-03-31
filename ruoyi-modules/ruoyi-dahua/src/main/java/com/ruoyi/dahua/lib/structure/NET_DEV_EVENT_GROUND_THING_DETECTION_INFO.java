package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_GROUND_THING_DETECTION (地物识别事件)对应的数据块描述信息
*/
public class NET_DEV_EVENT_GROUND_THING_DETECTION_INFO extends NetSDKLib.SdkStructure
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
     * 单目标小图分割结果数量
    */
    public int              nMaskNum;
    /**
     * 输出类型数
    */
    public int              nMapInfoNum;
    /**
     * 单目标小图分割结果,参见结构体定义 {@link NET_GROUND_THING_DETECTION_MASK_INFO}
    */
    public NET_GROUND_THING_DETECTION_MASK_INFO[] stuMaskInfo = new NET_GROUND_THING_DETECTION_MASK_INFO[64];
    /**
     * mask输出信息类型映射,参见结构体定义 {@link NET_GROUND_THING_DETECTION_MAP_INFO}
    */
    public NET_GROUND_THING_DETECTION_MAP_INFO[] stuMapInfo = new NET_GROUND_THING_DETECTION_MAP_INFO[256];
    /**
     * 上报原图,参见结构体定义 {@link NetSDKLib.SCENE_IMAGE_INFO}
    */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
    /**
     * 智能事件规则编号，用于标示哪个规则触发的事件
    */
    public int              nRuleID;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_DEV_EVENT_GROUND_THING_DETECTION_INFO() {
        for(int i = 0; i < stuMaskInfo.length; i++){
            stuMaskInfo[i] = new NET_GROUND_THING_DETECTION_MASK_INFO();
        }
        for(int i = 0; i < stuMapInfo.length; i++){
            stuMapInfo[i] = new NET_GROUND_THING_DETECTION_MAP_INFO();
        }
    }
}

