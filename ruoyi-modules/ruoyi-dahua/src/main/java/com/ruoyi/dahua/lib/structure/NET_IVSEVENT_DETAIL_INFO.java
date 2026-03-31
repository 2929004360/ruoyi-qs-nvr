package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件详细信息
*/
public class NET_IVSEVENT_DETAIL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 报警事件详情,参见结构体定义 {@link NET_IVSEVENT_DATA}
    */
    public NET_IVSEVENT_DATA[] stuEventInfo = new NET_IVSEVENT_DATA[32];
    /**
     * 报警事件数量
    */
    public int              nEventInfoNum;
    /**
     * 图片信息数组数量
    */
    public int              nImageInfoNum;
    /**
     * 图片信息数组,该接口只有emType、szPath两个字段有效,参见结构体定义 {@link NET_IMAGE_INFO_EX2}
    */
    public NET_IMAGE_INFO_EX2[] stuImageInfo = new NET_IMAGE_INFO_EX2[32];
    /**
     * 报警录像rtsp地址
    */
    public byte[]           szVideoRtsp = new byte[128];
    /**
     * 报警事件录像下载地址url
    */
    public byte[]           szVideoDownload = new byte[128];
    /**
     * 报警音频rtsp地址
    */
    public byte[]           szAudioRtsp = new byte[128];
    /**
     * 报警事件音频下载地址url
    */
    public byte[]           szAudioDownload = new byte[128];
    /**
     * 报警事件物联网数据下载地址url
    */
    public byte[]           szIotDownload = new byte[256];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_IVSEVENT_DETAIL_INFO() {
        for(int i = 0; i < stuEventInfo.length; i++){
            stuEventInfo[i] = new NET_IVSEVENT_DATA();
        }
        for(int i = 0; i < stuImageInfo.length; i++){
            stuImageInfo[i] = new NET_IMAGE_INFO_EX2();
        }
    }
}

