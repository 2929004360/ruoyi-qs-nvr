package com.ruoyi.gb28181.task.deviceSubscribe.deviceSubscribe;

import com.ruoyi.gb28181.api.bean.SipTransactionInfo;
import lombok.Data;

@Data
public class SubscribeTaskInfo {

    private String deviceId;

    private SipTransactionInfo transactionInfo;

    private String name;

    private String key;

    /**
     * 过期时间，单位： 秒
     */
    private long expireTime;

}
