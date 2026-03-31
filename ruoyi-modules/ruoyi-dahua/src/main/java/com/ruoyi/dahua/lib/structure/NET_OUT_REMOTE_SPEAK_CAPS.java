package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_GetDevCaps NET_REMOTE_SPEAK_CAPS 出参
*/
public class NET_OUT_REMOTE_SPEAK_CAPS extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 实际返回的能力集个数
    */
    public int              nRetCapNum;
    /**
     * 能力集,参见结构体定义 {@link NET_SPEAK_CAPS}
    */
    public Pointer          pstuCaps;
    /**
     * 字节对齐
    */
    public Pointer          pReserved;
    /**
     * 用户分配的最大能力集数组个数
    */
    public int              nMaxCapNum;

    public NET_OUT_REMOTE_SPEAK_CAPS() {
        this.dwSize = this.size();
    }
}

