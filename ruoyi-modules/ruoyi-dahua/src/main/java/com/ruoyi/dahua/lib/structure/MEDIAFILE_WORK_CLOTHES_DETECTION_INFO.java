package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 作业管控查询结果 ( CLIENT_FindFileEx + DH_FILE_QUERY_WORK_CLOTHES_DETECTION )
*/
public class MEDIAFILE_WORK_CLOTHES_DETECTION_INFO extends NetSDKLib.SdkStructure
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
     * 安全帽信息,参见结构体定义 {@link MEDIAFILE_HELMET_INFO}
    */
    public MEDIAFILE_HELMET_INFO stuHelmetInfo = new MEDIAFILE_HELMET_INFO();
    /**
     * 工作服信息,参见结构体定义 {@link MEDIAFILE_CLOTHES_INFO}
    */
    public MEDIAFILE_CLOTHES_INFO stuClothesInfo = new MEDIAFILE_CLOTHES_INFO();
    /**
     * 全景图路径
    */
    public byte[]           szGlobalSceneImageFilePath = new byte[260];
    /**
     * 全景图长度
    */
    public int              nGlobalSceneImageFileLength;
    /**
     * 人体小图路径
    */
    public byte[]           szHumanSmallImageFilePath = new byte[260];
    /**
     * 人体小图长度
    */
    public int              nHumanSmallImageFileLength;
    /**
     * 包围盒，8192坐标系,参见结构体定义 {@link NET_RECT}
    */
    public NET_RECT         stuHumanBoundingBox = new NET_RECT();
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
    /**
     * 预留字段
    */
    public byte[]           byReserved = new byte[968];

    public MEDIAFILE_WORK_CLOTHES_DETECTION_INFO() {
        this.dwSize = this.size();
    }
}

