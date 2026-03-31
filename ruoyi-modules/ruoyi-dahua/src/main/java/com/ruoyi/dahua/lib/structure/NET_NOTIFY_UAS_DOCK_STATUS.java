package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 机库拓扑信息
*/
public class NET_NOTIFY_UAS_DOCK_STATUS extends NetSDKLib.SdkStructure
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
     * 方法
    */
    public byte[]           szMethod = new byte[128];
    /**
     * 数据，json格式
    */
    public byte[]           szData = new byte[32767];
    /**
     * 消息的发送时间
    */
    public long             nTimestamp;
    /**
     * 保留字段
    */
    public byte[]           szResvered = new byte[1024];

    public NET_NOTIFY_UAS_DOCK_STATUS() {
    }
}

