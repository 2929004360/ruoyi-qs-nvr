package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 回调的实时数据
*/
public class NET_VIDEO_ANALYSE_ANALYSE_PROC_EX extends NetSDKLib.SdkStructure
{
    /**
     * 回调的数据json内容
    */
    public Pointer          pszOutBuf;
    /**
     * pszOutBuf返回内容大小
    */
    public int              nOutBufLen;
    /**
     * 保留字段
    */
    public byte[]           szResvered = new byte[252];

    public NET_VIDEO_ANALYSE_ANALYSE_PROC_EX() {
    }
}

