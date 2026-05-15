package com.ruoyi.zlm.api;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.gb28181.api.RemoteGb28181Service;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.jt1078.api.RemoteJt1078Service;
import com.ruoyi.onvif.api.RemoteOnvifService;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.RemoteZlmCloudRecordService;
import com.ruoyi.zlm.api.domain.*;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.gb28181.api.utils.DateUtil;
import com.ruoyi.zlm.common.InviteSessionType;
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IDevicePlayService;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.service.IZlmCloudRecordService;
import com.ruoyi.zlm.session.SSRCFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * zlm接口
 *
 * @FileName ZlmApiController
 * @Description
 * @Author fengcheng
 * @date 2026-04-01
 **/
@Slf4j
@RestController
@RequestMapping("/api/zlm")
public class ZlmApiController {

    @Autowired
    private IMediaServerService mediaServerService;

    @Autowired
    private IDevicePlayService devicePlayService;

    @Autowired
    private IZlmCloudRecordService zlmCloudRecordService;

    @Autowired
    private RemoteZlmCloudRecordService remoteZlmCloudRecordService;

    @Autowired
    private RemoteGb28181Service remoteGb28181Service;

    @Autowired
    private RemoteOnvifService remoteOnvifService;

    @Autowired
    private RemoteJt1078Service remoteJt1078Service;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private SSRCFactory ssrcFactory;

    @DeleteMapping("/sessionManagerPut/{mediaServerId}/{ssrc}")
    R<Void> releaseSsrc(@PathVariable String mediaServerId, @PathVariable String ssrc) {
        ssrcFactory.releaseSsrc(mediaServerId, ssrc);
        return R.ok();
    }

    /**
     * 关闭rtp服务
     *
     * @param mediaServerId
     * @param rtpServer
     */
    @PostMapping("/closeRTPServer/{mediaServerId}")
    R<Void> closeRTPServer(@PathVariable String mediaServerId, @RequestBody RtpServerParam rtpServer) {
        ZlmMediaServer mediaServer = mediaServerService.getOneFromDatabase(mediaServerId);
        mediaServerService.closeRTPServer(mediaServer, rtpServer.getStream());
        return R.ok();
    }

    /**
     * 连接rtp服务
     *
     * @param mediaServerId
     * @param address
     * @param port
     * @param stream
     * @return
     */
    @PostMapping("/connectRtpServer/{mediaServerId}")
    R<Boolean> connectRtpServer(@PathVariable String mediaServerId, @RequestParam String address, @RequestParam int port, @RequestParam String stream) {
        ZlmMediaServer mediaServer = mediaServerService.getOneFromDatabase(mediaServerId);
        Boolean b = mediaServerService.connectRtpServer(mediaServer, address, port, stream);
        return R.ok(b);
    }

    /**
     * 开始发送RTP流到指定地址
     *
     * @param mediaServerId
     * @param param
     * @return
     */
    @PostMapping("/startSendRtp/{mediaServerId}")
    R<?> startSendRtp(@PathVariable String mediaServerId, @RequestBody Map<String, Object> param) {
        ZlmMediaServer mediaServer = mediaServerService.getOneFromDatabase(mediaServerId);
        return R.ok(mediaServerService.startSendRtp(mediaServer, param));
    }

    /**
     * 停止发送RTP流
     *
     * @param mediaServerId
     * @param param
     * @return
     */
    @PostMapping("/stopSendRtp/{mediaServerId}")
    R<?> stopSendRtp(@PathVariable String mediaServerId, @RequestBody Map<String, Object> param) {
        ZlmMediaServer mediaServer = mediaServerService.getOneFromDatabase(mediaServerId);
        return R.ok(mediaServerService.stopSendRtp(mediaServer, param));
    }

    /**
     * 获取默认的媒体服务器
     *
     * @return
     */
    @GetMapping("/getDefaultMediaServer")
    R<ZlmMediaServer> getDefaultMediaServer() {
        ZlmMediaServer mediaServer = mediaServerService.getDefaultMediaServer();
        return R.ok(mediaServer);
    }

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    @GetMapping("/getOneFromDatabase/{id}")
    R<ZlmMediaServer> getOneFromDatabase(@PathVariable String id) {
        ZlmMediaServer mediaServer = mediaServerService.getOneFromDatabase(id);
        return R.ok(mediaServer);
    }

