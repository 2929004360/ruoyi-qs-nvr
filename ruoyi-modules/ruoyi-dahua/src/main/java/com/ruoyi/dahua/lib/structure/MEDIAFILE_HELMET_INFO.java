package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 安全帽信息
*/
public class MEDIAFILE_HELMET_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 安全帽颜色,参见枚举定义 {@link NetSDKLib.EM_CLOTHES_COLOR}
    */
    public int              emColor;
    /**
     * 安全帽状态,参见枚举定义 {@link NetSDKLib.EM_WORK_HELMET_STATE}
    */
    public int              emState;
    /**
     * 预留字段
    */
    public byte[]           byReserved = new byte[512];

    public MEDIAFILE_HELMET_INFO() {
    }
}

