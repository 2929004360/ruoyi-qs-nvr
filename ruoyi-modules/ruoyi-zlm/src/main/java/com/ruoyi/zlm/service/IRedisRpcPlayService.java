package com.ruoyi.zlm.service;

import com.ruoyi.zlm.api.domain.DownloadFileInfo;

/**
 * @FileName IRedisRpcPlayService
 * @Description
 * @Author fengcheng
 * @date 2026-04-11
 **/
public interface IRedisRpcPlayService {

    DownloadFileInfo getRecordPlayUrl(String serverId, Long recordId);
}
