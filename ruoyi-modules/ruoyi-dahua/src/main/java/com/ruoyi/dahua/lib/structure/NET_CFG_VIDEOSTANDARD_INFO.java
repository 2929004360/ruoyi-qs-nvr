package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 视频制式配置
 * 对应枚举：NET_EM_CFG_VIDEOSTANDARD
 *
 * @author 47040
 * @since Created at 2026-05-17
 */
public class NET_CFG_VIDEOSTANDARD_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int dwSize;
    /**
     * 视频制式
     * 0-PAL
     * 1-NTSC
     * 2-SECAM
     */
    public int emVideoStandard;
    /**
     * 国家/地区配置
     */
    public byte[] szCountry = new byte[128];

    public NET_CFG_VIDEOSTANDARD_INFO() {
        dwSize = this.size();
    }
}
