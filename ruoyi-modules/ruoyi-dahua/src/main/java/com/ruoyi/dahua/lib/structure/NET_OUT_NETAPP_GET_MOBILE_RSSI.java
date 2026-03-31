package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * EM_RPC_NETAPP_TYPE_GET_MOBILE_RSSI 出参
*/
public class NET_OUT_NETAPP_GET_MOBILE_RSSI extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 蜂窝移动模块的工作模式,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_WIRELESS_WORK_MODE}
    */
    public int              emWorkMode;
    /**
     * TD-SCDMA网络信号强度 范围0-100
    */
    public int              nTDSCDMA;
    /**
     * WCDMA网络信号强度 范围0-100
    */
    public int              nWCDMA;
    /**
     * CDMA1.x网络信号强度 范围0-100
    */
    public int              nCDMA1x;
    /**
     * EDGE网络信号强度 范围0-100
    */
    public int              nEDGE;
    /**
     * EVDO网络信号强度 范围0-100
    */
    public int              nEVDO;
    /**
     * TD-LTE网络信号强度 范围0-100
    */
    public int              nTDLTE;
    /**
     * FDD-LTE网络信号强度 范围0-100
    */
    public int              nFDDLTE;
    /**
     * NR网络信号强度 范围0-100
    */
    public int              nNR;
    /**
     * 当前网络模式下的信号强度值，单位dBm
    */
    public int              nRSSI;
    /**
     * 当前网络模式下的信干噪比值，单位dB
    */
    public int              nSINR;
    /**
     * 信号强度等级
    */
    public int              nSignalLevel;
    /**
     * 频段信息
    */
    public byte[]           szBAND = new byte[6];
    /**
     * 预留对齐字节
    */
    public byte[]           szReserved = new byte[2];
    /**
     * 绝对无线频道编号
    */
    public int              nARFCN;
    /**
     * 频宽，单位MHz
    */
    public int              nBandWidth;
    /**
     * 物理小区标识
    */
    public int              nPCI;
    /**
     * v2x 信噪比，十进制整数
    */
    public int              nV2X_SINR;
    /**
     * v2x 信号强度 十进制整数，单位dBm
    */
    public int              nV2X_RSRP;
    /**
     * 小区标识，取值范围0-0xFFFFFFFFF，字符串类型，16进制表示, 不带0x
    */
    public byte[]           szCELLID = new byte[15];
    /**
     * 预留对齐字节
    */
    public byte[]           szReserved1 = new byte[1];
    /**
     * 运营商PLMN信息，十进制整数
    */
    public int              nPLMN;
    /**
     * 服务小区跟踪区域码，取值范围0-0xFFFFFF，字符串类型，16进制表示
    */
    public byte[]           szTAC = new byte[10];
    /**
     * 预留对齐字节
    */
    public byte[]           szReserved2 = new byte[2];
    /**
     * 参考信号接收功率，十进制整数，单位：dB
    */
    public int              nRSRP;
    /**
     * 接收信号强度指示，0~31，99-未知或不可测，十进制整数
    */
    public int              nCSQRSSI;

    public NET_OUT_NETAPP_GET_MOBILE_RSSI() {
        this.dwSize = this.size();
    }
}

