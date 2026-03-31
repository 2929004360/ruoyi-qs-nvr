package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 动态识别的结构化信息对象
*/
public class NET_ACCESS_CTL_OBJECT_PROPERTIES extends NetSDKLib.SdkStructure
{
    /**
     * 红领巾识别结果 0:未知 1:未佩戴 2:已佩戴；255:未使能算法识别
    */
    public int              nRedScarfResult;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[60];

    public NET_ACCESS_CTL_OBJECT_PROPERTIES() {
    }
}

