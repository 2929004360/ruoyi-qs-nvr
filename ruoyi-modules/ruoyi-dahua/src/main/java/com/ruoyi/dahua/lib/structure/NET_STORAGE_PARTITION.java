package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

public class NET_STORAGE_PARTITION extends NetSDKLib.SdkStructure {
    public int              dwSize;
    public byte[]           szName = new byte[128];               // 名称
    public long             nTotalSpace;                          // 总空间, byte
    public long             nFreeSpace;                           // 剩余空间, byte
    public byte[]           szMountOn = new byte[64];             // 挂载点
    public byte[]           szFileSystem = new byte[16];          // 文件系统
    public int              nStatus;                              // 分区状态, 0-LV不可用, 1-LV可用
    /**
     * 设备是否支持当前文件系统, TRUE:支持， FALSE:不支持
    */
    public int              bIsSupportFs;
    /**
     * bIsSupportFs字段是否有效，FALSE表示设备是否支持系统未知
    */
    public int              abIsSupportFs;

	public NET_STORAGE_PARTITION() {
		this.dwSize =  this.size();
	}
}

