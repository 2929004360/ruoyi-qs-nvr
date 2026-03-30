package com.ruoyi.haikang.isup.api.domain;

import lombok.Data;

/**
 * 设备信息
 *
 * @FileName DeviceInfo
 * @Description
 * @Author fengcheng
 * @date 2025-12-24
 **/
@Data
public class HaiKangIsupDeviceInfo {
    /**
     * 模拟通道个数
     */
    public int dwChannelNumber;

    /**
     * 通道总数（包括模拟通道和数字通道）
     */
    public int dwChannelAmount;

    /**
     * 设备类型：1-数字视频录像机，3-数字视频服务器，30-网络摄像机，40-网络球机。
     */
    public int dwDevType;

    /**
     * 设备中的硬盘数量
     */
    public int dwDiskNumber;

    /**
     * 设备序列号。
     */
    public String sSerialNumber;

    /**
     * 模拟通道关联的报警输入个数。
     */
    public int dwAlarmInPortNum;

    /**
     * 报警输入总数
     */
    public int dwAlarmInAmount;

    /**
     * 模拟通道关联的报警输出个数
     */
    public int dwAlarmOutPortNum;

    /**
     * 报警输出总数
     */
    public int dwAlarmOutAmount;

    /**
     * 起始视频通道号
     */
    public int dwStartChannel;

    /**
     * 语音对讲通道个数
     */
    public int dwAudioChanNum;

    /**
     * 设备支持的最大数字通道个数
     */
    public int dwMaxDigitChannelNum;

    /**
     * 语音对讲的音频格式：0-G.722，1-G.711U，2-G.711A，3-G.726，4-AAC，5-MP2L2。
     */
    public int dwAudioEncType;  // 语音对讲的音频格式：0-G.722，1-G.711U，2-G.711A，3-G.726，4-AAC，5-MP2L2。

    /**
     * SIM 卡序列号（车载设备扩展）。最大长度为128 字节（对应宏定义为“MAX_SERIALNO_LEN”）
     */
    public String sSIMCardSN;

    /**
     * SIM 卡手机号码（车载设备扩展）。最大长度为 32 字节（对应宏定义为“MAX_PHOMENUM_LEN”）
     */
    public String sSIMCardPhoneNum;

    /**
     * 支持的零通道个数
     */
    public int dwSupportZeroChan;

    /**
     * 零通道的起始编号，默认为 10000。
     */
    public int dwStartZeroChan;

    /**
     * 0-智能（默认），1-专业智能。
     */
    public int dwSmartType;

    public String byRes1;

    public byte byStartDTalkChan;

    public String byRes;
}
