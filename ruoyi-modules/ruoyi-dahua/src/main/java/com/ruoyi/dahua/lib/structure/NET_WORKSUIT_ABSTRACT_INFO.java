package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 工装建模结果信息
 * @date 2022/10/09 11:22:52
 */
public class NET_WORKSUIT_ABSTRACT_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 工装合规组ID
	 */
    public byte[]           szGroupID = new byte[64];
	/**
	 * 工装建模进度,百分比
	 */
    public int              nProcess;
	/**
	 * 该次工装建模的token值
	 */
    public int              nToken;
	/**
	 * 工装图片建模成功的个数
	 */
    public int              nSucceedCnt;
	/**
	 * 工装图片建模失败的个数
	 */
    public int              nFailedCnt;
	/**
	 * 建模失败的全景图图片名称列表个数
	 */
    public int              nFailedSourceFileNamesNum;
	/**
	 * 建模失败的全景图唯一标识符列表个数
	 */
    public int              nFailedSourceUIDNum;
	/**
	 * 建模失败的全景图图片名称列表
	 */
    public Byte64Arr[]      szFailedSourceFileNames = new Byte64Arr[200];
	/**
	 * 建模失败的全景图唯一标识符列表
	 */
    public Byte64Arr[]      szFailedSourceUID = new Byte64Arr[200];
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved1 = new byte[4];
	/**
	 * 工装建模错误码个数
	 */
    public int              nErrCodeNum;
	/**
	 * 工装建模错误码，参考枚举{ @link EM_WORKSUIT_ABSTRACT_ERRCODE}
	 */
    public int[]            emErrCode = new int[200];
	/**
	 * 预留字段
	 */
    public byte[]           szReserved = new byte[1024];

	public NET_WORKSUIT_ABSTRACT_INFO() {
	}
}

