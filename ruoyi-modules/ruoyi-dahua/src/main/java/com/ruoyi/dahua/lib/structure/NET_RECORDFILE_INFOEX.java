package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 录像文件信息(扩展)
 * @date 2022/11/03 09:22:00
 */
public class NET_RECORDFILE_INFOEX extends NetSDKLib.SdkStructure {
	/**
	 * 录像文件信息
	 */
    public NetSDKLib.NET_RECORDFILE_INFO stuRecordFileInfo = new NetSDKLib.NET_RECORDFILE_INFO();
	/**
	 * 字节对齐
	 */
    public byte[]           bReserved1 = new byte[4];
	/**
	 * 关联的事件列表,事件类型列表,参见智能分析事件类型
	 */
    public int[]            nEventLists = new int[256];
	/**
	 * 事件总数
	 */
    public int              nEventCount;
	/**
	 * 文件标志，参考枚举EM_RECORD_SNAP_FLAG_TYPE
	 */
    public int[]            emFlagsList = new int[128];
	/**
	 * 文件标志总个数；
	 */
    public int              nFlagCount;
	/**
	 * 为TRUE表示仅stuStartTimeRealUTC和stuEndTimeRealUTC有效(仅使用stuStartTimeRealUTC和stuEndTimeRealUTC),
	 * 为FALSE表示仅stuRecordFileInfo中的starttime和endtime有效(仅使用stuRecordFileInfo中的starttime和endtime)
	 */
    public int              bRealUTC;
	/**
	 * UTC开始时间(标准UTC时间), 与stuEndTimeRealUTC配对使用
	 */
    public NET_TIME         stuStartTimeRealUTC = new NET_TIME();
	/**
	 * UTC结束时间(标准UTC时间), 与stuStartTimeRealUTC配对使用
	 */
    public NET_TIME         stuEndTimeRealUTC = new NET_TIME();
	/**
	 * 预留字节
	 */
    public byte[]           bReserved = new byte[452];

	public NET_RECORDFILE_INFOEX() {
	}
}

