package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 一张大图检测到小图的结果
 * @date 2023/04/20 15:18:00
 */
public class NET_IMAGE_RELATION_EX extends NetSDKLib.SdkStructure {
	/**
	 * 图片的请求ID
	 */
    public byte[]           szRequestID = new byte[64];
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
	 * 大图检测结果列表
	 */
    public NET_IMAGE_RELATION_LIST[] stuImageRelation = new NET_IMAGE_RELATION_LIST[32];
	/**
	 * 大图检测结果列表个数
	 */
    public int              nstuImageRelationNum;
	/**
	 * 查询令牌
	 */
    public int              nToken;
	/**
	 * 小图信息
	 */
    public NET_IMAGE_RELATION_EX_IMAGEINFO[] stuImageInfo = new NET_IMAGE_RELATION_EX_IMAGEINFO[32];
	/**
	 * 小图信息个数
	 */
    public int              nImageNum;
	/**
	 * 小图图片数据
	 */
    public Pointer          pData;
	/**
	 * 保留字节
	 */
    public byte[]           bReserved = new byte[248];

	public NET_IMAGE_RELATION_EX() {
		for (int i = 0; i < stuSmallPicInfo.length; i++) {
			stuSmallPicInfo[i] = new NET_SMALL_PIC_INFO();
		}
		for (int i = 0; i < stuImageRelation.length; i++) {
			stuImageRelation[i] = new NET_IMAGE_RELATION_LIST();
		}
		for (int i = 0; i < stuImageInfo.length; i++) {
			stuImageInfo[i] = new NET_IMAGE_RELATION_EX_IMAGEINFO();
		}
	}
}

