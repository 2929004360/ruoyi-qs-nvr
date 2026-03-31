package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * X光机集中判图时的开包信息
*/
public class NET_XRARY_UNPACKING_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 包裹标识
    */
    public byte[]           szPackageTag = new byte[32];
    /**
     * 主视角包裹内危险物品个数
    */
    public int              nMainViewInsideNum;
    /**
     * 主视角包裹内危险物品信息,参见结构体定义 {@link NET_INSIDE_OBJECT}
    */
    public NET_INSIDE_OBJECT[] stuMainViewInside = new NET_INSIDE_OBJECT[32];
    /**
     * 侧视角包裹内危险物品个数
    */
    public int              nSlaveViewInsideNum;
    /**
     * 侧视角包裹内危险物品信息,参见结构体定义 {@link NET_INSIDE_OBJECT}
    */
    public NET_INSIDE_OBJECT[] stuSlaveViewInside = new NET_INSIDE_OBJECT[32];
    /**
     * 开包包裹图片信息个数
    */
    public int              nImageInfoNum;
    /**
     * 开包包裹图片信息,参见结构体定义 {@link NET_XRAY_UNPACKING_IMAGE_INFO}
    */
    public NET_XRAY_UNPACKING_IMAGE_INFO[] stuImageInfo = new NET_XRAY_UNPACKING_IMAGE_INFO[32];
    /**
     * 二进制数据长度
    */
    public int              nBufferLen;
    /**
     * 二进制数据，仅用于fXRayUnpackingResult，内存由NetSDK申请释放
    */
    public Pointer          pBuffer;
    /**
     * 开包检查员ID
    */
    public byte[]           szInspectorID = new byte[32];
    public byte[]           reserved = new byte[472-NetSDKLib.POINTERSIZE];

    public NET_XRARY_UNPACKING_INFO() {
        for(int i = 0; i < stuMainViewInside.length; i++){
            stuMainViewInside[i] = new NET_INSIDE_OBJECT();
        }
        for(int i = 0; i < stuSlaveViewInside.length; i++){
            stuSlaveViewInside[i] = new NET_INSIDE_OBJECT();
        }
        for(int i = 0; i < stuImageInfo.length; i++){
            stuImageInfo[i] = new NET_XRAY_UNPACKING_IMAGE_INFO();
        }
    }
}

