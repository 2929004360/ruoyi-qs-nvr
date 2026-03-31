package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 用户组关联通知信息
 * @date 2023/03/15 21:15:00
 */
public class NET_NOTIFY_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 通知类型 {@link com.ruoyi.dahua.lib.enumeration.NET_NOTIFY_TYPE}
	 */
    public int              emType;
	/**
	 * 是否接受报警事件的推送
	 */
    public int              bAlarmEvents;
	/**
	 * 是否接受故障事件的推送
	 */
    public int              bSystemEvents;
	/**
	 * 是否接受操作事件的推送
	 */
    public int              bOperationEvents;

	public NET_NOTIFY_INFO() {
	}
}

