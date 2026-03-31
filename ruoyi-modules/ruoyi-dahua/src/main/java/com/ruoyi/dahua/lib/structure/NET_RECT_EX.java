package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 区域；各边距按整长8192的比例
*/
public class NET_RECT_EX extends NetSDKLib.SdkStructure
{
    public short            nLeft;
    public short            nTop;
    public short            nRight;
    public short            nBottom;

    public NET_RECT_EX() {
    }
}

