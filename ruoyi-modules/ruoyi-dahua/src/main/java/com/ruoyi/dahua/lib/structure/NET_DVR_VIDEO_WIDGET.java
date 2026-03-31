package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 视频OSD叠加配置
 * @date 2022/06/17 11:14:34
 */
public class NET_DVR_VIDEO_WIDGET extends NetSDKLib.SdkStructure {
	/**
	 * 物件的前景；按字节表示,分别为红、绿、蓝和透明度
	 */
    public int              rgbaFrontground;
	/**
	 * 物件的背景；按字节表示,分别为红、绿、蓝和透明度
	 */
    public int              rgbaBackground;
	/**
	 * 位置,物件边距与整长的比例*8191
	 */
    public RECT             rcRelativePos = new RECT();
	/**
	 * 预览叠加使能
	 */
    public int              bPreviewBlend;
	/**
	 * 编码叠加使能
	 */
    public int              bEncodeBlend;
	/**
	 * 保留
	 */
    public byte[]           bReserved = new byte[4];

	public NET_DVR_VIDEO_WIDGET() {
	}
}

