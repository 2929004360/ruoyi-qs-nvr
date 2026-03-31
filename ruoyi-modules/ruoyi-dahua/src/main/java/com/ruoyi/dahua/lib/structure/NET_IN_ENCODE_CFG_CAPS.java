package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.GetDevCaps_Type;
import com.sun.jna.Pointer;

/**
 * 获取设备编码配置对应能力输入参数
 * 能力集枚举 {@link GetDevCaps_Type#NET_ENCODE_CFG_CAPS}
 * 能力集接口 {@link NetSDKLib#CLIENT_GetDevCaps}
 *
 * @author 47040
 * @since Created at 2021/5/25 20:48
 */
public class NET_IN_ENCODE_CFG_CAPS extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 通道号
     */
    public int              nChannelId;
    /**
     * 码流类型,0：主码流；1：辅码流1；2：辅码流2；3：辅码流3；4：抓图码流
     */
    public int              nStreamType;
    /**
     * 此参数可以不填,不论指定什么类型,设备都返回主、辅、抓图码流的能力
     * Encode配置,通过调用 dhconfigsdk.dll中接口 CLIENT_PacketData封装得到
     * 对应的封装命令为 CFG_CMD_ENCODE
     */
    public Pointer          pchEncodeJson;

    public NET_IN_ENCODE_CFG_CAPS() {
        dwSize = this.size();
    }
}