    /**
     * 处理上级平台点播
     *
     * @param platformPlay
     * @return
     */
    @PostMapping("/gb28181PlatformPlay")
    R<Void> gb28181PlatformPlay(@RequestBody Gb28181PlatformPlay platformPlay) {
        log.info("[处理上级平台点播] 设备: {}, 目标地址: {}:{}, SSRC: {}", 
                platformPlay.getQsDevice().getDeviceName(), 
                platformPlay.getDstUrl(), 
                platformPlay.getDstPort(), 
                platformPlay.getSsrc());

        try {
            final ZlmMediaServer zlmMediaServer = mediaServerService.getDefaultMediaServer();
            if (zlmMediaServer == null) {
                log.error("[未找到默认的媒体服务器]");
                return R.fail("未找到默认的媒体服务器");
            }
            
            final String ssrc = platformPlay.getSsrc();
            final String dstUrl = platformPlay.getDstUrl();
            final int dstPort = platformPlay.getDstPort();

            devicePlayService.play(platformPlay.getQsDevice(), false, new ErrorCallback<StreamInfo>() {
                @Override
                public void run(int code, String msg, StreamInfo streamInfo) {
                    log.info("[设备播放回调] code: {}, msg: {}, streamInfo: {}", code, msg, streamInfo);
                    
                    if (code == 0 && streamInfo != null) {
                        try {
                            Map<String, Object> params = new HashMap<>();
                            params.put("secret", zlmMediaServer.getSecret());
                            params.put("vhost", "__defaultVhost__");
                            params.put("app", streamInfo.getApp());
                            params.put("stream", streamInfo.getStream());
                            params.put("ssrc", ssrc);
                            params.put("dst_url", dstUrl);
                            params.put("dst_port", dstPort);
                            params.put("is_udp", platformPlay.getIsUdp() != null ? platformPlay.getIsUdp() : 1);
                            params.put("use_ps", 1);
                            params.put("pt", 96);
                            params.put("rtcp", platformPlay.getRtcp() != null ? platformPlay.getRtcp() : 0);
                            
                            log.info("[准备调用startSendRtp] params: {}", params);
                            R<?> startSendRtpResult = startSendRtp(zlmMediaServer.getId(), params);
                            log.info("[调用startSendRtp结果] result: {}", startSendRtpResult.getData());
                        } catch (Exception e) {
                            log.error("[调用startSendRtp失败] error: ", e);
                        }
                    } else {
                        log.error("[设备播放失败] code: {}, msg: {}", code, msg);
                    }
                }
            });
        } catch (Exception e) {
            log.error("[处理上级平台点播失败] error: ", e);
            return R.fail("处理失败: " + e.getMessage());
        }

        return R.ok();
    }

