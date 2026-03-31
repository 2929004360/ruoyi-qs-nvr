package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_MOBILE_ENFORCE_UPLOAD_FLAG;

/**
 * 采集站和手持终端文件信息查询条件 入参
 * 接口 {@link NetSDKLib#CLIENT_FindFileEx}
 * 枚举 {@link NetSDKLib.EM_FILE_QUERY_TYPE#NET_FILE_QUERY_MOBILE_ENFORCE}
 *
 * @author 47040
 * @since Created at 2021/5/31 10:07
 */
public class MEDIAFILE_MOBILE_ENFORCE_PARAM extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 通道号从 0 开始, -1 表示查询所有通道
     */
    public int              nChannelID;
    /**
     * 开始时间
     */
    public NET_TIME         stuBeginTime;
    /**
     * 结束时间
     */
    public NET_TIME         stuEndTime;
    /**
     * 文件上传标志 枚举值 {@link EM_MOBILE_ENFORCE_UPLOAD_FLAG}
     */
    public int              emUploadFlag;
    /**
     * 文件类型个数, 为0表示查询任意类型
    */
    public int              nFileTypeNum;
    /**
     * 文件类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_FINDFILE_TYPE}
    */
    public int[]            emFileType = new int[32];
    /**
     * 手持设备序列号个数
    */
    public int              nSerialNoNum;
    /**
     * 文件对应手持设备序列号，和RegistID条件互斥， 2个条件同时存在，SerialNo优先，忽略RegistID
    */
    public BYTE_ARRAY_128[] szSerialNo = new BYTE_ARRAY_128[256];
    /**
     * 手持设备序列号个数
    */
    public int              nRegistIDNum;
    /**
     * 文件对应手持设备平台注册ID，和SerialNo条件互斥， 2个条件同时存在，SerialNo优先，忽略RegistID
    */
    public BYTE_ARRAY_128[] szRegistID = new BYTE_ARRAY_128[256];
    /**
     * 采集开始时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuCollectStartTime = new NetSDKLib.NET_TIME();
    /**
     * 采集结束时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuCollectEndTime = new NetSDKLib.NET_TIME();
    /**
     * 是否使用采集时间, 为TRUE表示stuCollectStartTime/stuCollectEndTime或stuCollectStartTimeRealUTC/stuCollectEndTimeRealUTC有效，为FALSE表示无效
    */
    public int              bIsColloectTime;
    /**
     * 为TRUE表示仅下发stuStartTimeRealUTC和stuEndTimeRealUTC(不下发stuBeginTime, stuEndTime), 为FALSE表示仅下发stuBeginTime, stuEndTime(不下发stuStartTimeRealUTC和stuEndTimeRealUTC)
    */
    public int              bOnlySupportRealUTC;
    /**
     * UTC开始时间(标准UTC时间), 与stuEndTimeRealUTC配对使用，与(stuBeginTime, stuEndTime)互斥,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * UTC结束时间(标准UTC时间), 与stuStartTimeRealUTC配对使用，与(stuStartTime, stuEndTime)互斥,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuEndTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 采集UTC开始时间(标准UTC时间), 文件的采集时间是一个时间点；如果stuCollectStartTimeRealUTC和stuCollectEndTimeRealUTC同时存在时，则按采集时间段查询,stuStartTimeRealUTC和stuEndTimeRealUTC无效,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuCollectStartTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 采集UTC结束时间(标准UTC时间), 文件的采集时间是一个时间点；如果stuCollectStartTimeRealUTC和stuCollectEndTimeRealUTC同时存在时，则按采集时间段查询,stuStartTimeRealUTC和stuEndTimeRealUTC无效,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuCollectEndTimeRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 是否使用采集UTC时间, 为TRUE表示下发stuCollectStartTimeRealUTC和stuCollectEndTimeRealUTC, 为FALSE表示下发stuCollectStartTimestuCollectEndTime
    */
    public int              bUserCollectTimeRealUTC;
    /**
     * 录像或抓图文件标志, 不设置标志表示查询所有文件,参见枚举定义 {@link NetSDKLib.EM_RECORD_SNAP_FLAG_TYPE}
    */
    public int[]            emFlagLists = new int[128];
    /**
     * 标志总数
    */
    public int              nFlagCount;
    /**
     * 警员ID
    */
    public byte[]           szPoliceID = new byte[32];

    public MEDIAFILE_MOBILE_ENFORCE_PARAM() {
        dwSize = this.size();
    }
}

