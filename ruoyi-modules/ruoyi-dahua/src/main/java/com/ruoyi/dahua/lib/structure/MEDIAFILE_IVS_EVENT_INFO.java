package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * DH_FILE_QUERY_IVS_EVENT 查询出来的media文件信息
*/
public class MEDIAFILE_IVS_EVENT_INFO extends NetSDKLib.SdkStructure
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
     * 文件路径
    */
    public byte[]           szFilePath = new byte[128];
    /**
     * 文件类型,0:任意类型, 1:jpg图片, 2:dav文件
    */
    public int              nMediaType;
    /**
     * 文件长度,该字段废弃,请使用nFileLengthEx
    */
    public int              nFileLength;
    /**
     * 簇号
    */
    public int              nCluster;
    /**
     * 文件定位索引
    */
    public int              nHint;
    /**
     * 磁盘号
    */
    public int              nDriveNo;
    /**
     * 视频码流 0-未知 1-主码流 2-辅码流1 3-辅码流2 4-辅码流3 5-辅码流4 6-辅码流5 7-辅码流6 8-辅码流7 9-辅码流8 10-辅码流9 11-辅码流10 12-辅码流11 13-辅码流12
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
     * 文件长度扩展，支持文件长度大于4G，单位字节
    */
    public long             nFileLengthEx;
    /**
     * 规则触发的对象类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_MEDIAFILE_IVS_OBJECT}
    */
    public int              emIvsObject;
    /**
     * 事件抠图信息,参见结构体定义 {@link NET_EVENT_IMAGE_INFO}
    */
    public NET_EVENT_IMAGE_INFO stuEventImageInfo = new NET_EVENT_IMAGE_INFO();
    /**
     * 物体型心(不是包围盒中心), 0-8191相对坐标, 相对于小图,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT stuObjCenter = new NetSDKLib.NET_POINT();
    /**
     * 为TRUE表示仅stuStartTimeRealUTC和stuEndTimeRealUTC有效(仅使用stuStartTimeRealUTC和stuEndTimeRealUTC), 为FALSE表示仅stuStartTime和stuEndTime有效(仅使用stuStartTime和stuEndTime)
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

    public MEDIAFILE_IVS_EVENT_INFO() {
        this.dwSize = this.size();
    }
}

