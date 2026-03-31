package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * X光机关联图片类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 14:46
 */
public class NET_XRAY_RELATED_IMAGE_INFO extends NetSDKLib.SdkStructure {
    /**
     *  图片类型  {@link com.ruoyi.dahua.lib.enumeration.EM_XRAY_RELATED_IMAGE_TYPE}
     */
    public int              emImageType;
    /**
     *  在二进制数据块中的偏移
     */
    public int              nOffset;
    /**
     *  图片大小,单位:字节	
     */
    public int              nLength;
    /**
     *  预留字节
     */
    public byte             byReserved[] = new byte[64];

    @Override
    public String toString() {
        return "NET_XRAY_RELATED_IMAGE_INFO{" +
                "emImageType=" + emImageType +
                ", nOffset=" + nOffset +
                ", nLength=" + nLength +
                '}';
    }
}

