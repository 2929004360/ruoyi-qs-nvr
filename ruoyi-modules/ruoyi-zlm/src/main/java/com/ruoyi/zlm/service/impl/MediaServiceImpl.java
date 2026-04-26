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

        // 拉流代理
        if ("rtsp".equals(app) || "rtmp".equals(app) || "flv".equals(app) || "hls".equals(app)) {
            if ("1".equals(data.getEnableDisableNoneReader())) {
                // 无人观看停用
                // 修改数据
                StreamPullPlay streamPullPlay = new StreamPullPlay();
                streamPullPlay.setDeviceId(data.getId());
                streamPullPlay.setStreamKey(data.getStreamKey());
                streamPullPlay.setMediaServerId(data.getMediaServerId());

                mediaServerService.stopStreamPullPlay(streamPullPlay);
                return true;
            } else {
                return false;
            }
        } else if ("haikang".equals(app) || "haikang_isup".equals(app) || "dahua".equals(app)) {
            if ("1".equals(data.getEnableDisableNoneReader())) {
                // 无人观看停用
                RTPServerParam rtpServerParam = new RTPServerParam();
                rtpServerParam.setId(data.getId());
                rtpServerParam.setType(data.getType());
                rtpServerParam.setStreamId(stream);
                mediaServerService.stopRtpPlay(rtpServerParam);
                return true;
            } else {
                return false;
            }
        } else if ("push".equals(app)) {
            if ("1".equals(data.getEnableDisableNoneReader())) {
                return true;
            } else {
                return false;
            }
        } else if ("gb28181".equals(app)) {
            if ("1".equals(data.getEnableDisableNoneReader())) {
                R<Device> deviceR = remoteGb28181Service.getDeviceByDeviceId(data.getGbDeviceId(), SecurityConstants.INNER);
                if (deviceR.getCode() == Constants.SUCCESS && deviceR.getData() != null) {
                    mediaServerService.stopGb28181Play(InviteSessionType.PLAY, data, deviceR.getData(), data.getDeviceCode());
                }
                return true;
            } else {
                return false;
            }
        } else if ("jt1078".equals(app)) {
            if ("1".equals(data.getEnableDisableNoneReader())) {
                R<Jt1078Device> deviceR = remoteJt1078Service.getDeviceByMobileNo(data.getJtMobileNo(), SecurityConstants.INNER);
                if (deviceR.getCode() == Constants.SUCCESS && deviceR.getData() != null) {
                    mediaServerService.stopJt1078Play(InviteSessionType.PLAY, data, deviceR.getData(), data.getDeviceCode());
                }
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public ResultForOnPublish authenticatePublish(ZlmMediaServer mediaServer, String app, String stream, String params) {
        ResultForOnPublish result = new ResultForOnPublish();
        result.setEnable_audio(true);

        // 海康sdk 海康isup 大华sdk
        if ("haikang".equals(app) || "haikang_isup".equals(app) || "dahua".equals(app)) {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceStream(stream, SecurityConstants.INNER);

            if (r.getCode() != Constants.SUCCESS) {
                result.setEnable_mp4(false);
            } else if (r.getData() == null) {
                result.setEnable_mp4(false);
            } else {
                result.setEnable_mp4("1".equals(r.getData().getEnableMp4()));
            }
        }
        // 推流
        if ("push".equals(app)) {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceStream(stream, SecurityConstants.INNER);

            if (r.getCode() != Constants.SUCCESS) {
                result.setEnable_mp4(false);
            } else if (r.getData() == null) {
                result.setEnable_mp4(false);
            } else {
                result.setEnable_mp4("1".equals(r.getData().getEnableMp4()));
            }
            if (userSetting.getPushAuthority()) {
                // 对于推流进行鉴权
                Map<String, String> paramMap = MediaServerUtils.urlParamToMap(params);
                // 推流鉴权
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
                // 推流自定义播放鉴权码
                String callId = paramMap.get("callId");
                // 鉴权配置
                String checkStr = callId == null ? userSetting.getPushKey() : (callId + "_" + userSetting.getPushKey());
                String checkSign = DigestUtils.md5DigestAsHex(checkStr.getBytes());
                if (!checkSign.equals(sign)) {
                    log.info("推流鉴权失败： sign 无权限: callId={}. sign={}", callId, sign);
                    throw new RuntimeException("推流鉴权失败： sign 无权限: callId=" + callId + ". sign=" + sign);
                }

                StreamAuthorityInfo streamAuthorityInfo = StreamAuthorityInfo.getInstanceByHook(app, stream, mediaServer.getId());
                streamAuthorityInfo.setCallId(callId);
                streamAuthorityInfo.setSign(sign);
                // 鉴权通过
                redisCatchStorage.updateStreamAuthorityInfo(app, stream, streamAuthorityInfo);
            }
        }

        return result;
    }
}
