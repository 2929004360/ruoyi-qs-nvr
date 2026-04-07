package com.ruoyi.zlm.service.impl;

import com.alibaba.nacos.api.model.v2.ErrorCode;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.MediaInfo;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.api.domain.StreamPullPlay;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.api.hook.OriginType;
import com.ruoyi.zlm.common.InviteErrorCode;
import com.ruoyi.zlm.config.DynamicTask;
import com.ruoyi.zlm.config.MediaConfig;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.constants.VideoManagerConstants;
import com.ruoyi.zlm.event.MediaArrivalEvent;
import com.ruoyi.zlm.event.MediaDepartureEvent;
import com.ruoyi.zlm.hook.Hook;
import com.ruoyi.zlm.hook.HookSubscribe;
import com.ruoyi.zlm.hook.HookType;
import com.ruoyi.zlm.mapper.MediaServerMapper;
import com.ruoyi.zlm.mediaServer.MediaNotFoundEvent;
import com.ruoyi.zlm.mediaServer.MediaServerDeleteEvent;
import com.ruoyi.zlm.mediaServer.MediaServerOfflineEvent;
import com.ruoyi.zlm.mediaServer.MediaServerOnlineEvent;
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IMediaNodeServerService;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.service.IRedisCatchStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 媒体服务器Service接口实现类
 *
 * @FileName MediaServerServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-03-31
 **/
@Slf4j
@Service
public class MediaServerServiceImpl implements IMediaServerService {

    @Autowired
    private MediaServerMapper mediaServerMapper;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private Map<String, IMediaNodeServerService> nodeServerServiceMap;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IRedisCatchStorage redisCatchStorage;

    @Autowired
    private MediaConfig mediaConfig;

    @Autowired
    private IMediaNodeServerService mediaNodeServerService;

    @Autowired
    private DynamicTask dynamicTask;

    @Autowired
    private HookSubscribe subscribe;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;


    /**
     * 流到来的处理
     */
    @Async("taskExecutor")
    @EventListener
    public void onApplicationEvent(MediaArrivalEvent event) {
        if ("rtsp".equals(event.getSchema())) {
            log.info("流变化：注册 app->{}, stream->{}", event.getApp(), event.getStream());
            addCount(event.getMediaServer().getId());
            String type = OriginType.values()[event.getMediaInfo().getOriginType()].getType();
            redisCatchStorage.addStream(event.getMediaServer(), type, event.getApp(), event.getStream(), event.getMediaInfo());
        }
    }

    /**
     * 流离开的处理
     */
    @Async("taskExecutor")
    @EventListener
    public void onApplicationEvent(MediaDepartureEvent event) {
        if ("rtsp".equals(event.getSchema())) {
            log.info("流变化：注销, app->{}, stream->{}", event.getApp(), event.getStream());
            removeCount(event.getMediaServer().getId());
            MediaInfo mediaInfo = redisCatchStorage.getStreamInfo(event.getApp(), event.getStream(), event.getMediaServer().getId());
            if (mediaInfo == null) {
                return;
            }
            String type = OriginType.values()[mediaInfo.getOriginType()].getType();
            redisCatchStorage.removeStream(mediaInfo.getMediaServer().getId(), type, event.getApp(), event.getStream());
        }
    }

    /**
     * 流未找到的处理
     */
    @Async("taskExecutor")
    @EventListener
    public void onApplicationEvent(MediaNotFoundEvent event) {
        log.info("[拉流代理] 自动点播成功，");
    }

    /**
     * 流媒体节点上线
     */
    @Async("taskExecutor")
    @EventListener
    @Transactional
    public void onApplicationEvent(MediaServerOnlineEvent event) {
        // 查看是否有未处理的RTP流
        log.info("流媒体节点上线: {}", event.getMediaServer());
    }

    /**
     * 流媒体节点离线
     */
    @Async("taskExecutor")
    @EventListener
    @Transactional
    public void onApplicationEvent(MediaServerOfflineEvent event) {
        log.info("流媒体节点离线: {}", event.getMediaServer());
    }

    public void addCount(String mediaServerId) {
        if (mediaServerId == null) {
            return;
        }
        String key = VideoManagerConstants.ONLINE_MEDIA_SERVERS_PREFIX + userSetting.getServerId();
        redisTemplate.opsForZSet().incrementScore(key, mediaServerId, 1);

    }

    public void removeCount(String mediaServerId) {
        String key = VideoManagerConstants.ONLINE_MEDIA_SERVERS_PREFIX + userSetting.getServerId();
        redisTemplate.opsForZSet().incrementScore(key, mediaServerId, -1);
    }

    /**
     * 获取默认的媒体服务器
     *
     * @return
     */
    @Override
    public ZlmMediaServer getDefaultMediaServer() {
        return mediaServerMapper.queryDefault(userSetting.getServerId());
    }

