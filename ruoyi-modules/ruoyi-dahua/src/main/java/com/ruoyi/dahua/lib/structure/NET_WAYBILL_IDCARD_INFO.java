package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 身份证信息
*/
public class NET_WAYBILL_IDCARD_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 身份证号码
    */
    public byte[]           szNumber = new byte[32];
    /**
     * 性别, -1:未知 0:男性 1:女性
    */
    public int              nGender;
    /**
     * 身份图片个数
    */
    public int              nImageCount;
    /**
     * 身份图片,参见结构体定义 {@link NET_WAYBILL_IDCARD_IMAGE_INFO}
    */
    public NET_WAYBILL_IDCARD_IMAGE_INFO[] stuImage = new NET_WAYBILL_IDCARD_IMAGE_INFO[8];
    /**
     * 姓名
    */
    public byte[]           szName = new byte[32];
    /**
     * 民族
    */
    public byte[]           szNation = new byte[32];
    /**
     * 出生日期
    */
    public byte[]           szBirthDate = new byte[32];
    /**
     * 住址
    */
    public byte[]           szAddress = new byte[256];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[260];

    public NET_WAYBILL_IDCARD_INFO() {
        for(int i = 0; i < stuImage.length; i++){
            stuImage[i] = new NET_WAYBILL_IDCARD_IMAGE_INFO();
        }
    }
}

