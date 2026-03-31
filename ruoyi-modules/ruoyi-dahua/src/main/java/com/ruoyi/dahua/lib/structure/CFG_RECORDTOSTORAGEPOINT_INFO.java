package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 录像存储点映射配置信息
 * @origin autoTool
 * @date 2023/10/30 15:54:19
 */
public class CFG_RECORDTOSTORAGEPOINT_INFO extends NetSDKLib.SdkStructure {
    /**
     * 存储点数目
     */
    public int              nStoragePointNum;
    /**
     * 存储点配置信息
     */
    public CFG_STORAGEPOINT_INFO[] stStoragePoints = new CFG_STORAGEPOINT_INFO[32];

    public CFG_RECORDTOSTORAGEPOINT_INFO() {
        for (int i = 0; i < stStoragePoints.length; i++) {
            stStoragePoints[i] = new CFG_STORAGEPOINT_INFO();
        }
    }
}

