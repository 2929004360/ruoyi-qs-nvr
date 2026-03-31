package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 查询记录数条件
 * @date 2023/05/05 11:12:30
 */
public class QUERY_DEVICE_LOG_PARAM extends NetSDKLib.SdkStructure {
    /**
     * 查询日志类型 {@link com.ruoyi.dahua.lib.enumeration.DH_LOG_QUERY_TYPE}
     */
    public int              emLogType;
    /**
     * 查询日志的开始时间
     */
    public NET_TIME         stuStartTime = new NET_TIME();
    /**
     * 查询日志的结束时间
     */
    public NET_TIME         stuEndTime = new NET_TIME();
    /**
     * 在时间段中从第几条日志开始查询,开始第一次查询可设为0
     */
    public int              nStartNum;
    /**
     * 一次查询中到第几条日志结束,日志返回条数的最大值为1024
     */
    public int              nEndNum;
    /**
     * 日志数据结构体类型,0:表示DH_DEVICE_LOG_ITEM；1:表示DH_DEVICE_LOG_ITEM_EX
     */
    public byte             nLogStuType;
    /**
     * 保留 对齐
     */
    public byte[]           reserved = new byte[3];
    /**
     * 通道号,0:兼容之前表示所有通道号,所以通道号从1开始; 1:第一个通道
     */
    public int              nChannelID;
    public byte[]           bReserved = new byte[40];

    public QUERY_DEVICE_LOG_PARAM() {
    }
}

