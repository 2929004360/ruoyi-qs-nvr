package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 251823
 * @description 连接的编码器信息
 * @date 2022/08/10
 */
public class DEV_ENCODER_INFO extends NetSDKLib.SdkStructure {
    /**
     *  前端DVR的IP地址
     */
    public byte[]           szDevIp = new byte[NetSDKLib.NET_MAX_IPADDR_LEN];
    /**
     *  前端DVR的端口号
     */
    public short            wDevPort;
    /**
     *  解码通道使能
     */
    public byte             bDevChnEnable;
    /**
     *  该字段已废弃,后续开发请用 dwDecoderID
     */
    public byte             byDecoderID;
    /**
     *  用户名
     */
    public byte[]           szDevUser = new byte[NetSDKLib.NET_USER_NAME_LENGTH_EX];
    /**
     *  密码
     */
    public byte[]           szDevPwd = new byte[NetSDKLib.NET_USER_PSW_LENGTH_EX];
    /**
     *  通道号
     */
    public int              nDevChannel;
    /**
     *  码流类型,0：主码流；1：辅码流1; 2:snap; 3: 辅码流2
     */
    public int              nStreamType;
    /**
     *  -1: auto, 0：TCP；1：UDP；2：组播
     */
    public byte             byConnType;
    /**
     *  0：直连；1：转发
     */
    public byte             byWorkMode;
    /**
     *  指示侦听服务的端口,转发时有效
     */
    public short            wListenPort;
    /**
     *  协议类型,
     *  0:兼容以前
     *  1:二代协议
     *  2:集成协议
     *  3:DSS协议
     *  4:rtsp协议
     */
    public int              dwProtoType;
    /**
     *  前端设备名称
     */
    public byte[]           szDevName = new byte[64];
    /**
     *  该解码通道当前视频源类型:0-标清(SD),1-高清(HD),注:设备支持高清通道才有效 
     */
    public byte             byVideoInType;
    /**
     *  szDevIp扩展,前端DVR的IP地址(可以输入域名)
     */
    public byte[]           szDevIpEx = new byte[NetSDKLib.NET_MAX_IPADDR_OR_DOMAIN_LEN];
    /**
     *  抓图模式(nStreamType==2时有效) 0：表示请求一帧,1：表示定时发送请求
     */
    public byte             bySnapMode;
    /**
     *  目标设备的生产商,具体参考EM_IPC_TYPE类
     */
    public byte             byManuFactory;
    /**
     *  目标设备的设备类型,0:IPC
     */
    public byte             byDeviceType;
    /**
     *  目标设备的解码策略,0:兼容以前
     *  1:实时等级高 2:实时等级中
     *  3:实时等级低 4:默认等级
     *  5:流畅等级高 6:流畅等级中
     *  7:流畅等级低
     */
    public byte             byDecodePolicy;
    /**
     *  保留字节
     */
    public byte[]           bReserved = new byte[3];
    /**
     *  Http端口号,0-65535
     */
    public int              dwHttpPort;
    /**
     *  Rtsp端口号,0-65535
     */
    public int              dwRtspPort;
    /**
     *  远程通道名称, 只有读取到的名称不为空时才可以修改该通道的名称
     */
    public byte[]           szChnName = new byte[32];
    /**
     *  对应解码通道号
     */
    public int              dwDecoderID;
}

