package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 场景变更事件，ReloadPicture(对应事件 EVENT_IVS_SCENE_CHANGE)
 * @origin autoTool
 * @date 2023/10/31 09:24:26
 */
public class DEV_ALRAM_SCENECHANGE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * / 通道号
	 */
    public int              nChannelID;
	/**
	 * / 持续型事件动作, 1表示开始, 2表示停止
	 */
    public int              nEventAction;
	/**
	 * / 时间戳(单位是毫秒)
	 */
    public double           dbPTS;
	/**
	 * / 事件发生的时间
	 */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
	/**
	 * / 事件ID
	 */
    public int              nEventID;
	/**
	 * / 事件对应文件信息
	 */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
	/**
	 * / 图片的序号, 同一时间内(精确到秒)可能有多张图片, 从0开始
	 */
    public byte             byImageIndex;
	/**
	 * / 抓图标志(按位),具体见 NET_RESERVED_COMMON
	 */
    public int              dwSnapFlagMask;
	/**
	 * / 事件公共扩展字段结构体
	 */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
	/**
	 * / 保留字节,留待扩展.
	 */
    public byte[]           bReserved = new byte[1024];

	public DEV_ALRAM_SCENECHANGE_INFO() {
	}
}

