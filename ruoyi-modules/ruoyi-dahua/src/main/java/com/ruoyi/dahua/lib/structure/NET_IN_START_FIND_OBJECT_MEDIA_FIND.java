package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_StartFindObjectMediaFind 接口输入参数
*/
public class NET_IN_START_FIND_OBJECT_MEDIA_FIND extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 收藏的开始时间点,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 收藏的结束时间点,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuEndTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 通道号,从0开始
    */
    public int              nChannel;
    /**
     * 通道号数组个数
    */
    public int              nChannelsCount;
    /**
     * 通道号数组
    */
    public int[]            nChannels = new int[16];
    /**
     * 视频码流 0-未知 1-主码流 2-辅码流1 3-辅码流2 4-辅码流3
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
    public int[]            nFlags = new int[4];
    /**
     * 目标轨迹过滤规则是否启用
    */
    public int              bObjectTrackFilter;
    /**
     * 目标轨迹过滤规则,参见结构体定义 {@link NET_OBJECT_TRACK_FILTER}
    */
    public NET_OBJECT_TRACK_FILTER stuObjectTrackFilter = new NET_OBJECT_TRACK_FILTER();
    /**
     * 目标事件查询结果的排序方式。0-按照图片抓拍时间排序；1-按照物体进入画面时间排序
    */
    public int              nEventOrderMode;

    public NET_IN_START_FIND_OBJECT_MEDIA_FIND() {
        this.dwSize = this.size();
    }
}

