package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 通道标题信息
*/
public class NET_GBMODE_CHANNELTITLE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * OSD显示位置,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_TITLE_POSITON}
    */
    public int              emPositon;
    /**
     * 文本对齐方式，取值方式同上
    */
    public int              nTextAlign;
    /**
     * OSD使能状态及文本信息, 一般左上角数组大小为1，左下角大小为3，右下角大小为5,参见结构体定义 {@link NET_TITLE_OSD_INFO}
    */
    public NET_TITLE_OSD_INFO[] stuOSD = new NET_TITLE_OSD_INFO[8];
    /**
     * OSD使能状态及文本信息有效个数;
    */
    public int              nOSDNum;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_GBMODE_CHANNELTITLE_INFO() {
        for(int i = 0; i < stuOSD.length; i++){
            stuOSD[i] = new NET_TITLE_OSD_INFO();
        }
    }
}

