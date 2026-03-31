package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 目标识别联动报警通道信息
*/
public class NET_FACE_RECOGNITION_ALARM_CHANNEL extends NetSDKLib.SdkStructure
{
    /**
     * 目标库ID
    */
    public byte[]           szGroupID = new byte[64];
    /**
     * 目标库名称
    */
    public byte[]           szGroupName = new byte[128];
    /**
     * 目标识别事件是否联动报警输出
    */
    public int              bAlarmOutEnable;
    /**
     * 联动报警输出通道个数
    */
    public int              nAlarmChannelNum;
    /**
     * 联动报警输出通道信息,参见结构体定义 {@link NET_ALARM_OUT_CHANNEL_INFO}
    */
    public NET_ALARM_OUT_CHANNEL_INFO[] stuAlarmChannel = new NET_ALARM_OUT_CHANNEL_INFO[64];
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[256];

    public NET_FACE_RECOGNITION_ALARM_CHANNEL() {
        for(int i = 0; i < stuAlarmChannel.length; i++){
            stuAlarmChannel[i] = new NET_ALARM_OUT_CHANNEL_INFO();
        }
    }
}

