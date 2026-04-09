package com.ruoyi.qs.api.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频监控设备对象 qs_device
 * 
 * @author fengcheng
 * @date 2026-03-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QsDevice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 设备唯一标识 */
    private String deviceCode;

    /** 设备名称 */
    private String deviceName;

    /** IP地址 */
    private String ipAddress;

    /** 端口号 */
    private Integer port;

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String password;

    /** 直播流接入类型(1=RTSP,2=RTMP,3=FLV,4=HLS,5=ONVIF,6=视频文件,7=海康SDK,8=海康ISUP,9=大华SDK,10=宇视SDK,11=天地伟业SDK,12=国标28181,13=PUSH,14=部标1078) */
    private String type;

    /** 设备类型(0=IPC, 1=NVR) */
    private Integer deviceType;

    /** 直播流地址 */
    private String liveAddress;

    /** 通道号 */
    private Integer channel;

    /** 报警通道号 */
    private Integer alarmChannelId;

    /** 状态(ENABLE/DEACTIVATE) */
    private String status;

    /** 码流类型(1=主码流,2=子码流,3=第三码流) */
    private String streamType;

    /** 经度 */
    private String longitude;

    /** 纬度 */
    private String latitude;

    /** 国标编码 */
    private String gbCode;

    /** 传输协议(UDP/TCP) */
    private String protocol;

    /** 设备状态(OFFLINE=离线,ON=在线) */
    private String deviceStatus;

    /** 上线类型(1=主动添加, 2=主动注册) */
    private String onlineType;

    /** 开启音频(0=关闭, 1=开启) */
    private String enableAudio;

    /** 开启mp4录制(0=关闭, 1=开启) */
    private String enableMp4;

    /** 流状态(0=停止,1=直播中) */
    private String streamStatus;

    /** 当前拉流使用的流媒体服务ID */
    private String mediaServerId;

    /** 拉流代理时zlm返回的key，用于停止拉流代理 */
    private String streamKey;

    /** 是否 无人观看时自动停用 */
    private String enableDisableNoneReader;

    /** 截图路径 */
    private String snap;

    /** flv 类型（ws/flv） */
    private String flvType;

    /** omvif 验证类型（1=WS-UsernameToken,2=Digest */
    private String onvifAuth;

    /** onvif 主机名 */
    private String onvifHostName;
}
