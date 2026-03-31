package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 存储设备io信息
 * @date 2021/07/09
 */
public class NET_DEV_IO_STAT_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  存储设备名称
     */
    public byte[]           szDeviceName = new byte[32];
    /**
     *  每秒完成的读次数
     */
    public int              nReadIos;
    /**
     *  每秒对该设备的读请求被合并次数
     */
    public int              nReadMerges;
    /**
     *  每秒读数据量(kB为单位)
     */
    public long             nReadSectors;
    /**
     *  每秒完成的写次数
     */
    public int              nWriteIos;
    /**
     *  每秒对该设备的写请求被合并次数
     */
    public int              nWriteMerges;
    /**
     *  每秒写数据量(kB为单位)
     */
    public long             nWriteSectors;
    /**
     *  平均每次IO操作的数据量(扇区数为单位)
     */
    public int              nSize;
    /**
     *  平均等待处理的IO请求队列长度
     */
    public int              nQueue;
    /**
     *  平均每次IO请求等待时间(包括等待时间和处理时间，毫秒为单位)
     */
    public int              nWait;
    /**
     *  平均每次IO请求的处理时间(毫秒为单位)
     */
    public int              nSvctm;
    /**
     *  采用周期内用于IO操作的时间比率，即IO队列非空的时间比率,范围[0,100]
     */
    public int              nUtil;
    /**
     *  字节对齐
     */
    public byte[]           szReserved1 = new byte[4];
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[128];
}

