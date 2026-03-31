package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 转码水印参数
*/
public class NET_TRANSCODE_IMAGE_WATER_MARK extends NetSDKLib.SdkStructure
{
    /**
     * 图片名称,通过CLIENT_GetCapsOfTransCode获取
    */
    public byte[]           szImage = new byte[256];
    /**
     * Position字段是否生效
    */
    public int              bPosition;
    /**
     * nOpacity字段是否生效
    */
    public int              bOpacity;
    /**
     * 水印位置0：左上 1：右上 2：左下 3：右下
    */
    public int              nPosition;
    /**
     * 图片水印的不透明度，范围0-100，0表示全透明，默认100
    */
    public int              nOpacity;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[512];

    public NET_TRANSCODE_IMAGE_WATER_MARK() {
    }
}

