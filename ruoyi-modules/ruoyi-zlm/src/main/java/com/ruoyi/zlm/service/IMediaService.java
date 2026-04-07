package com.ruoyi.zlm.service;

import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.hook.ResultForOnPublish;

/**
 * 媒体信息业务
 */
public interface IMediaService {

    boolean closeStreamOnNoneReader(String mediaServerId, String app, String stream, String schema);

    ResultForOnPublish authenticatePublish(ZlmMediaServer mediaServer, String app, String stream, String params);
}
