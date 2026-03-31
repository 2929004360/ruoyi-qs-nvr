package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 机库事件信息
*/
public class NET_NOTIFY_UAS_DOCK_EVENTS extends NetSDKLib.SdkStructure
{
    /**
     * 主题
    */
    public byte[]           szTopic = new byte[128];
    /**
     * 事务UUID
    */
    public byte[]           szTid = new byte[128];
    /**
     * 业务UUID
    */
    public byte[]           szBid = new byte[128];
    /**
     * 网关设备序列号
    */
    public byte[]           szGateway = new byte[128];
    /**
     * 消息内容
    */
    public byte[]           szData = new byte[32768];
    /**
     * 对应不同的数据内容
    */
    public byte[]           szMethod = new byte[128];
    /**
     * 消息的发送时间
    */
    public long             nTimestamp;
    /**
     * 是否给予收到答复;0代表不需要，1代表需要
    */
    public int              nNeedReply;
    /**
     * 保留字段
    */
    public byte[]           szResvered = new byte[1028];

    public NET_NOTIFY_UAS_DOCK_EVENTS() {
    }
}

