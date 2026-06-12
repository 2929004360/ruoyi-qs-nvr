package com.ruoyi.zlm.service.impl;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.gb28181.api.RemoteGb28181Service;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.jt1078.api.RemoteJt1078Service;
import com.ruoyi.jt1078.api.domain.Jt1078Device;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.RTPServerParam;
import com.ruoyi.zlm.api.domain.StreamPullPlay;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.api.utils.MediaServerUtils;
import com.ruoyi.zlm.common.InviteSessionType;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.domain.StreamAuthorityInfo;
import com.ruoyi.zlm.hook.ResultForOnPublish;
import com.ruoyi.zlm.service.IInviteStreamService;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.service.IMediaService;
import com.ruoyi.zlm.service.IRedisCatchStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Map;

@Slf4j
@Service
public class MediaServiceImpl implements IMediaService {

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private IMediaServerService mediaServerService;

    @Autowired
    private IInviteStreamService inviteStreamService;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private IRedisCatchStorage redisCatchStorage;

    @Autowired
    private RemoteGb28181Service remoteGb28181Service;

    @Autowired
    private RemoteJt1078Service remoteJt1078Service;

    @Override
    public boolean closeStreamOnNoneReader(String mediaServerId, String app, String stream, String schema) {

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceStream(stream, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            return false;
        }

        QsDevice data = r.getData();
        if (data == null) {
            return false;
        }

        // 判断是否是回放流
        boolean isPlayback = stream.equals(data.getPlaybackStreamKey());

        // 拉流代理 (rtsp/rtmp/flv/hls/onvif)
        if ("rtsp".equals(app) || "rtmp".equals(app) || "flv".equals(app) || "hls".equals(app) || "onvif".equals(app)) {
            if (isPlayback) {
                // 回放流一定要关闭
                StreamPullPlay streamPullPlay = new StreamPullPlay();
                streamPullPlay.setDeviceId(data.getId());
                streamPullPlay.setStreamKey(data.getPlaybackStreamKey());
                streamPullPlay.setMediaServerId(data.getPlaybackMediaServerId());
                streamPullPlay.setPlayback(true);
                mediaServerService.stopStreamPullPlay(streamPullPlay);
                return true;
            } else if ("1".equals(data.getEnableDisableNoneReader())) {
                // 非回放流且配置了无人观看停用
                StreamPullPlay streamPullPlay = new StreamPullPlay();
                streamPullPlay.setDeviceId(data.getId());
                streamPullPlay.setStreamKey(data.getStreamKey());
                streamPullPlay.setMediaServerId(data.getMediaServerId());
                streamPullPlay.setPlayback(false);
                mediaServerService.stopStreamPullPlay(streamPullPlay);
                return true;
            } else {
                return false;
            }
        } else if ("haikang".equals(app) || "haikang_isup".equals(app) || "dahua".equals(app)) {
            if (isPlayback) {
                // 回放流一定要关闭
                RTPServerParam rtpServerParam = new RTPServerParam();
                rtpServerParam.setId(data.getId());
                rtpServerParam.setType(data.getType());
                rtpServerParam.setStreamId(stream);
                rtpServerParam.setPlayback(true);
                mediaServerService.stopRtpPlay(rtpServerParam);
                return true;
            } else if ("1".equals(data.getEnableDisableNoneReader())) {
                // 非回放流且配置了无人观看停用
                RTPServerParam rtpServerParam = new RTPServerParam();
                rtpServerParam.setId(data.getId());
                rtpServerParam.setType(data.getType());
                rtpServerParam.setStreamId(stream);
                rtpServerParam.setPlayback(false);
                mediaServerService.stopRtpPlay(rtpServerParam);
                return true;
            } else {
                return false;
            }
        } else if ("push".equals(app)) {
            if (isPlayback || "1".equals(data.getEnableDisableNoneReader())) {
                return true;
            } else {
                return false;
            }
        } else if ("gb28181".equals(app)) {
            if (isPlayback || "1".equals(data.getEnableDisableNoneReader())) {
                R<Device> deviceR = remoteGb28181Service.getDeviceByDeviceId(data.getGbDeviceId(), SecurityConstants.INNER);
                if (deviceR.getCode() == Constants.SUCCESS && deviceR.getData() != null) {
                    InviteSessionType type = isPlayback ? InviteSessionType.PLAYBACK : InviteSessionType.PLAY;
                    mediaServerService.stopGb28181Play(type, data, deviceR.getData(), data.getDeviceCode());
                }
                return true;
            } else {
                return false;
            }
        } else if ("jt1078".equals(app)) {
            if (isPlayback || "1".equals(data.getEnableDisableNoneReader())) {
                R<Jt1078Device> deviceR = remoteJt1078Service.getDeviceByMobileNo(data.getJtMobileNo(), SecurityConstants.INNER);
                if (deviceR.getCode() == Constants.SUCCESS && deviceR.getData() != null) {
                    InviteSessionType type = isPlayback ? InviteSessionType.PLAYBACK : InviteSessionType.PLAY;
                    mediaServerService.stopJt1078Play(type, data, deviceR.getData(), data.getDeviceCode());
                }
                return true;
            } else {
                return false;
            }
        }
        return isPlayback;
    }

