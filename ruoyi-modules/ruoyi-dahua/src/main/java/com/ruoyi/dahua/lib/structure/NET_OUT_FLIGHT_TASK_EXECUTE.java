package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_FlightTaskExecute 接口输出参数
*/
public class NET_OUT_FLIGHT_TASK_EXECUTE extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 字节对齐
    */
    public byte[]           szReserved1 = new byte[4];
    /**
     * 消息的发送时间
    */
    public long             nTimestamp;
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
     * 数据
    */
    public byte[]           szData = new byte[10240];

    public NET_OUT_FLIGHT_TASK_EXECUTE() {
        this.dwSize = this.size();
    }
}

