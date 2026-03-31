package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;

/**
 * @author 119178
 * @description 平台接入配置({@link EM_NEW_CONFIG#CFG_CMD_VSP_GAYS})
 * @date 2021/4/21
 */
public class CFG_VSP_GAYS_INFO extends SdkStructure {
    public int              bEnable;                              // 是否使能，TRUE使能，FALSE不使能
    public byte[]           szSipSvrId = new byte[NetSDKLib.CFG_COMMON_STRING_64]; // SIP服务器编号	字符串（24位）
    public byte[]           szDomain = new byte[NetSDKLib.CFG_COMMON_STRING_256]; // SIP域	字符串（128位）
    public byte[]           szSipSvrIp = new byte[NetSDKLib.CFG_COMMON_STRING_32]; // SIP服务器IP	字符串（16位）
    public byte[]           szDeviceId = new byte[NetSDKLib.CFG_COMMON_STRING_64]; // 设备编号，也是注册用户名	字符串（24位）
    public byte[]           szPassword = new byte[NetSDKLib.CFG_COMMON_STRING_64]; // 注册密码,密文	字符串（24位）
    public short            nLocalSipPort;                        // 本地SIP服务端口	无符号短整型
    public short            nSipSvrPort;                          // SIP服务器端口	无符号短整型
    public int              nSipRegExpires;                       // 注册有效期	整型
    public int              nKeepAliveCircle;                     // 心跳周期	整型
    public int              nMaxTimeoutTimes;                     // 最大心跳超时次数	整型
    public byte[]           szCivilCode = new byte[NetSDKLib.CFG_COMMON_STRING_64]; // 行政区划代码	字符串（24位）
    public byte[]           szIntervideoID = new byte[NetSDKLib.CFG_COMMON_STRING_64]; // 接入模块识别码	字符串（24位）
    public short            nChannelSum;                          // 通道数	无符号短整型
    public short            nAlarmInSum;                          // 外部报警输入数	无符号短整型
    public CFG_VSP_GAYS_CHANNEL_INFO[] stuChannelInfo = (CFG_VSP_GAYS_CHANNEL_INFO[])new CFG_VSP_GAYS_CHANNEL_INFO().toArray(NetSDKLib.MAX_VIDEO_CHANNEL_NUM); // 通道相关信息数组，有效个数同channelSum
    public CFG_VSP_GAYS_ALARM_INFO[] stuAlarmInfo = (CFG_VSP_GAYS_ALARM_INFO[])new CFG_VSP_GAYS_ALARM_INFO().toArray(32); // 报警相关信息	数组，有效个数同alarmInSum
}

