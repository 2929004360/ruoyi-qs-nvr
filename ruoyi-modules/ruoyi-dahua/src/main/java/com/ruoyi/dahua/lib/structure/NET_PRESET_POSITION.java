package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 预置点的坐标和放大倍数
 * @date 2022/2/16 9:33
 */
public class NET_PRESET_POSITION extends NetSDKLib.SdkStructure {
    public    int           nHorizontal;                          // 水平坐标
    public  int             nVertical;                            // 垂直坐标
    public  int             nMagnification;                       // 放大倍数
}

