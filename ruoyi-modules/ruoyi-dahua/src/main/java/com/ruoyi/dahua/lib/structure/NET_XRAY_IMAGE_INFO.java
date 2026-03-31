package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * X光机视角类型
 *
 * @author ： 260611
 * @since ： Created in 2021/10/19 09:19
 */
public class NET_XRAY_IMAGE_INFO extends NetSDKLib.SdkStructure {
    /**
     *  视图类型
     */
    public int              emViewType;
    /**
     *  图片类型
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
    public byte             byReserved[] = new byte[1024];

    @Override
    public String toString() {
        return "NET_XRAY_IMAGE_INFO{" +
                "emViewType=" + emViewType +
                ", emImageType=" + emImageType +
                ", nOffset=" + nOffset +
                ", nLength=" + nLength +
                '}';
    }
}

