package com.ruoyi.zlm.service;

import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.jt1078.api.domain.Jt1078Device;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.*;
import com.ruoyi.zlm.common.InviteSessionType;
import com.ruoyi.zlm.domain.MediaServerLoad;
import com.ruoyi.zlm.domain.RecordInfo;
import com.ruoyi.zlm.domain.Snap;
import com.ruoyi.zlm.domain.dto.ZLMResult;

import java.util.List;
import java.util.Map;

/**
 * 媒体服务器Service接口
 *
 * @FileName IMediaServerService
 * @Description
 * @Author fengcheng
 * @date 2026-03-31
 **/
public interface IMediaServerService {

    /**
     * 获取默认的媒体服务器
     *
     * @return
     */
    ZlmMediaServer getDefaultMediaServer();

    /**
     * 添加媒体服务器
     *
     * @param zlmMediaServer
     */
    int add(ZlmMediaServer zlmMediaServer);

    /**
     * 修改媒体服务器
     *
     * @param zlmMediaServer
     */
    int update(ZlmMediaServer zlmMediaServer);

    /**
     * 删除媒体服务器
     *
     * @param zlmMediaServer
     */
    void delete(ZlmMediaServer zlmMediaServer);

    /**
     * 从数据库中获取所有媒体服务器
     *
     * @return
     */
    List<ZlmMediaServer> getAllFromDatabase();

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    ZlmMediaServer getOneFromDatabase(String id);

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    ZlmMediaServer getOne(String id);

    /**
     * 获取所有在线媒体服务器
     *
     * @return
     */
    List<ZlmMediaServer> getAllOnlineMediaServe();

    /**
     * 获取负载最小的媒体服务器
     *
     * @param hasAssist 是否包含辅助媒体服务器
     * @return
     */
    ZlmMediaServer getMediaServerForMinimumLoad(Boolean hasAssist);

    /**
     * 拉流播放
     *
     * @param streamPullPlay 拉流播放请求参数
     * @param callback       回调
     */
    void streamPullPlay(StreamPullPlay streamPullPlay, ErrorCallback<StreamInfo> callback);

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
    StreamInfo getStreamInfoByAppAndStreamWithCheck(String app, String stream, String mediaServerId, String addr, boolean authority);

    /**
     * 根据应用名和流ID获取播放地址, 只是地址拼接
     *
     * @param mediaServer 媒体服务器
     * @param app         应用名
     * @param stream      流ID
     * @param mediaInfo   媒体信息
     * @return
     */
    StreamInfo getStreamInfoByAppAndStream(ZlmMediaServer mediaServer, String app, String stream, MediaInfo mediaInfo);

    /**
     * 停止拉流播放
     *
     * @param streamPullPlay
     */
    void stopStreamPullPlay(StreamPullPlay streamPullPlay);

    /**
     * 点播成功时调用截图
     *
     * @param mediaServer media
     * @param app         app
     * @param stream      流id
     */
    String snapOnPlay(ZlmMediaServer mediaServer, String app, String stream);

    /**
     * 获取截图
     *
     * @param mediaServer
     * @param snap
     * @return
     */
    String getSnap(ZlmMediaServer mediaServer, Snap snap);

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
    int createRTPServer(ZlmMediaServer mediaServer, String app, String streamId, long ssrc, Integer port, Boolean onlyAuto, Boolean disableAudio, Boolean reUsePort, Integer tcpMode);

    /**
     * rtp播放
     *
     * @param rtpServerParam 创建rtp端口请求参数
     * @param callback
     * @return
     */
    void rtpPlay(RTPServerParam rtpServerParam, ErrorCallback<StreamInfo> callback);

    /**
     * 关闭RTP服务器
     *
     * @param mediaServerItem
     * @param streamId
     */
    void closeRTPServer(ZlmMediaServer mediaServerItem, String streamId);

    /**
     * 停止rtp播放
     *
     * @param rtpServerParam 创建rtp端口请求参数
     * @return
     */
    void stopRtpPlay(RTPServerParam rtpServerParam);

    /**
     * 判断流是否已经准备好
     *
     * @param mediaServer
     * @param rtp
     * @param streamId
     * @return
     */
    Boolean isStreamReady(ZlmMediaServer mediaServer, String rtp, String streamId);

    /**
     * 加载文件形成播放地址
     *
     * @param id       设备id
     * @param callback 回调
     * @return
     */
    void loadRecord(Long id, ErrorCallback<StreamInfo> callback);

    /**
     * 加载文件形成播放地址
     *
     * @param id         设备id
     * @param isPlayback 是否是回放
     * @param callback   回调
     * @return
     */
    void loadRecord(Long id, boolean isPlayback, ErrorCallback<StreamInfo> callback);

    /**
     * 加载云端录像文件形成播放地址
     *
     * @param mediaServer  媒体服务器
     * @param app         应用名
     * @param stream      流id
     * @param deviceId    设备id
     * @param videoPath   视频文件路径
     * @param isPlayback  是否是回放
     * @param callback    回调
     * @return
     */
    void loadCloudRecord(ZlmMediaServer mediaServer, String app, String stream, Long deviceId, String videoPath, boolean isPlayback, ErrorCallback<StreamInfo> callback);

