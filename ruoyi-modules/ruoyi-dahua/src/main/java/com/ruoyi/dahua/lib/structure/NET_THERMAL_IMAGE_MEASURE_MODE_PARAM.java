package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 热成像模式下参数
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/09 17:05
 */
public class NET_THERMAL_IMAGE_MEASURE_MODE_PARAM extends NetSDKLib.SdkStructure {
    /**
     *  人脸比对阈值
     */
    public int              nFaceCompareThreshold;
    /**
     *  热成像温度保留时间（s）	(人脸无法比对通过时，通过刷卡等其他方式验证权限，找到人员底库后再去热成像设备进行比对，此字段用于温度保存多少时间)    
     */
    public int              nRetentionTime;
    /**
     *  上报高的温度事件的最大距离，在此距离内测到高的温度直接上报（cm）    
     */
    public int              nOverTempMaxDistance;
    /**
     * 预留字段
     */
    public byte[]           byReserved = new byte[1020];

    @Override
    public String toString() {
        return "NET_THERMAL_IMAGE_MEASURE_MODE_PARAM{" +
                "nFaceCompareThreshold=" + nFaceCompareThreshold +
                ", nRetentionTime=" + nRetentionTime +
                ", nOverTempMaxDistance=" + nOverTempMaxDistance +
                '}';
    }
}

