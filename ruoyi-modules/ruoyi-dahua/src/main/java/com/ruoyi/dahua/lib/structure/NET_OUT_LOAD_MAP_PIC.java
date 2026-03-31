package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_LoadMapPic 接口输出参数
*/
public class NET_OUT_LOAD_MAP_PIC extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 错误码, -1:未知, 0: 操作成功, 1: 地图不存在
    */
    public int              nErrorCode;
    /**
     * 图片描述信息,当nErrorCode为0时, 该字段有效,参见结构体定义 {@link NET_MAP_PIC_INFO}
    */
    public NET_MAP_PIC_INFO stuPicInfo = new NET_MAP_PIC_INFO();
    /**
     * 申请的图片二进制数据长度
    */
    public int              nMaxBinaryDataLen;
    /**
     * 返回的图片二进制数据长度
    */
    public int              nRetBinaryDataLen;
    /**
     * 图片二进制数据，由用户申请释放内存，内存大小为nMaxBinaryDataLen
    */
    public Pointer          pszBinaryData;

    public NET_OUT_LOAD_MAP_PIC() {
        this.dwSize = this.size();
    }
}

