package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.CFG_NET_TIME;

/**
 * @author 251823
 * @version 1.0
 * @description 比较录像详细信息
 * @date 2021/11/07
 */
public class NET_VIDEO_DIAGNOSIS_COMPARE_RECORD_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  录像开始时间
     */
    public CFG_NET_TIME     stuStartTime;
    /**
     *  录像保存位置,参考{ @link EM_SOURCE_VIDEO_LOCATION}
     */
    public int              emSourceType;
    /**
     *  录像类型,参考{ @link EM_VIDEO_TYPE}
     */
    public int              emRecordType;
    /**
     *  视频诊断录像开始时间为当前实际检测时间的前PreSeconds秒(当该字段与StartTime同时存在时, 以该字段优先)
     */
    public int              nPreSeconds;
    /**
     *  保留字节
     */
    public byte[]           szReserved = new byte[256];
}

