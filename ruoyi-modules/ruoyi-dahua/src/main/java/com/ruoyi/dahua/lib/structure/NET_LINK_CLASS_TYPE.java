package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 联动业务大类选项
*/
public class NET_LINK_CLASS_TYPE extends NetSDKLib.SdkStructure
{
    /**
     * 待级联的业务所在通道号
    */
    public int              nChannel;
    /**
     * 待级联的业务大类,参见枚举定义 {@link NetSDKLib.EM_SCENE_CLASS_TYPE}
    */
    public int              emClassType;
    /**
     * 预留字段
    */
    public byte[]           szResvered = new byte[252];

    public NET_LINK_CLASS_TYPE() {
    }
}

