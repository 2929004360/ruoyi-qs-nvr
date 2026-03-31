package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 事件类型NET_ALARM_USER_PASS_CONFIRM (用户通过闸机进入或离开事件)对应的数据块描述信息
 * @date 2022/5/26 16:43
 */

public class NET_ALARM_USER_PASS_CONFIRM_INFO extends NetSDKLib.SdkStructure {
    /**
     * 事件动作,0 就是表示 pause， 1表示持续性事件开始,2表示持续性事件结束;
     */
    public int              nAction;
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      stuTime = new NET_TIME_EX();
    /**
     * stuRealUTC 是否有效，bRealUTC 为 1 时，用 stuRealUTC，否则用 stuTime 字段
     */
    public int              bRealUTC;
    /**
     *  事件发生的时间(标准UTC时间)
     */
    public NET_TIME_EX      stuRealUTC = new NET_TIME_EX();
    /**
     * 仅用于字节对齐
     */
    public byte[]           byReserved = new byte[4];
    /**
     * 通过人数数量
     */
    public int              nCount;
    /**
     * 进出方向(事件类型) {@link com.ruoyi.dahua.lib.enumeration.EM_USER_PASS_TYPE}
     */
    public int              emType;
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[1024];
}

