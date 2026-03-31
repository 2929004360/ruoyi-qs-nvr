package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 自适应下载录像 按时间 {@link NetSDKLib#CLIENT_AdaptiveDownloadByTime} 出参
 * 入参 {@link NET_OUT_ADAPTIVE_DOWNLOAD_BY_TIME}
 *
 * @author 47040
 * @since Created at 2021/6/19 18:23
 */
public class NET_OUT_ADAPTIVE_DOWNLOAD_BY_TIME extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;

    public NET_OUT_ADAPTIVE_DOWNLOAD_BY_TIME() {
        dwSize = this.size();
    }
}

