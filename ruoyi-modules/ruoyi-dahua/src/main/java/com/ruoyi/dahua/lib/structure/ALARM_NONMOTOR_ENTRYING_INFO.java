package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型DH_ALARM_NONMOTOR_ENTRYING (非机动车进入电梯) 对应的数据块描述信息
*/
public class ALARM_NONMOTOR_ENTRYING_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 事件动作,0表示脉冲事件,1表示事件开始,2表示事件结束;
    */
    public int              nAction;
    /**
     * 时间戳(单位是毫秒)
    */
    public double           dbPTS;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuTime = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 视频帧序号
    */
    public int              nSequence;
    /**
     * 目标个数
    */
    public int              nObjectNum;
    /**
     * 非机动车目标,参见结构体定义 {@link NetSDKLib.VA_OBJECT_NONMOTOR}
    */
    public NetSDKLib.VA_OBJECT_NONMOTOR[] stuObjects = new NetSDKLib.VA_OBJECT_NONMOTOR[8];
    /**
     * 预留字段
    */
    public byte[]           byReserved = new byte[1024];

    public ALARM_NONMOTOR_ENTRYING_INFO() {
        for(int i = 0; i < stuObjects.length; i++){
            stuObjects[i] = new NetSDKLib.VA_OBJECT_NONMOTOR();
        }
    }
}

