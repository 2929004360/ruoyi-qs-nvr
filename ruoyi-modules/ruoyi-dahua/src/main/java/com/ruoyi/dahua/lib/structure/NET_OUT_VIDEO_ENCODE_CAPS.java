package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 获取视频编码能力集出参
 * @date 2022/07/21 13:40:57
 */
public class NET_OUT_VIDEO_ENCODE_CAPS extends NetSDKLib.SdkStructure {
	/**
	 * 该结构体大小
	 */
    public int              dwSize;
	/**
	 * 支持SVC编码的视频格式个数
	 */
    public int              nSvcEncodeTypesNum;
	/**
	 * 支持SVC编码的视频格式
	 */
    public byte[]           szSvcEncodeTypes = new byte[32 * 32];
	/**
	 * 支持自定义I帧间隔的码流个数
	 */
    public int              nGOPCustomEncodesNum;
	/**
	 * 支持自定义I帧间隔的码流
	 */
    public byte[]           szGOPCustomEncodes = new byte[32 * 32];
	/**
	 * 最大SVC-T支持层数
	 */
    public int              nMaxSVCTLevel;
	/**
	 * 隐私遮挡通用能力集
	 */
    public NET_PRIVACY_MASKING_CAPS stuPrivacyMaskingCaps = new NET_PRIVACY_MASKING_CAPS();

	public NET_OUT_VIDEO_ENCODE_CAPS() {
		this.dwSize = this.size();
	}
}

