package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 报警事件类型EVENT_IVS_CONVEYER_BELT_BULK (传送带大块异物检测事件) 对应的数据块描述信息
*/
public class DEV_EVENT_CONVEYER_BELT_BULK_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 事件动作,1表示持续性事件开始,2表示持续性事件结束;
    */
    public int              nEventAction;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 时间戳(单位是毫秒)
    */
    public double           PTS;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX UTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 规则编号,用于标示哪个规则触发的事件，缺省时默认为0
    */
    public int              nRuleID;
    /**
     * 智能事件所属大类,参见枚举定义 {@link NetSDKLib.EM_CLASS_TYPE}
    */
    public int              emClassType;
    /**
     * 规则检测线顶点数
    */
    public int              nDetectLineNum;
    /**
     * 规则检测线,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] stuDetectLine = new NetSDKLib.NET_POINT[20];
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 规则检测区域顶点数
    */
    public int              nDetectRegionNum;
    /**
     * 规则检测区域,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] stuDetectRegion = new NetSDKLib.NET_POINT[20];
    /**
     * 大块物占比值, 尺寸大于SizeFilterThreshold  / 10* ComparetLine表示的传送带平均宽度 的物体被认为是大块异物取值[2-8]
    */
    public int              nSizeFilterThreshold;
    /**
     * 检测到的物体个数
    */
    public int              nObjectNum;
    /**
     * 检测到的物体信息,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT}
    */
    public NetSDKLib.NET_MSG_OBJECT[] stuObjects = new NetSDKLib.NET_MSG_OBJECT[20];
    /**
     * 全景广角图信息,参见结构体定义 {@link NetSDKLib.SCENE_IMAGE_INFO_EX}
    */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
    /**
     * 皮带实际物理宽度,范围[0-1000],单位cm
    */
    public int              nConveyorBeltWidth;
    /**
     * 大块物事件累计次数
    */
    public int              nBulkEventCount;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[1012];

    public DEV_EVENT_CONVEYER_BELT_BULK_INFO() {
        for(int i = 0; i < stuDetectLine.length; i++){
            stuDetectLine[i] = new NetSDKLib.NET_POINT();
        }
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.NET_POINT();
        }
        for(int i = 0; i < stuObjects.length; i++){
            stuObjects[i] = new NetSDKLib.NET_MSG_OBJECT();
        }
    }
}

