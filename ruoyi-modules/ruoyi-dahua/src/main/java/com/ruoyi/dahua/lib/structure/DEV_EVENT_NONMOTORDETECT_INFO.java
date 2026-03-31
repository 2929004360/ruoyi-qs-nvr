package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型EVENT_IVS_NONMOTORDETECT(非机动车检测) 对应数据块描述信息
*/
public class DEV_EVENT_NONMOTORDETECT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 事件ID
    */
    public int              nEventID;
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
     * 0:脉冲 1:开始 2:停止
    */
    public int              nAction;
    /**
     * 智能事件公共信息,参见结构体定义 {@link NetSDKLib.EVENT_INTELLI_COMM_INFO}
    */
    public NetSDKLib.EVENT_INTELLI_COMM_INFO stuIntelliCommInfo = new NetSDKLib.EVENT_INTELLI_COMM_INFO();
    /**
     * 抓图标志(按位),具体见NET_RESERVED_COMMON
    */
    public int              dwSnapFlagMask;
    /**
     * 对应图片的分辨率,参见结构体定义 {@link NetSDKLib.NET_RESOLUTION_INFO}
    */
    public NetSDKLib.NET_RESOLUTION_INFO stuResolution = new NetSDKLib.NET_RESOLUTION_INFO();
    /**
     * 非机动车抠图信息,参见结构体定义 {@link NONMOTOR_IMAGE_INFO}
    */
    public NONMOTOR_IMAGE_INFO stuImageInfo = new NONMOTOR_IMAGE_INFO();
    /**
     * 骑车人数量
    */
    public int              nNumOfCycling;
    /**
     * 非机动车颜色,参见枚举定义 {@link NetSDKLib.EM_OBJECT_COLOR_TYPE}
    */
    public int              emNonMotorColor;
    /**
     * 包围盒,参见结构体定义 {@link NetSDKLib.NET_RECT}
    */
    public NetSDKLib.NET_RECT stuBoundingBox = new NetSDKLib.NET_RECT();
    /**
     * 车上一人时的性别,参见枚举定义 {@link NetSDKLib.EM_SEX_TYPE}
    */
    public int              emSex;
    /**
     * 车上一人时的年龄
    */
    public int              nAge;
    /**
     * 车上人员头盔状态,参见枚举定义 {@link NetSDKLib.EM_NONMOTOR_OBJECT_STATUS}
    */
    public int              emHelmet;
    /**
     * 车上一人时是否在打电话,参见枚举定义 {@link NetSDKLib.EM_NONMOTOR_OBJECT_STATUS}
    */
    public int              emCall;
    /**
     * 车上一人时是否戴帽子,参见枚举定义 {@link NetSDKLib.EM_NONMOTOR_OBJECT_STATUS}
    */
    public int              emHat;
    /**
     * 车上一人时是否有背包,参见枚举定义 {@link NetSDKLib.EM_NONMOTOR_OBJECT_STATUS}
    */
    public int              emBag;
    /**
     * 车上一人时有没有手提包,参见枚举定义 {@link NetSDKLib.EM_NONMOTOR_OBJECT_STATUS}
    */
    public int              emCarrierBag;
    /**
     * 是否打伞,参见枚举定义 {@link NetSDKLib.EM_NONMOTOR_OBJECT_STATUS}
    */
    public int              emUmbrella;
    /**
     * 车上一人时是否有带眼镜,参见枚举定义 {@link NetSDKLib.EM_NONMOTOR_OBJECT_STATUS}
    */
    public int              emGlasses;
    /**
     * 车上一人时是否带口罩,参见枚举定义 {@link NetSDKLib.EM_NONMOTOR_OBJECT_STATUS}
    */
    public int              emMask;
    /**
     * 车上一人时的表情,参见枚举定义 {@link NetSDKLib.EM_EMOTION_TYPE}
    */
    public int              emEmotion;
    /**
     * 车上一人时上衣类型,参见枚举定义 {@link NetSDKLib.EM_CLOTHES_TYPE}
    */
    public int              emUpClothes;
    /**
     * 车上一人时下衣类型,参见枚举定义 {@link NetSDKLib.EM_CLOTHES_TYPE}
    */
    public int              emDownClothes;
    /**
     * 车上一人时上衣颜色,参见枚举定义 {@link NetSDKLib.EM_OBJECT_COLOR_TYPE}
    */
    public int              emUpperBodyColor;
    /**
     * 车上一人时下衣颜色,参见枚举定义 {@link NetSDKLib.EM_OBJECT_COLOR_TYPE}
    */
    public int              emLowerBodyColor;
    /**
     * 物体类型（NonMotor）中的子类别,参见枚举定义 {@link NetSDKLib.EM_CATEGORY_NONMOTOR_TYPE}
    */
    public int              emCategoryType;
    /**
     * 保留字节,留待扩展.
    */
    public byte[]           byReserved = new byte[1024];

    public DEV_EVENT_NONMOTORDETECT_INFO() {
    }
}

