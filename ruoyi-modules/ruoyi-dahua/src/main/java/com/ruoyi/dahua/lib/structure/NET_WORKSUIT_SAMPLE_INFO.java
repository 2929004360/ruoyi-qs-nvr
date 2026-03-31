package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 工装合规样本信息
 * @date 2022/10/08 19:51:42
 */
public class NET_WORKSUIT_SAMPLE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 合规库组ID
	 */
    public byte[]           szGroupID = new byte[64];
	/**
	 * 合规库组名称
	 */
    public byte[]           szGroupName = new byte[128];
	/**
	 * 全景图唯一标识符，由平台生成
	 */
    public byte[]           szSourceUID = new byte[64];
	/**
	 * 全景图图片名称
	 */
    public byte[]           szSourceFileName = new byte[64];
	/**
	 * 图片信息
	 */
    public NetSDKLib.SCENE_IMAGE_INFO stuImageInfo = new NetSDKLib.SCENE_IMAGE_INFO();
	/**
	 *文件路径, 支持HTTP URL表示; 支持FTP URL表示; 支持服务器本地路径,填写图片路径后无需填写图片信息中的offset与length字段
	 */
    public byte[]           szFilePath = new byte[256];
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1792];

	public NET_WORKSUIT_SAMPLE_INFO() {
	}
}

