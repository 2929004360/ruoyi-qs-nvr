package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 矩阵子卡信息
*/
public class NET_MATRIX_CARDS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 主机号, 从0开始编号, 含主机
    */
    public int              nHost;
    /**
     * 子卡号, 从0开始编号, 含主卡
    */
    public int              nCard;
    /**
     * 子卡类型个数
    */
    public int              nTypeCount;
    /**
     * 子卡类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_MATRIX_SUB_CARD_TYPE}
    */
    public int[]            emType = new int[32];
    /**
     * 清晰度类型
    */
    public byte[]           szDefinition = new byte[32];
    /**
     * 子卡接口类型
    */
    public byte[]           szInterface = new byte[32];
    /**
     * 视频输入通道数目
    */
    public int              nVideoInputChannels;
    /**
     * 视频输入通道接口类型个数
    */
    public int              nVideoInputInterfaceTypeCount;
    /**
     * 视频输入通道接口类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_VIDEO_INTERFACE_TYPE}
    */
    public int[]            emVideoInputInterfaceType = new int[32];
    /**
     * 视频输入逻辑通道号起止值
    */
    public int[]            nVideoInputChannelsRange = new int[2];
    /**
     * 音频输入通道数目
    */
    public int              nAudioInputChannels;
    /**
     * 音频输入逻辑通道号起止值
    */
    public int[]            nAudioInputChannelsRange = new int[2];
    /**
     * 视频输出通道数目
    */
    public int              nVideoOutputChannels;
    /**
     * 视频输出通道接口类型个数
    */
    public int              nVideoOutputInterfaceTypeCount;
    /**
     * 视频输出通道接口类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_VIDEO_INTERFACE_TYPE}
    */
    public int[]            emVideoOutputInterfaceType = new int[32];
    /**
     * 视频输出逻辑通道号起止值
    */
    public int[]            nVideoOutputChannelsRange = new int[2];
    /**
     * 音频输出通道数目
    */
    public int              nAudioOutputChannels;
    /**
     * 音频输出逻辑通道号起止值
    */
    public int[]            nAudioOutputChannelsRange = new int[2];
    /**
     * 视频编码通道数目
    */
    public int              nVideoEncodeChannels;
    /**
     * 视频编码逻辑通道号起止值
    */
    public int[]            nVideoEncodeChannelsRange = new int[2];
    /**
     * 音频编码通道数目
    */
    public int              nAudioEncodeChannels;
    /**
     * 音频编码逻辑通道号起止值
    */
    public int[]            nAudioEncodeChannelsRange = new int[2];
    /**
     * 视频解码通道数目
    */
    public int              nVideoDecodeChannels;
    /**
     * 视频解码逻辑通道起止值
    */
    public int[]            nVideoDecodeChannelsRange = new int[2];
    /**
     * 音频解码通道数目
    */
    public int              nAudioDecodeChannels;
    /**
     * 音频解码逻辑通道起止值
    */
    public int[]            nAudioDecodeChannelsRange = new int[2];
    /**
     * 智能分析通道数目
    */
    public int              nVideoAnalyseChannels;
    /**
     * 智能分析逻辑通道号起止值
    */
    public int[]            nVideoAnalyseChannelsRange = new int[2];
    /**
     * 报警输入通道数目
    */
    public int              nAlarmInputChannels;
    /**
     * 报警输入逻辑通道号起止值
    */
    public int[]            nAlarmInputChannelsRange = new int[2];
    /**
     * 报警输出通道数目
    */
    public int              nAlarmOutputChannels;
    /**
     * 报警输出逻辑通道号起止值
    */
    public int[]            nAlarmOutputChannelsRange = new int[2];
    /**
     * 子卡级联通道数
    */
    public int              nCascadeChannels;
    /**
     * 子卡级联通道带宽, 单位Mbps
    */
    public int              nCascadeChannelBitrate;
    /**
     * 设备IP地址或域名
    */
    public byte[]           szAddress = new byte[40];
    /**
     * 端口号
    */
    public int              nPort;
    /**
     * 子网掩码
    */
    public byte[]           szMask = new byte[40];
    /**
     * 网关
    */
    public byte[]           szGateway = new byte[40];
    /**
     * Mac地址，冒号+大写
    */
    public byte[]           szMac = new byte[40];
    /**
     * 串口数目
    */
    public int              nCommPorts;
    /**
     * 串口逻辑通道号起止值
    */
    public int[]            nCommChannelsRange = new int[2];
    /**
     * 子卡状态: "Normal": 正常, "NoResponse": 无响应, "NetAbort": 网络掉线, "IPConflict": IP冲突, "Upgrading": 正在升级, "LinkStatusError": 链路状态异常, "AdapterLinkOff": 子板背板未插好
    */
    public byte[]           szStatus = new byte[32];
    /**
     * 子卡模式
    */
    public byte[]           szMode = new byte[32];
    /**
     * 子卡版本信息
    */
    public byte[]           szVersion = new byte[32];
    /**
     * 子卡版本编译时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuBuildTime = new NetSDKLib.NET_TIME();
    /**
     * Biso版本信息
    */
    public byte[]           szBiosVersion = new byte[64];
    /**
     * 编码卡音频源类型, 0:未知, 1:只有NORMAL, 3:NORMAL及HDMI
    */
    public int              nAudioType;
    /**
     * 芯片个数
    */
    public int              nChipNum;
    /**
     * 单片机版本号个数
    */
    public int              nMCUVersionCount;
    /**
     * 单片机版本号
    */
    public BYTE_ARRAY_32[]  szMCUVersion = new BYTE_ARRAY_32[10];
    /**
     * FPGA版本个数
    */
    public int              nFPGAVersionCount;
    /**
     * FPGA版本
    */
    public BYTE_ARRAY_32[]  szFPGAVersion = new BYTE_ARRAY_32[10];
    /**
     * 电视墙回显通道数目
    */
    public int              nMonitorWallEchoDisplayChannels;
    /**
     * 电视墙回显逻辑通道号枚举个数
    */
    public int              nMonitorWallEchoDisplayChannelsEnumCount;
    /**
     * 电视墙回显逻辑通道号枚举
    */
    public int[]            nMonitorWallEchoDisplayChannelsEnum = new int[128];
    /**
     * 视频输出逻辑通道号枚举个数
    */
    public int              nVideoOutputChannelsEnumCount;
    /**
     * 视频输出逻辑通道号枚举
    */
    public int[]            nVideoOutputChannelsEnum = new int[128];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[496];

    public NET_MATRIX_CARDS_INFO() {
        for(int i = 0; i < szMCUVersion.length; i++){
            szMCUVersion[i] = new BYTE_ARRAY_32();
        }
        for(int i = 0; i < szFPGAVersion.length; i++){
            szFPGAVersion[i] = new BYTE_ARRAY_32();
        }
    }
}

