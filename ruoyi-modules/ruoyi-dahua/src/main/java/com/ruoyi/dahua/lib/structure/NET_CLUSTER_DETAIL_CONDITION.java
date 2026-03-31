package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 查询条件
 * @date 2022/10/09 14:06:13
 */
public class NET_CLUSTER_DETAIL_CONDITION extends NetSDKLib.SdkStructure {
	/**
	 * 聚档周期Key, 一次只能查询一个聚档周期内的数据,必填.与开始结束时间取交集
	 */
    public int              nClusterDate;
	/**
	 * 是否需要过滤ProfileID
	 */
    public int              bIsNeedProfileID;
	/**
	 * 聚档结果ID，如果不带该字段或为空，则默认不过滤ProfileID
	 */
    public int              nProfileID;
    /**
     * 批次结果ID，如果该字段为-1，则默认不过滤BatchID
    */
    public int              nBatchID;
    /**
     * 工作人员过滤条件，-1:全返回 0:仅返回非工作人员 1:仅返回工作人员
    */
    public int              nStaffType;
    /**
     * 区域过滤条件 -1视为全返回。大于等于0时根据当前区域配置过滤数据上报。区域内记录存在重叠，所以单次仅支持单区域查询。
    */
    public int              nAreadID;
    /**
     * 视频通道号，有效值从1开始。
    */
    public int              nChannel;
    /**
     * 预置点编号
    */
    public int              nPtzPresetId;
    /**
     * 区域ID个数
    */
    public int              nAreaIDExCount;
    /**
     * area ID
    */
    public int[]            nAreaIDEx = new int[20];
    /**
     * 是否使用标准UTC时间
    */
    public int              bRealUTC;
    /**
     * 查询起始时间, 标准UTC时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 查询结束时间, 标准UTC时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuEndRealUTC = new NetSDKLib.NET_TIME();
    /**
     * 是否使用带时区偏差的UTC事件
    */
    public int              bUTC;
    /**
     * 查询起始时间, 带时区偏差的UTC时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuStartUTC = new NetSDKLib.NET_TIME();
    /**
     * 查询结束时间, 带时区偏差的UTC时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuEndUTC = new NetSDKLib.NET_TIME();
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[48];

	public NET_CLUSTER_DETAIL_CONDITION() {
	}
}

