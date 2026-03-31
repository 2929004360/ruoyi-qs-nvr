package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 目标特征向量重建结果事件(对应事件DH_ALARM_FACE_FEATURE_ABSTRACT)
 * @origin autoTool
 * @date 2023/06/28 10:00:12
 */
public class ALARM_FACE_FEATURE_ABSTRACT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 事件ID
	 */
    public int              nEventID;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
	/**
	 * 时间戳(单位是毫秒)
	 */
    public double           PTS;
	/**
	 * 字节对齐
	 */
    public byte[]           byReserved1 = new byte[4];
	/**
	 * 实际返回的目标特征向量重建结果个数
	 */
    public int              nRetAbstractInfo;
	/**
	 * 目标特征向量重建结果
	 */
    public FACE_FEATURE_ABSTRACT_INFO[] stuAbstractInfo = new FACE_FEATURE_ABSTRACT_INFO[100];
	/**
	 * 保留扩展字节
	 */
    public byte[]           byReserved = new byte[512];

	public ALARM_FACE_FEATURE_ABSTRACT_INFO() {
		for (int i = 0; i < stuAbstractInfo.length; i++) {
			stuAbstractInfo[i] = new FACE_FEATURE_ABSTRACT_INFO();
		}
	}
}

