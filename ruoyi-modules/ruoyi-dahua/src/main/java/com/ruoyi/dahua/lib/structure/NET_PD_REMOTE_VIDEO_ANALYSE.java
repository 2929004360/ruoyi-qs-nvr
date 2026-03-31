package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 前智能接入能力
*/
public class NET_PD_REMOTE_VIDEO_ANALYSE extends NetSDKLib.SdkStructure
{
    /**
     * 是否支持智能分析
    */
    public int              bSupport;
    /**
     * 支持的智能大类列表数量
    */
    public int              nSupportedSceneNum;
    /**
     * 支持的智能大类列表
    */
    public BYTE_ARRAY_16[]  szSupportedScene = new BYTE_ARRAY_16[32];
    /**
     * 支持大类业务内容,参见结构体定义 {@link NET_PD_SUPPORTED_RULES_INFO}
    */
    public NET_PD_SUPPORTED_RULES_INFO stuSupportedRules = new NET_PD_SUPPORTED_RULES_INFO();
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_PD_REMOTE_VIDEO_ANALYSE() {
        for(int i = 0; i < szSupportedScene.length; i++){
            szSupportedScene[i] = new BYTE_ARRAY_16();
        }
    }
}

