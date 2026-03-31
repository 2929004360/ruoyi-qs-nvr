package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * CLIENT_SetWaybillStatus 接口输入参数
*/
public class NET_IN_SET_WAYBILL_STATUS extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 运单信息有效个数
    */
    public int              nWaybillInfoNum;
    /**
     * 运单信息,参见结构体定义 {@link NET_WAYBILL_INFO}
    */
    public NET_WAYBILL_INFO[] stuWaybillInfo = new NET_WAYBILL_INFO[16];
    /**
     * 状态，1开始/0结束
    */
    public int              nFlag;

    public NET_IN_SET_WAYBILL_STATUS() {
        this.dwSize = this.size();
        for(int i = 0; i < stuWaybillInfo.length; i++){
            stuWaybillInfo[i] = new NET_WAYBILL_INFO();
        }
    }
}

