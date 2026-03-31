package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 运单信息
*/
public class NET_WAYBILL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 运单号码
    */
    public byte[]           szNumber = new byte[256];
    /**
     * 身份证信息个数
    */
    public int              nIDCardInfoNum;
    /**
     * 身份证信息,参见结构体定义 {@link NET_WAYBILL_IDCARD_INFO}
    */
    public NET_WAYBILL_IDCARD_INFO[] stuIDCardInfo = new NET_WAYBILL_IDCARD_INFO[16];
    /**
     * 运单图片信息个数
    */
    public int              nImageInfoNum;
    /**
     * 运单图片信息,参见结构体定义 {@link NET_WAYBILL_IMAGE_INFO}
    */
    public NET_WAYBILL_IMAGE_INFO[] stuImageInfo = new NET_WAYBILL_IMAGE_INFO[16];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_WAYBILL_INFO() {
        for(int i = 0; i < stuIDCardInfo.length; i++){
            stuIDCardInfo[i] = new NET_WAYBILL_IDCARD_INFO();
        }
        for(int i = 0; i < stuImageInfo.length; i++){
            stuImageInfo[i] = new NET_WAYBILL_IMAGE_INFO();
        }
    }
}

