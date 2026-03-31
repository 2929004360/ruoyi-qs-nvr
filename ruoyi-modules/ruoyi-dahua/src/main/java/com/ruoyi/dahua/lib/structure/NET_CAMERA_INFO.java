package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 视频源信息
*/
public class NET_CAMERA_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 统一编号,  -1表示设备自动分配通道号
    */
    public int              nUniqueChannel;
    /**
     * 通道号
    */
    public int              nChannel;
    /**
     * 控制编号, 键盘用户用，用编号定位通道，提高操作效率
    */
    public byte[]           szControlID = new byte[128];
    /**
     * 设备是否启用, 固定true
    */
    public int              bEnable;
    /**
     * 名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 逻辑通道所属设备ID
    */
    public byte[]           szDeviceID = new byte[128];
    /**
     * 输入通道类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_CAMERA_CHANNEL_TYPE}
    */
    public int              emChannelType;
    /**
     * 视频码流,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_VIDEO_STREAM}
    */
    public int              emVideoStream;
    /**
     * 保留字节
    */
    public byte[]           reserved = new byte[128];

    public NET_CAMERA_INFO() {
    }
}

