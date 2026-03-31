package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;

public class CFG_TIME_SECTION_ARRAY_10 extends NetSDKLib.SdkStructure {
    /**
     * 参见结构体定义 {@link NetSDKLib.CFG_TIME_SECTION}
    */
    public NetSDKLib.CFG_TIME_SECTION[] obj_10 = new NetSDKLib.CFG_TIME_SECTION[10];

    public CFG_TIME_SECTION_ARRAY_10() {
        for(int i = 0; i < obj_10.length; i++){
            obj_10[i] = new NetSDKLib.CFG_TIME_SECTION();
        }
    }
}

