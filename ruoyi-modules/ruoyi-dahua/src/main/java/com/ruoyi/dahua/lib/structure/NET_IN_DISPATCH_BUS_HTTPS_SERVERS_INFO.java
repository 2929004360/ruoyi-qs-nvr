package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 下发https服务器地址，供设备以https方式上报公交线路、报站信息 CLIENT_DispatchBusHttpsServers 接口输入参数
*/
public class NET_IN_DISPATCH_BUS_HTTPS_SERVERS_INFO extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 服务器地址个数
    */
    public int              nAddressNum;
    /**
     * 服务器地址参数,参见结构体定义 {@link NET_BUS_HTTPS_ADDRESS_INFO}
    */
    public NET_BUS_HTTPS_ADDRESS_INFO[] stuAddress = new NET_BUS_HTTPS_ADDRESS_INFO[4];
    /**
     * https校验字段(目前32字符)
    */
    public byte[]           szToken = new byte[128];

    public NET_IN_DISPATCH_BUS_HTTPS_SERVERS_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuAddress.length; i++){
            stuAddress[i] = new NET_BUS_HTTPS_ADDRESS_INFO();
        }
    }
}

