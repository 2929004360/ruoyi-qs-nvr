package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * DH_FILE_QUERY_IVS_EVENT 对应的智能事件查询参数
*/
public class MEDIAFILE_IVS_EVENT_PARAM extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 起始时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartTime = new NetSDKLib.NET_TIME();
    /**
     * 结束时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuEndTime = new NetSDKLib.NET_TIME();
    /**
     * 文件类型,0:任意类型, 1:jpg图片, 2:dav文件
    */
    public int              nMediaType;
    /**
     * 视频码流 0-未知 1-主码流 2-辅码流1 3-辅码流2 4-辅码流3
    */
    public int              nVideoStream;
    /**
     * 事件类型列表,参见智能分析事件类型
    */
    public int[]            nEventLists = new int[256];
    /**
     * 事件总数
    */
    public int              nEventCount;
    /**
     * 录像或抓图文件标志, 不设置标志表示查询所有文件,参见枚举定义 {@link NetSDKLib.EM_RECORD_SNAP_FLAG_TYPE}
    */
    public int[]            emFalgLists = new int[128];
    /**
     * 标志总数
    */
    public int              nFalgCount;
    /**
     * 智能分析事件名, 事件类型,详见"智能分析事件类型"
    */
    public int              nRuleType;
    /**
     * 事件动作,参见枚举定义 {@link NetSDKLib.NET_CROSSREGION_ACTION_INFO}
    */
    public int              emAction;
    /**
     * 对象类型个数
    */
    public int              nIvsObjectNum;
    /**
     * 规则触发的对象类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_MEDIAFILE_IVS_OBJECT}
    */
    public int[]            emIvsObject = new int[16];
    /**
     * 为TRUE表示仅下发stuStartTimeRealUTC和stuEndTimeRealUTC(不下发stuStartTime, stuEndTime), 为FALSE表示仅下发stuStartTime, stuEndTime(不下发stuStartTimeRealUTC和stuEndTimeRealUTC)
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

    public MEDIAFILE_IVS_EVENT_PARAM() {
        this.dwSize = this.size();
    }
}

