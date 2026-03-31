package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 录像片段文件列表
*/
public class NET_OBJECT_MEDIA_FILE_INFOS extends NetSDKLib.SdkStructure
{
    /**
     * 通道号，从0开始
    */
    public int              nChannel;
    /**
     * 视频码流
    */
    public int              nVideoStream;
    /**
     * 目标类型列表个数
    */
    public int              nObjectTypesCount;
    /**
     * 目标类型列表, 0 未知, 1 人, 2 车, 3 非机动车, 4 动物
    */
    public int[]            nObjectTypes = new int[32];
    /**
     * 查询选项个数
    */
    public int              nFlagsCount;
    /**
     * 查询选项, 1:File, 录像文件，可通过findFile协议获取录像结果, 2:Event, 目标事件，可通过findEvent协议获取事件结果
    */
    public int[]            nFlags = new int[8];
    /**
     * 和这个文件关联的事件个数
    */
    public int              nEventsCount;
    /**
     * 和这个文件关联的事件数组
    */
    public int[]            nEvents = new int[256];
    /**
     * 文件的UTC开始时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 文件的UTC结束时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuEndTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * UTC时间偏移，文件中的本地时间相对于UTC时间的偏移量，单位秒
    */
    public int              nUTCOffset;
    /**
     * 录像文件路径
    */
    public byte[]           szFilePath = new byte[256];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_OBJECT_MEDIA_FILE_INFOS() {
    }
}

