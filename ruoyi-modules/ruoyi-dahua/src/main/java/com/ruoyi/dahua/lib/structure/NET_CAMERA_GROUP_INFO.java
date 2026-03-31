package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 设备组信息
*/
public class NET_CAMERA_GROUP_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 设备ID
    */
    public byte[]           szDeviceID = new byte[128];
    /**
     * 视频源信息数量
    */
    public int              nCameraCount;
    /**
     * 视频源信息数组,用户分配内存,大小为sizeof(NET_CAMERA_INFO)*nCameraCount,参见结构体定义 {@link NET_CAMERA_INFO}
    */
    public Pointer          pCameras;
    /**
     * 远程设备信息, szDeviceID 没有值时有效,参见结构体定义 {@link NetSDKLib.NET_REMOTE_DEVICE}
    */
    public NetSDKLib.NET_REMOTE_DEVICE stuRemoteDevice = new NetSDKLib.NET_REMOTE_DEVICE();
    /**
     * 保留字节
    */
    public byte[]           reserved = new byte[1024];

    public NET_CAMERA_GROUP_INFO() {
    }
}

