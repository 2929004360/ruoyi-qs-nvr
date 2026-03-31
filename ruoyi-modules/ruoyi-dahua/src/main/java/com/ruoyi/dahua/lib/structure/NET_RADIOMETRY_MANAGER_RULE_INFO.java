package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 单条测温规则信息
*/
public class NET_RADIOMETRY_MANAGER_RULE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 测温点报警设置个数
    */
    public int              nAlarmSettingNum;
    /**
     * 测温点报警设置,参见结构体定义 {@link NET_ALARM_SETTING_INFO}
    */
    public NET_ALARM_SETTING_INFO[] stuAlarmSetting = new NET_ALARM_SETTING_INFO[10];
    /**
     * 区域测温的子类型
    */
    public byte[]           szAreaSubType = new byte[16];
    /**
     * 测温使能
    */
    public int              bEnable;
    /**
     * 本地参数配置,参见结构体定义 {@link NET_LOCAL_PARAMETERS_INFO}
    */
    public NET_LOCAL_PARAMETERS_INFO stuLocalParameters = new NET_LOCAL_PARAMETERS_INFO();
    /**
     * 测温点坐标个数
    */
    public int              nCoordinatesNum;
    /**
     * 测温点坐标,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuCoordinates = new NET_POINT_EX[7];
    /**
     * 自定义名称
    */
    public byte[]           szName = new byte[64];
    /**
     * 温度采样周期
    */
    public int              nT;
    /**
     * 测温的类型
    */
    public byte[]           szType = new byte[16];
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[512];

    public NET_RADIOMETRY_MANAGER_RULE_INFO() {
        for(int i = 0; i < stuAlarmSetting.length; i++){
            stuAlarmSetting[i] = new NET_ALARM_SETTING_INFO();
        }
        for(int i = 0; i < stuCoordinates.length; i++){
            stuCoordinates[i] = new NET_POINT_EX();
        }
    }
}

