package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 单个WLAN配置
 * @date 2023/03/16 11:15:26
 */
public class CFG_WLAN_INFO extends NetSDKLib.SdkStructure {
	/**
	 * Wlan名称, 只能获取不能修改
	 */
    public byte[]           szWlanName = new byte[32];
	/**
	 * WIFI网卡使能开关, TRUE打开, FALSE关闭
	 */
    public int              bEnable;
	/**
	 * 网络名称(SSID)
	 */
    public byte[]           szSSID = new byte[36];
	/**
	 * 手动连接开关, TRUE手动连接, FALSE手动断开
	 */
    public int              bConnectEnable;
	/**
	 * 自动连接开关, TRUE不自动连接, FALSE自动连接, IPC无意义
	 */
    public int              bLinkEnable;
	/**
	 * 连接模式, 0: auto, 1: adhoc, 2: Infrastructure
	 */
    public int              nLinkMode;
	/**
	 * 加密模式, 0: off, 1: on, 2: WEP64, 3: WEP128, 4: WPA-PSK-TKIP, 5: WPA-PSK-AES, 6:
	 * WPA2-PSK-TKIP, 7: WPA2-PSK-AES, 8: WPA-TKIP, 9: WPA-AES, 10: WPA2-TKIP, 11:
	 * WPA2-AES, 12: AUTO
	 * 
	 *  二代byAuthMode  , byEncrAlgr  与三代映射关系
	* Authentication认证方式	DataEncryption数据加密方式	Encryption加密模式						
	* OPEN                    NONE                        "On" 							
	* OPEN                    WEP                         "WEP-OPEN"							
	* SHARD                   WEP                         "WEP-SHARED"						
	* WPA                     TKIP                        "WPA-TKIP"							
	* WPA-PSK                 TKIP                        "WPA-PSK-TKIP"						
	* WPA2                    TKIP                        "WPA2-TKIP"							
	* WPA2-PSK                TKIP                        "WPA2-PSK-TKIP"						
	* WPA                     AES(CCMP)                   "WPA-AES"							
	* WPA-PSK                 AES(CCMP)                   "WPA-PSK-AES"						
	* WPA2                    AES(CCMP)                   "WPA2-AES"							
	* WPA2-PSK                AES(CCMP)                   "WPA2-PSK-AES"					
	* WPA                     TKIP+AES( mix Mode)         "WPA-TKIP"或者"WPA-AES"				
	* WPA-PSK                 TKIP+AES( mix Mode)         "WPA-PSK-TKIP"或者"WPA-PSK-AES"		
	* WPA2                    TKIP+AES( mix Mode)         "WPA2-TKIP"或者"WPA2-AES"			
	* WPA2-PSK                TKIP+AES( mix Mode)         "WPA2-PSK-TKIP"或者"WPA2-PSK-AES"			
	* WPA3-SAE				AES(CCMP)					"WPA3-SAE-CCMP"						
	 */
    public int              nEncryption;
	/**
	 * 认证方式, 暂时没用 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_WIRELESS_AUTHENTICATION}
	 */
    public int              emAuthentication;
	/**
	 * 数据加密方式, 暂时没用 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_WIRELESS_DATA_ENCRYPT}
	 */
    public int              emDataEncryption;
	/**
	 * 密码类型, 0: Hex, 1: ASCII
	 */
    public int              nKeyType;
	/**
	 * 秘钥索引, 取值0~3
	 */
    public int              nKeyID;
	/**
	 * 四组密码
	 */
    public byte[]           szKeys = new byte[4 * 128];
	/**
	 * 密码是否已经设置
	 */
    public int              bKeyFlag;
	/**
	 * EAP
	 */
    public CFG_WLAN_EAP     stuEap = new CFG_WLAN_EAP();
	/**
	 * Network
	 */
    public CFG_WLAN_NETWORK stuNetwork = new CFG_WLAN_NETWORK();

	public CFG_WLAN_INFO() {
	}
}

