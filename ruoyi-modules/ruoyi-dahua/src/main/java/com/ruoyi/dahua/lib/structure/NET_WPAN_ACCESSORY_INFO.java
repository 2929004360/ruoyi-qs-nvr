package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  返回的配件信息 
* @date 2022/08/31 14:44:18
*/
public class NET_WPAN_ACCESSORY_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
录像使能，必须同时有RecordChannels
*/
    public			int            bRecordEnable;
/** 
外部告警使能
*/
    public			int            bExternalAlarmEnable;
/** 
无密码布防使能
*/
    public			int            bArmingWithoutPassword;
/** 
报警Led灯指示，0：关闭；1：打开
*/
    public			byte           byAlarmLedIndication;
/** 
控制检测外接电源状态的开关，0：关闭；1：打开
*/
    public			byte           byExPowerCheck;
/** 
配件防拆状态，0：关；1：开
*/
    public			byte           byTamper;
/** 
24H防区状态，0：关；1：开
*/
    public			byte           by24HDefenceStatus;
/** 
告警状态，0：正常；1：报警
*/
    public			byte           byAlarmStatus;
/** 
外部告警状态，0：正常；1：报警
*/
    public			byte           byExternalAlarmStatus;
/** 
Led灯指示，0：关；1：开
*/
    public			byte           byLedIndication;
/** 
布撤防以及进入退出延时是否有BEEP声音，0：无；1：有
*/
    public			byte           byBeepIndication;
/** 
紧急按钮状态，0：关；1：开
*/
    public			byte           bySosStatus;
/** 
配件上报方式，0：直连；1：中继上报
*/
    public			byte           byViaTrace;
/** 
传感器类型，0：常闭；1：常开
*/
    public			byte           bySensorType;
/** 
锁定状态，1：非锁定；2：锁定
*/
    public			byte           byLockState;
/** 
传感器故障状态，0：正常；1：故障
*/
    public			byte           bySensorFailure;
/** 
信号强度测试，0：关；1：开
*/
    public			byte           bySignalStrengthTest;
/** 
灵敏度测试，0：关；1：开
*/
    public			byte           bySensitivityTest;
/** 
音量测试，0：关；1：开
*/
    public			byte           byVolumeTest;
/** 
抓图测试，0：关；1：开
*/
    public			byte           bySnapshotTest;
/** 
wifi测试，0：关；1：开
*/
    public			byte           byWifiTest;
/** 
传感器屏蔽状态, 1:关闭屏蔽；2：开启屏蔽
*/
    public			byte           byBlockState;
/** 
配件短地址，从1开始
*/
    public			int            nShortAddr;
/** 
电池电量百分比：0~100
*/
    public			int            nPercent;
/** 
无线信号强度等级
*/
    public			int            nSignalLevel;
/** 
进入延时，单位：秒
*/
    public			int            nEntryDelay;
/** 
退出延时，单位：秒
*/
    public			int            nExitDelay;
/** 
报警持续时间，单位：秒
*/
    public			int            nAlarmDuring;
/** 
触发报警间隔时间，单位：秒
*/
    public			int            nTriggerAlarmInterval;
/** 
探测器状态 {@link com.ruoyi.dahua.lib.enumeration.EM_DETECTOR_STATUS_TYPE}
*/
    public			int            emState;
/** 
设备布防时音量 {@link com.ruoyi.dahua.lib.enumeration.EM_ACCESSORY_VOLUME}
*/
    public			int            emBeepVolume;
/** 
灵敏度 {@link com.ruoyi.dahua.lib.enumeration.EM_ACCESSORY_SENSITIVITY}
*/
    public			int            emSensentivity;
/** 
功率调节 {@link com.ruoyi.dahua.lib.enumeration.EM_POWER_REGULATION_TYPE}
*/
    public			int            emPowerRegulation;
