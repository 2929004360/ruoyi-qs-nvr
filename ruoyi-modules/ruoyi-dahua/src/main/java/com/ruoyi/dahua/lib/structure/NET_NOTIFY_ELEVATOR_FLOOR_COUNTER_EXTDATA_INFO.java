package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 扩展数据，用来传输电梯加速度等数据
*/
public class NET_NOTIFY_ELEVATOR_FLOOR_COUNTER_EXTDATA_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 开始采集时间, 单位秒
    */
    public int              nStartTime;
    /**
     * 结束采集时间, 单位秒
    */
    public int              nStopTime;
    /**
     * 扩展数据大小, 单位字节
    */
    public int              nLength;
    /**
     * 在二进制数据块中的偏移
    */
    public int              nOffset;
    /**
     * 数据名称
    */
    public byte[]           szFileName = new byte[256];
    /**
     * 二进制数据
    */
    public byte[]           szBinaryData = new byte[307200];
    /**
     * 二进制数据有效长度
    */
    public int              nBinaryDataLen;
    /**
     * 数据传输模式
    */
    public byte[]           szTransfer = new byte[12];
    /**
     * 保留字段
    */
    public byte[]           szResvered = new byte[1024];

    public NET_NOTIFY_ELEVATOR_FLOOR_COUNTER_EXTDATA_INFO() {
    }
}

