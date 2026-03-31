package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 返回的事件详细信息
*/
public class NET_NOTIFY_IVSEVENT_DETAIL_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 返回的事件数据指针，事件下标与传入数据id数组下标一一对应,参见结构体定义 {@link NET_IVSEVENT_DETAIL_INFO}
    */
    public Pointer          pstuIVSEventDetailInfo;
    /**
     * 返回的事件数据数量
    */
    public int              nIVSEventDetailInfoNum;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_NOTIFY_IVSEVENT_DETAIL_INFO() {
    }
}

