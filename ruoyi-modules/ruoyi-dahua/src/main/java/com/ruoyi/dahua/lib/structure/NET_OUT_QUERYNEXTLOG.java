package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description CLIENT_QueryNextLog 输出参数
 * @date 2023/06/12 09:23:55
 */
public class NET_OUT_QUERYNEXTLOG extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 用户指定分配结构体个数,需大于等于NET_IN_GETNEXTLOG的nGetCount
	 */
    public int              nMaxCount;
	/**
	 * 返回日志信息,缓存大小由用户指定,大小为nMaxCount*sizeof(NET_LOG_INFO)
	 */
    public Pointer          pstuLogInfo;
	/**
	 * 实际返回日志条数
	 */
    public int              nRetCount;

	public NET_OUT_QUERYNEXTLOG() {
		this.dwSize = this.size();
	}
}

