package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  回调的陀螺仪数据信息 
* @date 2022/10/26 15:16:37
*/
public class NET_NOTIFY_GYRO_DATA_INFO extends NetSDKLib.SdkStructure {
/** 
X轴角速度，单位rad/s
*/
    public			double         dbAngularSpeedX;
/** 
Y轴角速度，单位rad/s
*/
    public			double         dbAngularSpeedY;
/** 
z轴角速度，单位rad/s
*/
    public			double         dbAngularSpeedZ;
/** 
X轴加速度，单位m/s^2
*/
    public			double         dbAccelX;
/** 
Y轴加速度，单位m/s^2
*/
    public			double         dbAccelY;
/** 
Z轴加速度，单位m/s^2
*/
    public			double         dbAccelZ;
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[1024];

public NET_NOTIFY_GYRO_DATA_INFO(){
}

    @Override
    public String toString() {
        return "NET_NOTIFY_GYRO_DATA_INFO{" +
                "dbAngularSpeedX=" + dbAngularSpeedX +
                ", dbAngularSpeedY=" + dbAngularSpeedY +
                ", dbAngularSpeedZ=" + dbAngularSpeedZ +
                ", dbAccelX=" + dbAccelX +
                ", dbAccelY=" + dbAccelY +
                ", dbAccelZ=" + dbAccelZ +
                '}';
    }
}

