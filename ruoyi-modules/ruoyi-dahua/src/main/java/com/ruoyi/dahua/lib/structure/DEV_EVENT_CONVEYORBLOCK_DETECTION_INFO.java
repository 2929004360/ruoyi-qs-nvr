package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_CONVEYORBLOCK_DETECTION 传送阻塞报警事件 对应的数据块描述信息
*/
public class DEV_EVENT_CONVEYORBLOCK_DETECTION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 0-脉冲 1-开始 2-结束
    */
    public int              nAction;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类,参见枚举定义 {@link NetSDKLib.EM_CLASS_TYPE}
    */
    public int              emClassType;
    /**
     * 智能事件规则编号，用于标示哪个规则触发的事件
    */
    public int              nRuleID;
    /**
     * 相对事件时间戳,单位毫秒
    */
    public double           dPTS;
    /**
     * 事件发生时间，带时区偏差的UTC时间，单位秒,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX UTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件时间毫秒数
    */
    public int              nUTCMS;
    /**
     * 事件编号，用来唯一标志一个事件
    */
    public int              nEventID;
    /**
     * 帧序号
    */
    public int              nSequence;
    /**
     * 全景图,参见结构体定义 {@link NetSDKLib.SCENE_IMAGE_INFO_EX}
    */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
    /**
     * stuSceneImage 是否有效
    */
    public int              bSceneImage;
    /**
     * 图片信息数组,参见结构体定义 {@link NET_IMAGE_INFO_EX2}
    */
    public NET_IMAGE_INFO_EX2[] stuImageInfo = new NET_IMAGE_INFO_EX2[32];
    /**
     * 图片信息个数
    */
    public int              nImageInfoNum;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[1024];

    public DEV_EVENT_CONVEYORBLOCK_DETECTION_INFO() {
        for(int i = 0; i < stuImageInfo.length; i++){
            stuImageInfo[i] = new NET_IMAGE_INFO_EX2();
        }
    }
}

