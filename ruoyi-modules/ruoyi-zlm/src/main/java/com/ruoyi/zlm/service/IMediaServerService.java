package com.ruoyi.zlm.service;

import com.ruoyi.zlm.api.domain.StreamPullPlay;
import com.ruoyi.zlm.api.domain.MediaInfo;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;

import java.util.List;

/**
 * 媒体服务器Service接口
 *
 * @FileName IMediaServerService
 * @Description
 * @Author fengcheng
 * @date 2026-03-31
 **/
public interface IMediaServerService {

    /**
     * 获取默认的媒体服务器
     *
     * @return
     */
    ZlmMediaServer getDefaultMediaServer();

    /**
     * 添加媒体服务器
     *
     * @param zlmMediaServer
     */
    int add(ZlmMediaServer zlmMediaServer);

    /**
     * 修改媒体服务器
     *
     * @param zlmMediaServer
     */
    int update(ZlmMediaServer zlmMediaServer);

    /**
     * 删除媒体服务器
     *
     * @param zlmMediaServer
     */
    void delete(ZlmMediaServer zlmMediaServer);

    /**
     * 从数据库中获取所有媒体服务器
     *
     * @return
     */
    List<ZlmMediaServer> getAllFromDatabase();

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    ZlmMediaServer getOneFromDatabase(String id);

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    ZlmMediaServer getOne(String id);

    /**
     * 获取所有在线媒体服务器
     *
     * @return
     */
    List<ZlmMediaServer> getAllOnlineMediaServe();

    /**
     * 获取负载最小的媒体服务器
     *
     * @param hasAssist 是否包含辅助媒体服务器
     * @return
     */
    ZlmMediaServer getMediaServerForMinimumLoad(Boolean hasAssist);

    /**
     * 拉流播放
     *
     * @param streamPullPlay 拉流播放请求参数
     * @param callback       回调
     */
    void streamPullPlay(StreamPullPlay streamPullPlay, ErrorCallback<StreamInfo> callback);

    /**
     * 根据应用名和流ID获取播放地址, 通过zlm接口检查是否存在
     *
     * @param app           应用名
     * @param stream        流ID
     * @param mediaServerId 媒体服务器ID
     * @param addr          媒体服务器地址
     * @param authority     鉴权
     * @return
     */
    StreamInfo getStreamInfoByAppAndStreamWithCheck(String app, String stream, String mediaServerId, String addr, boolean authority);

    /**
     * 根据应用名和流ID获取播放地址, 只是地址拼接
     *
     * @param mediaServer 媒体服务器
     * @param app 应用名
     * @param stream 流ID
     * @param mediaInfo 媒体信息
     * @return
     */
    StreamInfo getStreamInfoByAppAndStream(ZlmMediaServer mediaServer, String app, String stream, MediaInfo mediaInfo);

    /**
     * 停止拉流播放
     *
     * @param streamPullPlay
     */
    void stopStreamPullPlay(StreamPullPlay streamPullPlay);
}
