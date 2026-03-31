package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421557
 * @description 消控室值班行为检测事件查询结果 ( CLIENT_FindFileEx + DH_FILE_QUERY_FIRE_CONTROL_MONITOR )
 * @origin autoTool
 * @date 2023/10/23 11:00:18
 */
public class MEDIAFILE_FIRE_CONTROL_MONITOR_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 结构体大小
     */
    public int              dwSize;
    /**
     * / 通道号
     */
    public int              nChannelID;
    /**
     * / 开始时间
     */
    public NET_TIME         stuBeginTime = new NET_TIME();
    /**
     * / 结束时间
     */
    public NET_TIME         stuEndTime = new NET_TIME();
    /**
     * / 文件路径
     */
    public byte[]           szGlobalSceneFilePath = new byte[260];
    /**
     * / 文件长度
     */
    public int              nGlobalSceneFileSize;
    /**
     * / 规则
     */
    public byte[]           szRule = new byte[64];
    /**
     * / 包围盒，8192坐标系
     */
    public NET_RECT         stuBoundingBox = new NET_RECT();
    /**
     * / 小图信息
     */
    public NET_EVENT_IMAGE_INFO stuImageInfo = new NET_EVENT_IMAGE_INFO();
    /**
     * / 文件类型，1:jpg, 2:dav，默认:jpg
     */
    public int              nFileType;
    /**
     * / 为TRUE表示仅stuStartTimeRealUTC和stuEndTimeRealUTC有效(仅使用stuStartTimeRealUTC和stuEndTimeRealUTC), 为FALSE表示仅stuBeginTime和stuEndTime有效(仅使用stuBeginTime和stuEndTime)
     */
    public int              bRealUTC;
    /**
     * / UTC开始时间(标准UTC时间), 与stuEndTimeRealUTC配对使用
     */
    public NET_TIME         stuStartTimeRealUTC = new NET_TIME();
    /**
     * / UTC结束时间(标准UTC时间), 与stuStartTimeRealUTC配对使用
     */
    public NET_TIME         stuEndTimeRealUTC = new NET_TIME();
    /**
     * 唯一标识一个事件
    */
    public byte[]           szEventUUIDStr = new byte[32];

    public MEDIAFILE_FIRE_CONTROL_MONITOR_INFO() {
        this.dwSize = this.size();
    }
}