/** 
在线状态 {@link com.ruoyi.dahua.lib.enumeration.EM_ONLINE_STATUS}
*/
    public			int            emOnline;
/** 
报警类型 {@link com.ruoyi.dahua.lib.enumeration.EM_ACCESSORY_ALARM_TYPE}
*/
    public			int            emAlarmType;
/** 
输入类型 {@link com.ruoyi.dahua.lib.enumeration.EM_ACCESSORY_INPUT_TYPE}
*/
    public			int            emInputType;
/** 
LED亮度 {@link com.ruoyi.dahua.lib.enumeration.EM_LED_BRIGHTNESS_LEVEL}
*/
    public			int            emLedBrightnessLevel;
/** 
操作模式 {@link com.ruoyi.dahua.lib.enumeration.EM_OPERATION_MODE}
*/
    public			int            emOperationMode;
/** 
防误按模式 {@link com.ruoyi.dahua.lib.enumeration.EM_ANTI_MISPRESS_TYPE}
*/
    public			int            emAntiMispress;
/** 
外部电源状态，若未开启检测外接电源状态则为未知 {@link com.ruoyi.dahua.lib.enumeration.EM_EXPOWER_STATE}
*/
    public			int            emExPowerState;
/** 
设备报警音量 {@link com.ruoyi.dahua.lib.enumeration.EM_ACCESSORY_VOLUME}
*/
    public			int            emVolume;
/** 
环境温度，单位：摄氏度
*/
    public			float          fAmbientTemperature;
/** 
配件序列号
*/
    public			byte[]         szSN = new byte[32];
/** 
配件名称
*/
    public			byte[]         szName = new byte[64];
/** 
配件版本
*/
    public			byte[]         szVersion = new byte[64];
/** 
配件型号
*/
    public			byte[]         szModel = new byte[64];
/** 
报警提示音
*/
    public			byte[]         szAlarmTone = new byte[128];
/** 
中继状态
*/
    public NET_WPAN_RELAY_INFO stuRelayTran = new NET_WPAN_RELAY_INFO();
/** 
传感器感应方式 {@link com.ruoyi.dahua.lib.enumeration.EM_BUTTON_ALARM_TYPE}
*/
    public			int            emType;
/** 
心跳信息
*/
    public			NET_WPAN_HEARTBEAT_INFO stuHeartbeat = new NET_WPAN_HEARTBEAT_INFO();
/** 
配件能力集
*/
    public			NET_WPAN_ACCESSORY_CAPS_INFO stuCaps = new NET_WPAN_ACCESSORY_CAPS_INFO();
/** 
登录失败的锁定信息
*/
    public			NET_WPAN_ACCESSORY_LOCK_INFO stuLockInfo = new NET_WPAN_ACCESSORY_LOCK_INFO();
/** 
读卡器配置
*/
    public NET_WPAN_CARD_READER_INFO stuCardReader = new NET_WPAN_CARD_READER_INFO();
/** 
图片信息
*/
    public NET_WPAN_ACCESSORY_IMAGE_INFO stuImageInfo = new NET_WPAN_ACCESSORY_IMAGE_INFO();
/** 
外部wifi信息
*/
    public NET_WPAN_EXTERNAL_WIFI_INFO stuExternalWifi = new NET_WPAN_EXTERNAL_WIFI_INFO();
/** 
Wi-Fi信息
*/
    public NET_WPAN_WIFI_INFO stuWifiInfo = new NET_WPAN_WIFI_INFO();
/** 
超温报警
*/
    public NET_WPAN_OVER_TEMPERATURE_ALARM_INFO stuOverTemperatureAlarm = new NET_WPAN_OVER_TEMPERATURE_ALARM_INFO();
/** 
所属区域个数
*/
    public			int            nAreaNumberCnt;
/** 
所属区域编号，整形数组从1开始
*/
    public			int[]          nAreaNumber = new int[64];
