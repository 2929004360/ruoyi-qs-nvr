package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * DH_FILE_QUERY_NONMOTOR 非机动车对应FINDNEXT查询返回参数
*/
public class MEDIAFILE_NONMOTOR_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 通道
    */
    public int              nChannel;
    /**
     * 开始时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stStartTime = new NetSDKLib.NET_TIME();
    /**
     * 结束时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stEndTime = new NetSDKLib.NET_TIME();
    /**
     * 文件路径
    */
    public byte[]           szFilePath = new byte[260];
    /**
     * 文件大小
    */
    public int              nFileSize;
    /**
     * 骑车人数
    */
    public int              nNumOfCycling;
    /**
     * 非机动车颜色,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_NONMOTOR_COLOR}
    */
    public int              emNonmotorColor;
    /**
     * 非机动车子类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_NONMOTOR_SUBTYPE}
    */
    public int              emNonmotorSubtype;
    /**
     * 头盔状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_HELMET_STATE}
    */
    public int              emHelmetState;
    /**
     * 目标型心(不是包围盒中心), 0-8191相对坐标, 相对于小图,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT stuFaceCenter = new NetSDKLib.NET_POINT();
    /**
     * 为TRUE表示仅stuStartTimeRealUTC和stuEndTimeRealUTC有效(仅使用stuStartTimeRealUTC和stuEndTimeRealUTC), 为FALSE表示仅stStartTime和stEndTime有效(仅使用stStartTime和stEndTime)
    */
    public int              bRealUTC;
    /**
     * UTC开始时间(标准UTC时间), 与stuEndTimeRealUTC配对使用,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * UTC结束时间(标准UTC时间), 与stuStartTimeRealUTC配对使用,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuEndTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 抠图信息,参见结构体定义 {@link NET_EVENT_IMAGE_INFO}
    */
    public NET_EVENT_IMAGE_INFO stuImageInfo = new NET_EVENT_IMAGE_INFO();

    public MEDIAFILE_NONMOTOR_INFO() {
        this.dwSize = this.size();
    }
}

