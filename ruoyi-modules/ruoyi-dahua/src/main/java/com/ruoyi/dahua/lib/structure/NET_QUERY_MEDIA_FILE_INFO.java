package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 采集文件信息
*/
public class NET_QUERY_MEDIA_FILE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 文件类型,1-视频,2-音频,3-图片
    */
    public int              nType;
    /**
     * 文件格式,1-DAV,2-MP4,3-MP3,4-AAC,5-JPG,6-MOV,7-AVI,8-FLV,9-WAV,10-WMA,11-BMP,12-PNG,13-RAW,14-M4V
    */
    public int              nFormat;
    /**
     * 文件长度，单位字节
    */
    public long             nLength;
    /**
     * 文件开始时间,格式为yyyy-mm-dd hh:mm:ss，24小时制
    */
    public byte[]           szStartTime = new byte[64];
    /**
     * 文件结束时间,格式为yyyy-mm-dd hh:mm:ss，24小时制
    */
    public byte[]           szEndTime = new byte[64];
    /**
     * 文件采集时间,格式为yyyy-mm-dd hh:mm:ss，24小时制
    */
    public byte[]           szCollectTime = new byte[64];
    /**
     * 文件对应的执法记录仪序列号
    */
    public byte[]           szDeviceID = new byte[64];
    /**
     * 文件存储路径
    */
    public byte[]           szFilePath = new byte[128];
    /**
     * 文件名称
    */
    public byte[]           szFileName = new byte[128];
    /**
     * 文件可修改备注,参见结构体定义 {@link NET_MEDIA_FILE_REMARK_INFO}
    */
    public NET_MEDIA_FILE_REMARK_INFO stuFileRemark = new NET_MEDIA_FILE_REMARK_INFO();
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[64];

    public NET_QUERY_MEDIA_FILE_INFO() {
    }
}

