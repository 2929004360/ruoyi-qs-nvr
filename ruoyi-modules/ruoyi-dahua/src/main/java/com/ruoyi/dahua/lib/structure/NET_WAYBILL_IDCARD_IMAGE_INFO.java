package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 身份图片
*/
public class NET_WAYBILL_IDCARD_IMAGE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 图片类型, -1:未知, 0:身份证图片,1:可见光人脸图片
    */
    public int              nType;
    /**
     * 二进制数据偏移
    */
    public int              nOffset;
    /**
     * 图片大小
    */
    public int              nLength;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[244];

    public NET_WAYBILL_IDCARD_IMAGE_INFO() {
    }
}

