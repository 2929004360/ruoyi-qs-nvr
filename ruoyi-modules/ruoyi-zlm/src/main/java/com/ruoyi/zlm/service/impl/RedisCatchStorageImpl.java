package com.ruoyi.zlm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.constants.VideoManagerConstants;
import com.ruoyi.zlm.api.domain.MediaInfo;
import com.ruoyi.zlm.domain.StreamAuthorityInfo;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.service.IRedisCatchStorage;
import com.ruoyi.zlm.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @FileName RedisCatchStorageImpl
 * @Description
 * @Author fengcheng
 * @date 2026-04-01
 **/
@SuppressWarnings("rawtypes")
@Slf4j
@Component
public class RedisCatchStorageImpl implements IRedisCatchStorage {

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void addStream(ZlmMediaServer mediaServerItem, String type, String app, String streamId, MediaInfo mediaInfo) {
        // 查找是否使用了callID
        StreamAuthorityInfo streamAuthorityInfo = getStreamAuthorityInfo(app, streamId);
        String key = VideoManagerConstants.ZLM_SERVER_STREAM_PREFIX + userSetting.getServerId() + "_" + type.toUpperCase() + "_" + app + "_" + streamId + "_" + mediaServerItem.getId();
        if (streamAuthorityInfo != null) {
            mediaInfo.setCallId(streamAuthorityInfo.getCallId());
        }
        redisTemplate.opsForValue().set(key, JSON.toJSONString(mediaInfo));
    }

    @Override
    public void removeStream(String mediaServerId, String type, String app, String streamId) {
        String key = VideoManagerConstants.ZLM_SERVER_STREAM_PREFIX + userSetting.getServerId() + "_" + type.toUpperCase() + "_"  + app + "_" + streamId + "_" + mediaServerId;
        redisTemplate.delete(key);
    }

    /**
     * 获取流信息
     *
     * @param app
     * @param streamId
     * @param mediaServerId
     * @return
     */
    @Override
    public MediaInfo getStreamInfo(String app, String streamId, String mediaServerId) {
        String scanKey = VideoManagerConstants.ZLM_SERVER_STREAM_PREFIX  + userSetting.getServerId() + "_*_" + app + "_" + streamId + "_" + mediaServerId;

        MediaInfo result = null;
        List<Object> keys = RedisUtil.scan(redisTemplate, scanKey);
        if (keys.size() > 0) {
            String key = (String) keys.get(0);
            String mediaInfoJson = (String)redisTemplate.opsForValue().get(key);
            result = JSON.parseObject(mediaInfoJson, MediaInfo.class);
        }

        return result;
    }

    public StreamAuthorityInfo getStreamAuthorityInfo(String app, String stream) {
        String key = VideoManagerConstants.MEDIA_STREAM_AUTHORITY;
        String objectKey = app+ "_" + stream;
        return (StreamAuthorityInfo)redisTemplate.opsForHash().get(key, objectKey);
    }

    /**
     * 存储推流的鉴权信息
     *
     * @param app 应用名
     * @param stream 流
     * @param streamAuthorityInfo 鉴权信息
     */
    @Override
    public void updateStreamAuthorityInfo(String app, String stream, StreamAuthorityInfo streamAuthorityInfo) {
        String key = VideoManagerConstants.MEDIA_STREAM_AUTHORITY;
        String objectKey = app+ "_" + stream;
        redisTemplate.opsForHash().put(key, objectKey, streamAuthorityInfo);
    }

    /**
     * 添加推流列表信息到redis
     *
     * @param app
     * @param stream
     * @param mediaInfo
     */
    @Override
    public void addPushListItem(String app, String stream, MediaInfo mediaInfo) {
        String key = VideoManagerConstants.PUSH_STREAM_LIST + app + "_" + stream;
        redisTemplate.opsForValue().set(key, mediaInfo);
    }

    /**
     * 获取推流列表信息从redis
     *
     * @param app
     * @param stream
     * @return
     */
    @Override
    public MediaInfo getPushListItem(String app, String stream) {
        String key = VideoManagerConstants.PUSH_STREAM_LIST + app + "_" + stream;
        return (MediaInfo)redisTemplate.opsForValue().get(key);
    }

    /**
     * 移除推流的鉴权信息
     *
     * @param app
     * @param stream
     * @param id
     */
    @Override
    public void removePushListItem(String app, String stream, String id) {
        String key = VideoManagerConstants.PUSH_STREAM_LIST + app + "_" + stream;
        MediaInfo param = (MediaInfo)redisTemplate.opsForValue().get(key);
        if (param != null) {
            redisTemplate.delete(key);
        }
    }
}
