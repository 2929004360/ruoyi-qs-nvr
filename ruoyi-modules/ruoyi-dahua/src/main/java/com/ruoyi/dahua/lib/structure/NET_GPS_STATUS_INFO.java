package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description GPS状态信息
 * @origin autoTool
 * @date 2023/09/06 15:55:03
 */
public class NET_GPS_STATUS_INFO extends NetSDKLib.SdkStructure {
    /**
     * 定位时间
     */
    public NET_TIME         revTime = new NET_TIME();
    /**
     * 设备序列号
     */
    public byte[]           DvrSerial = new byte[50];
    /**
     * 对齐字节
     */
    public byte[]           byRserved1 = new byte[6];
    /**
     * 经度(单位是百万分之度,范围0-360度)
     */
    public double           longitude;
    /**
     * 纬度(单位是百万分之度,范围0-180度)
     */
    public double           latidude;
    /**
     * 高度(米)
     */
    public double           height;
    /**
     * 方向角(正北方向为原点,顺时针为正)
     */
    public double           angle;
    /**
     * 速度(单位km/H)
     */
    public double           speed;
    /**
     * 定位星数, emDateSource为 EM_DATE_SOURCE_GPS时有效
     */
    public short            starCount;
    /**
     * 对齐字节
     */
    public byte[]           byRserved2 = new byte[2];
    /**
     * 天线状态, emDateSource为 EM_DATE_SOURCE_GPS时有效 {@link com.ruoyi.dahua.lib.enumeration.NET_THREE_STATUS_BOOL}
     */
    public int              antennaState;
    /**
     * 定位状态  {@link com.ruoyi.dahua.lib.enumeration.NET_THREE_STATUS_BOOL}
     */
    public int              orientationState;
    /**
     * 工作状态(0=未定位,1=非差分定位,2=差分定位,3=无效PPS,6=正在估算
     * emDateSource为 EM_DATE_SOURCE_GPS时有效
     */
    public int              workStae;
    /**
     * 发生的报警位置个数
     */
    public int              nAlarmCount;
    /**
     * 发生的报警位置,值可能多个, emDateSource为 EM_DATE_SOURCE_GPS时有效
     */
    public int[]            nAlarmState = new int[128];
    /**
     * 0-实时 1-补传
     */
    public byte             bOffline;
    /**
     * GPS信噪比,表示GPS信号强度,值越大,信号越强 范围：0~100,0表示不可用
     */
    public byte             bSNR;
    /**
     * 对齐字节
     */
    public byte[]           byRserved3 = new byte[2];
    /**
     * 数据来源 {@link com.ruoyi.dahua.lib.enumeration.EM_DATE_SOURCE}
     */
    public int              emDateSource;
    /**
     * 在当前工作模式下（GPS或北斗等系统）的信号强度
     */
    public int              nSignalStrength;
    /**
     * 水平精度因子惯性导航时无效
     */
    public float            fHdop;
    /**
     * 位置精度因子,惯性导航时无效
     */
    public float            fPdop;
    /**
     * 总里程， 单位 米
     */
    public int              nMileage;
    /**
     * 保留字节
     */
    public byte[]           byRserved = new byte[96];

    public NET_GPS_STATUS_INFO() {
    }
}

