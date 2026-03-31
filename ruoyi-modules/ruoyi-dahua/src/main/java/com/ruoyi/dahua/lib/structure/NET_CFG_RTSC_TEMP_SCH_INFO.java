package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 临时方案配置,对应配置项NET_EM_CFG_RTSC_TEMP_SCH
 * @date 2021/09/28
 */
public class NET_CFG_RTSC_TEMP_SCH_INFO extends NetSDKLib.SdkStructure {
	/**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  临时方案开始时间个数
     */
    public int              nStartTimeNum;
    /**
     *  临时方案开始时间   int[0]时       Int[1]分  后两位预留
     */
    public int[]            nStartTime = new int[4];
    /**
     *  临时方案结束时间个数
     */
    public int              nEndTimeNum;
    /**
     *  临时方案结束时间   int[0]时      Int[1]分  后两位预留
     */
    public int[]            nEndTime = new int[4];
    /**
     *  环信息个数
     */
    public int              nRingsNum;
    /**
     *  环信息
     */
    public NET_RINGS_INFO[] stuRingsInfo = (NET_RINGS_INFO[]) new NET_RINGS_INFO().toArray(4);
    /**
     *  跟随相位列表个数
     */
    public int              nOverLapsNum;
    /**
     *  跟随相位列表
     */
    public NET_OVER_LAPS_INFO[] stuOverLapsInfo = (NET_OVER_LAPS_INFO[]) new NET_OVER_LAPS_INFO().toArray(16);

    public NET_CFG_RTSC_TEMP_SCH_INFO(){
        this.dwSize = this.size();
    }
}

