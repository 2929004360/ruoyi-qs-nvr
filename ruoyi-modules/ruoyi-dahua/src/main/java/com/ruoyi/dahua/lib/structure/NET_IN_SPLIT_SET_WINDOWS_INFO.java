package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CIENT_SetSplitWindowsInfo接口输入参数
*/
public class NET_IN_SPLIT_SET_WINDOWS_INFO extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 画面分割所在的视频输出通道
    */
    public int              nChannel;
    /**
     * 拼接屏ID
    */
    public byte[]           szCompositeID = new byte[64];
    /**
     * 窗口信息,参见结构体定义 {@link NetSDKLib.NET_BLOCK_COLLECTION}
    */
    public NetSDKLib.NET_BLOCK_COLLECTION stuInfos = new NetSDKLib.NET_BLOCK_COLLECTION();

    public NET_IN_SPLIT_SET_WINDOWS_INFO() {
        this.dwSize = this.size();
    }
}

