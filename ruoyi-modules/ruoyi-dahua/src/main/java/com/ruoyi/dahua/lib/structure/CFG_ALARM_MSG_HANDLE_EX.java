package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 报警联动信息扩展
*/
public class CFG_ALARM_MSG_HANDLE_EX extends NetSDKLib.SdkStructure
{
    /**
     * 标题文本字体大小，单位像素,对应SnapshotTitle，个数与stuSnapshotTitle匹配。
    */
    public int[]            nSnapshotTitleFontSize = new int[256];
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public CFG_ALARM_MSG_HANDLE_EX() {
    }
}

