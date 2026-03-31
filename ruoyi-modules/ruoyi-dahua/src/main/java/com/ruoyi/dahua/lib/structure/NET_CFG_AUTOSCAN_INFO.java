package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 自动线扫设置
*/
public class NET_CFG_AUTOSCAN_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 用户分配的pAutoScanInfos内存的个数
    */
    public int              nMaxScanInfoNum;
    /**
     * 线扫信息，内存由用户分配，大小应为 nMaxScanInfoNum * sizeof(NET_AUTOSCAN_INFO),参见结构体定义 {@link NET_AUTOSCAN_INFO}
    */
    public Pointer          pAutoScanInfos;
    /**
     * 实际获取/设置的配置的个数，必须小于nMaxScanInfoNum
    */
    public int              nRetScanInfoNum;

    public NET_CFG_AUTOSCAN_INFO() {
        this.dwSize = this.size();
    }
}

