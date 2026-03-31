package com.ruoyi.dahua.lib.callback.securityCheck;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;

/**
 * @author 291189
 * @version 1.0
 * @description 安全门报警订阅回调函数
 * @date 2021/6/29
 */
public interface fSecurityGateAttachAlarmStatistics extends Callback{

    /**
     * @param lAttachHandle 订阅句柄
     * @param pInfo 安全门报警统计信息，对应结构体{@link com.ruoyi.dahua.lib.structure.NET_SECURITYGATE_ALARM_STATISTICS_INFO}
     * @param dwUser 用户数据
     */
    void invoke(
            NetSDKLib.LLong lAttachHandle,
            Pointer pInfo,
            Pointer dwUser);

   // (LLONG lAttachHandle, NET_SECURITYGATE_ALARM_STATISTICS_INFO* pInfo, LDWORD dwUser);
}
