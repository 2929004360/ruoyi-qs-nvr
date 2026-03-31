package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 窗口显示源信息
*/
public class NET_SPLIT_WND_SOURCE extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 显示源是否有效
    */
    public int              bEnable;
    /**
     * 设备ID
    */
    public byte[]           szDeviceID = new byte[128];
    /**
     * 控制ID
    */
    public byte[]           szControlID = new byte[128];
    /**
     * 视频通道号
    */
    public int              nVideoChannel;
    /**
     * 视频码流类型
    */
    public int              nVideoStream;
    /**
     * 音频通道
    */
    public int              nAudioChannel;
    /**
     * 音频码流类型
    */
    public int              nAudioStream;
    /**
     * 设备内统一编号的唯一通道号, 只读
    */
    public int              nUniqueChannel;
    /**
     * 是否远程设备
    */
    public int              bRemoteDevice;
    /**
     * 远程设备信息,参见结构体定义 {@link NetSDKLib.NET_REMOTE_DEVICE}
    */
    public NetSDKLib.NET_REMOTE_DEVICE stuRemoteDevice = new NetSDKLib.NET_REMOTE_DEVICE();
    /**
     * 视频源区域,若区域为(0,0,0,0)表示数据无效，设备使用默认值(0,0,8192,8192),参见结构体定义 {@link NET_RECT}
    */
    public NET_RECT         stuSRect = new NET_RECT();
    /**
     * 轮巡时间间隔,单位：秒
    */
    public int              nInterval;

    public NET_SPLIT_WND_SOURCE() {
        this.dwSize = this.size();
    }
}

