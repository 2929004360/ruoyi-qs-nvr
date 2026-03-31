package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 获取屏幕窗口解码信息CLIENT_MonitorWallGetWindowInfo出参
*/
public class NET_OUT_MW_GET_WINDOW_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 用户使用该结构体时,dwSize 需赋值为 sizeof(NET_OUT_MW_GET_WINDOW_INFO)
    */
    public int              dwSize;
    /**
     * 用户需要获取的解码通道信息个数,从0开始获取,用户指定
    */
    public int              nVideoInfoNum;
    /**
     * 解码通道信息列表,用户申请空间,用户申请列表个数和nVideoInfoNum一致,申请内存大小为sizeof(NET_MW_GET_WINDOW_INFO)*nVideoInfoNum,参见结构体定义 {@link NET_MW_GET_WINDOW_INFO}
    */
    public Pointer          pNetVideoChannelInfo;
    /**
     * 返回实际获取到的解码通道信息个数,SDK返回
    */
    public int              nRetVideoInfoNum;

    public NET_OUT_MW_GET_WINDOW_INFO() {
        this.dwSize = this.size();
    }
}

