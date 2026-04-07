package com.ruoyi.zlm.service;

/**
 * 媒体信息业务
 */
public interface IMediaService {

    boolean closeStreamOnNoneReader(String mediaServerId, String app, String stream, String schema);
}