    /**
     * 添加媒体服务器
     *
     * @param zlmMediaServer
     */
    @Override
    public int add(ZlmMediaServer zlmMediaServer) {
        zlmMediaServer.setCreateTime(DateUtils.getNowDate());
        zlmMediaServer.setUpdateTime(DateUtils.getNowDate());
        if (zlmMediaServer.getHookAliveInterval() == null || zlmMediaServer.getHookAliveInterval() == 0F) {
            zlmMediaServer.setHookAliveInterval(10F);
        }
        if (zlmMediaServer.getType() == null) {
            log.info("[添加媒体节点] 失败, mediaServer的类型：为空");
            throw new SecurityException("[添加媒体节点] 失败, mediaServer的类型：为空");
        }
        if (mediaServerMapper.queryOne(zlmMediaServer.getId(), userSetting.getServerId()) != null) {
            log.info("[添加媒体节点] 失败, 媒体服务ID已存在，请修改媒体服务器配置, {}", zlmMediaServer.getId());
            throw new SecurityException("保存失败，媒体服务ID [ " + zlmMediaServer.getId() + " ] 已存在，请修改媒体服务器配置");
        }
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(zlmMediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[添加媒体节点] 失败, mediaServer的类型： {}，未找到对应的实现类", zlmMediaServer.getType());
            throw new SecurityException("[添加媒体节点] 失败, mediaServer的类型： " + zlmMediaServer.getType() + "，未找到对应的实现类");
        }

        return mediaServerMapper.add(zlmMediaServer);
    }

    /**
     * 修改媒体服务器
     *
     * @param zlmMediaServer
     */
    @Override
    public int update(ZlmMediaServer zlmMediaServer) {
        return mediaServerMapper.update(zlmMediaServer);
    }

    /**
     * 删除媒体服务器
     *
     * @param zlmMediaServer
     */
    @Override
    public void delete(ZlmMediaServer zlmMediaServer) {
        mediaServerMapper.delOne(zlmMediaServer.getId(), userSetting.getServerId());

        // 发送节点移除通知
        MediaServerDeleteEvent event = new MediaServerDeleteEvent(this);
        event.setMediaServer(zlmMediaServer);
        applicationEventPublisher.publishEvent(event);
    }

    /**
     * 从数据库中获取所有媒体服务器
     *
     * @return
     */
    @Override
    public List<ZlmMediaServer> getAllFromDatabase() {
        return mediaServerMapper.queryAll(userSetting.getServerId());
    }

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    @Override
    public ZlmMediaServer getOneFromDatabase(String id) {
        return mediaServerMapper.queryOne(id, userSetting.getServerId());
    }

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    @Override
    public ZlmMediaServer getOne(String id) {
        return mediaServerMapper.getOne(id);
    }

    /**
     * 获取所有在线媒体服务器
     *
     * @return
     */
    @Override
    public List<ZlmMediaServer> getAllOnlineMediaServe() {
        return mediaServerMapper.getAllOnlineMediaServe();
    }

    /**
     * 获取负载最小的媒体服务器
     *
     * @param hasAssist 是否包含辅助媒体服务器
     * @return
     */
    @Override
    public ZlmMediaServer getMediaServerForMinimumLoad(Boolean hasAssist) {
        List<ZlmMediaServer> allOnlineMediaServe = getAllOnlineMediaServe();
        if (allOnlineMediaServe.size() == 0) {
            log.info("获取负载最低的节点时无在线节点");
            return null;
        }

        String key = VideoManagerConstants.ONLINE_MEDIA_SERVERS_PREFIX + userSetting.getServerId();

        // 获取分数最低的，及并发最低的
        Set<Object> objects = redisTemplate.opsForZSet().range(key, 0, -1);
        ArrayList<Object> mediaServerObjectS = new ArrayList<>(objects);
        ZlmMediaServer mediaServer = null;
        if (hasAssist == null) {
            String mediaServerId = (String) mediaServerObjectS.get(0);
            mediaServer = getOne(mediaServerId);
        } else if (hasAssist) {
            for (Object mediaServerObject : mediaServerObjectS) {
                String mediaServerId = (String) mediaServerObject;
                ZlmMediaServer serverItem = getOne(mediaServerId);
                if (serverItem.getRecordAssistPort() > 0) {
                    mediaServer = serverItem;
                    break;
                }
            }
        } else if (!hasAssist) {
            for (Object mediaServerObject : mediaServerObjectS) {
                String mediaServerId = (String) mediaServerObject;
                ZlmMediaServer serverItem = getOne(mediaServerId);
                if (serverItem.getRecordAssistPort() == 0) {
                    mediaServer = serverItem;
                    break;
                }
            }
        }

        return mediaServer;
    }

    /**
     * 拉流播放
     *
     * @param streamPullPlay 拉流播放请求参数
     * @param callback       回调
     */
    @Override
    public void streamPullPlay(StreamPullPlay streamPullPlay, ErrorCallback<StreamInfo> callback) {
        log.info("[拉流代理] app：{}, stream: {}, 流地址： {}", streamPullPlay.getApp(), streamPullPlay.getStream(), streamPullPlay.getUrl());

        ZlmMediaServer mediaServer = getMediaServerForMinimumLoad(null);

        StreamInfo stream = getStreamInfoByAppAndStreamWithCheck(streamPullPlay.getApp(), streamPullPlay.getStream(), mediaServer.getId(), null, false);
        if (stream != null) {
            callback.run(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), stream);
            return;
        }

