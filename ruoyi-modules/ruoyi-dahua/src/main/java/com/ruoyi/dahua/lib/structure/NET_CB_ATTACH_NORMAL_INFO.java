package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_AttachNormalUsingJson的回调信息
*/
public class NET_CB_ATTACH_NORMAL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * Json形式出参
    */
    public Pointer          pstuOutJson;
    /**
     * Json长度, 单位:字节
    */
    public int              nstuOutJsonLen;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[2044];

    public NET_CB_ATTACH_NORMAL_INFO() {
    }
}

