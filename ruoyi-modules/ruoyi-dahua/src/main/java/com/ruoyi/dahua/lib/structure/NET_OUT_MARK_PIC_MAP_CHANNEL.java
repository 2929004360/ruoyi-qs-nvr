package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_MarkPicMapChannel 接口输出参数
*/
public class NET_OUT_MARK_PIC_MAP_CHANNEL extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 错误码, -1:未知，0: 标记成功, 1: 全部标记失败，地图未上传, 2: 部分标记失败
    */
    public int              nErrorCode;
    /**
     * 错误描述
    */
    public byte[]           szErrorInfo = new byte[256];

    public NET_OUT_MARK_PIC_MAP_CHANNEL() {
        this.dwSize = this.size();
    }
}

