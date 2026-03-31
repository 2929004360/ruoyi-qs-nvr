package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 错误详情
*/
public class NET_ERROR_DETAIL extends NetSDKLib.SdkStructure
{
    /**
     * 下发失败时，对失败原因的一个补充描述
    */
    public BYTE_ARRAY_256[] szExtraInfo = new BYTE_ARRAY_256[20];
    /**
     * 补充描述有效个数
    */
    public int              nRetExtraInfoNum;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_ERROR_DETAIL() {
    }
}

