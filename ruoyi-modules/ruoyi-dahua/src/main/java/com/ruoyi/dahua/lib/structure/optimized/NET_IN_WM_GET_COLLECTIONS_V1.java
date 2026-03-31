package com.ruoyi.dahua.lib.structure.optimized;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description CLIENT_GetMonitorWallCollectionsV1接口输入参数(获取电视墙预案信息)
 * @date 2022/12/21 14:01
 */
public class NET_IN_WM_GET_COLLECTIONS_V1 extends NetSDKLib.SdkStructure{
    public int                 dwSize;
    public  int                  nMonitorWallID;                // 电视墙ID
    public NET_IN_WM_GET_COLLECTIONS_V1(){
        this.dwSize=this.size();
    }
}