    /**
     * 处理上级平台回放
     *
     * @param platformPlayback
     * @return
     */
    @PostMapping("/gb28181PlatformPlayback")
    R<Void> gb28181PlatformPlayback(@RequestBody Gb28181PlatformPlayback platformPlayback) {
        log.info("[处理上级平台回放] 设备: {}, 设备类型: {}, 开始时间: {}, 结束时间: {}, 目标地址: {}:{}, SSRC: {}", 
                platformPlayback.getQsDevice().getDeviceName(), 
                platformPlayback.getQsDevice().getType(),
                platformPlayback.getStartTime(), 
                platformPlayback.getEndTime(),
                platformPlayback.getDstUrl(), 
                platformPlayback.getDstPort(), 
                platformPlayback.getSsrc());

        try {
            final ZlmMediaServer zlmMediaServer = mediaServerService.getDefaultMediaServer();
            if (zlmMediaServer == null) {
                log.error("[未找到默认的媒体服务器]");
                return R.fail("未找到默认的媒体服务器");
            }
            
            final String ssrc = platformPlayback.getSsrc();
            final String dstUrl = platformPlayback.getDstUrl();
            final int dstPort = platformPlayback.getDstPort();
            final QsDevice qsDevice = platformPlayback.getQsDevice();
            final Long startTime = platformPlayback.getStartTime();
            final Long endTime = platformPlayback.getEndTime();

            // 根据设备类型判断是云端录像回放还是设备录像回放
            String deviceType = qsDevice.getType();
            
            // 设备类型 1-4,6,13 是云端录像
            if (LiveStreamType.RTSP.getCode().equals(deviceType) || 
                LiveStreamType.RTMP.getCode().equals(deviceType) || 
                LiveStreamType.FLV.getCode().equals(deviceType) || 
                LiveStreamType.HLS.getCode().equals(deviceType) || 
                LiveStreamType.VIDEO_FILE.getCode().equals(deviceType) || 
                LiveStreamType.PUSH.getCode().equals(deviceType)) {
                
                log.info("[云端录像回放] 查询云端录像，设备类型: {}", deviceType);
                
                // 构建查询条件
                ZlmCloudRecord queryRecord = new ZlmCloudRecord();
                
                // 根据设备类型设置对应的 app
                String app = "";
                if (LiveStreamType.RTSP.getCode().equals(deviceType)) {
                    app = "rtsp";
                } else if (LiveStreamType.RTMP.getCode().equals(deviceType)) {
                    app = "rtmp";
                } else if (LiveStreamType.FLV.getCode().equals(deviceType)) {
                    app = "flv";
                } else if (LiveStreamType.HLS.getCode().equals(deviceType)) {
                    app = "hls";
                } else if (LiveStreamType.PUSH.getCode().equals(deviceType)) {
                    app = "push";
                } else if (LiveStreamType.VIDEO_FILE.getCode().equals(deviceType)) {
                    app = "video_file";
                }
                queryRecord.setApp(app);
                
                // 使用 deviceCode 作为 stream 查询条件
                String stream = qsDevice.getDeviceCode();
                queryRecord.setStream(stream);
                queryRecord.setApp(app);

                log.info("[云端录像查询条件] app: {}, stream: {}", app, stream);
                
                // 先不限制时间范围，查询所有相关录像
                R<List<ZlmCloudRecord>> queryResult = remoteZlmCloudRecordService.selectZlmCloudRecordList(queryRecord, SecurityConstants.INNER);
                
                if (queryResult.getCode() != Constants.SUCCESS) {
                    log.error("[查询云端录像失败] code: {}, msg: {}", queryResult.getCode(), queryResult.getMsg());
                    return R.fail("查询云端录像失败: " + queryResult.getMsg());
                }
                
                List<ZlmCloudRecord> cloudRecordList = queryResult.getData();
                
                log.info("[云端录像查询结果] 数量: {}", cloudRecordList != null ? cloudRecordList.size() : 0);
                
                if (cloudRecordList == null || cloudRecordList.isEmpty()) {
                    log.error("[未找到匹配的云端录像] 查询条件: app={}, stream={}", app, stream);
                    return R.fail("未找到匹配的云端录像");
                }
                
                // 如果有时间范围，再过滤一下，找到最合适的
                ZlmCloudRecord cloudRecord = cloudRecordList.get(0); // 默认取最新的
                if (startTime != null || endTime != null) {
                    ZlmCloudRecord bestMatch = null;
                    long bestScore = Long.MIN_VALUE;
                    
                    for (ZlmCloudRecord record : cloudRecordList) {
                        boolean match = true;
                        long score = 0;
                        
                        // 检查是否匹配时间范围
                        if (startTime != null && record.getEndTime() != null && record.getEndTime() < startTime) {
                            match = false;
                        }
                        if (endTime != null && record.getStartTime() != null && record.getStartTime() > endTime) {
                            match = false;
                        }
                        
                        if (match) {
                            // 计算匹配分数，优先选择完全包含在时间范围内的
                            if (startTime != null && endTime != null && 
                                record.getStartTime() != null && record.getEndTime() != null) {
                                // 录像完全在查询时间范围内
                                if (record.getStartTime() >= startTime && record.getEndTime() <= endTime) {
                                    score = 100;
                                }
                                // 录像部分重叠
                                else {
                                    score = 50;
                                }
                            }
                            
                            // 选择分数最高的
                            if (score > bestScore) {
                                bestScore = score;
                                bestMatch = record;
                            }
                        }
                    }
                    
                    if (bestMatch != null) {
                        cloudRecord = bestMatch;
                    }
                }
                log.info("[找到云端录像] id: {}, fileName: {}, app: {}, stream: {}, startTime: {}, endTime: {}", 
                        cloudRecord.getId(), cloudRecord.getFileName(), 
                        cloudRecord.getApp(), cloudRecord.getStream(), 
                        cloudRecord.getStartTime(), cloudRecord.getEndTime());
                
                // 播放云端录像
                mediaServerService.loadCloudRecord(
                        zlmMediaServer, 
                        cloudRecord.getApp(), 
                        platformPlayback.getStreamId(), 
                        qsDevice.getId(), 
                        cloudRecord.getFilePath(), 
                        true, 
                        new ErrorCallback<StreamInfo>() {
                    @Override
                    public void run(int code, String msg, StreamInfo streamInfo) {
                        log.info("[云端录像播放回调] code: {}, msg: {}, streamInfo: {}", code, msg, streamInfo);
                        
                        if (code == 0 && streamInfo != null) {
                            try {
                                Map<String, Object> params = new HashMap<>();
                                params.put("secret", zlmMediaServer.getSecret());
                                params.put("vhost", "__defaultVhost__");
                                params.put("app", streamInfo.getApp());
                                params.put("stream", streamInfo.getStream());
                                params.put("ssrc", ssrc);
                                params.put("dst_url", dstUrl);
                                params.put("dst_port", dstPort);
                                params.put("is_udp", platformPlayback.getIsUdp() != null ? platformPlayback.getIsUdp() : 1);
                                params.put("use_ps", 1);
                                params.put("pt", 96);
                                params.put("rtcp", platformPlayback.getRtcp() != null ? platformPlayback.getRtcp() : 0);
                                
                                log.info("[准备调用startSendRtp] params: {}", params);
                                R<?> startSendRtpResult = startSendRtp(zlmMediaServer.getId(), params);
                                log.info("[调用startSendRtp结果] result: {}", startSendRtpResult.getData());
                            } catch (Exception e) {
                                log.error("[调用startSendRtp失败] error: ", e);
                            }
                        } else {
                            log.error("[云端录像播放失败] code: {}, msg: {}", code, msg);
                        }
                    }
                });
                
            } else {
                // 设备类型 5-12,14 是设备录像，调用设备回放
                log.info("[设备录像回放] 设备类型: {}, startTime: {}, endTime: {}", deviceType, platformPlayback.getStartTime(), platformPlayback.getEndTime());
                
                String startTimeStr = null;
                String endTimeStr = null;
                
                // 设置回放时间（需要将时间戳转换为字符串格式）
                if (platformPlayback.getStartTime() != null) {
                    startTimeStr = DateUtil.timestampMsTo_yyyy_MM_dd_HH_mm_ss(platformPlayback.getStartTime());
                }
                if (platformPlayback.getEndTime() != null) {
                    endTimeStr = DateUtil.timestampMsTo_yyyy_MM_dd_HH_mm_ss(platformPlayback.getEndTime());
                }
                
                // 根据设备类型调用不同的回放方法
                if (LiveStreamType.ONVIF.getCode().equals(deviceType)) {
                    
                    // ONVIF设备回放
                    log.info("[ONVIF设备回放] 设备ID: {}, startTime: {}, endTime: {}", qsDevice.getId(), startTimeStr, endTimeStr);
                    
                    // 1. 查询录像文件获取recordingToken和trackToken
                    R<Object> queryResult = remoteOnvifService.queryRecord(
                            qsDevice.getIpAddress(),
                            qsDevice.getUserName(),
                            qsDevice.getPassword(),
                            startTimeStr,
                            endTimeStr,
                            SecurityConstants.INNER
                    );
                    
                    if (queryResult.getCode() != Constants.SUCCESS) {
                        log.error("[ONVIF查询录像失败]");
                        return R.fail("ONVIF查询录像失败");
                    }
                    
                    // 2. 从查询结果中提取recordingToken和trackToken
                    String recordingToken = null;
                    String trackToken = null;
                    String selectedReplayUri = null;
                    
                    if (queryResult.getData() != null) {
                        log.info("[ONVIF查询录像结果] {}", queryResult.getData());
                        
                        // 解析查询结果
                        java.util.ArrayList<java.util.HashMap<String, Object>> recordList = null;
                        if (queryResult.getData() instanceof java.util.ArrayList) {
                            recordList = (java.util.ArrayList<java.util.HashMap<String, Object>>) queryResult.getData();
                        }
                        
                        if (recordList != null && !recordList.isEmpty()) {
                            // 获取第一条记录
                            java.util.HashMap<String, Object> record = recordList.get(0);
                            recordingToken = (String) record.get("recordingToken");
                            trackToken = (String) record.get("trackToken");
                            selectedReplayUri = (String) record.get("replayUri");
                            
                            log.info("[ONVIF设备回放] 选择的录像 - recordingToken: {}, trackToken: {}, replayUri: {}", 
                                    recordingToken, trackToken, selectedReplayUri);
                        }
                    }
                    
                    if (StringUtils.isEmpty(recordingToken) || StringUtils.isEmpty(trackToken)) {
                        log.error("[ONVIF设备回放] 未找到recordingToken或trackToken");
                        return R.fail("未找到ONVIF录像文件");
                    }
                    
                    // 3. 获取ONVIF回放地址
                    R<String> replayUriResult = remoteOnvifService.getReplayUri(
                            qsDevice.getIpAddress(),
                            qsDevice.getUserName(),
                            qsDevice.getPassword(),
                            recordingToken,
                            trackToken,
                            SecurityConstants.INNER
                    );
                    
                    if (replayUriResult.getCode() != Constants.SUCCESS || StringUtils.isEmpty(replayUriResult.getData())) {
                        log.error("[ONVIF获取回放地址失败]");
                        return R.fail("ONVIF获取回放地址失败");
                    }
                    
                    // 4. 构造拉流播放参数
                    StreamPullPlay streamPullPlay = new StreamPullPlay();
                    streamPullPlay.setDeviceId(qsDevice.getId());
                    streamPullPlay.setApp("onvif");
                    streamPullPlay.setStream(qsDevice.getDeviceCode());
                    streamPullPlay.setUrl(replayUriResult.getData());
                    streamPullPlay.setEnable_audio("1".equals(qsDevice.getEnableAudio()));
                    streamPullPlay.setEnable_mp4(false);
                    streamPullPlay.setRtp_type(qsDevice.getProtocol() != null ? qsDevice.getProtocol() : "0");
                    streamPullPlay.setTimeOut(15000);
                    streamPullPlay.setPlayback(true);
                    
                    // 调用拉流播放
                    mediaServerService.streamPullPlay(streamPullPlay, new ErrorCallback<StreamInfo>() {
                        @Override
                        public void run(int code, String msg, StreamInfo streamInfo) {
                            log.info("[ONVIF设备回放回调] code: {}, msg: {}, streamInfo: {}", code, msg, streamInfo);
                            
                            if (code == 0 && streamInfo != null) {
                                try {
                                    Map<String, Object> params = new HashMap<>();
                                    params.put("secret", zlmMediaServer.getSecret());
                                    params.put("vhost", "__defaultVhost__");
                                    params.put("app", streamInfo.getApp());
                                    params.put("stream", streamInfo.getStream());
                                    params.put("ssrc", ssrc);
                                    params.put("dst_url", dstUrl);
                                    params.put("dst_port", dstPort);
                                    params.put("is_udp", platformPlayback.getIsUdp() != null ? platformPlayback.getIsUdp() : 1);
                                    params.put("use_ps", 1);
                                    params.put("pt", 96);
                                    params.put("rtcp", platformPlayback.getRtcp() != null ? platformPlayback.getRtcp() : 0);
                                    
                                    log.info("[准备调用startSendRtp] params: {}", params);
                                    R<?> startSendRtpResult = startSendRtp(zlmMediaServer.getId(), params);
                                    log.info("[调用startSendRtp结果] result: {}", startSendRtpResult.getData());
                                } catch (Exception e) {
                                    log.error("[调用startSendRtp失败] error: ", e);
                                }
                            } else {
                                log.error("[ONVIF设备回放失败] code: {}, msg: {}", code, msg);
                            }
                        }
                    });
                    
                } else if (LiveStreamType.HIK_SDK.getCode().equals(deviceType) || 
                    LiveStreamType.HIK_ISUP.getCode().equals(deviceType) || 
                    LiveStreamType.DAHUA_SDK.getCode().equals(deviceType) ||
                    LiveStreamType.UNIVIEW_SDK.getCode().equals(deviceType) ||
                    LiveStreamType.TIANDI_SDK.getCode().equals(deviceType)) {
                    
                    // 海康SDK/海康ISUP/大华SDK/宇视SDK/天地伟业SDK - 使用rtpPlay
                    RTPServerParam rtpServerParam = new RTPServerParam();
                    if (LiveStreamType.HIK_SDK.getCode().equals(deviceType)) {
                        rtpServerParam.setApp("haikang");
                    } else if (LiveStreamType.HIK_ISUP.getCode().equals(deviceType)) {
                        rtpServerParam.setApp("haikang_isup");
                    } else if (LiveStreamType.DAHUA_SDK.getCode().equals(deviceType)) {
                        rtpServerParam.setApp("dahua");
                    } else if (LiveStreamType.UNIVIEW_SDK.getCode().equals(deviceType)) {
                        rtpServerParam.setApp("uniview");
                    } else if (LiveStreamType.TIANDI_SDK.getCode().equals(deviceType)) {
                        rtpServerParam.setApp("tiandi");
                    }
                    rtpServerParam.setStreamId(qsDevice.getDeviceCode() + "_" + System.currentTimeMillis());
                    rtpServerParam.setType(deviceType);
                    rtpServerParam.setId(qsDevice.getId());
                    rtpServerParam.setPlayback(true);
                    rtpServerParam.setStartTime(startTimeStr);
                    rtpServerParam.setEndTime(endTimeStr);
                    
                    log.info("[设备SDK回放参数] {}", rtpServerParam);
                    
                    // 调用设备SDK回放
                    mediaServerService.rtpPlay(rtpServerParam, new ErrorCallback<StreamInfo>() {
                        @Override
                        public void run(int code, String msg, StreamInfo streamInfo) {
                            log.info("[设备SDK回放回调] code: {}, msg: {}, streamInfo: {}", code, msg, streamInfo);
                            
                            if (code == 0 && streamInfo != null) {
                                try {
                                    Map<String, Object> params = new HashMap<>();
                                    params.put("secret", zlmMediaServer.getSecret());
                                    params.put("vhost", "__defaultVhost__");
                                    params.put("app", streamInfo.getApp());
                                    params.put("stream", streamInfo.getStream());
                                    params.put("ssrc", ssrc);
                                    params.put("dst_url", dstUrl);
                                    params.put("dst_port", dstPort);
                                    params.put("is_udp", platformPlayback.getIsUdp() != null ? platformPlayback.getIsUdp() : 1);
                                    params.put("use_ps", 1);
                                    params.put("pt", 96);
                                    params.put("rtcp", platformPlayback.getRtcp() != null ? platformPlayback.getRtcp() : 0);
                                    
                                    log.info("[准备调用startSendRtp] params: {}", params);
                                    R<?> startSendRtpResult = startSendRtp(zlmMediaServer.getId(), params);
                                    log.info("[调用startSendRtp结果] result: {}", startSendRtpResult.getData());
                                } catch (Exception e) {
                                    log.error("[调用startSendRtp失败] error: ", e);
                                }
                            } else {
                                log.error("[设备SDK回放失败] code: {}, msg: {}", code, msg);
                            }
                        }
                    });
                    
                } else if (LiveStreamType.GB28181.getCode().equals(deviceType)) {
                    
                    // GB28181设备回放
                    log.info("[GB28181设备回放] 设备ID: {}, startTime: {}, endTime: {}", qsDevice.getId(), startTimeStr, endTimeStr);
                    
                    R<Device> deviceR = remoteGb28181Service.getDeviceByDeviceId(qsDevice.getGbDeviceId(), SecurityConstants.INNER);
                    if (deviceR.getCode() != Constants.SUCCESS) {
                        log.error("[GB28181获取设备信息失败] id:{}", qsDevice.getGbDeviceId());
                        return R.fail("GB28181获取设备信息失败");
                    }
                    
                    qsDevice.setStreamMode(deviceR.getData().getStreamMode());
                    
                    // 调用GB28181回放
                    mediaServerService.startGb28181Playback(qsDevice, deviceR.getData(), startTimeStr, endTimeStr, new ErrorCallback<StreamInfo>() {
                        @Override
                        public void run(int code, String msg, StreamInfo streamInfo) {
                            log.info("[GB28181设备回放回调] code: {}, msg: {}, streamInfo: {}", code, msg, streamInfo);
                            
                            if (code == 0 && streamInfo != null) {
                                try {
                                    Map<String, Object> params = new HashMap<>();
                                    params.put("secret", zlmMediaServer.getSecret());
                                    params.put("vhost", "__defaultVhost__");
                                    params.put("app", streamInfo.getApp());
                                    params.put("stream", streamInfo.getStream());
                                    params.put("ssrc", ssrc);
                                    params.put("dst_url", dstUrl);
                                    params.put("dst_port", dstPort);
                                    params.put("is_udp", platformPlayback.getIsUdp() != null ? platformPlayback.getIsUdp() : 1);
                                    params.put("use_ps", 1);
                                    params.put("pt", 96);
                                    params.put("rtcp", platformPlayback.getRtcp() != null ? platformPlayback.getRtcp() : 0);
                                    
                                    log.info("[准备调用startSendRtp] params: {}", params);
                                    R<?> startSendRtpResult = startSendRtp(zlmMediaServer.getId(), params);
                                    log.info("[调用startSendRtp结果] result: {}", startSendRtpResult.getData());
                                } catch (Exception e) {
                                    log.error("[调用startSendRtp失败] error: ", e);
                                }
                            } else {
                                log.error("[GB28181设备回放失败] code: {}, msg: {}", code, msg);
                            }
                        }
                    });
                    
                } else if (LiveStreamType.JT1078.getCode().equals(deviceType)) {

                    // JT1078设备回放
                    log.info("[JT1078设备回放] 设备ID: {}, startTime: {}, endTime: {}", qsDevice.getId(), startTimeStr, endTimeStr);

                    // 检查JT1078设备手机号
                    if (StringUtils.isEmpty(qsDevice.getJtMobileNo())) {
                        log.error("[JT1078设备回放] 设备手机号为空，设备ID:{}", qsDevice.getId());
                        return R.fail("JT1078设备手机号为空");
                    }

                    // 获取JT1078设备信息
                    R<com.ruoyi.jt1078.api.domain.Jt1078Device> deviceR = remoteJt1078Service.getDeviceByMobileNo(qsDevice.getJtMobileNo(), SecurityConstants.INNER);
                    if (deviceR.getCode() != Constants.SUCCESS) {
                        log.error("[JT1078获取设备信息失败] mobileNo:{}", qsDevice.getJtMobileNo());
                        return R.fail("JT1078获取设备信息失败");
                    }

                    // 调用JT1078回放
                    mediaServerService.startJt1078Playback(qsDevice, deviceR.getData(), startTimeStr, endTimeStr, new ErrorCallback<StreamInfo>() {
                        @Override
                        public void run(int code, String msg, StreamInfo streamInfo) {
                            log.info("[JT1078设备回放回调] code: {}, msg: {}, streamInfo: {}", code, msg, streamInfo);
                            
                            if (code == 0 && streamInfo != null) {
                                try {
                                    Map<String, Object> params = new HashMap<>();
                                    params.put("secret", zlmMediaServer.getSecret());
                                    params.put("vhost", "__defaultVhost__");
                                    params.put("app", streamInfo.getApp());
                                    params.put("stream", streamInfo.getStream());
                                    params.put("ssrc", ssrc);
                                    params.put("dst_url", dstUrl);
                                    params.put("dst_port", dstPort);
                                    params.put("is_udp", platformPlayback.getIsUdp() != null ? platformPlayback.getIsUdp() : 1);
                                    params.put("use_ps", 1);
                                    params.put("pt", 96);
                                    params.put("rtcp", platformPlayback.getRtcp() != null ? platformPlayback.getRtcp() : 0);
                                    
                                    log.info("[准备调用startSendRtp] params: {}", params);
                                    R<?> startSendRtpResult = startSendRtp(zlmMediaServer.getId(), params);
                                    log.info("[调用startSendRtp结果] result: {}", startSendRtpResult.getData());
                                } catch (Exception e) {
                                    log.error("[调用startSendRtp失败] error: ", e);
                                }
                            } else {
                                log.error("[JT1078设备回放失败] code: {}, msg: {}", code, msg);
                            }
                        }
                    });

                } else {
                    
                    // 其他设备类型，暂时不支持
                    log.error("[设备回放] 不支持的设备类型: {}", deviceType);
                    return R.fail("不支持的设备类型回放: " + deviceType);
                    
                }
            }
        } catch (Exception e) {
            log.error("[处理上级平台回放失败] error: ", e);
            return R.fail("处理失败: " + e.getMessage());
        }

        return R.ok();
    }

