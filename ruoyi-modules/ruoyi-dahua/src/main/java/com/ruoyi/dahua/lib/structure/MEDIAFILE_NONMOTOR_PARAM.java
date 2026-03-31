package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * DH_FILE_QUERY_NONMOTOR 非机动车对应查询参数
*/
public class MEDIAFILE_NONMOTOR_PARAM extends NetSDKLib.SdkStructure
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
     * 骑车人数最小值
    */
    public int              nNumOfCyclingMin;
    /**
     * 骑车人数最大值
    */
    public int              nNumOfCyclingMax;
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
     * 为TRUE表示仅下发stuStartTimeRealUTC和stuEndTimeRealUTC(不下发stStartTime, stEndTime), 为FALSE表示仅下发stStartTime, stEndTime(不下发stuStartTimeRealUTC和stuEndTimeRealUTC)
    */
    public int              bOnlySupportRealUTC;
    /**
     * UTC开始时间(标准UTC时间), 与stuEndTimeRealUTC配对使用，与(stuStartTime, stuEndTime)互斥,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * UTC结束时间(标准UTC时间), 与stuStartTimeRealUTC配对使用，与(stuStartTime, stuEndTime)互斥,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuEndTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 骑车人数, 255表示查未知
    */
    public int              nNumOfCycling;
    /**
     * 查询方式: 0:未知, 1: 相等, 2: 小于, 3:大于, 当nNumOfCycling不为255时有效
    */
    public int              nNumOfCyclingCmp;
    /**
     * 事件类型列表,参见智能分析事件类型
    */
    public int[]            nEventLists = new int[256];
    /**
     * 事件总数
    */
    public int              nEventCount;
    /**
     * 上装颜色,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_NONMOTOR_COLOR}
    */
    public int[]            emUpperBodyColor = new int[16];
    /**
     * 上装颜色个数
    */
    public int              nUpperBodyColorNum;
    /**
     * 车上人员帽子状态,参见枚举定义 {@link NetSDKLib.EM_HAS_HAT}
    */
    public int              emHasHat;
    /**
     * 支持不下发骑车人数字段
    */
    public int              bUnEnableSupportNumOfCycling;
    /**
     * 支持不下发查询方式字段
    */
    public int              bUnEnableSupportNumOfCyclingCmp;
    /**
     * 通道号数组有效个数
    */
    public int              nChannelsNum;
    /**
     * 通道号数组
    */
    public int[]            nChannels = new int[256];
    /**
     * 支持不下发车上人员帽子状态字段
    */
    public int              bUnEnableSupportHasHat;

    public MEDIAFILE_NONMOTOR_PARAM() {
        this.dwSize = this.size();
    }
}

