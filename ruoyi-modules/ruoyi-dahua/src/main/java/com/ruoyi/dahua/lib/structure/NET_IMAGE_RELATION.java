package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 小图信息
 * @date 2023/04/20 15:11:18
 */
public class NET_IMAGE_RELATION extends NetSDKLib.SdkStructure {
	/**
	 * 大图ID
	 */
    public int              nBigPicId;
	/**
	 * 大图中小图张数
	 */
    public int              nSmallPicNum;
	/**
	 * 大图中小图的信息
	 */
    public NET_SMALL_PIC_INFO[] stuSmallPicInfo = new NET_SMALL_PIC_INFO[32];
	/**
	 * 大图检测小图结果错误码 {@link com.ruoyi.dahua.lib.enumeration.EM_MULTIFACE_DETECT_ERRCODE}
	 */
    public int              emDetectErrCode;
	/**
	 * 保留字节
	 */
    public byte[]           bReserved = new byte[124];

	public NET_IMAGE_RELATION() {
		for (int i = 0; i < stuSmallPicInfo.length; i++) {
			stuSmallPicInfo[i] = new NET_SMALL_PIC_INFO();
		}
	}
}

