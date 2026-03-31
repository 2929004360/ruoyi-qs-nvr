package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 421657
 * @description 录像存储点映射配置扩展信息
 * @origin autoTool
 * @date 2023/10/30 15:54:19
 */
public class CFG_RECORDTOSTORAGEPOINT_EX_INFO extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 最大录像通道配置个数
     */
    public int              nMaxChannelRecord;
    /**
     * 实际返回通道配置个数
     */
    public int              nRetChannelRecord;
    /**
     * 通道存储点配置信息, 由用户申请内存, 大小为sizeof(CFG_RECORDTOSTORAGEPOINT_INFO)*nMaxChannelRecord, refer to {@link CFG_RECORDTOSTORAGEPOINT_INFO}
     */
    public Pointer          pstRecordStorage;

    public CFG_RECORDTOSTORAGEPOINT_EX_INFO() {
        this.dwSize = this.size();
    }
}

