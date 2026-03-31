package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetASGState 接口输出参数
*/
public class NET_OUT_ACCESS_GET_ASG_STATE extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 红外状态个数
    */
    public int              nInfraredStateNum;
    /**
     * 红外状态掩码; , 数组[0] Bit0 表示第1路，Bit7表示第8路, 数组[1] Bit0 表示第9路，Bit7表示第16路;, 位定义为 0：灭(未遮挡),1：亮(被遮挡)
    */
    public int[]            szInfraredStateMask = new int[6];
    /**
     * 门翼状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_DOOR_WING_STATE}
    */
    public int              emDoorWingState;
    /**
     * Can通信状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_CAN_COMMUNICATE_STATE}
    */
    public int              emCanState;
    /**
     * 进通行人数
    */
    public int              nEnterNum;
    /**
     * 出通行人数
    */
    public int              nLeaveNum;
    /**
     * 蓄电池状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_ASG_BATTERY_STATE}
    */
    public int              emBatteryState;
    /**
     * 电机异常次数
    */
    public int              nMotorExceptionNum;
    /**
     * 电机转动次数
    */
    public int              nMotorRotationNum;

    public NET_OUT_ACCESS_GET_ASG_STATE() {
        this.dwSize = this.size();
    }
}

