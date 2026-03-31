package com.ruoyi.dahua.lib.structure.optimized;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.structure.BYTE_64;
import com.sun.jna.Pointer;

/**
 * @author 291189
 * @version 1.0
 * @description CLIENT_GetMonitorWallCollectionsV1接口输出参数(获取电视墙预案信息)
 * @date 2022/12/21 14:09
 */
public class NET_OUT_WM_GET_COLLECTIONS_V1 extends NetSDKLib.SdkStructure {
    public  int                        dwSize;
    public byte[]						szReserved=new byte[4];
    public Pointer pBuff;           // 电视墙预案
    public int                         nBuffSize;		 // 电视墙预案申请的大小，由用户申请
    public int                         nRetBuffSize;    // 电视墙预案实际返回的长度
    public int							nNameCount;		 // 电视墙预案名称个数
    public BYTE_64[]				szName=new BYTE_64[128]; // 电视墙预案名称

    public NET_OUT_WM_GET_COLLECTIONS_V1(){
        for(int i=0;i<szName.length;i++){
            szName[i]=new BYTE_64();
        }
        this.dwSize=this.size();
    }
}
