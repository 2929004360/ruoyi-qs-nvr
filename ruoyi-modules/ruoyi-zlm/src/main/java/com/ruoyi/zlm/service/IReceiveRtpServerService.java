package com.ruoyi.zlm.service;

import com.ruoyi.zlm.api.domain.RTPServerParam;
import com.ruoyi.zlm.domain.OpenRTPServerResult;
import com.ruoyi.zlm.domain.SSRCInfo;

/**
 * @FileName IReceiveRtpServerService
 * @Description
 * @Author fengcheng
 * @date 2026-04-07
 **/
public interface IReceiveRtpServerService {

    SSRCInfo openRTPServer(RTPServerParam rtpServerParam, ErrorCallback<OpenRTPServerResult> callback);
}
