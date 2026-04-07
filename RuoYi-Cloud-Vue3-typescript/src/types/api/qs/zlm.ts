export interface RTPServerParam {
    /** 主键ID */
    id?: number;

    /** 媒体服务器信息 */
    mediaServer?: any; // 建议替换为具体的 ZlmMediaServer 类型

    /** 流信息 */
    streamInfo?: any; // 建议替换为具体的 StreamInfo 类型

    /** 应用名 */
    app?: string;

    /** 流ID */
    streamId?: string;

    /** 预设的SSRC */
    presetSsrc?: string;

    /** 是否开启SSRC校验 */
    ssrcCheck?: boolean;

    /** 是否为回放模式 */
    playback?: boolean;

    /** 端口号 */
    port?: number;

    /** 是否仅自动模式 */
    onlyAuto?: boolean;

    /** 是否禁用音频 */
    disableAudio?: boolean;

    /** 是否复用端口 */
    reUsePort?: boolean;

    /** SSRC字符串 */
    ssrc?: string;

    /** 类型 */
    type?: string;

    /**
     * TCP模式
     * 0: 不启用TCP监听
     * 1: 启用TCP监听
     * 2: TCP主动连接模式
     */
    tcpMode?: number;
}

/**
 * 截图参数
 */
export interface Snap {
    /**
     * 应用名称
     */
    app: string;

    /**
     * 流 ID / 流名称
     */
    stream: string;

    /**
     * 原始流地址
     */
    url: string;
}

/**
 * 拉流配置请求参数
 */
export interface PullConfig {
    /**
     * 应用名称
     */
    app: string;

    /**
     * 流 ID / 流名称
     */
    stream: string;

    /**
     * 原始 RTSP 流地址
     */
    url: string;

    /**
     * 是否启用音频
     * false: 不解析音频 / 静音
     * true: 解析音频
     */
    enable_audio: boolean;

    /**
     * 是否启用 MP4 录制
     * false: 不录制
     * true: 开启录制
     */
    enable_mp4: boolean;

    /**
     * RTP 传输类型
     * "0": 通常代表 UDP
     * "1": 通常代表 TCP
     * 具体需参考 ZLMediaKit 文档
     */
    rtp_type: string;

    /**
     * 拉流超时时间
     * 单位通常为秒 (s)
     */
    timeOut: number;
}

/**
 * 流媒体节点配置信息
 */
export interface MediaServer {
    createBy: string | null;
    createTime: string;
    updateBy: string | null;
    updateTime: string;
    remark: string | null;
    id: string;
    ip: string;
    hookIp: string;
    sdpIp: string;
    streamIp: string;
    httpPort: number;
    httpSslPort: number;
    rtmpPort: number;
    rtmpSslPort: number;
    rtpProxyPort: number;
    rtspPort: number;
    rtspSslPort: number;
    flvPort: number;
    flvSslPort: number;
    mp4Port: number;
    mp4SslPort: string | null;
    wsFlvPort: number;
    wsFlvSslPort: number;
    jttProxyPort: number;
    autoConfig: boolean;
    secret: string;
    type: string;
    rtpEnable: boolean;
    rtpPortRange: string;
    sendRtpPortRange: string;
    recordAssistPort: number;
    defaultServer: boolean;
    hookAliveInterval: number;
    recordPath: string;
    recordDay: number;
    transcodeSuffix: string | null;
    serverId: string;
    status: string;
}

/**
 * 流编码及状态信息
 */
export interface MediaInfo {
    app: string;
    stream: string;
    mediaServer: MediaServer;
    schema: string;
    readerCount: number;
    videoCodec: string;
    width: number;
    height: number;
    fps: number | null;
    loss: number | null;
    audioCodec: string | null;
    audioChannels: number | null;
    audioSampleRate: number | null;
    duration: number | null;
    online: boolean;
    originType: number;
    originTypeStr: string;
    originUrl: string;
    aliveSecond: number;
    bytesSpeed: number;
    callId: string | null;
    paramMap: Record<string, string>;
    serverId: string;
}

/**
 * 流地址响应对象
 */
export interface StreamContent {
    app: string;
    stream: string;
    ip: string | null;
    flv: string;
    https_flv: string;
    ws_flv: string;
    wss_flv: string;
    fmp4: string;
    https_fmp4: string;
    ws_fmp4: string;
    wss_fmp4: string;
    hls: string;
    https_hls: string;
    ws_hls: string;
    wss_hls: string;
    ts: string;
    https_ts: string;
    ws_ts: string;
    wss_ts: string | null;
    rtmp: string;
    rtmps: string | null;
    rtsp: string;
    rtsps: string | null;
    rtc: string;
    rtcs: string;
    mediaServerId: string;
    mediaInfo: MediaInfo;
    startTime: string | null;
    endTime: string | null;
    duration: number | null;
    downLoadFilePath: string | null;
    transcodeStream: string | null;
    progress: number;
    key: string | null;
    serverId: string;
}