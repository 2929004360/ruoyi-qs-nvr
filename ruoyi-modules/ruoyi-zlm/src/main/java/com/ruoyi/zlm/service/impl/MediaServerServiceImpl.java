package com.ruoyi.zlm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.api.model.v2.ErrorCode;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.dahua.api.RemoteDaHuaService;
import com.ruoyi.haikang.api.RemoteHaiKangService;
import com.ruoyi.haikang.isup.api.RemoteHaiKangIsupService;
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
import com.ruoyi.zlm.domain.*;
import com.ruoyi.zlm.domain.dto.ZLMResult;
import com.ruoyi.zlm.event.MediaArrivalEvent;
import com.ruoyi.zlm.event.MediaDepartureEvent;
import com.ruoyi.zlm.hook.Hook;
import com.ruoyi.zlm.hook.HookSubscribe;
import com.ruoyi.zlm.hook.HookType;
import com.ruoyi.zlm.mapper.MediaServerMapper;
import com.ruoyi.zlm.mediaServer.*;
import com.ruoyi.zlm.service.*;
import com.ruoyi.zlm.session.SSRCFactory;
import com.ruoyi.zlm.utils.ZLMRESTfulUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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
import org.springframework.util.DigestUtils;

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

    @Autowired
    private RemoteHaiKangIsupService remoteHaiKangIsupService;

    @Autowired
    private RemoteDaHuaService remoteDaHuaService;

    @Autowired
    private IZlmCloudRecordService zlmCloudRecordService;

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

        // 推流到来处理
        if ("push".equals(event.getSchema())) {
            pushProcessArrival(event);
        }
    }

    /**
     * 推流到来处理
     *
     * @param event
     */
    private void pushProcessArrival(MediaArrivalEvent event) {
        MediaInfo mediaInfo = event.getMediaInfo();
        if (mediaInfo == null) {
            return;
        }
        if (mediaInfo.getOriginType() != OriginType.RTMP_PUSH.ordinal() && mediaInfo.getOriginType() != OriginType.RTSP_PUSH.ordinal() && mediaInfo.getOriginType() != OriginType.RTC_PUSH.ordinal()) {
            return;
        }

        StreamAuthorityInfo streamAuthorityInfo = redisCatchStorage.getStreamAuthorityInfo(event.getApp(), event.getStream());
        if (streamAuthorityInfo == null) {
            streamAuthorityInfo = StreamAuthorityInfo.getInstanceByHook(event);
        } else {
            streamAuthorityInfo.setOriginType(mediaInfo.getOriginType());
        }
        redisCatchStorage.updateStreamAuthorityInfo(event.getApp(), event.getStream(), streamAuthorityInfo);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceStream("pull_" + event.getApp() + "_" + event.getStream(), SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败,stream:{}", event.getStream());
            return;
        }

        if (r.getData() == null) {
            r = remoteQsDeviceService.getQsDeviceStream(event.getStream(), SecurityConstants.INNER);
            if (r.getCode() != Constants.SUCCESS) {
                log.error("获取设备信息失败,stream:{}", event.getStream());
                return;
            }
        }

        if (r.getData() == null) {
            QsDevice device = new QsDevice();
            device.setDeviceStatus("ON");
            device.setMediaServerId(mediaInfo.getMediaServer().getId());
            device.setDeviceName("推流设备_" + event.getApp() + "_" + event.getStream());
            device.setType(LiveStreamType.PUSH.getCode());
            device.setStatus("ENABLE");
            device.setStreamStatus("1");
            device.setStreamKey("pull_" + event.getApp() + "_" + event.getStream());
            device.setDeviceCode("pull_" + event.getApp() + "_" + event.getStream());

            String filePath = snapOnPlay(mediaInfo.getMediaServer(), event.getApp(), event.getStream());
            device.setSnap(filePath);

            R<Boolean> addR = remoteQsDeviceService.addQsDevice(device, SecurityConstants.INNER);
            if (addR.getCode() != Constants.SUCCESS) {
                throw new RuntimeException("添加推流设备设备失败" + event.getApp() + "_" + event.getStream());
            }

            if (!addR.getData()) {
                throw new RuntimeException("添加推流设备设备失败" + event.getApp() + "_" + event.getStream());
            }
        } else {
            QsDevice device = new QsDevice();
            device.setDeviceStatus("ON");
            device.setMediaServerId(mediaInfo.getMediaServer().getId());
            device.setStreamKey(r.getData().getDeviceCode());
            device.setStreamStatus("1");
            device.setId(r.getData().getId());
            String filePath = snapOnPlay(mediaInfo.getMediaServer(), event.getApp(), event.getStream());
            device.setSnap(filePath);
            R<Boolean> updateR = remoteQsDeviceService.updateQsDevice(device, SecurityConstants.INNER);
            if (updateR.getCode() != Constants.SUCCESS) {
                throw new RuntimeException("修改推流设备设备失败" + event.getApp() + "_" + event.getStream());
            }

            if (!updateR.getData()) {
                throw new RuntimeException("修改推流设备设备失败" + event.getApp() + "_" + event.getStream());
            }
        }

        // 冗余数据，自己系统中自用
        redisCatchStorage.addPushListItem(event.getApp(), event.getStream(), event.getMediaInfo());

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

        if ("haikang".equals(event.getApp()) || "haikang_isup".equals(event.getApp()) || "dahua".equals(event.getApp())) {
            InviteInfo inviteInfo = inviteStreamService.getInviteInfoByStream(null, event.getStream());
            if (inviteInfo != null && (inviteInfo.getType() == InviteSessionType.PLAY || inviteInfo.getType() == InviteSessionType.PLAYBACK)) {
                inviteStreamService.removeInviteInfo(inviteInfo);

                R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(Long.valueOf(inviteInfo.getDeviceId()), SecurityConstants.INNER);
                if (r.getCode() != Constants.SUCCESS) {
                    return;
                }

                if (r.getData() == null) {
                    return;
                }

                RTPServerParam rtpServerParam = new RTPServerParam();
                rtpServerParam.setId(r.getData().getId());
                rtpServerParam.setType(r.getData().getType());
                rtpServerParam.setStreamId(event.getStream());
                stopRtpPlay(rtpServerParam);

                ssrcFactory.releaseSsrc(inviteInfo.getMediaServerId(), null);
            }
        }

        if ("video_file".equals(event.getApp())) {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceStream(event.getStream(), SecurityConstants.INNER);
            if (r.getCode() != Constants.SUCCESS) {
                return;
            }

            if (r.getData() == null) {
                return;
            }

            QsDevice qsDevice = new QsDevice();
            qsDevice.setId(r.getData().getId());
            qsDevice.setStreamKey("");
            qsDevice.setMediaServerId("");
            qsDevice.setStreamStatus("0");
            R<Boolean> qsDevicer = remoteQsDeviceService.updateQsDevice(qsDevice, SecurityConstants.INNER);
            if (qsDevicer.getCode() != Constants.SUCCESS) {
                log.error("更新设备失败");
            }
        }

        // 推流离开处理
        if ("push".equals(event.getSchema())) {
            pushProcessLeave(event);
        }
    }

    /**
     * 推流离开处理
     *
     * @param event
     */
    private void pushProcessLeave(MediaDepartureEvent event) {
        // 兼容流注销时类型从redis记录获取
        MediaInfo mediaInfo = redisCatchStorage.getPushListItem(event.getApp(), event.getStream());

        if (mediaInfo != null) {
            log.info("[推流信息] 查询到redis存在推流缓存， 开始清理，{}/{}", event.getApp(), event.getStream());
            String type = OriginType.values()[mediaInfo.getOriginType()].getType();
            // 冗余数据，自己系统中自用
            redisCatchStorage.removePushListItem(event.getApp(), event.getStream(), event.getMediaServer().getId());
        }

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceStream("pull_" + event.getApp() + "_" + event.getStream(), SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败,stream:{}", event.getStream());
            return;
        }

        if (r.getData() == null) {
            r = remoteQsDeviceService.getQsDeviceStream(event.getStream(), SecurityConstants.INNER);
            if (r.getCode() != Constants.SUCCESS) {
                log.error("获取设备信息失败,stream:{}", event.getStream());
                return;
            }
        }

        if (r.getData() == null) {
            return;
        }
        QsDevice device = new QsDevice();
        device.setDeviceStatus("OFFLINE");
        device.setMediaServerId("");
        device.setStreamKey("");
        device.setStreamStatus("0");
        device.setId(r.getData().getId());
        R<Boolean> updateR = remoteQsDeviceService.updateQsDevice(device, SecurityConstants.INNER);
        if (updateR.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("修改推流设备设备失败" + event.getApp() + "_" + event.getStream());
        }

        if (!updateR.getData()) {
            throw new RuntimeException("修改推流设备设备失败" + event.getApp() + "_" + event.getStream());
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
        if (event.getMediaServer().getId() == null) {
            return;
        }
        String key = VideoManagerConstants.ONLINE_MEDIA_SERVERS_PREFIX + userSetting.getServerId();
        redisTemplate.opsForZSet().remove(key, event.getMediaServer().getId());
    }

    /**
     * 流录制完成
     *
     * @param event
     */
    @Async("taskExecutor")
    @EventListener
    public void onApplicationEvent(MediaRecordMp4Event event) {
        CloudRecordItem cloudRecordItem = CloudRecordItem.getInstance(event);
        cloudRecordItem.setServerId(userSetting.getServerId());
        if (ObjectUtils.isEmpty(cloudRecordItem.getCallId())) {
            StreamAuthorityInfo streamAuthorityInfo = redisCatchStorage.getStreamAuthorityInfo(event.getApp(), event.getStream());
            if (streamAuthorityInfo != null) {
                cloudRecordItem.setCallId(streamAuthorityInfo.getCallId());
            }
        }
        log.info("[添加录像记录] {}/{}, callId: {}, 内容：{}", event.getApp(), event.getStream(), cloudRecordItem.getCallId(), event.getRecordInfo());

        ZlmCloudRecord zlmCloudRecord = new ZlmCloudRecord();
        zlmCloudRecord.setApp(cloudRecordItem.getApp());
        zlmCloudRecord.setStream(cloudRecordItem.getStream());
        zlmCloudRecord.setCallId(cloudRecordItem.getCallId());
        zlmCloudRecord.setServerId(cloudRecordItem.getServerId());
        zlmCloudRecord.setStartTime(cloudRecordItem.getStartTime());
        zlmCloudRecord.setEndTime(cloudRecordItem.getEndTime());
        zlmCloudRecord.setFilePath(cloudRecordItem.getFilePath());
        zlmCloudRecord.setMediaServerId(cloudRecordItem.getMediaServerId());
        zlmCloudRecord.setFileName(cloudRecordItem.getFileName());
        zlmCloudRecord.setFolder(cloudRecordItem.getFolder());
        zlmCloudRecord.setCollect(cloudRecordItem.getCollect());
        zlmCloudRecord.setFileSize(cloudRecordItem.getFileSize());
        zlmCloudRecord.setTimeLen(cloudRecordItem.getTimeLen());
        zlmCloudRecordService.insertZlmCloudRecord(zlmCloudRecord);
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

        if (mediaServer == null) {
            callback.run(InviteErrorCode.FAIL.getCode(), "无可用的节点", null);
            return;
        }
        R<QsDevice> devicer = remoteQsDeviceService.getQsDeviceInfo(streamPullPlay.getDeviceId(), SecurityConstants.INNER);
        if (devicer.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败" + streamPullPlay.getDeviceId());
        }

        if (devicer.getData() == null) {
            throw new RuntimeException("设备不存在" + streamPullPlay.getDeviceId());
        }

        if ("OFFLINE".equals(devicer.getData().getDeviceStatus())) {
            throw new RuntimeException("设备不在线" + streamPullPlay.getDeviceId());
        }

        StreamInfo stream = getStreamInfoByAppAndStreamWithCheck(streamPullPlay.getApp(), streamPullPlay.getStream(), mediaServer.getId(), null, false);
        if (stream != null) {
            callback.run(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), stream);
            return;
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
            log.error("[startProxy] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            callback.run(InviteErrorCode.FAIL.getCode(), "[startProxy] 失败, mediaServer的类型： " + mediaServer.getType() + "，未找到对应的实现类", null);
            return;
        }

        String key = mediaNodeServerService.startProxy(mediaServer, streamPullPlay);
        QsDevice qsDevice = new QsDevice();
        qsDevice.setId(streamPullPlay.getDeviceId());
        qsDevice.setStreamKey(key);
        qsDevice.setMediaServerId(mediaServer.getId());
        qsDevice.setStreamStatus("1");
        R<Boolean> r = remoteQsDeviceService.updateQsDevice(qsDevice, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("更新设备失败");
            callback.run(InviteErrorCode.FAIL.getCode(), "更新设备失败", null);
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

        if (mediaServer == null) {
            callback.run(InviteErrorCode.FAIL.getCode(), "无可用的节点", null);
            return;
        }

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(rtpServerParam.getId(), SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            callback.run(InviteErrorCode.FAIL.getCode(), "获取设备信息失败" + rtpServerParam.getId(), null);
            return;
        }
        if (r.getData() == null) {
            callback.run(InviteErrorCode.FAIL.getCode(), "设备不存在" + rtpServerParam.getId(), null);
            return;
        }

        if ("OFFLINE".equals(r.getData().getDeviceStatus())) {
            callback.run(InviteErrorCode.FAIL.getCode(), "设备不在线" + rtpServerParam.getId(), null);
        }
        play(mediaServer, rtpServerParam, r.getData(), null, callback);
    }

    /**
     * 点播
     *
     * @param mediaServer    zlm服务实例
     * @param rtpServerParam 创建rtp端口请求参数
     * @param device         设备信息
     * @param ssrc           ssrc
     * @param record         是否录制
     * @param callback       回调
     * @return
     */
    private SSRCInfo play(ZlmMediaServer mediaServer, RTPServerParam rtpServerParam, QsDevice device, String ssrc, ErrorCallback<StreamInfo> callback) {
        // 获取点播的状态信息
        InviteInfo inviteInfoInCatch = inviteStreamService.getInviteInfoByDeviceAndChannel(InviteSessionType.PLAY, device.getId());
        if (inviteInfoInCatch != null) {
            if (inviteInfoInCatch.getStreamInfo() == null) {
                // 释放生成的ssrc，使用上一次申请的
                ssrcFactory.releaseSsrc(mediaServer.getId(), null);
                // 点播发起了但是尚未成功, 仅注册回调等待结果即可
                inviteStreamService.once(InviteSessionType.PLAY, device.getId(), null, callback);
                log.info("[点播开始] 已经请求中，等待结果， deviceId: {}, channel: {}", device.getId(), device.getId());
                return inviteInfoInCatch.getSsrcInfo();
            } else {
                StreamInfo streamInfo = inviteInfoInCatch.getStreamInfo();
                String streamId = streamInfo.getStream();
                if (streamId == null) {
                    callback.run(InviteErrorCode.ERROR_FOR_CATCH_DATA.getCode(), "点播失败， redis缓存streamId等于null", null);
                    inviteStreamService.call(InviteSessionType.PLAY, device.getId(), null, InviteErrorCode.ERROR_FOR_CATCH_DATA.getCode(), "点播失败， redis缓存streamId等于null", null);
                    return inviteInfoInCatch.getSsrcInfo();
                }
                ZlmMediaServer mediaInfo = streamInfo.getMediaServer();
                Boolean ready = isStreamReady(mediaInfo, rtpServerParam.getApp(), streamId);
                if (ready != null && ready) {
                    if (callback != null) {
                        callback.run(InviteErrorCode.SUCCESS.getCode(), InviteErrorCode.SUCCESS.getMsg(), streamInfo);
                    }
                    inviteStreamService.call(InviteSessionType.PLAY, device.getId(), null, InviteErrorCode.SUCCESS.getCode(), InviteErrorCode.SUCCESS.getMsg(), streamInfo);
                    log.info("[点播已存在] 直接返回， 设备编号: {}", rtpServerParam.getId().intValue());
                    return inviteInfoInCatch.getSsrcInfo();
                } else {
                    // 点播发起了但是尚未成功, 仅注册回调等待结果即可
                    inviteStreamService.once(InviteSessionType.PLAY, device.getId(), null, callback);
                    RTPServerParam rtpServer = new RTPServerParam();
                    rtpServer.setId(rtpServerParam.getId());
                    rtpServer.setType(rtpServerParam.getType());
                    rtpServer.setStreamId(rtpServerParam.getStreamId());
                    stopRtpPlay(rtpServer);
                    inviteStreamService.removeInviteInfoByDeviceAndChannel(InviteSessionType.PLAY, device.getId());
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

                    inviteStreamService.call(InviteSessionType.PLAY, device.getId(), null, InviteErrorCode.ERROR_FOR_STREAM_PARSING_EXCEPTIONS.getCode(), InviteErrorCode.ERROR_FOR_STREAM_PARSING_EXCEPTIONS.getMsg(), null);
                    return;
                }
                if (callback != null) {
                    callback.run(InviteErrorCode.SUCCESS.getCode(), InviteErrorCode.SUCCESS.getMsg(), streamInfo);

//                    inviteStreamService.call(InviteSessionType.PLAY, device.getId(), null,
//                            InviteErrorCode.SUCCESS.getCode(),
//                            InviteErrorCode.SUCCESS.getMsg(),
//                            streamInfo);

                    InviteInfo inviteInfo = inviteStreamService.getInviteInfoByDeviceAndChannel(InviteSessionType.PLAY, device.getId());

                    if (inviteInfo != null) {
                        inviteInfo.setStatus(InviteSessionStatus.ok);
                        inviteInfo.setStreamInfo(streamInfo);
                        inviteStreamService.updateInviteInfo(inviteInfo);
                    }


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

                inviteStreamService.call(InviteSessionType.PLAY, device.getId(), null, code, msg, null);
                inviteStreamService.removeInviteInfoByDeviceAndChannel(InviteSessionType.PLAY, device.getId());
            }
        });

        if (ssrcInfo == null || ssrcInfo.getPort() <= 0) {
            log.info("[点播端口/SSRC]获取失败，设备编号：{}, 通道编号：{},ssrcInfo；{}", device.getId().toString(), device.getId(), ssrcInfo);
            callback.run(InviteErrorCode.ERROR_FOR_RESOURCE_EXHAUSTION.getCode(), "获取端口或者ssrc失败", null);
            inviteStreamService.call(InviteSessionType.PLAY, device.getId(), null, InviteErrorCode.ERROR_FOR_RESOURCE_EXHAUSTION.getCode(), InviteErrorCode.ERROR_FOR_RESOURCE_EXHAUSTION.getMsg(), null);
            return null;
        }

        int port = ssrcInfo.getPort();
        String ip = mediaServer.getIp();
        RtpServerParam rtpServer = new RtpServerParam();
        rtpServer.setPort(port);
        rtpServer.setIp(ip);
        rtpServer.setId(rtpServerParam.getId());
        rtpServer.setSsrc(rtpServerParam.getSsrc());

        log.info("[点播开始] 设备编号: {}, 通道编号: {}, 收流端口： {}, 流ID：{}, SSRC: {}", device.getId().toString(), device.getId(), ssrcInfo.getPort(), ssrcInfo.getStream(), ssrcInfo.getSsrc());

        InviteInfo inviteInfo = InviteInfo.getInviteInfo(device.getId().toString(), device.getId(), ssrcInfo.getStream(), ssrcInfo, mediaServer.getId(), mediaServer.getSdpIp(), ssrcInfo.getPort(), "TCP-ACTIVE", InviteSessionType.PLAY, InviteSessionStatus.ready, userSetting.getRecordSip());

        if ("1".equals(device.getEnableMp4())) {
            inviteInfo.setRecord(true);
        }

        inviteStreamService.updateInviteInfo(inviteInfo);

        // 播放海康sdk
        if (LiveStreamType.HIK_SDK.getCode().equals(rtpServerParam.getType())) {
            remoteHaiKangService.startPlay(rtpServer, SecurityConstants.INNER);
        }

        // 播放海康isup
        if (LiveStreamType.HIK_ISUP.getCode().equals(rtpServerParam.getType())) {
            remoteHaiKangIsupService.startPlay(rtpServer, SecurityConstants.INNER);
        }

        // 播放大华sdk
        if (LiveStreamType.DAHUA_SDK.getCode().equals(rtpServerParam.getType())) {
            remoteDaHuaService.startPlay(rtpServer, SecurityConstants.INNER);
        }
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

        if (LiveStreamType.HIK_ISUP.getCode().equals(rtpServerParam.getType())) {
            remoteHaiKangIsupService.stopPlay(rtpServerParam.getId(), SecurityConstants.INNER);
        }

        if (LiveStreamType.DAHUA_SDK.getCode().equals(rtpServerParam.getType())) {
            remoteDaHuaService.stopPlay(rtpServerParam.getId(), SecurityConstants.INNER);
        }

        InviteInfo inviteInfo = inviteStreamService.getInviteInfo(InviteSessionType.PLAY, device.getId(), rtpServerParam.getStreamId());

        if (inviteInfo != null) {
            inviteStreamService.removeInviteInfo(inviteInfo);
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
     * @param app
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

    /**
     * 加载文件形成播放地址
     *
     * @param id       设备id
     * @param callback 回调
     * @return
     */
    @Override
    public void loadRecord(Long id, ErrorCallback<StreamInfo> callback) {
        ZlmMediaServer mediaServer = getMediaServerForMinimumLoad(null);

        if (mediaServer == null) {
            callback.run(InviteErrorCode.FAIL.getCode(), "无可用的节点", null);
            return;
        }

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            callback.run(InviteErrorCode.FAIL.getCode(), "获取设备信息失败" + id, null);
            return;
        }
        if (r.getData() == null) {
            callback.run(InviteErrorCode.FAIL.getCode(), "设备不存在" + id, null);
            return;
        }

        if ("OFFLINE".equals(r.getData().getDeviceStatus())) {
            throw new RuntimeException("设备不在线" + id);
        }

        QsDevice device = r.getData();
        String videoPath = convertUrlToPath(device.getLiveAddress(), this.fileDomain, this.filePrefix, this.filePath);

        loadMP4File(mediaServer, "video_file", device.getDeviceCode(), id, videoPath, ((code, msg, streamInfo) -> {
            callback.run(code, msg, streamInfo);
        }));
    }

    /**
     * 关闭流文件形成播放地址
     *
     * @param id 设备id
     */
    @Override
    public void closeStreams(Long id) {
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败");
        }
        if (r.getData() == null) {
            throw new RuntimeException("设备不存在");
        }

        QsDevice device = r.getData();
        ZlmMediaServer mediaServer = getOne(device.getMediaServerId());

        if (mediaServer == null) {
            throw new RuntimeException("无可用的节点");
        }

        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[closeStreams] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            return;
        }
        // 停止录像
        mediaNodeServerService.stopRecord(mediaServer, "video_file", device.getStreamKey());
        mediaNodeServerService.closeStreams(mediaServer, "video_file", device.getStreamKey());

    }

    /**
     * 获取流媒体服务器列表
     *
     * @return
     */
    @Override
    public List<ZlmMediaServer> getAll() {
        return mediaServerMapper.queryAll(userSetting.getServerId());
    }

    /**
     * 测试流媒体服务
     *
     * @param ip     流媒体服务IP
     * @param port   流媒体服务HTT端口
     * @param secret 流媒体服务secret
     * @param type   流媒体服务类型
     * @return
     */
    @Override
    public ZlmMediaServer checkMediaServer(String ip, int port, String secret, String type) {
        if (mediaServerMapper.queryOneByHostAndPort(ip, port, userSetting.getServerId()) != null) {
            throw new RuntimeException("此连接已存在");
        }

        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(type);
        if (mediaNodeServerService == null) {
            log.info("[closeRTPServer] 失败, mediaServer的类型： {}，未找到对应的实现类", type);
            return null;
        }
        ZlmMediaServer mediaServer = mediaNodeServerService.checkMediaServer(ip, port, secret);
        if (mediaServer != null) {
            if (mediaServerMapper.queryOne(mediaServer.getId(), userSetting.getServerId()) != null) {
                throw new RuntimeException("媒体服务ID [" + mediaServer.getId() + " ] 已存在，请修改媒体服务器配置");
            }
        }
        return mediaServer;
    }

    /**
     * 获取流信息
     *
     * @param app         应用名
     * @param stream      流ID
     * @param mediaServer 媒体服务器
     * @return
     */
    @Override
    public MediaInfo getMediaInfo(ZlmMediaServer mediaServer, String app, String stream) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[getMediaInfo] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            return null;
        }
        return mediaNodeServerService.getMediaInfo(mediaServer, app, stream);
    }

    /**
     * 删除录制文件
     *
     * @param mediaServer
     * @param app
     * @param stream
     * @param date
     * @param fileName
     * @return
     */
    @Override
    public boolean deleteRecordDirectory(ZlmMediaServer mediaServer, String app, String stream, String date, String fileName) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[stopSendRtp] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            return false;
        }
        return mediaNodeServerService.deleteRecordDirectory(mediaServer, app, stream, date, fileName);
    }

    /**
     * 获取下载文件路径
     *
     * @param mediaServer
     * @param recordInfo
     * @return
     */
    @Override
    public DownloadFileInfo getDownloadFilePath(ZlmMediaServer mediaServer, RecordInfo recordInfo) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[setRecordSpeed] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            throw new RuntimeException("未找到mediaServer对应的实现类");
        }
        return mediaNodeServerService.getDownloadFilePath(mediaServer, recordInfo);
    }

    /**
     * 设置录像播放速度
     *
     * @param mediaServer 使用的节点
     * @param app         应用名
     * @param stream      流id
     * @param stamp       播放速度
     * @param schema      播放协议
     */
    @Override
    public void seekRecordStamp(ZlmMediaServer mediaServer, String app, String stream, Double stamp, String schema) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[seekRecordStamp] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            throw new RuntimeException("未找到mediaServer对应的实现类");
        }
        mediaNodeServerService.seekRecordStamp(mediaServer, app, stream, stamp, schema);
    }

    /**
     * 定位录像播放到制定位置
     *
     * @param mediaServer 使用的节点
     * @param app         应用名
     * @param stream      流ID
     * @param speed       要定位的时间位置，从录像开始的时间算起
     * @param schema      播放协议
     */
    @Override
    public void setRecordSpeed(ZlmMediaServer mediaServer, String app, String stream, Integer speed, String schema) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[setRecordSpeed] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            throw new RuntimeException("未找到mediaServer对应的实现类");
        }
        mediaNodeServerService.setRecordSpeed(mediaServer, app, stream, speed, schema);
    }

    /**
     * 关闭流
     *
     * @param mediaServer 媒体服务器
     * @param app         应用名
     * @param stream      流ID
     */
    @Override
    public void closeStreams(ZlmMediaServer mediaServer, String app, String stream) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[closeStreams] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            return;
        }
        mediaNodeServerService.closeStreams(mediaServer, app, stream);
    }

    /**
     * 开始播放
     *
     * @param device   设备信息
     * @param record   是否录制
     * @param callback 回调
     */
    @Override
    public void play(QsDevice device, Boolean record, ErrorCallback<StreamInfo> callback) {
        ZlmMediaServer mediaServer = getMediaServerForMinimumLoad(null);

        if (mediaServer == null) {
            callback.run(InviteErrorCode.FAIL.getCode(), "无可用的节点", null);
            return;
        }

        // 播放海康sdk/播放海康isup/播放大华sdk
        if (LiveStreamType.HIK_SDK.getCode().equals(device.getType()) || LiveStreamType.HIK_ISUP.getCode().equals(device.getType()) || LiveStreamType.DAHUA_SDK.getCode().equals(device.getType())) {
            RTPServerParam rtpServerParam = new RTPServerParam();
            if (LiveStreamType.HIK_SDK.getCode().equals(device.getType())) {
                rtpServerParam.setApp("haikang");
            } else if (LiveStreamType.HIK_ISUP.getCode().equals(device.getType())) {
                rtpServerParam.setApp("haikang_isup");
            } else if (LiveStreamType.DAHUA_SDK.getCode().equals(device.getType())) {
                rtpServerParam.setApp("dahua");
            }

            rtpServerParam.setStreamId(device.getDeviceCode());
            rtpServerParam.setTcpMode(0);
            rtpServerParam.setType(device.getType());
            rtpServerParam.setId(device.getId());
            System.out.println(rtpServerParam);
            play(mediaServer, rtpServerParam, device, null, callback);
        }

        // rtsp/rtmp/flv/hls/onvif
        if (LiveStreamType.RTSP.getCode().equals(device.getType()) || LiveStreamType.RTMP.getCode().equals(device.getType()) || LiveStreamType.FLV.getCode().equals(device.getType()) || LiveStreamType.HLS.getCode().equals(device.getType()) || LiveStreamType.ONVIF.getCode().equals(device.getType())) {
            StreamPullPlay streamPullPlay = new StreamPullPlay();
            streamPullPlay.setDeviceId(device.getId());
            streamPullPlay.setStream(device.getDeviceCode());
            streamPullPlay.setUrl(device.getLiveAddress());
            streamPullPlay.setEnable_mp4("1".equals(device.getEnableMp4()));
            streamPullPlay.setEnable_audio("1".equals(device.getEnableAudio()));
            streamPullPlay.setRtp_type("1");
            streamPullPlay.setTimeOut(10);

            if (LiveStreamType.RTSP.getCode().equals(device.getType())) {
                streamPullPlay.setApp("rtsp");
            } else if (LiveStreamType.RTMP.getCode().equals(device.getType())) {
                streamPullPlay.setApp("rtmp");
            } else if (LiveStreamType.FLV.getCode().equals(device.getType())) {
                streamPullPlay.setApp("flv");
                if ("ws".equals(device.getFlvType())) {
                    streamPullPlay.setUrl(convertWsToHttp(device.getLiveAddress()));
                }
            } else if (LiveStreamType.HLS.getCode().equals(device.getType())) {
                streamPullPlay.setApp("hls");
            } else if (LiveStreamType.ONVIF.getCode().equals(device.getType())) {
                streamPullPlay.setApp("onvif");
            }

            streamPullPlay(streamPullPlay, callback);
        }

        // 视频文件
        if (LiveStreamType.VIDEO_FILE.getCode().equals(device.getType())) {
            loadRecord(device.getId(), callback);
        }
    }

    /**
     * 获取流媒体服务器负载
     *
     * @param mediaServer
     * @return
     */
    @Override
    public MediaServerLoad getLoad(ZlmMediaServer mediaServer) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[closeStreams] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            throw new RuntimeException("[closeStreams] 失败, mediaServer的类型： " + mediaServer.getType() + "，未找到对应的实现类");
        }
        ZLMResult<?> threadsLoadZlmResult = mediaNodeServerService.getThreadsLoad(mediaServer);
        ZLMResult<?> workThreadsLoadZlmResult = mediaNodeServerService.getWorkThreadsLoad(mediaServer);

        MediaServerLoad result = new MediaServerLoad();
        result.setWorkThreadsLoad(workThreadsLoadZlmResult.getData());
        result.setThreadsLoad(threadsLoadZlmResult.getData());
        result.setId(mediaServer.getId());
        return result;
    }

    /**
     * 重启流媒体
     *
     * @param mediaServer 流媒体
     * @return
     */
    @Override
    public void restartServer(ZlmMediaServer mediaServer) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[closeStreams] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            return;
        }
        mediaNodeServerService.restartServer(mediaServer);
    }

    /**
     * 生成推流地址
     *
     * @param id     设备id
     * @param callId
     * @return
     */
    @Override
    public Map<String, Object> getStreamPushAddress(Long id, String callId) {
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new RuntimeException("获取设备信息失败");
        }
        if (r.getData() == null) {
            throw new RuntimeException("设备不存在");
        }
        ZlmMediaServer mediaServer = getMediaServerForMinimumLoad(null);
        String sign = DigestUtils.md5DigestAsHex((callId + '_' + userSetting.getPushKey()).getBytes());

        HashMap<String, Object> map = new HashMap<>();

        String rtsp = StrUtil.format("rtsp://{}:{}/push/{}?callId={}&sign={}", mediaServer.getIp(), mediaServer.getRtspPort(), r.getData().getDeviceCode(), callId, sign);

        String rtmp = StrUtil.format("rtmp://{}:{}/push/{}?callId={}&sign={}", mediaServer.getIp(), mediaServer.getRtmpPort(), r.getData().getDeviceCode(), callId, sign);
        map.put("rtsp", rtsp);
        map.put("rtmp", rtmp);
        return map;
    }

    /**
     * 推流播放
     *
     * @param id
     * @param callback
     */
    @Override
    public void streamPullPush(Long id, ErrorCallback<StreamInfo> callback) {
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.info("获取设备信息失败 id:{}", id);
            callback.run(InviteErrorCode.FAIL.getCode(), "获取设备信息失败", null);
            return;
        }
        if (r.getData() == null) {
            log.info("设备不存在 id:{}", id);
            callback.run(InviteErrorCode.FAIL.getCode(), "设备不存在", null);
            return;
        }
        QsDevice device = r.getData();

        if (!LiveStreamType.PUSH.getCode().equals(device.getType())) {
            log.info("直播流接入类型不对，应当是PUSH id:{}", id);
            callback.run(InviteErrorCode.FAIL.getCode(), "直播流接入类型不对，应当是PUSH", null);
            return;
        }

        ZlmMediaServer mediaServer = getOne(device.getMediaServerId());
        if (mediaServer != null) {
            MediaInfo mediaInfo = getMediaInfo(mediaServer, "push", device.getDeviceCode());
            if (mediaInfo != null) {
                String callId = null;
                StreamAuthorityInfo streamAuthorityInfo = redisCatchStorage.getStreamAuthorityInfo("push", device.getDeviceCode());
                if (streamAuthorityInfo != null) {
                    callId = streamAuthorityInfo.getCallId();
                }
                callback.run(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), getStreamInfoByAppAndStream(mediaServer, "push", device.getDeviceCode(), mediaInfo));
                if ("0".equals(device.getStreamStatus())) {
                    QsDevice qsDevice = new QsDevice();
                    qsDevice.setId(id);
                    qsDevice.setStreamStatus("1");
                    R<Boolean> updateR = remoteQsDeviceService.updateQsDevice(qsDevice, SecurityConstants.INNER);
                    if (updateR.getCode() != Constants.SUCCESS) {
                        log.info("修改推流设备设备失败 id:{}", id);
                        throw new RuntimeException("修改推流设备设备失败");
                    }

                    if (!updateR.getData()) {
                        log.info("修改推流设备设备失败 id:{}", id);
                        throw new RuntimeException("修改推流设备设备失败");
                    }
                }
                return;
            }
        }
    }

    /**
     * 将 WebSocket 协议地址转换为 HTTP 协议地址
     * ws:// -> http://
     * wss:// -> https://
     *
     * @param wsUrl 输入的 WebSocket 地址
     * @return 转换后的 HTTP 地址
     */
    public static String convertWsToHttp(String wsUrl) {
        // 1. 空值检查
        if (wsUrl == null || wsUrl.isEmpty()) {
            return wsUrl;
        }

        // 2. 判断并替换协议
        if (wsUrl.startsWith("wss://")) {
            return wsUrl.replace("wss://", "https://");
        } else if (wsUrl.startsWith("ws://")) {
            return wsUrl.replace("ws://", "http://");
        }

        // 3. 如果已经是 http/https 或其他格式，直接返回
        return wsUrl;
    }

    private void loadMP4File(ZlmMediaServer mediaServer, String app, String stream, Long id, String videoPath, ErrorCallback<StreamInfo> callback) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[loadMP4File] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            throw new RuntimeException("未找到mediaServer对应的实现类");
        }

        StreamInfo streamData = getStreamInfoByAppAndStreamWithCheck(app, stream, mediaServer.getId(), null, false);
        if (streamData != null) {
            callback.run(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), streamData);
            return;
        }

        Hook hook = Hook.getInstance(HookType.on_media_arrival, app, stream, mediaServer.getServerId());
        subscribe.addSubscribe(hook, (hookData) -> {
            StreamInfo streamInfo = getStreamInfoByAppAndStream(mediaServer, app, stream, hookData.getMediaInfo());
            if (callback != null) {
                callback.run(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), streamInfo);

                QsDevice qsDevice = new QsDevice();
                qsDevice.setId(id);
                qsDevice.setStreamKey(stream);
                qsDevice.setMediaServerId(mediaServer.getId());
                qsDevice.setStreamStatus("1");
                R<Boolean> r = remoteQsDeviceService.updateQsDevice(qsDevice, SecurityConstants.INNER);
                if (r.getCode() != Constants.SUCCESS) {
                    log.error("更新设备失败");
                    callback.run(InviteErrorCode.FAIL.getCode(), "更新设备失败", null);
                }

                // 开启录像
                mediaNodeServerService.startRecord(mediaServer, app, stream);
            }
        });

        ZLMResult<?> zlmResult = zlmresTfulUtils.loadMP4File(mediaServer, app, stream, videoPath);

        if (zlmResult == null) {
            throw new RuntimeException("请求失败");
        }
        if (zlmResult.getCode() != 0) {
            throw new RuntimeException(zlmResult.getMsg());
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


    /**
     * 将网络访问路径转换为本地文件物理路径
     */
    public String convertUrlToPath(String url, String domain, String prefix, String localBasePath) {
        // 步骤 A: 去除域名部分
        // 例如：http://127.0.0.1:9300/statics/...  ->  /statics/...
        if (url.startsWith(domain)) {
            url = url.substring(domain.length());
        }

        // 步骤 B: 去除前缀部分
        // 例如：/statics/2026/...  ->  /2026/...
        if (url.startsWith(prefix)) {
            url = url.substring(prefix.length());
        }

        // 步骤 C: 拼接本地路径
        // 注意：防止路径分隔符重复或缺失
        // localBasePath: D:/ruoyi/uploadPath
        // url: /2026/03/27/...

        if (url.startsWith("/")) {
            return localBasePath + url;
        } else {
            return localBasePath + "/" + url;
        }
    }
}
