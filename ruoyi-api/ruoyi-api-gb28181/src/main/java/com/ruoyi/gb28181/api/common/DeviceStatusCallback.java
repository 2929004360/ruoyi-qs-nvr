package com.ruoyi.gb28181.api.common;

import com.ruoyi.gb28181.api.bean.SipTransactionInfo;

public interface DeviceStatusCallback {
    public void run(String deviceId, SipTransactionInfo transactionInfo);
}
