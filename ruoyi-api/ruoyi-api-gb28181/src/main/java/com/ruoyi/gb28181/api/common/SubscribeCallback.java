package com.ruoyi.gb28181.api.common;

import com.ruoyi.gb28181.api.bean.SipTransactionInfo;

public interface SubscribeCallback {
    public void run(String deviceId, SipTransactionInfo transactionInfo);
}