    /**
     * 停止上级平台回放
     *
     * @param deviceId 设备ID
     * @param deviceType 设备类型
     * @param stream 流名称
     * @return
     */
    @GetMapping("/stopPlayback")
    public R<Void> stopPlayback(@RequestParam Long deviceId, @RequestParam String deviceType, @RequestParam String stream) {
        try {
            log.info("[停止上级平台回放] deviceId: {}, deviceType: {}, stream: {}", deviceId, deviceType, stream);

            R<QsDevice> qsDeviceResult = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (qsDeviceResult.getCode() != Constants.SUCCESS || qsDeviceResult.getData() == null) {
                log.warn("[停止上级平台回放] 获取设备信息失败, deviceId: {}", deviceId);
                return R.fail("获取设备信息失败");
            }

            QsDevice qsDevice = qsDeviceResult.getData();

            if (LiveStreamType.GB28181.getCode().equals(deviceType)) {
                log.info("[停止GB28181设备回放] deviceId: {}, gbDeviceId: {}, gbChannelId: {}", 
                        deviceId, qsDevice.getGbDeviceId(), qsDevice.getGbChannelId());
                
                R<Device> deviceResult = remoteGb28181Service.getDeviceByDeviceId(qsDevice.getGbDeviceId(), SecurityConstants.INNER);
                if (deviceResult.getCode() != Constants.SUCCESS || deviceResult.getData() == null) {
                    log.warn("[停止GB28181设备回放] 获取GB28181设备信息失败");
                    return R.fail("获取GB28181设备信息失败");
                }

                Device gbDevice = deviceResult.getData();
                mediaServerService.stopGb28181Play(InviteSessionType.PLAYBACK, qsDevice, gbDevice, stream);
                
            } else if (LiveStreamType.ONVIF.getCode().equals(deviceType)) {
                
                log.info("[停止ONVIF设备回放] deviceId: {}, deviceType: {}, stream: {}", deviceId, deviceType, stream);
                
                String mediaServerId = qsDevice.getPlaybackMediaServerId();
                if (mediaServerId == null) {
                    log.warn("[停止ONVIF设备回放] 未找到回放媒体服务器ID");
                    return R.ok();
                }

                ZlmMediaServer mediaServer = mediaServerService.getOneFromDatabase(mediaServerId);
                if (mediaServer == null) {
                    log.warn("[停止ONVIF设备回放] 获取媒体服务器信息失败");
                    return R.fail("获取媒体服务器信息失败");
                }

                com.ruoyi.zlm.api.domain.StreamPullPlay streamPullPlay = new com.ruoyi.zlm.api.domain.StreamPullPlay();
                streamPullPlay.setDeviceId(deviceId);
                streamPullPlay.setStreamKey(stream);
                streamPullPlay.setMediaServerId(mediaServerId);
                streamPullPlay.setPlayback(true);

                mediaServerService.stopStreamPullPlay(streamPullPlay);
                
            } else if (LiveStreamType.RTSP.getCode().equals(deviceType) || 
                LiveStreamType.RTMP.getCode().equals(deviceType) || 
                LiveStreamType.FLV.getCode().equals(deviceType) || 
                LiveStreamType.HLS.getCode().equals(deviceType) || 
                LiveStreamType.VIDEO_FILE.getCode().equals(deviceType) || 
                LiveStreamType.PUSH.getCode().equals(deviceType)) {
                
                log.info("[停止云端录像回放] deviceId: {}, deviceType: {}, stream: {}", deviceId, deviceType, stream);
                mediaServerService.closeStreams(deviceId);
                
            } else if (LiveStreamType.HIK_SDK.getCode().equals(deviceType) || 
                    LiveStreamType.HIK_ISUP.getCode().equals(deviceType) || 
                    LiveStreamType.DAHUA_SDK.getCode().equals(deviceType) || 
                    LiveStreamType.UNIVIEW_SDK.getCode().equals(deviceType) || 
                    LiveStreamType.TIANDI_SDK.getCode().equals(deviceType)) {
                
                log.info("[停止SDK设备回放] deviceId: {}, deviceType: {}", deviceId, deviceType);
                
                com.ruoyi.zlm.api.domain.RTPServerParam rtpServerParam = new com.ruoyi.zlm.api.domain.RTPServerParam();
                rtpServerParam.setId(deviceId);
                rtpServerParam.setType(deviceType);
                rtpServerParam.setPlayback(true);
                rtpServerParam.setStreamId(stream);

                mediaServerService.stopRtpPlay(rtpServerParam);
                
            } else if (LiveStreamType.JT1078.getCode().equals(deviceType)) {

                log.info("[停止JT1078设备回放] deviceId: {}, jtMobileNo: {}", deviceId, qsDevice.getJtMobileNo());

                // 获取JT1078设备信息
                R<com.ruoyi.jt1078.api.domain.Jt1078Device> deviceR = remoteJt1078Service.getDeviceByMobileNo(qsDevice.getJtMobileNo(), SecurityConstants.INNER);
                if (deviceR.getCode() != Constants.SUCCESS || deviceR.getData() == null) {
                    log.warn("[停止JT1078设备回放] 获取JT1078设备信息失败");
                    return R.fail("获取JT1078设备信息失败");
                }

                mediaServerService.stopJt1078Play(InviteSessionType.PLAYBACK, qsDevice, deviceR.getData(), stream);

            } else {
                log.info("[停止上级平台回放] 不支持的设备类型: {}", deviceType);
            }

            return R.ok();
        } catch (Exception e) {
            log.error("[停止上级平台回放异常] error: ", e);
            return R.fail("停止回放失败: " + e.getMessage());
        }
    }
}
