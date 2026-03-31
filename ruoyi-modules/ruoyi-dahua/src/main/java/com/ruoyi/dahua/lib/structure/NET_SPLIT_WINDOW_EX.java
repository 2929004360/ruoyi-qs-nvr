package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 分割窗口信息
*/
public class NET_SPLIT_WINDOW_EX extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 窗口是否有视频源
    */
    public int              bEnable;
    /**
     * 窗口ID
    */
    public int              nWindowID;
    /**
     * 控制ID
    */
    public byte[]           szControlID = new byte[128];
    /**
     * 窗口区域, 自由分割模式下有效,参见结构体定义 {@link NetSDKLib.NET_RECT}
    */
    public NetSDKLib.NET_RECT stuRect = new NetSDKLib.NET_RECT();
    /**
     * 坐标是否满足直通条件
    */
    public int              bDirectable;
    /**
     * 窗口Z次序
    */
    public int              nZOrder;
    /**
     * 显示信息,参见结构体定义 {@link NET_SPLIT_WND_SOURCE}
    */
    public NET_SPLIT_WND_SOURCE stuSource = new NET_SPLIT_WND_SOURCE();
    /**
     * 窗口是否被锁定位置
    */
    public int              bLock;
    /**
     * 窗口是否具有粘附效果
    */
    public int              bDock;
    /**
     * 窗口是否为会议模式状态
    */
    public int              bMeetingMode;
    /**
     * 窗口是否开启音频使能
    */
    public int              bAudioEnable;
    /**
     * 窗口是否开启轮询
    */
    public int              bTourEnable;
    /**
     * 用户申请的OSD个数
    */
    public int              nMaxOSDNum;
    /**
     * 返回的OSD个数
    */
    public int              nRetOSDNum;
    /**
     * OSD信息指针，用户申请内存,参见结构体定义 {@link NetSDKLib.NET_SPLIT_OSD}
    */
    public Pointer          pstuOSD;

    public NET_SPLIT_WINDOW_EX() {
        this.dwSize = this.size();
    }
}

