package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421557
 * @description 消控室值班行为检测事件查询条件 ( CLIENT_FindFileEx + DH_FILE_QUERY_FIRE_CONTROL_MONITOR )
 * @origin autoTool
 * @date 2023/10/23 10:56:04
 */
public class MEDIAFILE_FIRE_CONTROL_MONITOR_PARAM extends NetSDKLib.SdkStructure {
    /**
     * / 结构体大小
     */
    public int              dwSize;
    /**
     * / 文件类型,0:查询任意类型,1:查询jpg图片,2:查询dav
     */
    public int              nFileType;
    /**
     * / 通道号从0开始,-1表示查询所有通道
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
     * / 事件类型列表,参见智能分析事件类型，如EVENT_IVS_ALL、EVENT_IVS_SLEEP_DETECT
     */
    public int[]            nEventList = new int[256];
    /**
     * / 事件总数
     */
    public int              nEventCount;
    /**
     * / 过滤规则
     */
    public NET_FIRE_CONTROL_MONITOR_FILTER stuFilter = new NET_FIRE_CONTROL_MONITOR_FILTER();
    /**
     * / 为TRUE表示仅下发stuStartTimeRealUTC和stuEndTimeRealUTC(不下发stuBeginTime, stuEndTime), 为FALSE表示仅下发stuBeginTime, stuEndTime(不下发stuStartTimeRealUTC和stuEndTimeRealUTC)
     */
    public int              bOnlySupportRealUTC;
    /**
     * / UTC开始时间(标准UTC时间), 与stuEndTimeRealUTC配对使用，与(stuBeginTime, stuEndTime)互斥
     */
    public NET_TIME         stuStartTimeRealUTC = new NET_TIME();
    /**
     * / UTC结束时间(标准UTC时间), 与stuStartTimeRealUTC配对使用，与(stuBeginTime, stuEndTime)互斥
     */
    public NET_TIME         stuEndTimeRealUTC = new NET_TIME();

    public MEDIAFILE_FIRE_CONTROL_MONITOR_PARAM() {
        this.dwSize = this.size();
    }
}

