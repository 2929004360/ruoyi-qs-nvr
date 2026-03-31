package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 热成像人体测温查询条件( CLIENT_FindFileEx + DH_FILE_QUERY_ANATOMY_TEMP_DETECT )
 * @date 2021/02/22
 */
public class MEDIAFILE_ANATOMY_TEMP_DETECT_PARAM extends NetSDKLib.SdkStructure {
	 /**
     *  结构体大小 
     */
    public int              dwSize;
    /**
     *  通道号,从0开始,-1表示查询所有通道
     */
    public int              nChannelID;
    /**
     *  开始时间 
     */
    public NET_TIME         stuBeginTime;
    /**
     *  结束时间 
     */
    public NET_TIME         stuEndTime;
    /**
     *  过滤信息
     */
    public NET_ANATOMY_TEMP_DETECT_FILTER stuFilter;
	/**
	 * 为TRUE表示仅下发stuStartTimeRealUTC和stuEndTimeRealUTC(不下发stuBeginTime, stuEndTime),
	 * 为FALSE表示仅下发stuBeginTime, stuEndTime(不下发stuStartTimeRealUTC和stuEndTimeRealUTC)
	 */
    public int              bOnlySupportRealUTC;
	/**
	 * UTC开始时间(标准UTC时间), 与stuEndTimeRealUTC配对使用，与(stuBeginTime, stuEndTime)互斥
	 */
    public NET_TIME         stuStartTimeRealUTC;
	/**
	 * UTC结束时间(标准UTC时间), 与stuStartTimeRealUTC配对使用，与(stuBeginTime, stuEndTime)互斥
	 */
    public NET_TIME         stuEndTimeRealUTC;

    public MEDIAFILE_ANATOMY_TEMP_DETECT_PARAM(){
        this.dwSize = this.size();
    }		
}

