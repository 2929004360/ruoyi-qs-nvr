package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @descriptionCLIENT_DownloadByRecordFileEx3 接口输入参数
 * @origin autoTool
 * @date 2023/11/30 19:38:56
 */
public class NET_IN_DOWNLOAD extends NetSDKLib.SdkStructure {
	/**
	 * / 结构体大小：赋值为结构体大小
	 */
    public int              dwSize;
	/**
	 * / 偏移大小
	 */
    public int              nOffLength;
	/**
	 * / 录像文件信息
	 */
    public LPNET_RECORDFILE_INFO lpRecordFile = new LPNET_RECORDFILE_INFO();
	/**
	 * / 录像文件保存路径
	 */
    public Pointer          pszSavedFileName;
	/**
	 * / 回放进度回调函数
	 */
    public NetSDKLib.fDownLoadPosCallBack cbDownLoadPos;
	/**
	 * / 回放进度回调函数自定义数据
	 */
    public Pointer          dwUserData;
	/**
	 * / 回放数据回调函数
	 */
    public NetSDKLib.fDataCallBack fDownLoadDataCallBack;
	/**
	 * / 回放数据回调函数自定义数据
	 */
    public Pointer          dwDataUser;
	/**
	 * / 是否使用UTC时间
	 */
    public int              bUseUTCTime;
	/**
	 * / 字节对齐
	 */
    public byte[]           szResvered = new byte[4];

	public NET_IN_DOWNLOAD() {
		this.dwSize = this.size();
	}
}

