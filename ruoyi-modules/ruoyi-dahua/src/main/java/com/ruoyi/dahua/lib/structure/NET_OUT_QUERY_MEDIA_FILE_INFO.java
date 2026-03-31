package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_QueryMediaFile 接口输出参数
*/
public class NET_OUT_QUERY_MEDIA_FILE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 查找到的个数,查询结束情况：查找到的个数CountResult小于需要查询的个数Count
    */
    public int              nCountResult;
    /**
     * 文件信息,是一个数组，表示查询到的文件信息，CountResult = 0时，Info为空数组,参见结构体定义 {@link NET_QUERY_MEDIA_FILE}
    */
    public NET_QUERY_MEDIA_FILE[] stuInfo = new NET_QUERY_MEDIA_FILE[32];
    /**
     * 文件信息个数
    */
    public int              nInfoNum;

    public NET_OUT_QUERY_MEDIA_FILE_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuInfo.length; i++){
            stuInfo[i] = new NET_QUERY_MEDIA_FILE();
        }
    }
}

