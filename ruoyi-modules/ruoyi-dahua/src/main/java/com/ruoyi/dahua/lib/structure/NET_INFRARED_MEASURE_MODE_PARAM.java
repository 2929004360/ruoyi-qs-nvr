package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

import java.util.Arrays;


/**
 * 单片机红外测温模式参数
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/09 17:05
 */
public class NET_INFRARED_MEASURE_MODE_PARAM extends NetSDKLib.SdkStructure {
    /**
     *  最远测试距离，单位: cm
     */
    public int              nMaxDistance;
    /**
     *  红外温度保存时间（ms）	（人员在设备鉴权时，从红外设备取温度的有效倒退时间）
     */
    public int              nRetentionTime;
    /**
     *  温度阈值，单位: 摄氏度
     */
    public double           dbTempThreshold;
    /**
     *  温度矫正值，单位: 摄氏度	
     */
    public double           dbCorrectTemp;
    /**
     *  有效温度下限，低于此值的温度为无效温度（摄氏度） 
     */
    public double           dbValidTempLowerLimit;
    /**
     *  是否开启测温调试模式（在屏幕上显示测温调试数据） 	
     */
    public int              bDebugModelEnable;
    /**
     *  测温区域框显示使能（在视频流界面显示测试区域框）
     */
    public int              bRectEnable;
    /**
     *  测温模块传感器类型
     */
    public byte             szSensorType[] = new byte[64];
    /**
     *  预留字段
     */
    public byte[]           byReserved = new byte[1024];

    @Override
    public String toString() {
        return "NET_INFRARED_MEASURE_MODE_PARAM{" +
                "nMaxDistance=" + nMaxDistance +
                ", nRetentionTime=" + nRetentionTime +
                ", dbTempThreshold=" + dbTempThreshold +
                ", dbCorrectTemp=" + dbCorrectTemp +
                ", dbValidTempLowerLimit=" + dbValidTempLowerLimit +
                ", bDebugModelEnable=" + bDebugModelEnable +
                ", bRectEnable=" + bRectEnable +
                ", szSensorType=" + Arrays.toString(szSensorType) +
                '}';
    }
}

