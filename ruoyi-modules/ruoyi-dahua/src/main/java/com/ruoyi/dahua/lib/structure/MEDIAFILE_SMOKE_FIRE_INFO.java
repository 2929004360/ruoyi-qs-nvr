package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 烟火检测查询结果 ( CLIENT_FindFileEx + DH_FILE_QUERY_SMOKE_FIRE )
*/
public class MEDIAFILE_SMOKE_FIRE_INFO extends NetSDKLib.SdkStructure
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
     * 开始时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuBeginTime = new NetSDKLib.NET_TIME();
    /**
     * 结束时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuEndTime = new NetSDKLib.NET_TIME();
    /**
     * 文件路径
    */
    public byte[]           szGlobalSceneFilePath = new byte[260];
    /**
     * 文件长度
    */
    public int              nGlobalSceneFileSize;
    /**
     * 规则
    */
    public byte[]           szRule = new byte[64];
    /**
     * 包围盒，8192坐标系,参见结构体定义 {@link NET_RECT}
    */
    public NET_RECT         stuBoundingBox = new NET_RECT();
    /**
     * 小图信息,参见结构体定义 {@link NET_EVENT_IMAGE_INFO}
    */
    public NET_EVENT_IMAGE_INFO stuImageInfo = new NET_EVENT_IMAGE_INFO();
    /**
     * 文件类型，1:jpg, 2:dav，默认:jpg
    */
    public int              nFileType;
    /**
     * 为TRUE表示仅stuStartTimeRealUTC和stuEndTimeRealUTC有效(仅使用stuStartTimeRealUTC和stuEndTimeRealUTC), 为FALSE表示仅stuBeginTime和stuEndTime有效(仅使用stuBeginTime和stuEndTime)
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

    public MEDIAFILE_SMOKE_FIRE_INFO() {
        this.dwSize = this.size();
    }
}

