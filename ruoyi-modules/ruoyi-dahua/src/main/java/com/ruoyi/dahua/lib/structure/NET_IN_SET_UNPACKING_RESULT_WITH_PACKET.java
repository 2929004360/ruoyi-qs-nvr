package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_SetUnpackingResultWithPacket 接口输入参数
*/
public class NET_IN_SET_UNPACKING_RESULT_WITH_PACKET extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 开包结果,参见结构体定义 {@link NET_XRARY_UNPACKING_INFO}
    */
    public NET_XRARY_UNPACKING_INFO stuUnpackingResult = new NET_XRARY_UNPACKING_INFO();
    /**
     * 二进制数据长度
    */
    public int              nBufferLen;
    /**
     * 二进制数据,用户申请内存
    */
    public Pointer          pBuffer;

    public NET_IN_SET_UNPACKING_RESULT_WITH_PACKET() {
        this.dwSize = this.size();
    }
}

