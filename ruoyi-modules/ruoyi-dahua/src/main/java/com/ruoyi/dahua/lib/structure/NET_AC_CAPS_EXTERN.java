package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * ACCaps能力集扩展
*/
public class NET_AC_CAPS_EXTERN extends NetSDKLib.SdkStructure
{
    /**
     * 支持互锁回能力掩码，支持位或 bit0-本地反潜;bit1-网络反潜主控;bit2-网络反潜分控
    */
    public int              bSupportCollectUserByDev;
    /**
     * 设备中可用于提取信息的可用读头能力信息数量
    */
    public int              nDevCollectAttributeNum;
    /**
     * 设备中可用于提取信息的可用读头能力信息,参见结构体定义 {@link NET_DEV_COLLECT_ATTRIBUTE}
    */
    public NET_DEV_COLLECT_ATTRIBUTE[] stuDevCollectAttribute = new NET_DEV_COLLECT_ATTRIBUTE[16];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_AC_CAPS_EXTERN() {
        for(int i = 0; i < stuDevCollectAttribute.length; i++){
            stuDevCollectAttribute[i] = new NET_DEV_COLLECT_ATTRIBUTE();
        }
    }
}

