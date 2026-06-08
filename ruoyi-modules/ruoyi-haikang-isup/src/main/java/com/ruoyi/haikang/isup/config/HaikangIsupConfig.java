package com.ruoyi.haikang.isup.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "haikang-isup")
public class HaikangIsupConfig {

    /**
     * 注册服务器监听地址(服务器本地地址)
     */
    private CmsServer cmsServer = new CmsServer();

    /**
     * DAS地址（此处的IP和端口与设备网页端设置的平台IP地址和端口一致）
     */
    private DasServer dasServer = new DasServer();

    /**
     * 取流服务器地址端口（公网对接填入公网地址和端口）
     */
    private SmsServer smsServer = new SmsServer();

    /**
     * 回放流媒体服务器地址端口（公网对接填入公网地址和端口）
     */
    private SmsBackServer smsBackServer = new SmsBackServer();

    /**
     * 语音流媒体服务器地址端口（公网对接填入公网地址和端口）
     */
    private VoiceSmsServer voiceSmsServer = new VoiceSmsServer();

    /**
     * 报警服务器地址（公网对接填入公网地址和端口）
     */
    private AlarmServer alarmServer = new AlarmServer();

    /**
     * 图片存储服务器地址：IUSP5.0版本接入端口建议固定为6011
     */
    private PicServer picServer = new PicServer();

    /**
     * ISUP5.0登录秘钥
     */
    private String isupKey;

    /**
     * 报警服务器打印事件模式
     * file=事件保存到文件中
     * console=事件打印在控制台上
     */
    private String eventInfoPrintType;

    @Data
    public static class CmsServer {
        private String ip;
        private int port;
        private String listenIp;
        private int listenPort;
    }

    @Data
    public static class DasServer {
        private String ip;
        private int port;
    }

    @Data
    public static class SmsServer {
        private String ip;
        private int port;
        private String listenIp;
        private int listenPort;
    }

    @Data
    public static class SmsBackServer {
        private String ip;
        private int port;
        private String listenIp;
        private int listenPort;
    }

    @Data
    public static class VoiceSmsServer {
        private String ip;
        private int port;
        private String listenIp;
        private int listenPort;
    }

    @Data
    public static class AlarmServer {
        private String ip;
        private int tcpPort;
        private int udpPort;
        private Boolean alarmStorage;
        private int type; // 0=UDP, 1=UDP+TCP, 2=MQTT
        private String listenIp;
        private int listenTcpPort;
        private int listenUdpPort;
    }

    @Data
    public static class PicServer {
        private String ip;
        private int port;
        private int type; // 0=Tomcat, 1=VRB, 2=云存储, 3=KMS, 4=ISUP5.0
        private String listenIp;
        private int listenPort;
    }
}