    @Override
    public ResultForOnPublish authenticatePublish(ZlmMediaServer mediaServer, String app, String stream, String params) {
        ResultForOnPublish result = new ResultForOnPublish();
        result.setEnable_audio(true);
        // 默认不允许录制
        result.setEnable_mp4(false);
        // 默认禁用无人观看事件（只有回放流或配置了自动关流才启用）
        result.setEnable_disable_none_reader(true);

        // 推流 app 单独处理鉴权，但也要处理录制和无人观看配置
        if ("push".equals(app)) {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceStream(stream, SecurityConstants.INNER);
            if (r.getCode() == Constants.SUCCESS && r.getData() != null) {
                boolean isPlayback = stream.equals(r.getData().getPlaybackStreamKey());
                if (!isPlayback && "1".equals(r.getData().getEnableMp4())) {
                    result.setEnable_mp4(true);
                }
                if (isPlayback || "1".equals(r.getData().getEnableDisableNoneReader())) {
                    result.setEnable_disable_none_reader(false);
                }
            }
            // 推流鉴权
            if (userSetting.getPushAuthority()) {
                Map<String, String> paramMap = MediaServerUtils.urlParamToMap(params);
                if (params == null) {
                    log.info("推流鉴权失败： 缺少必要参数：sign=md5(user表的pushKey)");
                    throw new RuntimeException("Unauthorized");
                }
                String sign = paramMap.get("sign");
                if (sign == null) {
                    log.info("推流鉴权失败： 缺少必要参数：sign=md5");
                    throw new RuntimeException("Unauthorized");
                }
                sign = sign.replaceAll("/$", "");
                String callId = paramMap.get("callId");
                String checkStr = callId == null ? userSetting.getPushKey() : (callId + "_" + userSetting.getPushKey());
                String checkSign = DigestUtils.md5DigestAsHex(checkStr.getBytes());
                if (!checkSign.equals(sign)) {
                    log.info("推流鉴权失败： sign 无权限: callId={}. sign={}", callId, sign);
                    throw new RuntimeException("推流鉴权失败： sign 无权限: callId=" + callId + ". sign=" + sign);
                }
                StreamAuthorityInfo streamAuthorityInfo = StreamAuthorityInfo.getInstanceByHook(app, stream, mediaServer.getId());
                streamAuthorityInfo.setCallId(callId);
                streamAuthorityInfo.setSign(sign);
                redisCatchStorage.updateStreamAuthorityInfo(app, stream, streamAuthorityInfo);
            }
            return result;
        }

        // 拉流代理 (rtsp/rtmp/flv/hls/onvif)
        if ("rtsp".equals(app) || "rtmp".equals(app) || "flv".equals(app) || "hls".equals(app) || "onvif".equals(app)) {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceStream(stream, SecurityConstants.INNER);
            if (r.getCode() == Constants.SUCCESS && r.getData() != null) {
                boolean isPlayback = stream.equals(r.getData().getPlaybackStreamKey());
                if (!isPlayback && "1".equals(r.getData().getEnableMp4())) {
                    result.setEnable_mp4(true);
                }
                if (isPlayback || "1".equals(r.getData().getEnableDisableNoneReader())) {
                    result.setEnable_disable_none_reader(false);
                }
            }
            return result;
        }

        // 海康SDK / 海康ISUP / 大华SDK
        if ("haikang".equals(app) || "haikang_isup".equals(app) || "dahua".equals(app)) {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceStream(stream, SecurityConstants.INNER);
            if (r.getCode() == Constants.SUCCESS && r.getData() != null) {
                boolean isPlayback = stream.equals(r.getData().getPlaybackStreamKey());
                if (!isPlayback && "1".equals(r.getData().getEnableMp4())) {
                    result.setEnable_mp4(true);
                }
                if (isPlayback || "1".equals(r.getData().getEnableDisableNoneReader())) {
                    result.setEnable_disable_none_reader(false);
                }
            }
            return result;
        }

        // GB28181
        if ("gb28181".equals(app)) {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceStream(stream, SecurityConstants.INNER);
            if (r.getCode() == Constants.SUCCESS && r.getData() != null) {
                boolean isPlayback = stream.equals(r.getData().getPlaybackStreamKey());
                if (!isPlayback && "1".equals(r.getData().getEnableMp4())) {
                    result.setEnable_mp4(true);
                }
                if (isPlayback || "1".equals(r.getData().getEnableDisableNoneReader())) {
                    result.setEnable_disable_none_reader(false);
                }
            }
            return result;
        }

        // JT1078
        if ("jt1078".equals(app)) {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceStream(stream, SecurityConstants.INNER);
            if (r.getCode() == Constants.SUCCESS && r.getData() != null) {
                boolean isPlayback = stream.equals(r.getData().getPlaybackStreamKey());
                if (!isPlayback && "1".equals(r.getData().getEnableMp4())) {
                    result.setEnable_mp4(true);
                }
                if (isPlayback || "1".equals(r.getData().getEnableDisableNoneReader())) {
                    result.setEnable_disable_none_reader(false);
                }
            }
            return result;
        }

        return result;
    }
}
