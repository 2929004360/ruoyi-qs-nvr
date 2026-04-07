package com.ruoyi.zlm.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.zlm.api.domain.MediaInfo;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.api.domain.StreamPullPlay;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.domain.dto.FlagData;
import com.ruoyi.zlm.domain.dto.StreamProxyResult;
import com.ruoyi.zlm.domain.dto.ZLMResult;
import com.ruoyi.zlm.service.IMediaNodeServerService;
import com.ruoyi.zlm.utils.ZLMRESTfulUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * zlm媒体节点服务接口
 *
 * @FileName ZLMMediaNodeServerServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-03-31
 **/
@Slf4j
@Service("zlm")
public class ZLMMediaNodeServerServiceImpl implements IMediaNodeServerService {

    @Autowired
    private ZLMRESTfulUtils zlmresTfulUtils;

    @Autowired
    private UserSetting userSetting;

    @Override
    public List<StreamInfo> getMediaList(ZlmMediaServer mediaServer, String app, String stream) {
        List<StreamInfo> streamInfoList = new ArrayList<>();
        ZLMResult<JSONArray> zlmResult = zlmresTfulUtils.getMediaList(mediaServer, app, stream);
        if (zlmResult != null) {
            if (zlmResult.getCode() == 0) {
                if (zlmResult.getData() == null) {
                    return streamInfoList;
                }
                for (int i = 0; i < zlmResult.getData().size(); i++) {
                    JSONObject mediaJSON = zlmResult.getData().getJSONObject(0);
                    MediaInfo mediaInfo = MediaInfo.getInstance(mediaJSON, mediaServer, userSetting.getServerId());
                    StreamInfo streamInfo = getStreamInfoByAppAndStream(mediaServer, mediaInfo.getApp(),
                            mediaInfo.getStream(), mediaInfo, null, true);
                    if (streamInfo != null) {
                        streamInfoList.add(streamInfo);
                    }
                }
            }
        }
        return streamInfoList;
    }

    @Override
    public StreamInfo getStreamInfoByAppAndStream(ZlmMediaServer mediaServer, String app, String stream, MediaInfo mediaInfo, String addr, boolean isPlay) {
        StreamInfo streamInfoResult = new StreamInfo();
        streamInfoResult.setStream(stream);
        streamInfoResult.setApp(app);
        if (addr == null) {
            addr = mediaServer.getStreamIp();
        }

        streamInfoResult.setIp(addr);
        if (mediaInfo != null) {
            streamInfoResult.setServerId(mediaInfo.getServerId());
        } else {
            streamInfoResult.setServerId(userSetting.getServerId());
        }

        streamInfoResult.setMediaServer(mediaServer);
        Map<String, String> param = new HashMap<>();
        if (mediaInfo != null && !ObjectUtils.isEmpty(mediaInfo.getOriginTypeStr())) {
            if (!ObjectUtils.isEmpty(mediaInfo.getOriginTypeStr())) {
                param.put("originTypeStr", mediaInfo.getOriginTypeStr());
            }
            if (!ObjectUtils.isEmpty(mediaInfo.getVideoCodec())) {
                param.put("videoCodec", mediaInfo.getVideoCodec());
            }
            if (!ObjectUtils.isEmpty(mediaInfo.getAudioCodec())) {
                param.put("audioCodec", mediaInfo.getAudioCodec());
            }
        }
        StringBuilder callIdParamBuilder = new StringBuilder();
        if (!param.isEmpty()) {
            callIdParamBuilder.append("?");
            for (Map.Entry<String, String> entry : param.entrySet()) {
                callIdParamBuilder.append(entry.getKey()).append("=").append(entry.getValue());
                callIdParamBuilder.append("&");
            }
            callIdParamBuilder.deleteCharAt(callIdParamBuilder.length() - 1);
        }

        String callIdParam = callIdParamBuilder.toString();

        streamInfoResult.setRtmp(addr, mediaServer.getRtmpPort(), mediaServer.getRtmpSslPort(), app, stream, callIdParam);
        streamInfoResult.setRtsp(addr, mediaServer.getRtspPort(), mediaServer.getRtspSslPort(), app, stream, callIdParam);

        String flvFile = String.format("%s/%s.live.flv%s", app, stream, callIdParam);
        streamInfoResult.setFlv(addr, mediaServer.getHttpPort(), mediaServer.getHttpSslPort(), flvFile);
        streamInfoResult.setWsFlv(addr, mediaServer.getHttpPort(), mediaServer.getHttpSslPort(), flvFile);

        String mp4File = String.format("%s/%s.live.mp4%s", app, stream, callIdParam);
        streamInfoResult.setFmp4(addr, mediaServer.getHttpPort(), mediaServer.getHttpSslPort(), mp4File);
        streamInfoResult.setWsMp4(addr, mediaServer.getHttpPort(), mediaServer.getHttpSslPort(), mp4File);

        streamInfoResult.setHls(addr, mediaServer.getHttpPort(), mediaServer.getHttpSslPort(), app, stream, callIdParam);
        streamInfoResult.setWsHls(addr, mediaServer.getHttpPort(), mediaServer.getHttpSslPort(), app, stream, callIdParam);

        streamInfoResult.setTs(addr, mediaServer.getHttpPort(), mediaServer.getHttpSslPort(), app, stream, callIdParam);
        streamInfoResult.setWsTs(addr, mediaServer.getHttpPort(), mediaServer.getHttpSslPort(), app, stream, callIdParam);

        streamInfoResult.setRtc(addr, mediaServer.getHttpPort(), mediaServer.getHttpSslPort(), app, stream, callIdParam, isPlay);

        streamInfoResult.setMediaInfo(mediaInfo);

        if (!"broadcast".equalsIgnoreCase(app) && !ObjectUtils.isEmpty(mediaServer.getTranscodeSuffix()) && !"null".equalsIgnoreCase(mediaServer.getTranscodeSuffix())) {
            String newStream = stream + "_" + mediaServer.getTranscodeSuffix();
            mediaServer.setTranscodeSuffix(null);
            StreamInfo transcodeStreamInfo = getStreamInfoByAppAndStream(mediaServer, app, newStream, null, addr, isPlay);
            streamInfoResult.setTranscodeStream(transcodeStreamInfo);
        }
        return streamInfoResult;
    }

    @Override
    public String startProxy(ZlmMediaServer mediaServer, StreamPullPlay streamPullPlay) {
        ZLMResult<StreamProxyResult> zlmResult = zlmresTfulUtils.addStreamProxy(
                mediaServer, streamPullPlay.getApp(),
                streamPullPlay.getStream(),
                streamPullPlay.getUrl(),
                streamPullPlay.isEnable_audio(),
                streamPullPlay.isEnable_mp4(),
                streamPullPlay.getRtp_type(),
                streamPullPlay.getTimeOut());

        if (zlmResult.getCode() != 0) {
            throw new RuntimeException(zlmResult.getMsg());
        } else {
            StreamProxyResult data = zlmResult.getData();
            if (data == null) {
                throw new RuntimeException("代理结果异常： " + zlmResult);
            } else {
                return data.getKey();
            }
        }
    }

    @Override
    public void stopProxy(ZlmMediaServer mediaServer, String streamKey) {
        ZLMResult<FlagData> zlmResult = zlmresTfulUtils.delStreamProxy(mediaServer, streamKey);
        if (zlmResult == null) {
            throw new RuntimeException("请求失败");
        } else if (zlmResult.getCode() != 0) {
            throw new RuntimeException(zlmResult.getMsg());
        }
    }
}
