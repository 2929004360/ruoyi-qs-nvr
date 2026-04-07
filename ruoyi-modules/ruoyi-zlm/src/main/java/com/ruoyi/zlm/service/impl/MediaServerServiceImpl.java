package com.ruoyi.zlm.service.impl;

import com.alibaba.nacos.api.model.v2.ErrorCode;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.haikang.api.RemoteHaiKangService;
import com.ruoyi.haikang.api.domain.RtpServerParam;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.*;
import com.ruoyi.zlm.api.hook.OriginType;
import com.ruoyi.zlm.common.InviteErrorCode;
import com.ruoyi.zlm.common.InviteSessionStatus;
import com.ruoyi.zlm.common.InviteSessionType;
import com.ruoyi.zlm.config.DynamicTask;
import com.ruoyi.zlm.config.MediaConfig;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.constants.VideoManagerConstants;
import com.ruoyi.zlm.domain.InviteInfo;
import com.ruoyi.zlm.domain.SSRCInfo;
import com.ruoyi.zlm.domain.Snap;
import com.ruoyi.zlm.domain.dto.ZLMResult;
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
import com.ruoyi.zlm.service.*;
import com.ruoyi.zlm.session.SSRCFactory;
import com.ruoyi.zlm.utils.ZLMRESTfulUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
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

    @Autowired
    private ZLMRESTfulUtils zlmresTfulUtils;

    @Autowired
    private SSRCFactory ssrcFactory;

    @Autowired
    @Lazy
    private IReceiveRtpServerService receiveRtpServerService;

    @Autowired
    @Lazy
    private RemoteHaiKangService remoteHaiKangService;

    @Autowired
    @Lazy
    private IInviteStreamService inviteStreamService;

    @Value("${file.domain}")
    private String fileDomain;

    @Value("${file.path}")
    private String filePath;

    @Value("${file.prefix}")
    private String filePrefix;


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
        if (event.getMediaServer().getId() == null) {
            return;
        }

        String key = VideoManagerConstants.ONLINE_MEDIA_SERVERS_PREFIX + userSetting.getServerId();
        redisTemplate.opsForZSet().incrementScore(key, event.getMediaServer().getId(), 0);
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
        if (!ssrcFactory.hasMediaServerSSRC(zlmMediaServer.getId())) {
            ssrcFactory.initMediaServerSSRC(zlmMediaServer.getId(), null);
        }
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
            String filePath = snapOnPlay(streamInfo.getMediaServer(), streamInfo.getApp(), streamInfo.getStream());
            // hook响应
            callback.run(InviteErrorCode.SUCCESS.getCode(), InviteErrorCode.SUCCESS.getMsg(), streamInfo);
            subscribe.removeSubscribe(rtpHook);

            QsDevice qsDevice = new QsDevice();
            qsDevice.setId(streamPullPlay.getDeviceId());
            qsDevice.setSnap(filePath);
            R<Boolean> r = remoteQsDeviceService.updateQsDevice(qsDevice, SecurityConstants.INNER);
            if (r.getCode() != Constants.SUCCESS) {
                throw new RuntimeException("更新设备失败");
            }
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
        qsDevice.setStreamKey("");
        qsDevice.setMediaServerId("");
        qsDevice.setStreamStatus("0");
        R<Boolean> r = remoteQsDeviceService.updateQsDevice(qsDevice, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("更新设备失败");
        }
    }

    /**
     * 点播成功时调用截图
     *
     * @param mediaServer media
     * @param app         app
     * @param stream      流id
     */
    @Override
    public String snapOnPlay(ZlmMediaServer mediaServer, String app, String stream) {
        String fileName = app + "-" + stream + ".jpg";
        // 请求截图
        log.info("[请求截图]: " + fileName);

        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[getSnap] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            throw new RuntimeException("[getSnap] 失败, mediaServer的类型： " + mediaServer.getType() + "，未找到对应的实现类");
        }
        String filePath = fileDomain + filePrefix + "/snap/" + fileName;
        mediaNodeServerService.getSnap(mediaServer, app, stream, 15, 1, this.filePath + "/snap", fileName);
        return filePath;
    }

    /**
     * 获取截图
     *
     * @param mediaServer
     * @return
     */
    @Override
    public String getSnap(ZlmMediaServer mediaServer, Snap snap) {
        String fileName = snap.getApp() + "-" + snap.getStream() + ".jpg";
        mediaNodeServerService.getSnap(mediaServer, snap.getUrl(), 15, 1, this.filePath + "/snap", fileName);
        return fileDomain + filePrefix + "/snap/" + fileName;
    }

    /**
     * 创建RTP服务器
     *
     * @param mediaServer  zlm服务实例
     * @param app          应用名
     * @param streamId     流Id
     * @param ssrc         ssrc
     * @param port         端口， 0/null为使用随机
     * @param onlyAuto     是否只自动分配
     * @param disableAudio 是否禁用音频
     * @param reUsePort    是否重用端口
     * @param tcpMode      0/null udp 模式，1 tcp 被动模式, 2 tcp 主动模式。
     * @return
     */
    @Override
    public int createRTPServer(ZlmMediaServer mediaServer, String app, String streamId, long ssrc, Integer port, Boolean onlyAuto, Boolean disableAudio, Boolean reUsePort, Integer tcpMode) {
        int result = -1;
        // 查询此rtp server 是否已经存在
        ZLMResult<?> rtpInfoResult = zlmresTfulUtils.getRtpInfo(mediaServer, streamId);
        if (rtpInfoResult.getCode() == 0) {
            if (rtpInfoResult.getExist() != null && rtpInfoResult.getExist()) {
                result = rtpInfoResult.getLocal_port();
                if (result == 0) {
                    // 此时说明rtpServer已经创建但是流还没有推上来
                    // 此时重新打开rtpServer
                    Map<String, Object> param = new HashMap<>();
                    param.put("stream_id", streamId);
                    ZLMResult<?> zlmResult = zlmresTfulUtils.closeRtpServer(mediaServer, param);
                    if (zlmResult != null) {
                        if (zlmResult.getCode() == 0) {
                            return createRTPServer(mediaServer, streamId, app, ssrc, port, onlyAuto, reUsePort, disableAudio, tcpMode);
                        } else {
                            log.warn("[开启rtpServer], 重启RtpServer错误");
                        }
                    }
                }
                return result;
            }
        } else if (rtpInfoResult.getCode() == -2) {
            return result;
        }

        Map<String, Object> param = new HashMap<>();

        if (tcpMode == null) {
            tcpMode = 0;
        }
        param.put("tcp_mode", tcpMode);
        param.put("app", app);
        param.put("stream_id", streamId);
        if (disableAudio != null) {
            param.put("only_track", disableAudio ? 2 : 0);
        }

        if (reUsePort != null) {
            param.put("re_use_port", reUsePort ? "1" : "0");
        }
        // 推流端口设置0则使用随机端口
        if (port == null) {
            param.put("port", 0);
        } else {
            param.put("port", port);
        }
        if (onlyAuto != null) {
            param.put("only_audio", onlyAuto ? "1" : "0");
        }
        if (ssrc != 0) {
            param.put("ssrc", ssrc);
        }

        ZLMResult<?> zlmResult = zlmresTfulUtils.openRtpServer(mediaServer, param);
        if (zlmResult != null) {
            if (zlmResult.getCode() == 0) {
                result = zlmResult.getPort();
            } else {
                log.error("创建RTP Server 失败 {}: ", zlmResult.getMsg());
            }
        } else {
            //  检查ZLM状态
            log.error("创建RTP Server 失败 {}: 请检查ZLM服务", param.get("port"));
        }
        return result;
    }

    /**
     * rtp播放
     *
     * @param rtpServerParam 创建rtp端口请求参数
     * @return
     */
    @Override
    public void rtpPlay(RTPServerParam rtpServerParam, ErrorCallback<StreamInfo> callback) {
        ZlmMediaServer mediaServer = getMediaServerForMinimumLoad(null);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(rtpServerParam.getId(), SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败");
        }
        if (r.getData() == null) {
            throw new RuntimeException("设备不存在");
        }
        play(mediaServer, rtpServerParam, r.getData(), null, userSetting.getRecordSip(), callback);
    }

    private SSRCInfo play(ZlmMediaServer mediaServer, RTPServerParam rtpServerParam, QsDevice device, String ssrc, Boolean record, ErrorCallback<StreamInfo> callback) {
        InviteInfo inviteInfoInCatch = inviteStreamService.getInviteInfoByDeviceAndChannel(InviteSessionType.PLAY, device.getChannel());
        if (inviteInfoInCatch != null) {
            if (inviteInfoInCatch.getStreamInfo() == null) {
                // 释放生成的ssrc，使用上一次申请的
                ssrcFactory.releaseSsrc(mediaServer.getId(), null);
                // 点播发起了但是尚未成功, 仅注册回调等待结果即可
                inviteStreamService.once(InviteSessionType.PLAY, device.getChannel(), null, callback);
                log.info("[点播开始] 已经请求中，等待结果， id: {}", rtpServerParam.getId());
                return inviteInfoInCatch.getSsrcInfo();

            } else {
                StreamInfo streamInfo = inviteInfoInCatch.getStreamInfo();
                String streamId = streamInfo.getStream();
                if (streamId == null) {
                    callback.run(InviteErrorCode.ERROR_FOR_CATCH_DATA.getCode(), "点播失败， redis缓存streamId等于null", null);
                    inviteStreamService.call(InviteSessionType.PLAY, device.getChannel(),
                            null,
                            InviteErrorCode.ERROR_FOR_CATCH_DATA.getCode(),
                            "点播失败， redis缓存streamId等于null",
                            null);
                    return inviteInfoInCatch.getSsrcInfo();
                }
                ZlmMediaServer mediaInfo = streamInfo.getMediaServer();
                Boolean ready = isStreamReady(mediaInfo, rtpServerParam.getApp(), streamId);
                if (ready != null && ready) {
                    if (callback != null) {
                        callback.run(InviteErrorCode.SUCCESS.getCode(), InviteErrorCode.SUCCESS.getMsg(), streamInfo);
                    }
                    inviteStreamService.call(InviteSessionType.PLAY, device.getChannel(), null, InviteErrorCode.SUCCESS.getCode(), InviteErrorCode.SUCCESS.getMsg(), streamInfo);
                    log.info("[点播已存在] 直接返回， 设备编号: {}", rtpServerParam.getId().intValue());
                    return inviteInfoInCatch.getSsrcInfo();
                } else {
                    // 点播发起了但是尚未成功, 仅注册回调等待结果即可
                    inviteStreamService.once(InviteSessionType.PLAY, device.getChannel(), null, callback);
                    RTPServerParam rtpServer = new RTPServerParam();
                    rtpServer.setId(rtpServerParam.getId());
                    rtpServer.setType(rtpServerParam.getType());
                    stopRtpPlay(rtpServer);
                    inviteStreamService.removeInviteInfoByDeviceAndChannel(InviteSessionType.PLAY, device.getChannel());
                }
            }
        }

        rtpServerParam.setMediaServer(mediaServer);
        // 获取mediaServer可用的ssrc
        if (rtpServerParam.getPresetSsrc() != null) {
            ssrc = rtpServerParam.getPresetSsrc();
        } else {
            if (rtpServerParam.isPlayback()) {
                ssrc = ssrcFactory.getPlayBackSsrc(mediaServer.getId());
            } else {
                ssrc = ssrcFactory.getPlaySsrc(mediaServer.getId());
            }
        }
        rtpServerParam.setSsrc(ssrc);

        SSRCInfo ssrcInfo = receiveRtpServerService.openRTPServer(rtpServerParam, (code, msg, result) -> {
            if (code == InviteErrorCode.SUCCESS.getCode() && result != null && result.getHookData() != null) {
                log.info("[创建RTP服务器] 成功, code: {}, msg: {}, result: {}", code, msg, result);
                StreamInfo streamInfo = getStreamInfoByAppAndStream(mediaServer, rtpServerParam.getApp(), rtpServerParam.getStreamId(), result.getHookData().getMediaInfo());
                if (streamInfo == null) {
                    if (callback != null) {
                        callback.run(InviteErrorCode.ERROR_FOR_STREAM_PARSING_EXCEPTIONS.getCode(), InviteErrorCode.ERROR_FOR_STREAM_PARSING_EXCEPTIONS.getMsg(), null);
                    }
                    return;
                }
                if (callback != null) {
                    callback.run(InviteErrorCode.SUCCESS.getCode(), InviteErrorCode.SUCCESS.getMsg(), streamInfo);

                    InviteInfo inviteInfo = inviteStreamService.getInviteInfoByDeviceAndChannel(InviteSessionType.PLAY, device.getChannel());

                    inviteInfo.setStatus(InviteSessionStatus.ok);
                    inviteInfo.setStreamInfo(streamInfo);
                    inviteStreamService.updateInviteInfo(inviteInfo);

                    String filePath = snapOnPlay(streamInfo.getMediaServer(), streamInfo.getApp(), streamInfo.getStream());
                    QsDevice qsDevice = new QsDevice();
                    qsDevice.setId(rtpServerParam.getId());
                    qsDevice.setStreamKey(rtpServerParam.getStreamId());
                    qsDevice.setMediaServerId(mediaServer.getId());
                    qsDevice.setStreamStatus("1");
                    qsDevice.setSnap(filePath);
                    R<Boolean> r = remoteQsDeviceService.updateQsDevice(qsDevice, SecurityConstants.INNER);
                    if (r.getCode() != Constants.SUCCESS) {
                        throw new RuntimeException("更新设备失败");
                    }
                }
            } else {
                log.error("[创建RTP服务器] 失败, code: {}, msg: {}, result: {}", code, msg, result);

                if (callback != null) {
                    callback.run(code, msg, null);
                }
            }
        });

        if (ssrcInfo == null || ssrcInfo.getPort() <= 0) {
            log.info("[点播端口/SSRC]获取失败，设备编号：{}, 通道编号：{},ssrcInfo；{}",device.getId().toString(), device.getChannel(), ssrcInfo);
            callback.run(InviteErrorCode.ERROR_FOR_RESOURCE_EXHAUSTION.getCode(), "获取端口或者ssrc失败", null);
            inviteStreamService.call(InviteSessionType.PLAY, device.getChannel(), null,
                    InviteErrorCode.ERROR_FOR_RESOURCE_EXHAUSTION.getCode(),
                    InviteErrorCode.ERROR_FOR_RESOURCE_EXHAUSTION.getMsg(),
                    null);
            return null;
        }

        int port = ssrcInfo.getPort();
        String ip = mediaServer.getIp();
        RtpServerParam rtpServer = new RtpServerParam();
        rtpServer.setPort(port);
        rtpServer.setIp(ip);
        rtpServer.setId(rtpServerParam.getId());
        rtpServer.setSsrc(rtpServerParam.getSsrc());
        // 播放
        if (LiveStreamType.HIK_SDK.getCode().equals(rtpServerParam.getType())) {
            remoteHaiKangService.startPlay(rtpServer, SecurityConstants.INNER);
        }

        log.info("[点播开始] 设备编号: {}, 通道编号: {}, 收流端口： {}, 流ID：{}, SSRC: {}",
                device.getId().toString(), device.getChannel(), ssrcInfo.getPort(), ssrcInfo.getStream(),
                ssrcInfo.getSsrc());

        InviteInfo inviteInfo = InviteInfo.getInviteInfo(device.getId().toString(), device.getChannel(), ssrcInfo.getStream(), ssrcInfo, mediaServer.getId(),
                mediaServer.getSdpIp(), ssrcInfo.getPort(), "TCP-ACTIVE", InviteSessionType.PLAY,
                InviteSessionStatus.ready, userSetting.getRecordSip());
        if (record != null) {
            inviteInfo.setRecord(record);
        }else {
            inviteInfo.setRecord(userSetting.getRecordSip());
        }
        inviteStreamService.updateInviteInfo(inviteInfo);

        return ssrcInfo;
    }


    /**
     * 关闭RTP服务器
     *
     * @param mediaServer
     * @param streamId
     */
    @Override
    public void closeRTPServer(ZlmMediaServer mediaServer, String streamId) {
        if (mediaServer == null) {
            return;
        }
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[closeRTPServer] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            return;
        }
        mediaNodeServerService.closeRtpServer(mediaServer, streamId, null);
    }

    /**
     * 停止rtp播放
     *
     * @param rtpServerParam 创建rtp端口请求参数
     * @return
     */
    @Override
    public void stopRtpPlay(RTPServerParam rtpServerParam) {
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(rtpServerParam.getId(), SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败");
        }
        if (r.getData() == null) {
            throw new RuntimeException("设备不存在");
        }
        QsDevice device = r.getData();
        String mediaServerId = device.getMediaServerId();
        ZlmMediaServer mediaServer = getOne(mediaServerId);
        closeRTPServer(mediaServer, device.getStreamKey());
        if (LiveStreamType.HIK_SDK.getCode().equals(rtpServerParam.getType())) {
            remoteHaiKangService.stopPlay(rtpServerParam.getId(), SecurityConstants.INNER);
        }

        QsDevice qsDevice = new QsDevice();
        qsDevice.setId(rtpServerParam.getId());
        qsDevice.setStreamKey("");
        qsDevice.setMediaServerId("");
        qsDevice.setStreamStatus("0");
        R<Boolean> devicer = remoteQsDeviceService.updateQsDevice(qsDevice, SecurityConstants.INNER);
        if (devicer.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("更新设备失败");
        }
    }

    /**
     * 判断流是否已经准备好
     *
     * @param mediaServer
     * @param rtp
     * @param streamId
     * @return
     */
    @Override
    public Boolean isStreamReady(ZlmMediaServer mediaServer, String app, String streamId) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[isStreamReady] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            return false;
        }
        MediaInfo mediaInfo = mediaNodeServerService.getMediaInfo(mediaServer, app, streamId);
        return mediaInfo != null;
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
