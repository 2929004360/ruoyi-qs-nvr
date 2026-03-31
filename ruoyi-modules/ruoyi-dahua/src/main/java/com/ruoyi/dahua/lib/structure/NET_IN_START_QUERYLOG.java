package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_StartQueryLog 输入参数
 * @date 2023/06/12 09:18:50
 */
public class NET_IN_START_QUERYLOG extends NetSDKLib.SdkStructure {
    public int              dwSize;
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
	 * 对emLogType进行扩展分类查询方式;TRUE 表明使用szLogTypeEx参数,FALSE使用emLogType
	 */
    public int              bLogTypeExFlag;
	/**
	 * szLogTypeEx数组真实长度，最大64个
	 */
    public int              nLogTypeExNum;
	/**
	 * 报警主机日志类型扩展透传数组,如果需要查询的类型超过64个,则分组多次查询
	 */
    public byte[]           szLogTypeEx = new byte[64 * 128];
	/**
	 * 查询结果排序方式 {@link EM_RESULT_ORDER_TYPE}
	 */
    public int              emResultOrder;
	/**
	 * 是否使用UTC时间
	 */
    public int              bSendByUTCTime;
	/**
	 * UTC查询开始时间
	 */
    public NET_TIME         stuStartTimeRealUTC = new NET_TIME();
	/**
	 * UTC查询结束时间
	 */
    public NET_TIME         stuEndTimeRealUTC = new NET_TIME();

	public NET_IN_START_QUERYLOG() {
		this.dwSize = this.size();
	}
}

