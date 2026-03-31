package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_SetWaybillInfo 接口输入参数
*/
public class NET_IN_SET_WAYBILL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 运单信息个数
    */
    public int              nWaybillInfo;
    /**
     * 运单信息,参见结构体定义 {@link NET_WAYBILL_INFO}
    */
    public NET_WAYBILL_INFO[] stuWaybillInfo = new NET_WAYBILL_INFO[16];
    /**
     * 字节对齐
    */
    public byte[]           szReserved = new byte[4];
    /**
     * 二进制数据长度
    */
    public int              nBufferLen;
    /**
     * 二进制数据,用户申请内存
    */
    public Pointer          pBuffer;

    public NET_IN_SET_WAYBILL_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuWaybillInfo.length; i++){
            stuWaybillInfo[i] = new NET_WAYBILL_INFO();
        }
    }
}