    /**
     * 关闭流文件形成播放地址
     *
     * @param id 设备id
     */
    void closeStreams(Long id);

    /**
     * 获取流媒体服务器列表
     *
     * @return
     */
    List<ZlmMediaServer> getAll();

    /**
     * 测试流媒体服务
     *
     * @param ip     流媒体服务IP
     * @param port   流媒体服务HTT端口
     * @param secret 流媒体服务secret
     * @param type   流媒体服务类型
     * @return
     */
    ZlmMediaServer checkMediaServer(String ip, int port, String secret, String type);

    /**
     * 获取流信息
     *
     * @param app         应用名
     * @param stream      流ID
     * @param mediaServer 媒体服务器
     * @return
     */
    MediaInfo getMediaInfo(ZlmMediaServer mediaServer, String app, String stream);

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
    boolean deleteRecordDirectory(ZlmMediaServer mediaServer, String app, String stream, String date, String fileName);

    /**
     * 获取下载文件路径
     *
     * @param mediaServer
     * @param recordInfo
     * @return
     */
    DownloadFileInfo getDownloadFilePath(ZlmMediaServer mediaServer, RecordInfo recordInfo);

    /**
     * 设置录像播放速度
     *
     * @param mediaServer 使用的节点
     * @param app         应用名
     * @param stream      流id
     * @param stamp       播放速度
     * @param schema      播放协议
     */
    void seekRecordStamp(ZlmMediaServer mediaServer, String app, String stream, Double stamp, String schema);

    /**
     * 定位录像播放到制定位置
     *
     * @param mediaServer 使用的节点
     * @param app         应用名
     * @param stream      流ID
     * @param speed       要定位的时间位置，从录像开始的时间算起
     * @param schema      播放协议
     */
    void setRecordSpeed(ZlmMediaServer mediaServer, String app, String stream, Integer speed, String schema);

    /**
     * 关闭流
     *
     * @param mediaServer 媒体服务器
     * @param app         应用名
     * @param stream      流ID
     */
    void closeStreams(ZlmMediaServer mediaServer, String app, String stream);

    /**
     * 开始播放
     *
     * @param device   设备信息
     * @param record   是否录制
     * @param callback 回调
     */
    void play(QsDevice device, Boolean record, ErrorCallback<StreamInfo> callback);

    /**
     * 获取流媒体服务器负载
     *
     * @param mediaServer
     * @return
     */
    MediaServerLoad getLoad(ZlmMediaServer mediaServer);

    /**
     * 重启流媒体
     *
     * @param mediaServer 流媒体ID
     * @return
     */
    void restartServer(ZlmMediaServer mediaServer);

    /**
     * 生成推流地址
     *
     * @param id     设备id
     * @param callId
     * @return
     */
    Map<String, Object> getStreamPushAddress(Long id, String callId);

    /**
     * 推流播放
     *
     * @param id
     * @param callback
     */
    void streamPullPush(Long id, ErrorCallback<StreamInfo> callback);

    /**
     * gb28181 播放
     *
     * @param qsDevice
     * @param gbDevice
     * @param callback
     */
    void startGb28181Play(QsDevice qsDevice, Device gbDevice, ErrorCallback<StreamInfo> callback);

    /**
     * gb28181 回放
     *
     * @param qsDevice
     * @param gbDevice
     * @param startTime
     * @param endTime
     * @param callback
     */
    void startGb28181Playback(QsDevice qsDevice, Device gbDevice, String startTime, String endTime, ErrorCallback<StreamInfo> callback);

    /**
     * 连接rtp服务
     *
     * @param mediaServer
     * @param address
     * @param port
     * @param stream
     * @return
     */
    Boolean connectRtpServer(ZlmMediaServer mediaServer, String address, int port, String stream);

    /**
     * gb28181 停止点播
     *
     * @param type
     * @param qsDevice
     * @param device
     * @param stream
     */
    void stopGb28181Play(InviteSessionType type, QsDevice qsDevice, Device device, String stream);

    /**
     * jt1078 播放
     *
     * @param qsDevice
     * @param jt1078Device
     * @param callback
     */
    void startJt1078Play(QsDevice qsDevice, Jt1078Device jt1078Device, ErrorCallback<StreamInfo> callback);

    /**
     * jt1078 回放
     *
     * @param qsDevice
     * @param jt1078Device
     * @param startTime
     * @param endTime
     * @param callback
     */
    void startJt1078Playback(QsDevice qsDevice, Jt1078Device jt1078Device, String startTime, String endTime, ErrorCallback<StreamInfo> callback);

    /**
     * jt1078 停止点播
     *
     * @param type
     * @param qsDevice
     * @param device
     * @param stream
     */
    void stopJt1078Play(InviteSessionType type, QsDevice qsDevice, Jt1078Device device, String stream);

    /**
     * 开始发送RTP流到指定地址
     *
     * @param mediaServer
     * @param param
     * @return
     */
    ZLMResult<?> startSendRtp(ZlmMediaServer mediaServer, Map<String, Object> param);

    /**
     * 停止发送RTP流
     *
     * @param mediaServer
     * @param param
     * @return
     */
    ZLMResult<?> stopSendRtp(ZlmMediaServer mediaServer, Map<String, Object> param);
}
