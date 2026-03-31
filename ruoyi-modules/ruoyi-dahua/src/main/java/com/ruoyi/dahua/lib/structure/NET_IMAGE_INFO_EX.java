package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 图片类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 18:54
 */
public class NET_IMAGE_INFO_EX extends NetSDKLib.SdkStructure {
    /**
     *  在上传图片数据中的图片序号
     */
    public int              nIndexInData;
    /**
     *  在二进制数据块中的偏移
     */
    public int              nOffset;
    /**
     *  图片大小,单位:字节
     */
    public int              nLength;
    /**
     *  图片大小,单位:字节，参考{ @link EM_IMAGE_TYPE}
     */
    public int              emType;
}

