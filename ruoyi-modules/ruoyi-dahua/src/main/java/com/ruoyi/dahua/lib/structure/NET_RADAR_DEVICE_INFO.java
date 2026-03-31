package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 雷达设备信息
 * @since ： Created in 2022/02/11 10:22
 */

public class NET_RADAR_DEVICE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 协议类型
     */
    public byte[]           szProtocalType = new byte[32];
    /**
     * 雷达设备IP
     */
    public byte[]           szRadarIP = new byte[32];
    /**
     * 设备类型
     */
    public byte[]           szDeviceType = new byte[32];
    /**
     * 设备名称
     */
    public byte[]           szDeviceName = new byte[32];
    /**
     * Radar模块版本号
     */
    public byte[]           szRadarVer = new byte[16];
    /**
     * 用户名
     */
    public byte[]           szUserName = new byte[128];
    /**
     * 密码
     */
    public byte[]           szPassWord = new byte[32];
    /**
     * 服务端口号
     */
    public int              nPort;
    /**
     * 经度, 用整型传输, 放大1000000倍, 小数点后6位有效, 不足6位用0补齐, 例如120125400代表120.1254
     */
    public int              nLongitude;
    /**
     * 纬度, 用整型传输, 放大1000000倍, 小数点后6位有效, 不足6位用0补齐, 例如120125400代表120.1254
     */
    public int              nLatitude;
    /**
     * 雷达地图朝向角, 放大100倍, 例如112897, 实际是128.97度
     */
    public int              nAngle;
    /**
     * 保留字节
     */
    public byte[]           bReserved = new byte[1024];
}

