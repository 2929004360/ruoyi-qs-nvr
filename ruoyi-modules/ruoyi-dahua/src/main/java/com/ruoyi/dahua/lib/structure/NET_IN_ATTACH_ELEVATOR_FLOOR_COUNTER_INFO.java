package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_AttachElevatorFloorCounter 接口入参
*/
public class NET_IN_ATTACH_ELEVATOR_FLOOR_COUNTER_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 订阅通道号；-1代表订阅全通道；
    */
    public int[]            nChannel = new int[256];
    /**
     * 通道号个数
    */
    public int              nChannelNum;
    /**
     * 回调函数,参见回调函数定义 {@link NetSDKLib.fNotifyElevatorFloorCounterdata}
    */
    public NetSDKLib.fNotifyElevatorFloorCounterdata cbNotifyElevatorFloorCounterdata;
    /**
     * 用户自定义参数
    */
    public Pointer          dwUser;

    public NET_IN_ATTACH_ELEVATOR_FLOOR_COUNTER_INFO() {
        this.dwSize = this.size();
    }
}

