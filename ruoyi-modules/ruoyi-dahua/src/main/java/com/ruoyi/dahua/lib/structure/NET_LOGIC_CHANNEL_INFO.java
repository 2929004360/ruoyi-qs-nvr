package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.NET_EM_LOGIC_CHANNEL;

/**
 * 通道信息
 *
 * @author ： 47040
 * @since ： Created in 2020/9/18 9:42
 */
public class NET_LOGIC_CHANNEL_INFO extends NetSDKLib.SdkStructure {
    /**
     * 教室ID号
     */
    public int              nRoomID;
    /**
     * 逻辑通道号 {@link NET_EM_LOGIC_CHANNEL}
     */
    public int              emLogicChannel;
    /**
     * 保留字节
     */
    public byte[]           byReserved = new byte[32];
}

