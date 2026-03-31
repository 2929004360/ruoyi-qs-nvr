package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description
 * @date 2021/8/9 16:32
 */
public class NET_TRAFFIC_LATTICE_SCREEN_LOGO_INFO extends NetSDKLib.SdkStructure {
    public  int             emLogoType;                           /** LOGO类型 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_LATTICE_SCREEN_LOGO_TYPE} */
    public byte[]           szContent = new byte[128];            // emLogoType为文字时，为文字内容;emLogoType为图片时，为图片名称
}

