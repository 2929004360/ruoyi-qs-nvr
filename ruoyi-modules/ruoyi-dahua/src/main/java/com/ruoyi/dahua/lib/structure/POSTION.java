package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * PTZ 绝对坐标
 *
 * @author ： 47040
 * @since ： Created in 2020/10/26 15:56
 */
public class POSTION extends NetSDKLib.SdkStructure {
    /**
     * 水平角度
     */
    public int              nHorizontalAngle;
    /**
     * 垂直角度
     */
    public int              nVerticalAngle;
    /**
     * 放大倍数
     */
    public int              nMagnification;
}

