package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 设置源边框高亮使能开关输入参数
 * @date 2022/06/22 09:56:19
 */
public class NET_IN_SPLIT_SET_HIGHLIGHT extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 视频输出通道
     */
    public int              nChannel;
    /**
     * 窗口号
     */
    public int              nWindow;
    /**
     * 边框高亮使能,TRUE-表示高亮
     */
    public int              bHighLightEn;
    /**
     * 边框颜色
     */
    public NET_COLOR_RGBA   stuColor = new NET_COLOR_RGBA();
    /**
     * 边框闪烁次数
     */
    public int              nBlinkTimes;
    /**
     * 闪烁间隔时间,单位ms
     */
    public int              nBlinkInterval;

    public NET_IN_SPLIT_SET_HIGHLIGHT() {
        this.dwSize = this.size();
    }
}

