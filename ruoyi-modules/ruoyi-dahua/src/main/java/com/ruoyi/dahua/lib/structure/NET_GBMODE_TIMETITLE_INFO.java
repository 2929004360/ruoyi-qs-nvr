package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 时间标题信息
*/
public class NET_GBMODE_TIMETITLE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 是否开启时间标题
    */
    public int              bEnable;
    /**
     * 是否显示星期
    */
    public int              bShowWeek;
    /**
     * OSD显示位置,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_TITLE_POSITON}
    */
    public int              emPositon;
    /**
     * 文本对齐方式: 0x0000 左对齐 0x0001 X坐标中对齐 0x0010 Y坐标中对齐 0x0011 居中, 0x0002 右对齐 0x0020 按照顶部对齐 0x0100 换行对齐
    */
    public int              nTextAlign;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[128];

    public NET_GBMODE_TIMETITLE_INFO() {
    }
}