/** 
控制区域个数
*/
    public			int            nControlAreaNumCnt;
/** 
控制区域编号：所属区域编号，从1开始；-1：全局，0：关闭
*/
    public			int[]          nControlAreaNum = new int[64];
/** 
录像通道号个数
*/
    public			int            nRecordChannelsNum;
/** 
录像通道号列表，一维数组，每个成员表示对应的通道需要执行录像，通道号从0开始
*/
    public			int[]          nRecordChannels = new int[256];
/** 
警号联动个数
*/
    public			int            nSirenLinkageNum;
/** 
警号联动
*/
    public			int[]          nSirenLinkage = new int[256];
/** 
布防信息个数
*/
    public			int            nArmingInfoNum;
/** 
布防信息，最大4组
*/
    public			NET_WPAN_ARMING_INFO[] stuArmingInfo = new NET_WPAN_ARMING_INFO[4];
/** 
按键个数
*/
    public			int            nButtonNum;
/** 
按键信息
*/
    public			NET_WPAN_ACCESSORY_BUTTON_INFO[] stuButton = new NET_WPAN_ACCESSORY_BUTTON_INFO[16];
    /**
     * 配件类型标识
    */
    public byte[]           szDeviceIdentification = new byte[32];
    /**
     * 输出信息,参见结构体定义 {@link NET_ACCESSORY_OUTPUT_INFO}
    */
    public NET_ACCESSORY_OUTPUT_INFO stuOutput = new NET_ACCESSORY_OUTPUT_INFO();
    /**
     * 外部接入探测器个数
    */
    public int              nExternalSensorCount;
    /**
     * 外部接入探测器信息,参见结构体定义 {@link NET_ACCESSORY_EXTERNAL_SENSOR_INFO}
    */
    public NET_ACCESSORY_EXTERNAL_SENSOR_INFO[] stuExternalSensor = new NET_ACCESSORY_EXTERNAL_SENSOR_INFO[16];
    /**
     * 移动报警检测,参见结构体定义 {@link NET_ACCESSORY_MOVEMENT_DETECTED_INFO}
    */
    public NET_ACCESSORY_MOVEMENT_DETECTED_INFO stuMovementDetected = new NET_ACCESSORY_MOVEMENT_DETECTED_INFO();
    /**
     * 输入电压，单位V，精确到1个小数点
    */
    public float            fInputVoltage;
    /**
     * 电流，单位A，精确到1个小数点
    */
    public float            fElectricCurrent;
    /**
     * 功率，单位W，精确到1个小数点
    */
    public float            fPower;
    /**
     * 电能，单位KW.h
    */
    public float            fElectricEnergy;
    /**
     * 门铃功能使能是否存在
    */
    public int              abChimeEnable;
    /**
     * 门铃功能使能
    */
    public int              bChimeEnable;
    /**
     * 是否过滤无效字段
    */
    public int              bFilterValidField;
    /**
     * 相机模组信息是否存在
    */
    public int              bCameraInfo;
    /**
     * 相机模组信息,参见结构体定义 {@link NET_ACCESSORY_CAMERA_INFO}
    */
    public NET_ACCESSORY_CAMERA_INFO stuCameraInfo = new NET_ACCESSORY_CAMERA_INFO();
    /**
     * 随机数，配合抓图测试使用，若携带这个字段，则在设备执行完抓图后，上报的抓图测试事件(ManualTest)中的DelayUploadSeq的值与传入的一致
    */
    public byte[]           szDelayUploadSeq = new byte[64];

public			NET_WPAN_ACCESSORY_INFO(){
		for(int i=0;i<stuArmingInfo.length;i++){
			stuArmingInfo[i]=new NET_WPAN_ARMING_INFO();
			}
		for(int i=0;i<stuButton.length;i++){
			stuButton[i]=new NET_WPAN_ACCESSORY_BUTTON_INFO();
			}
		this.dwSize=this.size();
}
}

