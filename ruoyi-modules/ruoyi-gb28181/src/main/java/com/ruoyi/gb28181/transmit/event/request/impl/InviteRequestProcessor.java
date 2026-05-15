package com.ruoyi.gb28181.transmit.event.request.impl;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.gb28181.api.bean.Gb28181Sdp;
import com.ruoyi.gb28181.api.common.InviteSessionType;
import com.ruoyi.gb28181.api.common.RemoteAddressInfo;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.api.domain.SsrcTransaction;
import com.ruoyi.gb28181.api.utils.SipUtils;
import com.ruoyi.gb28181.config.UserSetting;
import com.ruoyi.gb28181.service.IDeviceService;
import com.ruoyi.gb28181.service.IGb28181PlatformService;
import com.ruoyi.gb28181.session.SipInviteSessionManager;
import com.ruoyi.gb28181.transmit.ISIPProcessorObserver;
import com.ruoyi.gb28181.transmit.SIPSender;
import com.ruoyi.gb28181.transmit.event.request.ISIPRequestProcessor;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.RemoteZlmService;
import com.ruoyi.zlm.api.domain.Gb28181PlatformPlay;
import com.ruoyi.zlm.api.domain.Gb28181PlatformPlayback;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sip.RequestEvent;
import javax.sip.header.FromHeader;
import javax.sip.header.SubjectHeader;
import javax.sip.header.ToHeader;
import javax.sip.message.Response;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 处理上级平台INVITE点播请求
 */
@Slf4j
@Component
public class InviteRequestProcessor extends SIPRequestProcessorParent implements InitializingBean, ISIPRequestProcessor {

    public final String method = "INVITE";

    @Autowired
    private ISIPProcessorObserver sipProcessorObserver;

    @Autowired
    private SIPSender sipSender;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private IGb28181PlatformService gb28181PlatformService;

    @Autowired
    private SipInviteSessionManager sessionManager;

    @Autowired
    private RemoteZlmService remoteZlmService;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    private static final AtomicInteger streamIdCounter = new AtomicInteger(1000);

    @Override
    public void afterPropertiesSet() throws Exception {
        sipProcessorObserver.addRequestProcessor(method, this);
    }

