package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_QueryMediaFileOpen 接口输入参数
*/
public class NET_IN_QUERY_MEDIA_FILE_OPEN_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 文件类型，无字段表示查询全部类型,0表示无此字段，1-视频,2-音频,3-图片
    */
    public int              nType;
    /**
     * 上传平台状态，无字段表示查询全部状态,0表示无此字段,1-未上传,2-已上传
    */
    public int              nUpLoadState;
    /**
     * 文件记录案件类型，无字段表示查询全部案件类型,0表示无此字段,1-重点案件,2-普通案件
    */
    public int              nCaseType;
    /**
     * 查询开始时间,格式为yyyy-mm-dd hh:mm:ss，24小时制
    */
    public byte[]           szStartTime = new byte[64];
    /**
     * 查询结束时间,格式为yyyy-mm-dd hh:mm:ss，24小时制
    */
    public byte[]           szEndTime = new byte[64];
    /**
     * 文件记录案件编号
    */
    public byte[]           szCaseID = new byte[64];
    /**
     * 文件记录案件地点
    */
    public byte[]           szCaseAddress = new byte[128];
    /**
     * 文件记录案件备注
    */
    public byte[]           szCaseRemark = new byte[256];
    /**
     * 文件对应的执法记录仪序列号
    */
    public BYTE_ARRAY_64[]  szDeviceID = new BYTE_ARRAY_64[32];
    /**
     * 文件对应的执法记录仪序列号个数
    */
    public int              nDeviceIDNum;

    public NET_IN_QUERY_MEDIA_FILE_OPEN_INFO() {
        this.dwSize = this.size();
    }
}

