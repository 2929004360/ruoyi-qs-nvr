package com.ruoyi.zlm.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.zlm.api.config.ZLMServerConfig;
import com.ruoyi.zlm.api.domain.*;
import com.ruoyi.zlm.common.CommonCallback;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.domain.RecordInfo;
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
                    JSONObject mediaJSON = zlmResult.getData().getJSONObject(i);
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

    @Override
    public void getSnap(ZlmMediaServer mediaServer, String app, String stream, int timeoutSec, int expireSec, String path, String fileName) {
        String streamUrl;
        if (mediaServer.getRtspPort() != 0) {
            streamUrl = String.format("rtsp://127.0.0.1:%s/%s/%s", mediaServer.getRtspPort(), app, stream);
        } else {
            streamUrl = String.format("http://127.0.0.1:%s/%s/%s.live.mp4", mediaServer.getHttpPort(), app, stream);
        }
        zlmresTfulUtils.getSnap(mediaServer, streamUrl, timeoutSec, expireSec, path, fileName);
    }

    @Override
    public void getSnap(ZlmMediaServer mediaServer, String streamUrl, int timeoutSec, int expireSec, String path, String fileName) {
        zlmresTfulUtils.getSnap(mediaServer, streamUrl, timeoutSec, expireSec, path, fileName);
    }

    @Override
    public void closeRtpServer(ZlmMediaServer mediaServer, String streamId, CommonCallback<Boolean> callback) {
        if (mediaServer == null) {
            if (callback != null) {
                callback.run(false);
            }
            return;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("stream_id", streamId);
        zlmresTfulUtils.closeRtpServer(mediaServer, param, zlmResult -> {
            if (zlmResult.getCode() == 0) {
                if (callback != null) {
                    callback.run(zlmResult.getHit() >= 1);
                }
                return;
            } else {
                log.error("关闭RTP Server 失败: " + zlmResult.getMsg());
            }
            if (callback != null) {
                callback.run(false);
            }
        });
    }

    @Override
    public MediaInfo getMediaInfo(ZlmMediaServer mediaServer, String app, String stream) {
        ZLMResult<JSONObject> zlmResult = zlmresTfulUtils.getMediaInfo(mediaServer, app, "rtsp", stream);
        if (zlmResult.getCode() != 0 || zlmResult.getData() == null || zlmResult.getData().getString("app") == null) {
            return null;
        }
        return MediaInfo.getInstance(zlmResult.getData(), mediaServer, userSetting.getServerId());
    }

    @Override
    public void closeStreams(ZlmMediaServer mediaServer, String app, String stream) {
        zlmresTfulUtils.closeStreams(mediaServer, app, stream);
    }

    @Override
    public ZlmMediaServer checkMediaServer(String ip, int port, String secret) {
        ZlmMediaServer mediaServer = new ZlmMediaServer();
        mediaServer.setServerId(userSetting.getServerId());
        mediaServer.setIp(ip);
        mediaServer.setHttpPort(port);
        mediaServer.setSecret(secret);
        ZLMResult<List<JSONObject>> mediaServerConfigResult = zlmresTfulUtils.getMediaServerConfig(mediaServer);
        if (mediaServerConfigResult == null) {
            throw new RuntimeException("连接失败");
        }
        List<JSONObject> configList = mediaServerConfigResult.getData();
        if (configList == null) {
            throw new RuntimeException("读取配置失败");
        }
        ZLMServerConfig zlmServerConfig = JSON.parseObject(JSON.toJSONString(configList.get(0)), ZLMServerConfig.class);
        if (zlmServerConfig == null) {
            throw new RuntimeException("读取配置失败");
        }
        mediaServer.setId(zlmServerConfig.getGeneralMediaServerId());
        mediaServer.setHttpSslPort(zlmServerConfig.getHttpSSLport());
        mediaServer.setRtmpPort(zlmServerConfig.getRtmpPort());
        mediaServer.setRtmpSslPort(zlmServerConfig.getRtmpSslPort());
        mediaServer.setRtspPort(zlmServerConfig.getRtspPort());
        mediaServer.setRtspSslPort(zlmServerConfig.getRtspSSlport());
        mediaServer.setRtpProxyPort(zlmServerConfig.getRtpProxyPort());
        mediaServer.setStreamIp(ip);

        mediaServer.setHookIp("127.0.0.1");
        mediaServer.setSdpIp(ip);
        mediaServer.setType("zlm");
        return mediaServer;
    }

    @Override
    public boolean deleteRecordDirectory(ZlmMediaServer mediaServer, String app, String stream, String date, String fileName) {
        log.info("[zlm-deleteRecordDirectory] 删除磁盘文件, server: {} {}:{}->{}/{}", mediaServer.getId(), app, stream, date, fileName);
        ZLMResult<?> zlmResult = zlmresTfulUtils.deleteRecordDirectory(mediaServer, app,
                stream, date, fileName);
        if (zlmResult.getCode() == 0) {
            return true;
        } else {
            log.info("[zlm-deleteRecordDirectory] 删除磁盘文件错误, server: {} {}:{}->{}/{}, 结果： {}", mediaServer.getId(), app, stream, date, fileName, zlmResult);
            throw new RuntimeException("删除磁盘文件失败");
        }
    }

    @Override
    public DownloadFileInfo getDownloadFilePath(ZlmMediaServer mediaServerItem, RecordInfo recordInfo) {

        // 将filePath作为独立参数传入，避免%符号解析问题
        String pathTemplate = "%s://%s:%s/index/api/downloadFile?file_path=%s";

        DownloadFileInfo info = new DownloadFileInfo();

        // filePath作为第4个参数
        info.setHttpPath(String.format(pathTemplate,
                "http",
                mediaServerItem.getStreamIp(),
                mediaServerItem.getHttpPort(),
                recordInfo.getFilePath()));

        // 同样作为第4个参数
        if (mediaServerItem.getHttpSslPort() > 0) {
            info.setHttpsPath(String.format(pathTemplate,
                    "https",
                    mediaServerItem.getStreamIp(),
                    mediaServerItem.getHttpSslPort(),
                    recordInfo.getFilePath()));
        }
        return info;
    }

    @Override
    public void seekRecordStamp(ZlmMediaServer mediaServer, String app, String stream, Double stamp, String schema) {
        ZLMResult<?> zlmResult = zlmresTfulUtils.seekRecordStamp(mediaServer, app, stream, stamp, schema);
        if (zlmResult == null) {
            throw new RuntimeException("请求失败");
        }
        if (zlmResult.getCode() != 0) {
            throw new RuntimeException(zlmResult.getMsg());
        }
    }

    @Override
    public void setRecordSpeed(ZlmMediaServer mediaServer, String app, String stream, Integer speed, String schema) {
        ZLMResult<?> zlmResult = zlmresTfulUtils.setRecordSpeed(mediaServer, app, stream, speed, schema);
        if (zlmResult == null) {
            throw new RuntimeException("请求失败");
        }
        if (zlmResult.getCode() != 0) {
            throw new RuntimeException(zlmResult.getMsg());
        }
    }

    @Override
    public void startRecord(ZlmMediaServer mediaServer, String app, String stream) {
        ZLMResult<?> zlmResult = zlmresTfulUtils.startRecord(mediaServer, app, stream);
        if (zlmResult == null) {
            throw new RuntimeException("请求失败");
        }
        if (zlmResult.getCode() != 0) {
            throw new RuntimeException(zlmResult.getMsg());
        }
    }

    @Override
    public void stopRecord(ZlmMediaServer mediaServer, String app, String stream) {
        ZLMResult<?> zlmResult = zlmresTfulUtils.stopRecord(mediaServer, app, stream);
        if (zlmResult == null) {
            throw new RuntimeException("请求失败");
        }
        if (zlmResult.getCode() != 0) {
            throw new RuntimeException(zlmResult.getMsg());
        }
    }

    @Override
    public ZLMResult<?> getThreadsLoad(ZlmMediaServer mediaServer) {
        return zlmresTfulUtils.getThreadsLoad(mediaServer);
    }

    @Override
    public ZLMResult<?> getWorkThreadsLoad(ZlmMediaServer mediaServer) {
        return zlmresTfulUtils.getWorkThreadsLoad(mediaServer);
    }

    /**
     * 重启流媒体
     *
     * @param mediaServer 流媒体
     * @return
     */
    @Override
    public void restartServer(ZlmMediaServer mediaServer) {
        zlmresTfulUtils.restartServer(mediaServer);
    }

    /**
     * 连接rtp服务
     *
     * @param mediaServer
     * @param address
     * @param port
     * @param stream
     * @return
     */
    @Override
    public Boolean connectRtpServer(ZlmMediaServer mediaServer, String address, int port, String stream) {
        ZLMResult<?> zlmResult = zlmresTfulUtils.connectRtpServer(mediaServer, address, port, stream);
        log.info("[TCP主动连接对方] 结果： {}", zlmResult);
        return zlmResult.getCode() == 0;
    }
}
