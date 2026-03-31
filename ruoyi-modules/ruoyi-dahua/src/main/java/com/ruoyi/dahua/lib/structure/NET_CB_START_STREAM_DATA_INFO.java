package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_AttachStartStreamData的回调信息
*/
public class NET_CB_START_STREAM_DATA_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 导出数据包描述,参见结构体定义 {@link NET_STREAM_DATA_FILE_INFO}
    */
    public NET_STREAM_DATA_FILE_INFO stuFileInfo = new NET_STREAM_DATA_FILE_INFO();
    /**
     * 数据完成标志
    */
    public int              bEOF;
    /**
     * 数据传输进度, 百分比, 范围[0, 100]
    */
    public int              nProgress;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[2048];

    public NET_CB_START_STREAM_DATA_INFO() {
    }
}

