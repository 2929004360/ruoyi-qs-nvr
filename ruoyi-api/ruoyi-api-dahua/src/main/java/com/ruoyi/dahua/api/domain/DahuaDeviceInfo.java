package com.ruoyi.dahua.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 大华设备详细信息
 *
 * @author ruoyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DahuaDeviceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 序列号
     */
    private String serialNumber;

    /**
     * 报警输入个数
     */
    private Integer alarmInPortNum;

    /**
     * 报警输出个数
     */
    private Integer alarmOutPortNum;

    /**
     * 硬盘个数
     */
    private Integer diskNum;

    /**
     * DVR类型
     */
    private Integer dvrType;

    /**
     * 通道个数
     */
    private Integer channelNum;

    /**
     * 在线超时时间(分钟)，0表示不限制
     */
    private Integer limitLoginTime;

    /**
     * 剩余登陆次数
     */
    private Integer leftLogTimes;

    /**
     * 解锁剩余时间(秒)，-1表示设备未设置
     */
    private Integer lockLeftTime;

}
