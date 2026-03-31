package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 录播主机获取模式入参
 *
 * @author ： 47040
 * @since ： Created in 2020/9/27 17:24
 */
public class NET_IN_COURSECOMPOSITE_CHANNEL_MODE_GET extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 要获取的模式个数
     */
    public int              nCount;

    public NET_IN_COURSECOMPOSITE_CHANNEL_MODE_GET() {
        dwSize = this.size();
    }
}

