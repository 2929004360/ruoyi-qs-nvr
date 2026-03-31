package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * className：NET_MEMORY_INFO
 * description：
 * author：251589
 * createTime：2021/2/25 13:38
 *
 * @version v1.0
 */

public class NET_MEMORY_INFO extends NetSDKLib.SdkStructure {
    /**
     * dwSize;
     */
    public int              dwSize;
    /**
     *  总内存, M
     */
    public int              dwTotal;
    /**
     *  剩余内存, M
     */
    public int              dwFree;

    public NET_MEMORY_INFO(){
        this.dwSize = this.size();
    }
}

