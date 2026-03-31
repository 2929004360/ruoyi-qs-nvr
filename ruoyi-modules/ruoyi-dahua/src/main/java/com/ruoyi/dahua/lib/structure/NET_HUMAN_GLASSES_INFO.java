package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  眼镜佩戴过滤条件 
* @origin autoTool
* @date 2023/05/29 11:40:41
*/
public class NET_HUMAN_GLASSES_INFO extends NetSDKLib.SdkStructure {
/** 
是否过滤启用: TRUE-下发该配置  FALSE-不下发该配置
*/
    public			int            bEnable;
/** 
过滤显示的眼镜佩戴情况的个数,最大值是16
*/
    public			int            nShowListNums;
/** 
过滤显示的眼镜佩戴情况
*/
    public			int[]          emHumanGlassesShowList = new int[16];
/** 
是否戴眼镜，TRUE-有，FALSE-无
*/
    public			int            bHasGlasses;
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[256];

public			NET_HUMAN_GLASSES_INFO(){
}
}

