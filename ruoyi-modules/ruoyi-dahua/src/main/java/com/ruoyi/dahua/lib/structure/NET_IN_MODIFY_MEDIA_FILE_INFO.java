package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_ModifyMediaFile 接口输入参数
*/
public class NET_IN_MODIFY_MEDIA_FILE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 文件Id号个数
    */
    public int              nIdNum;
    /**
     * 文件Id号，是一个数组，表示要修改备注的文件Id号
    */
    public int[]            nId = new int[32];
    /**
     * 文件备注,参见结构体定义 {@link NET_MEDIA_FILE_REMARK_INFO}
    */
    public NET_MEDIA_FILE_REMARK_INFO[] stuFileRemark = new NET_MEDIA_FILE_REMARK_INFO[32];
    /**
     * 文件备注个数
    */
    public int              nFileRemarkNum;

    public NET_IN_MODIFY_MEDIA_FILE_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuFileRemark.length; i++){
            stuFileRemark[i] = new NET_MEDIA_FILE_REMARK_INFO();
        }
    }
}

