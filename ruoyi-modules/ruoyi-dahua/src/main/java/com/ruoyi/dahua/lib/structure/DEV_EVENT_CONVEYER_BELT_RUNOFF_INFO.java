package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 报警事件类型EVENT_IVS_CONVEYER_BELT_RUNOFF (传送带跑偏检测事件) 对应的数据块描述信息
*/
public class DEV_EVENT_CONVEYER_BELT_RUNOFF_INFO extends NetSDKLib.SdkStructure
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
     * 事件ID
    */
    public int              nEventID;
    /**
     * 字节对齐
    */
    public byte[]           byReserved1 = new byte[4];
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
     * 规则检测区域顶点数
    */
    public int              nDetectRegionNum;
    /**
     * 规则检测区域,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] stuDetectRegion = new NetSDKLib.NET_POINT[20];
    /**
     * 预警值，用于界定跑偏的程度；0<预警值<停机值<10取值[1-9]
    */
    public int              nWarningThreshold;
    /**
     * 停机值，用于界定跑偏的程度；0<预警值<停机值<10取值[1-9]
    */
    public int              nDowntimeThreshold;
    /**
     * 皮带跑偏报警类型，0代表不报警，1代表预警报警，2代表停机报警
    */
    public int              nAlarmType;
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
     * 跑偏事件累计次数
    */
    public int              nRunOffEventCount;
    /**
     * 跑偏程度，取值范围为1.0到10.0，越趋近于10.0表示跑偏越严重
    */
    public float            fRunoffRate;
    /**
     * 跑偏方向, Int,0-未知，1-前面、正面 ； 2-后面,背面；3-左面,左侧； 4-右面,右侧；5-上面,顶部； 6-下面,底部
    */
    public int              nRunoffDirection;
    /**
     * 皮带跑偏报警等级, 范围1~5
    */
    public int              nAlarmLevel;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[1004];

    public DEV_EVENT_CONVEYER_BELT_RUNOFF_INFO() {
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.NET_POINT();
        }
        for(int i = 0; i < stuObjects.length; i++){
            stuObjects[i] = new NetSDKLib.NET_MSG_OBJECT();
        }
    }
}

