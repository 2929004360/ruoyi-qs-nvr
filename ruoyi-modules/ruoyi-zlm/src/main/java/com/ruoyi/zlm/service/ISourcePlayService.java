package com.ruoyi.zlm.service;

import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.StreamInfo;

/**
 * 资源能力接入-实时录像
 */
public interface ISourcePlayService {

    void play(QsDevice device, Boolean record, ErrorCallback<StreamInfo> callback);
}
