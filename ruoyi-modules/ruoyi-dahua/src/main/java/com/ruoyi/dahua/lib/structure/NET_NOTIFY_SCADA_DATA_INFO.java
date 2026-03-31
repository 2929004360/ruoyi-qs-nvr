package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 订阅的遥测数据
 * @date 2022/12/13 10:24:15
 */
public class NET_NOTIFY_SCADA_DATA_INFO extends NetSDKLib.SdkStructure {
    /**
     * 探测器设备ID
     */
    public byte[]           szDeviceId = new byte[256];
    /**
     * 遥测数据信息数组有效个数
     */
    public int              nDataInfoNum;
    /**
     * 遥测数据信息
     */
    public DEV_SCADA_DATA_INFO[] stuDataInfo = new DEV_SCADA_DATA_INFO[128];
    /**
     * 保留字节
     */
    public byte[]           szResvered = new byte[1024];

    public NET_NOTIFY_SCADA_DATA_INFO() {
        for (int i = 0; i < stuDataInfo.length; i++) {
            stuDataInfo[i] = new DEV_SCADA_DATA_INFO();
        }
    }
}

