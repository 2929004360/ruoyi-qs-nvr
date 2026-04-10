package com.ruoyi.zlm.service;

import com.ruoyi.zlm.api.domain.MediaInfo;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.domain.StreamAuthorityInfo;

public interface IRedisCatchStorage {

    /**
     * 添加流信息到redis
     *
     * @param mediaServerItem
     * @param app
     * @param streamId
     */
    void addStream(ZlmMediaServer mediaServerItem, String type, String app, String streamId, MediaInfo mediaInfo);

    /**
     * 移除流信息从redis
     *
     * @param mediaServerId
     * @param app
     * @param streamId
     */
    void removeStream(String mediaServerId, String type, String app, String streamId);

    /**
     * 获取流信息
     *
     * @param app
     * @param streamId
     * @param mediaServerId
     * @return
     */
    MediaInfo getStreamInfo(String app, String streamId, String mediaServerId);

    /**
     * 获取推流的鉴权信息
     * @param app 应用名
     * @param stream 流
     * @return
     */
    StreamAuthorityInfo getStreamAuthorityInfo(String app, String stream);
}
