package com.ruoyi.zlm.domain;


import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import com.ruoyi.zlm.config.ZLMServerConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.util.ObjectUtils;

/**
 * 流媒体服务信息
 *
 * @author fengcheng
 */
@Schema(description = "流媒体服务信息")
@Data
public class ZlmMediaServer extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** IP */
    private String ip;

    /** hook使用的IP（zlm访问WVP使用的IP） */
    private String hookIp = "127.0.0.1";

    /** SDP IP */
    private String sdpIp;

    /** 流IP */
    private String streamIp;

    /** HTTP端口 */
    private Integer httpPort;

    /** HTTPS端口 */
    private Integer httpSslPort;

    /** RTMP端口 */
    private Integer rtmpPort;

    /** RTMPS端口 */
    private Integer rtmpSslPort;

    /** RTP收流端口（单端口模式有用） */
    private Integer rtpProxyPort;

    /** RTSP端口 */
    private Integer rtspPort;

    /** RTSPS端口 */
    private Integer rtspSslPort;

    /** flv端口 */
    private Integer flvPort;

    /** https-flv端口 */
    private Integer flvSslPort;

    /** mp4端口 */
    private Integer mp4Port;

    /** mp4端口 */
    private Integer mp4SslPort;

    /** ws-flv端口 */
    private Integer wsFlvPort;

    /** wss-flv端口 */
    private Integer wsFlvSslPort;

    /** 1078收流端口（单端口模式有用） */
    private Integer jttProxyPort;

    /** 是否开启自动配置ZLM */
    private Boolean autoConfig;

    /** ZLM鉴权参数 */
    private String secret;

    /** 类型： zlm/abl */
    private String type;

    /** 是否使用多端口模式 */
    private Boolean rtpEnable;

    /** 多端口RTP收流端口范围 */
    private String rtpPortRange;

    /** RTP发流端口范围 */
    private String sendRtpPortRange;

    /** assist服务端口 */
    private Integer recordAssistPort;

    /** 是否是默认ZLM */
    private Boolean defaultServer;

    /** keepalive hook触发间隔,单位秒 */
    private Float hookAliveInterval;

    /** 录像存储路径 */
    private String recordPath;

    /** 录像存储时长 */
    private Integer recordDay;

    /** 转码的前缀 */
    private String transcodeSuffix;

    /** 服务Id */
    @Excel(name = "服务Id")
    private String serverId;

    /**
     * 状态(OFFLINE=离线,ON=在线)
     */
    private String status;

    public ZlmMediaServer() {
    }

    public ZlmMediaServer(ZLMServerConfig zlmServerConfig, String sipIp) {
        id = zlmServerConfig.getGeneralMediaServerId();
        ip = zlmServerConfig.getIp();
        hookIp = ObjectUtils.isEmpty(zlmServerConfig.getHookIp()) ? sipIp : zlmServerConfig.getHookIp();
        sdpIp = ObjectUtils.isEmpty(zlmServerConfig.getSdpIp()) ? zlmServerConfig.getIp() : zlmServerConfig.getSdpIp();
        streamIp = ObjectUtils.isEmpty(zlmServerConfig.getStreamIp()) ? zlmServerConfig.getIp() : zlmServerConfig.getStreamIp();
        httpPort = zlmServerConfig.getHttpPort();
        httpSslPort = zlmServerConfig.getHttpSSLport();
        rtmpPort = zlmServerConfig.getRtmpPort();
        rtmpSslPort = zlmServerConfig.getRtmpSslPort();
        rtpProxyPort = zlmServerConfig.getRtpProxyPort();
        rtspPort = zlmServerConfig.getRtspPort();
        rtspSslPort = zlmServerConfig.getRtspSSlport();
        autoConfig = true; // 默认值true;
        secret = zlmServerConfig.getApiSecret();
        hookAliveInterval = zlmServerConfig.getHookAliveInterval();
        rtpEnable = false; // 默认使用单端口;直到用户自己设置开启多端口
        rtpPortRange = zlmServerConfig.getPortRange().replace("_", ","); // 默认使用30000,30500作为级联时发送流的端口号
        recordAssistPort = 0; // 默认关闭
        transcodeSuffix = zlmServerConfig.getTranscodeSuffix();

    }
}
