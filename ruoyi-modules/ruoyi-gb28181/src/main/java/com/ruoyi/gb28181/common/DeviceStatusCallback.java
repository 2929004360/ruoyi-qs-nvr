package com.ruoyi.gb28181.common;

import com.ruoyi.gb28181.bean.SipTransactionInfo;

public interface DeviceStatusCallback {
    public void run(String deviceId, SipTransactionInfo transactionInfo);
}
