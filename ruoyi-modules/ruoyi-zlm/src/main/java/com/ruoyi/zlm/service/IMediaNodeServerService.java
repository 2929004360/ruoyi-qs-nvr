package com.ruoyi.zlm.service;

import com.ruoyi.zlm.api.domain.*;
import com.ruoyi.zlm.common.CommonCallback;
import com.ruoyi.zlm.domain.RecordInfo;
import com.ruoyi.zlm.domain.dto.ZLMResult;

import java.util.List;

/**
 * 媒体节点服务接口
 *
 * @FileName IMediaNodeServerService
 * @Description
 * @Author fengcheng
 * @date 2026-03-31
 **/
public interface IMediaNodeServerService {
    List<StreamInfo> getMediaList(ZlmMediaServer mediaServer, String app, String stream);

    StreamInfo getStreamInfoByAppAndStream(ZlmMediaServer mediaServer, String app, String stream, MediaInfo mediaInfo, String addr, boolean isPlay);

    String startProxy(ZlmMediaServer mediaServer, StreamPullPlay streamPullPlay);

    void stopProxy(ZlmMediaServer mediaServer, String streamKey);

    void getSnap(ZlmMediaServer mediaServer, String app, String stream, int timeoutSec, int expireSec, String path, String fileName);

    void getSnap(ZlmMediaServer mediaServer, String streamUrl, int timeoutSec, int expireSec, String path, String fileName);

    void closeRtpServer(ZlmMediaServer mediaServer, String streamId, CommonCallback<Boolean> callback);

    MediaInfo getMediaInfo(ZlmMediaServer mediaServer, String app, String stream);

    void closeStreams(ZlmMediaServer mediaServer, String app, String stream);

    ZlmMediaServer checkMediaServer(String ip, int port, String secret);

    boolean deleteRecordDirectory(ZlmMediaServer mediaServer, String app, String stream, String date, String fileName);

    DownloadFileInfo getDownloadFilePath(ZlmMediaServer mediaServer, RecordInfo recordInfo);

    void seekRecordStamp(ZlmMediaServer mediaServer, String app, String stream, Double stamp, String schema);

    void setRecordSpeed(ZlmMediaServer mediaServer, String app, String stream, Integer speed, String schema);

    void startRecord(ZlmMediaServer mediaServer, String app, String stream);

    void stopRecord(ZlmMediaServer mediaServer, String app, String stream);

    ZLMResult<?> getThreadsLoad(ZlmMediaServer mediaServer);

    ZLMResult<?> getWorkThreadsLoad(ZlmMediaServer mediaServer);

    void restartServer(ZlmMediaServer mediaServer);

    Boolean connectRtpServer(ZlmMediaServer mediaServer, String address, int port, String stream);
}
