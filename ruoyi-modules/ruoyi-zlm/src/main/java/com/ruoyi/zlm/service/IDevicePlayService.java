package com.ruoyi.zlm.service;

import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.StreamInfo;

public interface IDevicePlayService {

    void play(QsDevice device, Boolean record, ErrorCallback<StreamInfo> callback);
}