    @Override
    public void process(RequestEvent evt) {
        try {
            SIPRequest request = (SIPRequest) evt.getRequest();
            log.info("[收到INVITE请求] request: {}", request);

            ToHeader toHeader = (ToHeader) request.getHeader(ToHeader.NAME);
            String toDeviceId = ((gov.nist.javax.sip.address.SipUri) toHeader.getAddress().getURI()).getUser();
            log.info("[目标ID] toDeviceId: {}", toDeviceId);

            // 获取 From 头部
            FromHeader fromHeader = (FromHeader) request.getHeader(FromHeader.NAME);
            String fromDeviceId = ((gov.nist.javax.sip.address.SipUri) fromHeader.getAddress().getURI()).getUser();
            log.info("[源端ID] fromDeviceId: {}", fromDeviceId);

            // 尝试解析 Subject 头部获取通道 ID
            String channelId = toDeviceId;
            SubjectHeader subjectHeader = (SubjectHeader) request.getHeader(SubjectHeader.NAME);
            if (subjectHeader != null) {
                String subject = subjectHeader.getSubject();
                log.info("[Subject] subject: {}", subject);
                // Subject 格式: "目标:通道,源:0"
                try {
                    String[] parts = subject.split(",");
                    if (parts.length >= 2) {
                        String[] targetPart = parts[0].split(":");
                        if (targetPart.length >= 2) {
                            channelId = targetPart[1]; // 获取通道 ID
                            log.info("[从Subject获取通道ID] channelId: {}", channelId);
                        }
                    }
                } catch (Exception e) {
                    log.warn("[解析Subject失败] error: {}", e.getMessage());
                }
            }

            // 首先解析 SDP 的 o= 字段获取平台 ID
            String sdpPlatformId = null;
            try {
                byte[] content = request.getRawContent();
                if (content != null && content.length > 0) {
                    String sdp = new String(content);
                    log.info("[SDP内容] sdp: {}", sdp);
                    String[] lines = sdp.split("\r\n|\n|\r");
                    for (String line : lines) {
                        if (line.startsWith("o=")) {
                            // o=34020000001320000001 0 0 IN IP4 139.9.214.221
                            String[] oParts = line.substring(2).split(" ");
                            if (oParts.length > 0) {
                                sdpPlatformId = oParts[0];
                                log.info("[从SDP获取平台ID] sdpPlatformId: {}", sdpPlatformId);
                            }
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("[解析SDP失败] error: {}", e.getMessage());
            }

            Gb28181Platform platform = null;
            Device device = null;

            // 优先使用 SDP 中的平台 ID 查询
            if (sdpPlatformId != null) {
                platform = gb28181PlatformService.selectGb28181PlatformByDeviceGbId(sdpPlatformId);
                if (platform != null) {
                    log.info("[通过SDP平台ID找到平台] platform: {}, platformId: {}", platform.getName(), sdpPlatformId);
                }
            }

            // 如果没找到平台，尝试用 From 头部的 ID 查找
            if (platform == null) {
                platform = gb28181PlatformService.selectGb28181PlatformByDeviceGbId(fromDeviceId);
                if (platform != null) {
                    log.info("[通过From平台ID找到平台] platform: {}, platformId: {}", platform.getName(), fromDeviceId);
                }
            }

            // 如果还没找到平台，尝试用 To 头部的 ID 查找
            if (platform == null) {
                platform = gb28181PlatformService.selectGb28181PlatformByDeviceGbId(toDeviceId);
                if (platform != null) {
                    log.info("[通过To平台ID找到平台] platform: {}, platformId: {}", platform.getName(), toDeviceId);
                }
            }

            // 如果还没找到平台，尝试查找设备
            if (platform == null) {
                device = deviceService.getDeviceByDeviceId(toDeviceId);
            }

            if (device == null && platform == null) {
                log.error("[未找到设备或平台] sdpPlatformId: {}, fromId: {}, toId: {}", sdpPlatformId, fromDeviceId, toDeviceId);
                Response notFoundResponse = getMessageFactory().createResponse(Response.NOT_FOUND, request);
                sipSender.transmitRequest(request.getLocalAddress().getHostAddress(), notFoundResponse);
                return;
            }

            if (platform != null) {
                log.info("[来自上级平台的点播/回放] platform: {}, channelId: {}, toDeviceId: {}", platform.getName(), channelId, toDeviceId);

                // 解析 SDP，判断是播放还是回放，并提取时间范围
                String sdpStr = new String(request.getRawContent());
                boolean isPlayback = isPlaybackRequest(sdpStr);
                Long playbackStartTime = null;
                Long playbackEndTime = null;
                if (isPlayback) {
                    playbackStartTime = extractPlaybackStartTime(sdpStr);
                    playbackEndTime = extractPlaybackEndTime(sdpStr);
                    log.info("[来自上级平台的回放请求] startTime: {}, endTime: {}", playbackStartTime, playbackEndTime);
                }

                QsDevice qsDevice = null;

                // 先用 toDeviceId 根据 gbCode 查找设备
                try {
                    R<QsDevice> qsDeviceR = remoteQsDeviceService.getDeviceByGbCode(toDeviceId, SecurityConstants.INNER);
                    if (qsDeviceR.getCode() == Constants.SUCCESS && qsDeviceR.getData() != null) {
                        qsDevice = qsDeviceR.getData();
                        log.info("[通过gbCode找到设备] gbCode: {}, deviceName: {}", toDeviceId, qsDevice.getDeviceName());
                    }
                } catch (Exception e) {
                    log.warn("[通过gbCode查询失败] error: {}", e.getMessage());
                }

                // 如果没找到，尝试用 channelId 根据 gbCode 查找
                if (qsDevice == null && !toDeviceId.equals(channelId)) {
                    try {
                        R<QsDevice> qsDeviceR = remoteQsDeviceService.getDeviceByGbCode(channelId, SecurityConstants.INNER);
                        if (qsDeviceR.getCode() == Constants.SUCCESS && qsDeviceR.getData() != null) {
                            qsDevice = qsDeviceR.getData();
                            log.info("[通过channelId(gbCode)找到设备] gbCode: {}, deviceName: {}", channelId, qsDevice.getDeviceName());
                        }
                    } catch (Exception e) {
                        log.warn("[通过channelId(gbCode)查询失败] error: {}", e.getMessage());
                    }
                }

                // 如果还没找到，查询所有设备进行匹配
                if (qsDevice == null) {
                    try {
                        QsDevice queryDevice = new QsDevice();
                        R<java.util.List<QsDevice>> deviceListR = remoteQsDeviceService.list(queryDevice, SecurityConstants.INNER);
                        
                        if (deviceListR.getCode() == Constants.SUCCESS && deviceListR.getData() != null) {
                            for (QsDevice d : deviceListR.getData()) {
                                if (toDeviceId.equals(d.getGbCode()) || toDeviceId.equals(d.getGbDeviceId()) || toDeviceId.equals(d.getGbChannelId()) ||
                                    channelId.equals(d.getGbCode()) || channelId.equals(d.getGbDeviceId()) || channelId.equals(d.getGbChannelId())) {
                                    qsDevice = d;
                                    log.info("[通过全量设备找到匹配设备] deviceName: {}, gbCode: {}, gbDeviceId: {}, gbChannelId: {}", 
                                            qsDevice.getDeviceName(), qsDevice.getGbCode(), qsDevice.getGbDeviceId(), qsDevice.getGbChannelId());
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.warn("[通过全量设备查询失败] error: {}", e.getMessage());
                    }
                }

                if (qsDevice == null) {
                    log.error("[未找到对应设备] toDeviceId: {}, channelId: {}", toDeviceId, channelId);
                    Response notFoundResponse = getMessageFactory().createResponse(Response.NOT_FOUND, request);
                    sipSender.transmitRequest(request.getLocalAddress().getHostAddress(), notFoundResponse);
                    return;
                }

                // 使用 channelId 作为最终的设备 ID（如果是通道的话）
                String finalDeviceId = channelId;
                if (isPlayback) {
                    handlePlatformPlayback(request, platform, qsDevice, finalDeviceId, playbackStartTime, playbackEndTime);
                } else {
                    handlePlatformInvite(request, platform, qsDevice, finalDeviceId);
                }
                return;
            }

            log.info("[普通设备点播] deviceId: {}", toDeviceId);
            Response okResponse = getMessageFactory().createResponse(Response.OK, request);
            sipSender.transmitRequest(request.getLocalAddress().getHostAddress(), okResponse);

        } catch (Exception e) {
            log.error("[处理INVITE请求失败] error: ", e);
        }
    }

    private void handlePlatformInvite(SIPRequest request, Gb28181Platform platform, QsDevice qsDevice, String toDeviceId) {
        try {
            log.info("[开始处理上级平台点播] 平台: {}, 设备: {}, 通道ID: {}", 
                    platform.getName(), qsDevice.getDeviceName(), toDeviceId);

            RemoteAddressInfo remoteAddressInfo = SipUtils.getRemoteAddressFromRequest(request,
                    userSetting.getSipUseSourceIpAsRemoteAddress());
            String dstUrl = remoteAddressInfo.getIp();

            // 从 SDP 中提取 m=video 端口号和传输协议
            String sdpStr = new String(request.getRawContent());
            SdpVideoInfo videoInfo = extractVideoInfoFromSdp(sdpStr);
            int dstPort = videoInfo.getPort();
            int isUdp = videoInfo.getIsUdp();

            log.info("[上级平台接收地址] IP: {}, 端口: {}, isUdp: {}", dstUrl, dstPort, isUdp);

            Gb28181Sdp gb28181Sdp = SipUtils.parseSDP(sdpStr);
            String ssrc = gb28181Sdp.getSsrc();

            log.info("[SDP解析] ssrc: {}", ssrc);

            // 获取默认媒体服务器
            R<ZlmMediaServer> zlmMediaServerResult = remoteZlmService.getDefaultMediaServer(SecurityConstants.INNER);
            if (zlmMediaServerResult.getCode() != Constants.SUCCESS || zlmMediaServerResult.getData() == null) {
                log.error("[媒体服务器获取失败] 无法获取默认媒体服务器");
                Response serverErrorResponse = getMessageFactory().createResponse(Response.SERVER_INTERNAL_ERROR, request);
                sipSender.transmitRequest(request.getLocalAddress().getHostAddress(), serverErrorResponse);
                return;
            }

            ZlmMediaServer zlmMediaServer = zlmMediaServerResult.getData();
            log.info("[媒体服务器已获取] ID: {}, IP: {}, HTTP端口: {}, RTSP端口: {}, RTMP端口: {}", 
                    zlmMediaServer.getId(), zlmMediaServer.getIp(), 
                    zlmMediaServer.getHttpPort(), zlmMediaServer.getRtspPort(), zlmMediaServer.getRtmpPort());

            String streamId = "gb-cascade-" + streamIdCounter.incrementAndGet();
            Response okResponse = getMessageFactory().createResponse(Response.OK, request);

            String replySdp = buildReplySdp(zlmMediaServer, streamId, ssrc);
            okResponse.setContent(replySdp, getHeaderFactory().createContentTypeHeader("application", "sdp"));

            log.info("[回复200 OK] streamId: {}, ssrc: {}, sdp: {}", streamId, ssrc, replySdp);
            sipSender.transmitRequest(request.getLocalAddress().getHostAddress(), okResponse);

            // 创建会话记录
            String callId = request.getCallIdHeader().getCallId();
            SsrcTransaction ssrcTransaction = SsrcTransaction.buildForPlatformWithQsDevice(
                    platform.getDeviceGbId(),
                    toDeviceId,
                    callId,
                    "live",
                    streamId,
                    ssrc,
                    zlmMediaServer.getId(),
                    (SIPResponse) okResponse,
                    InviteSessionType.PLAY,
                    qsDevice.getId(),
                    qsDevice.getType());
            sessionManager.put(ssrcTransaction);

            log.info("[会话记录已创建] callId: {}, streamId: {}, ssrc: {}, 媒体服务器: {}", 
                    callId, streamId, ssrc, zlmMediaServer.getId());

            // 构建上级平台点播请求
            Gb28181PlatformPlay platformPlay = new Gb28181PlatformPlay();
            platformPlay.setQsDevice(qsDevice);
            platformPlay.setPlatformDeviceGbId(platform.getDeviceGbId());
            platformPlay.setDstUrl(dstUrl);
            platformPlay.setDstPort(dstPort);
            platformPlay.setSsrc(ssrc);
            platformPlay.setStreamId(streamId);
            platformPlay.setIsUdp(isUdp);
            platformPlay.setRtcp(platform.getRtcp());

            // 调用zlm模块处理上级平台点播
            log.info("[请求ZLM开始推流] 平台ID: {}, 设备: {}, 流ID: {}, ssrc: {}, 目标: {}:{}", 
                    platform.getDeviceGbId(), qsDevice.getDeviceName(), streamId, ssrc, dstUrl, dstPort);
            
            R<Void> playResult = remoteZlmService.gb28181PlatformPlay(platformPlay, SecurityConstants.INNER);

            if (playResult.getCode() == Constants.SUCCESS) {
                log.info("[ZLM推流请求成功] streamId: {}", streamId);
            } else {
                log.error("[ZLM推流请求失败] streamId: {}, code: {}, msg: {}", 
                        streamId, playResult.getCode(), playResult.getMsg());
            }

        } catch (Exception e) {
            log.error("[处理平台INVITE请求异常] 平台: {}, 设备: {}, error: ", 
                    platform.getName(), qsDevice.getDeviceName(), e);
        }
    }

    /**
     * 处理上级平台回放请求
     */
    private void handlePlatformPlayback(SIPRequest request, Gb28181Platform platform, QsDevice qsDevice, String toDeviceId, Long startTime, Long endTime) {
        try {
            log.info("[开始处理上级平台回放] 平台: {}, 设备: {}, 通道ID: {}, startTime: {}, endTime: {}", 
                    platform.getName(), qsDevice.getDeviceName(), toDeviceId, startTime, endTime);

            RemoteAddressInfo remoteAddressInfo = SipUtils.getRemoteAddressFromRequest(request,
                    userSetting.getSipUseSourceIpAsRemoteAddress());
            String dstUrl = remoteAddressInfo.getIp();

            // 从 SDP 中提取 m=video 端口号和传输协议
            String sdpStr = new String(request.getRawContent());
            SdpVideoInfo videoInfo = extractVideoInfoFromSdp(sdpStr);
            int dstPort = videoInfo.getPort();
            int isUdp = videoInfo.getIsUdp();

            log.info("[上级平台接收地址] IP: {}, 端口: {}, isUdp: {}", dstUrl, dstPort, isUdp);

            Gb28181Sdp gb28181Sdp = SipUtils.parseSDP(sdpStr);
            String ssrc = gb28181Sdp.getSsrc();

            log.info("[SDP解析] ssrc: {}", ssrc);

            // 获取默认媒体服务器
            R<ZlmMediaServer> zlmMediaServerResult = remoteZlmService.getDefaultMediaServer(SecurityConstants.INNER);
            if (zlmMediaServerResult.getCode() != Constants.SUCCESS || zlmMediaServerResult.getData() == null) {
                log.error("[媒体服务器获取失败] 无法获取默认媒体服务器");
                Response serverErrorResponse = getMessageFactory().createResponse(Response.SERVER_INTERNAL_ERROR, request);
                sipSender.transmitRequest(request.getLocalAddress().getHostAddress(), serverErrorResponse);
                return;
            }

            ZlmMediaServer zlmMediaServer = zlmMediaServerResult.getData();
            log.info("[媒体服务器已获取] ID: {}, IP: {}, HTTP端口: {}, RTSP端口: {}, RTMP端口: {}", 
                    zlmMediaServer.getId(), zlmMediaServer.getIp(), 
                    zlmMediaServer.getHttpPort(), zlmMediaServer.getRtspPort(), zlmMediaServer.getRtmpPort());

            String streamId = "gb-cascade-playback-" + streamIdCounter.incrementAndGet();
            Response okResponse = getMessageFactory().createResponse(Response.OK, request);

            String replySdp = buildReplySdp(zlmMediaServer, streamId, ssrc, startTime, endTime);
            okResponse.setContent(replySdp, getHeaderFactory().createContentTypeHeader("application", "sdp"));

            log.info("[回复200 OK] streamId: {}, ssrc: {}, sdp: {}", streamId, ssrc, replySdp);
            sipSender.transmitRequest(request.getLocalAddress().getHostAddress(), okResponse);

            // 创建会话记录
            String callId = request.getCallIdHeader().getCallId();
            SsrcTransaction ssrcTransaction = SsrcTransaction.buildForPlatformWithQsDevice(
                    platform.getDeviceGbId(),
                    toDeviceId,
                    callId,
                    "playback",
                    streamId,
                    ssrc,
                    zlmMediaServer.getId(),
                    (SIPResponse) okResponse,
                    InviteSessionType.PLAYBACK,
                    qsDevice.getId(),
                    qsDevice.getType());
            sessionManager.put(ssrcTransaction);

            log.info("[会话记录已创建] callId: {}, streamId: {}, ssrc: {}, 媒体服务器: {}", 
                    callId, streamId, ssrc, zlmMediaServer.getId());

            // 解析通道ID获取通道号
            Integer channel = null;
            // 如果通道ID是20位的国标ID，尝试从后8位提取通道号
            if (toDeviceId != null && toDeviceId.length() == 20) {
                try {
                    // 尝试从最后8位提取通道号
                    String channelPart = toDeviceId.substring(12);
                    channel = Integer.parseInt(channelPart);
                    log.info("[通道号解析] 从国标ID中提取通道号: {}", channel);
                } catch (Exception e) {
                    log.warn("[通道号解析失败] 使用设备默认通道: {}", qsDevice.getChannel());
                }
            }
            // 如果没提取到通道号，使用设备的默认通道
            if (channel == null) {
                channel = qsDevice.getChannel();
                log.info("[通道号] 使用设备默认通道: {}", channel);
            }

            // 构建上级平台回放请求
            Gb28181PlatformPlayback platformPlayback = new Gb28181PlatformPlayback();
            platformPlayback.setQsDevice(qsDevice);
            platformPlayback.setPlatformDeviceGbId(platform.getDeviceGbId());
            platformPlayback.setDstUrl(dstUrl);
            platformPlayback.setDstPort(dstPort);
            platformPlayback.setSsrc(ssrc);
            platformPlayback.setStreamId(streamId);
            platformPlayback.setIsUdp(isUdp);
            platformPlayback.setRtcp(platform.getRtcp());
            platformPlayback.setStartTime(startTime);
            platformPlayback.setEndTime(endTime);

            // 调用zlm模块处理上级平台回放
            log.info("[请求ZLM开始回放推流] 平台ID: {}, 设备: {}, 流ID: {}, ssrc: {}, 目标: {}:{}, 通道: {}", 
                    platform.getDeviceGbId(), qsDevice.getDeviceName(), streamId, ssrc, dstUrl, dstPort, channel);
            
            R<Void> playbackResult = remoteZlmService.gb28181PlatformPlayback(platformPlayback, SecurityConstants.INNER);

            if (playbackResult.getCode() == Constants.SUCCESS) {
                log.info("[ZLM回放推流请求成功] streamId: {}", streamId);
            } else {
                log.error("[ZLM回放推流请求失败] streamId: {}, code: {}, msg: {}", 
                        streamId, playbackResult.getCode(), playbackResult.getMsg());
            }

        } catch (Exception e) {
            log.error("[处理平台回放请求异常] 平台: {}, 设备: {}, error: ", 
                    platform.getName(), qsDevice.getDeviceName(), e);
        }
    }

    /**
     * 判断是回放请求还是播放请求
     * @param sdpStr SDP字符串
     * @return true表示回放，false表示播放
     */
    private boolean isPlaybackRequest(String sdpStr) {
        try {
            String[] lines = sdpStr.split("\r\n|\n|\r");
            for (String line : lines) {
                if (line.startsWith("t=")) {
                    // 格式：t=1777266297 1777266322 或 t=0 0
                    // GB28181标准中t=字段通常是秒级时间戳
                    String[] parts = line.substring(2).split(" ");
                    if (parts.length >= 2) {
                        long startTime = parseTimestamp(parts[0]);
                        long endTime = parseTimestamp(parts[1]);
                        if (startTime != 0 || endTime != 0) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("[判断回放请求失败] error: {}", e.getMessage());
        }
        return false;
    }

    /**
     * 从 SDP 中提取回放开始时间（毫秒）
     * @param sdpStr SDP字符串
     * @return 开始时间（毫秒）
     */
    private Long extractPlaybackStartTime(String sdpStr) {
        try {
            String[] lines = sdpStr.split("\r\n|\n|\r");
            for (String line : lines) {
                if (line.startsWith("t=")) {
                    String[] parts = line.substring(2).split(" ");
                    if (parts.length >= 1) {
                        return parseTimestamp(parts[0]);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("[提取回放开始时间失败] error: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 从 SDP 中提取回放结束时间（毫秒）
     * @param sdpStr SDP字符串
     * @return 结束时间（毫秒）
     */
    private Long extractPlaybackEndTime(String sdpStr) {
        try {
            String[] lines = sdpStr.split("\r\n|\n|\r");
            for (String line : lines) {
                if (line.startsWith("t=")) {
                    String[] parts = line.substring(2).split(" ");
                    if (parts.length >= 2) {
                        return parseTimestamp(parts[1]);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("[提取回放结束时间失败] error: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 解析时间戳，自动判断是秒还是毫秒
     * @param timestampStr 时间戳字符串
     * @return 毫秒级时间戳
     */
    private long parseTimestamp(String timestampStr) {
        long timestamp = Long.parseLong(timestampStr);
        // 判断是否是秒级时间戳（小于当前时间的秒级表示）
        // 当前时间的秒级时间戳大概在1.7e9左右，毫秒级在1.7e12左右
        if (timestamp < 2000000000L) {
            // 秒级时间戳，转换为毫秒
            return timestamp * 1000L;
        }
        // 已经是毫秒级时间戳
        return timestamp;
    }

    private String buildReplySdp(ZlmMediaServer mediaServer, String streamId, String ssrc) {
        return buildReplySdp(mediaServer, streamId, ssrc, null, null);
    }
    
    private String buildReplySdp(ZlmMediaServer mediaServer, String streamId, String ssrc, Long startTime, Long endTime) {
        StringBuilder sdp = new StringBuilder();
        sdp.append("v=0\r\n");
        sdp.append("o=- 0 0 IN IP4 ").append(mediaServer.getIp()).append("\r\n");
        if (startTime != null && endTime != null) {
            sdp.append("s=Playback\r\n");
        } else {
            sdp.append("s=Play\r\n");
        }
        sdp.append("c=IN IP4 ").append(mediaServer.getIp()).append("\r\n");
        if (startTime != null && endTime != null) {
            // 回复时使用秒级时间戳（GB28181标准）
            long replyStartTime = startTime / 1000L;
            long replyEndTime = endTime / 1000L;
            sdp.append("t=").append(replyStartTime).append(" ").append(replyEndTime).append("\r\n");
        } else {
            sdp.append("t=0 0\r\n");
        }
        sdp.append("m=video 0 RTP/AVP 96\r\n");
        sdp.append("a=sendonly\r\n");
        sdp.append("y=").append(ssrc).append("\r\n");
        return sdp.toString();
    }

    /**
     * 从 SDP 中提取 m=video 行的端口号和传输协议
     * @param sdpStr SDP 字符串
     * @return 包含端口和isUdp的结果，isUdp为1表示UDP，0表示TCP
     */
    private SdpVideoInfo extractVideoInfoFromSdp(String sdpStr) {
        int port = 5060;
        int isUdp = 1; // 默认UDP
        
        try {
            String[] lines = sdpStr.split("\r\n|\n|\r");
            
            for (String line : lines) {
                if (line.startsWith("m=video")) {
                    // m=video 34852 TCP/RTP/AVP 96 97 98 99 或 m=video 34852 RTP/AVP 96...
                    String content = line.substring(7).trim();
                    String[] parts = content.split("\\s+");
                    
                    if (parts.length > 0) {
                        port = Integer.parseInt(parts[0]);
                        log.info("[提取SDP端口] 成功提取端口: {}", port);
                    }
                    
                    if (parts.length > 1) {
                        String protocol = parts[1];
                        if (protocol.contains("TCP")) {
                            isUdp = 0;
                        }
                        log.info("[提取SDP传输协议] protocol: {}, isUdp: {}", protocol, isUdp);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            log.warn("[提取SDP信息失败] error: {}", e.getMessage());
        }
        
        return new SdpVideoInfo(port, isUdp);
    }
    
    /**
     * SDP视频信息
     */
    private static class SdpVideoInfo {
        private final int port;
        private final int isUdp;
        
        public SdpVideoInfo(int port, int isUdp) {
            this.port = port;
            this.isUdp = isUdp;
        }
        
        public int getPort() {
            return port;
        }
        
        public int getIsUdp() {
            return isUdp;
        }
    }
}
