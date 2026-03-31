package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 工作服信息
*/
public class MEDIAFILE_CLOTHES_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 工作服颜色,参见枚举定义 {@link NetSDKLib.EM_CLOTHES_COLOR}
    */
    public int              emColor;
    /**
     * 工作服状态,参见枚举定义 {@link NetSDKLib.EM_WORKCLOTHES_STATE}
    */
    public int              emState;
    /**
     * 预留字段
    */
    public byte[]           byReserved = new byte[512];

    public MEDIAFILE_CLOTHES_INFO() {
    }
}

