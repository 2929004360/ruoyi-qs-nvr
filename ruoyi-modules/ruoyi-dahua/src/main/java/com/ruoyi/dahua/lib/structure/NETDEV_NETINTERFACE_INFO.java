package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 251823
 * @description 网络接口,最大支持DH_MAX_NETINTERFACE_NUM个网卡
 * @date 2021/9/17
 */
public class NETDEV_NETINTERFACE_INFO extends NetSDKLib.SdkStructure {
	 /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     *  是否有效
     */
    public int              bValid;
    /**
     *  是否虚拟网卡
     */
    public int              bVirtual;
    /**
     *  网卡理论速度(Mbps)
     */
    public int              nSpeed;
    /**
     *  0-未使能, 1-正在获取, 2-获取成功
     */
    public int              nDHCPState;
    /**
     *  网口名称
     */
    public byte[]           szName = new byte[NetSDKLib.NET_NETINTERFACE_NAME_LEN];
    /**
     *  网络类型
     */
    public byte[]           szType = new byte[NetSDKLib.NET_NETINTERFACE_TYPE_LEN];
    /**
     *  MAC地址
     */
    public byte[]           szMAC = new byte[NetSDKLib.NET_MACADDR_LEN];
    /**
     *  SSID, 只对无线网络有效(szType == "Wireless")
     */
    public byte[]           szSSID = new byte[NetSDKLib.NET_MAX_SSID_LEN];
    /**
     *  Wifi、3G的连接状态,"Inexistence" : 网络不存在 "Down"：关闭 "Disconn"：未连接 "Connecting"：正连接 "Connected"： 已连接
     */
    public byte[]           szConnStatus = new byte[NetSDKLib.NET_MAX_CONNECT_STATUS_LEN];
    /**
     *  实际3G支持的网络模式个数
     */
    public int              nSupportedModeNum;
    /**
     *  3G支持的网络模式 "TD-SCDMA", "WCDMA", "CDMA1x", "EDGE", "EVDO"
     */
    public SupportedModeByteArr[] szSupportedModes = (SupportedModeByteArr[])new SupportedModeByteArr().toArray(NetSDKLib.NET_MAX_MODE_NUM);
    /**
     *  是否支持长距离POE速度协商，需要网卡和交换机协商后才能确定
     */
    public int              bSupportLongPoE;
    /**
     *  真实网卡名
     */
    public byte[]           szNetCardName = new byte[8];
    /**
     *  模块名称
     */
    public byte[]           szModuleName = new byte[32];
    /**
     *  国际移动用户识别码
     */
    public byte[]           szIMEI = new byte[16];
    /**
     *  集成电路卡识别码即SIM卡卡号,20位数字组成
     */
    public byte[]           szICCID = new byte[24];
    /**
     * 国际移动用户识别码
    */
    public byte[]           szIMSI = new byte[16];

    public NETDEV_NETINTERFACE_INFO(){
        this.dwSize = this.size();
    }
}

