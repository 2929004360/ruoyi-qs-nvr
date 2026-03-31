package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 智慧灯杆项外设配置, 对应 NET_EM_CFG_LAMP_EXTERNAL_DEVICE
 * @date 2023/05/11 14:09:05
 */
public class NET_CFG_LAMP_EXTERNAL_DEVICE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 照明灯设备数量
	 */
    public int              nLightNum;
	/**
	 * LED广告屏数量
	 */
    public int              nScreenNum;
	/**
	 * 动环设备数量
	 */
    public int              nEnvironmentNum;
	/**
	 * 摄像头设备数量
	 */
    public int              nCameraNum;
	/**
	 * 可视对讲设备数量
	 */
    public int              nAlarmNum;
	/**
	 * 音柱设备数量
	 */
    public int              nSpeakerNum;
	/**
	 * WifiAp设备数量
	 */
    public int              nWifiApNum;
	/**
	 * 照明灯设备
	 */
    public NET_LAMP_EXTERNAL_DEVICE_LIGHT_INFO[] stuLight = new NET_LAMP_EXTERNAL_DEVICE_LIGHT_INFO[16];
	/**
	 * LED广告屏
	 */
    public NET_LAMP_EXTERNAL_DEVICE_SCREEN_INFO[] stuScreen = new NET_LAMP_EXTERNAL_DEVICE_SCREEN_INFO[16];
	/**
	 * 动环设备
	 */
    public NET_LAMP_EXTERNAL_DEVICE_ENVIRONMENT_INFO[] stuEnvironment = new NET_LAMP_EXTERNAL_DEVICE_ENVIRONMENT_INFO[16];
	/**
	 * 摄像头设备
	 */
    public NET_LAMP_EXTERNAL_DEVICE_CAMERA_INFO[] stuCamera = new NET_LAMP_EXTERNAL_DEVICE_CAMERA_INFO[16];
	/**
	 * 可视对讲设备
	 */
    public NET_LAMP_EXTERNAL_DEVICE_ALARM_INFO[] stuAlarm = new NET_LAMP_EXTERNAL_DEVICE_ALARM_INFO[16];
	/**
	 * 音柱设备
	 */
    public NET_LAMP_EXTERNAL_DEVICE_SPEAKER_INFO[] stuSpeaker = new NET_LAMP_EXTERNAL_DEVICE_SPEAKER_INFO[16];
	/**
	 * WifiAp设备
	 */
    public NET_LAMP_EXTERNAL_DEVICE_WIFIAP_INFO[] stuWifiAp = new NET_LAMP_EXTERNAL_DEVICE_WIFIAP_INFO[16];

	public NET_CFG_LAMP_EXTERNAL_DEVICE_INFO() {
		for (int i = 0; i < stuLight.length; i++) {
			stuLight[i] = new NET_LAMP_EXTERNAL_DEVICE_LIGHT_INFO();
		}
		for (int i = 0; i < stuScreen.length; i++) {
			stuScreen[i] = new NET_LAMP_EXTERNAL_DEVICE_SCREEN_INFO();
		}
		for (int i = 0; i < stuEnvironment.length; i++) {
			stuEnvironment[i] = new NET_LAMP_EXTERNAL_DEVICE_ENVIRONMENT_INFO();
		}
		for (int i = 0; i < stuCamera.length; i++) {
			stuCamera[i] = new NET_LAMP_EXTERNAL_DEVICE_CAMERA_INFO();
		}
		for (int i = 0; i < stuAlarm.length; i++) {
			stuAlarm[i] = new NET_LAMP_EXTERNAL_DEVICE_ALARM_INFO();
		}
		for (int i = 0; i < stuSpeaker.length; i++) {
			stuSpeaker[i] = new NET_LAMP_EXTERNAL_DEVICE_SPEAKER_INFO();
		}
		for (int i = 0; i < stuWifiAp.length; i++) {
			stuWifiAp[i] = new NET_LAMP_EXTERNAL_DEVICE_WIFIAP_INFO();
		}
		this.dwSize = this.size();
	}
}

