package com.ruoyi.zlm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.api.model.v2.ErrorCode;
import com.ruoyi.zlm.api.domain.DownloadFileInfo;
import com.ruoyi.zlm.config.RedisRpcConfig;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.domain.redis.RedisRpcRequest;
import com.ruoyi.zlm.domain.redis.RedisRpcResponse;
import com.ruoyi.zlm.service.IRedisRpcPlayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @FileName RedisRpcPlayServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-04-11
 **/
@Slf4j
@Service
public class RedisRpcPlayServiceImpl implements IRedisRpcPlayService {

    @Autowired
    private RedisRpcConfig redisRpcConfig;

    @Autowired
    private UserSetting userSetting;

    private RedisRpcRequest buildRequest(String uri, Object param) {
        RedisRpcRequest request = new RedisRpcRequest();
        request.setFromId(userSetting.getServerId());
        request.setParam(param);
        request.setUri(uri);
        return request;
    }

    @Override
    public DownloadFileInfo getRecordPlayUrl(String serverId, Long recordId) {
        RedisRpcRequest request = buildRequest("cloudRecord/play", recordId);
        RedisRpcResponse response = redisRpcConfig.request(request, userSetting.getPlayTimeout(), TimeUnit.SECONDS);
        if (response != null && response.getStatusCode() == ErrorCode.SUCCESS.getCode()) {
            return JSON.parseObject(response.getBody().toString(), DownloadFileInfo.class);
        }
        return null;
    }
}
