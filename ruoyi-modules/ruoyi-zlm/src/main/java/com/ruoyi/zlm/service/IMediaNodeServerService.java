package com.ruoyi.zlm.service;

import com.ruoyi.zlm.api.domain.StreamPullPlay;
import com.ruoyi.zlm.api.domain.MediaInfo;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;

import java.util.List;

/**
 * 媒体节点服务接口
 *
 * @FileName IMediaNodeServerService
 * @Description
 * @Author fengcheng
 * @date 2026-03-31
 **/
public interface IMediaNodeServerService {
    List<StreamInfo> getMediaList(ZlmMediaServer mediaServer, String app, String stream);

    StreamInfo getStreamInfoByAppAndStream(ZlmMediaServer mediaServer, String app, String stream, MediaInfo mediaInfo, String addr, boolean isPlay);

    String startProxy(ZlmMediaServer mediaServer, StreamPullPlay streamPullPlay);

    void stopProxy(ZlmMediaServer mediaServer, String streamKey);
}