        if (mediaServer == null) {
            throw new RuntimeException("没有可用的媒体服务器" + mediaServer.getId());
        }

        // 设置流超时的定时任务
        String timeOutTaskKey = UUID.randomUUID().toString();
        Hook rtpHook = Hook.getInstance(HookType.on_media_arrival, streamPullPlay.getApp(), streamPullPlay.getStream(), mediaServer.getId());
        dynamicTask.startDelay(timeOutTaskKey, () -> {
            log.info("[拉流代理] 收流超时，app：{}，stream: {}", streamPullPlay.getApp(), streamPullPlay.getStream());
            // 收流超时
            subscribe.removeSubscribe(rtpHook);
            callback.run(InviteErrorCode.ERROR_FOR_STREAM_TIMEOUT.getCode(), InviteErrorCode.ERROR_FOR_STREAM_TIMEOUT.getMsg(), null);
        }, userSetting.getPlayTimeout());

        // 开启流到来的监听
        subscribe.addSubscribe(rtpHook, (hookData) -> {
            log.info("[拉流代理] 收流成功，app：{}，stream: {}", hookData.getApp(), hookData.getStream());
            dynamicTask.stop(timeOutTaskKey);
            StreamInfo streamInfo = getStreamInfoByAppAndStream(mediaServer, hookData.getApp(), hookData.getStream(), hookData.getMediaInfo());
            // hook响应
            callback.run(InviteErrorCode.SUCCESS.getCode(), InviteErrorCode.SUCCESS.getMsg(), streamInfo);
            subscribe.removeSubscribe(rtpHook);
        });

        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[startProxy] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            throw new RuntimeException("未找到mediaServer对应的实现类");
        }
        String key = mediaNodeServerService.startProxy(mediaServer, streamPullPlay);
        QsDevice qsDevice = new QsDevice();
        qsDevice.setId(streamPullPlay.getDeviceId());
        qsDevice.setStreamKey(key);
        qsDevice.setMediaServerId(mediaServer.getId());
        qsDevice.setStreamStatus("1");
        R<Boolean> r = remoteQsDeviceService.updateQsDevice(qsDevice, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("更新设备失败");
        }
    }

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
    @Override
    public StreamInfo getStreamInfoByAppAndStreamWithCheck(String app, String stream, String mediaServerId, String addr, boolean authority) {
        if (mediaServerId == null) {
            mediaServerId = mediaConfig.getId();
        }
        ZlmMediaServer mediaInfo = getOne(mediaServerId);
        if (mediaInfo == null) {
            throw new RuntimeException("未找到使用的媒体节点");
        }
        List<StreamInfo> streamInfoList = getMediaList(mediaInfo, app, stream);
        if (streamInfoList == null || streamInfoList.isEmpty()) {
            return null;
        } else {
            StreamInfo streamInfo = streamInfoList.get(0);
            if (addr != null && !addr.isEmpty()) {
                streamInfo.changeStreamIp(addr);
            }
            return streamInfo;
        }
    }

    /**
     * 根据应用名和流ID获取播放地址, 只是地址拼接
     *
     * @param mediaServer 媒体服务器
     * @param app         应用名
     * @param stream      流ID
     * @param mediaInfo   媒体信息
     * @return
     */
    @Override
    public StreamInfo getStreamInfoByAppAndStream(ZlmMediaServer mediaServer, String app, String stream, MediaInfo mediaInfo) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[getStreamInfoByAppAndStream] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            return null;
        }
        return mediaNodeServerService.getStreamInfoByAppAndStream(mediaServer, app, stream, mediaInfo, null, false);
    }

    /**
     * 停止拉流播放
     *
     * @param streamPullPlay
     */
    @Override
    public void stopStreamPullPlay(StreamPullPlay streamPullPlay) {
        String mediaServerId = streamPullPlay.getMediaServerId();
        Assert.notNull(mediaServerId, "代理节点不存在");

        ZlmMediaServer mediaServer = getOne(mediaServerId);
        if (mediaServer == null) {
            throw new RuntimeException("媒体节点不存在");
        }

        stopProxy(mediaServer, streamPullPlay.getStreamKey());

        QsDevice qsDevice = new QsDevice();
        qsDevice.setId(streamPullPlay.getDeviceId());
        qsDevice.setStreamKey(null);
        qsDevice.setMediaServerId(null);
        qsDevice.setStreamStatus("0");
        R<Boolean> r = remoteQsDeviceService.updateQsDevice(qsDevice, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("更新设备失败");
        }
    }

    private void stopProxy(ZlmMediaServer mediaServer, String streamKey) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[stopProxy] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            throw new RuntimeException("未找到mediaServer对应的实现类");
        }
        mediaNodeServerService.stopProxy(mediaServer, streamKey);
    }

    public List<StreamInfo> getMediaList(ZlmMediaServer mediaServer, String app, String stream) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[getMediaList] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            return new ArrayList<>();
        }
        return mediaNodeServerService.getMediaList(mediaServer, app, stream);
    }


}
