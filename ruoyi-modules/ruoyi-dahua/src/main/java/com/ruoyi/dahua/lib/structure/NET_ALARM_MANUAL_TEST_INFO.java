package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 291189
 * @description  手动测试上报事件 (对应 DH_ALARM_MANUAL_TEST)
 * @date 2022/07/25 15:32:25
 */
public class NET_ALARM_MANUAL_TEST_INFO extends NetSDKLib.SdkStructure {
	/**
	 事件动作 0:脉冲
	 */
    public			int            nAction;
	/**
	 通道号
	 */
    public			int            nChannel;
	/**
	 事件发生的时间,标准的（不带时区偏差的）UTC时间
	 */
    public			NET_TIME_EX    stuUTC = new NET_TIME_EX();
	/**
	 配件序列号
	 */
    public			byte[]         szSN = new byte[32];
	/**
	 配件名称
	 */
    public			byte[]         szName = new byte[32];
	/**
	 配件所属区域名称
	 */
    public			byte[]         szAreaName = new byte[128];
    /**
     * 设备名称
    */
    public byte[]           szMachineName = new byte[256];
    /**
     * 事件抓图图片ID
    */
    public byte[]           szAlarmId = new byte[128];
    /**
     * 抓图文件路径，上传到云端的路径
    */
    public byte[]           szFilePath = new byte[256];
    /**
     * 上报图片的随机序号
    */
    public byte[]           szDelayUploadSeq = new byte[64];
    /**
     * 配件型号
    */
    public byte[]           szModel = new byte[32];
    /**
     * 配件类型
    */
    public byte[]           szDevType = new byte[32];
    /**
     * 传感器类型
    */
    public byte[]           szSenseMethod = new byte[32];
    /**
     * 文件类型, 1: 图片(JPEG), 2: 视频(MP4)
    */
    public int              nFileType;
    /**
     * 事件发生的UTC时间，不带时区,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuRealUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * RealUTC 是否有效，bRealUTC 为 TRUE 时，用 stuRealUTC，否则 stuRealUTC 字段无效(用原事件结构体中的 事件发生时间/事件触发时间(UTC) 字段)
    */
    public int              bRealUTC;
    /**
     * 区域信息,参见结构体定义 {@link NET_MANUAL_TEST_AREA_INFO}
    */
    public Pointer          pstuAreaInfo;
    /**
     * 文件信息,参见结构体定义 {@link NET_MANUAL_TEST_FILE_INFO}
    */
    public Pointer          pstuFilesInfo;
	/**
	 保留字节
	 */
    public			byte[]         szReserved = new byte[180-2*NetSDKLib.POINTERSIZE];

	public			NET_ALARM_MANUAL_TEST_INFO(){
	}
}

