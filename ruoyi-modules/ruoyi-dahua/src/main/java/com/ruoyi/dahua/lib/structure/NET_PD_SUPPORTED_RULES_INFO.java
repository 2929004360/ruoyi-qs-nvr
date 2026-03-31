package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 支持大类业务字段
*/
public class NET_PD_SUPPORTED_RULES_INFO extends NetSDKLib.SdkStructure
{
    /**
     * Normal大类业务列表
    */
    public BYTE_ARRAY_64[]  szNormal = new BYTE_ARRAY_64[64];
    /**
     * Normal大类业务数量
    */
    public int              nNormalNum;
    /**
     * ChangeDetection 大类业务列表最大数量
    */
    public int              nChangeDetectionMaxNum;
    /**
     * ChangeDetection 大类业务列表,由用户申请内存,大小为32*nChangeDetectionMaxNum,每个元素的长度都固定为：  32
    */
    public Pointer          pszChangeDetection;
    /**
     * ChangeDetection 大类业务列表实际数量
    */
    public int              nChangeDetectionRetNum;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1012-NetSDKLib.POINTERSIZE];

    public NET_PD_SUPPORTED_RULES_INFO() {
        for(int i = 0; i < szNormal.length; i++){
            szNormal[i] = new BYTE_ARRAY_64();
        }
    }
}

