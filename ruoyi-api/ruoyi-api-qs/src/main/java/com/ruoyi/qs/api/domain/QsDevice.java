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

    /** 录制计划id */
    private String recordPlanId;

    /** 国标-行政区域 */
    private String gbCivilCode;

    /** 国标-义务分组 */
    private String gbBusinessGroupId;

    /** 国标-父节点ID */
    private String gbParentId;

    /** 生产厂商 */
    private String manufacturer;

    /** 安装地址 */
    private String address;

    /**
     * 摄像机结构类型,标识摄像机类型: 1-球机; 2-半球; 3-固定枪机; 4-遥控枪机;5-遥控半球;6-多目设备的全景/拼接通道;7-多目设备的分割通道
     */
    private Integer ptzType;

    /**
     * gb设备id
     */
    private String gbDeviceId;

    /**
     * gb通道id
     */
    private String gbChannelId;

    /**
     * 数据流传输模式
     * UDP:udp传输
     * TCP-ACTIVE：tcp主动模式
     * TCP-PASSIVE：tcp被动模式
     */
    private String streamMode;

    /** JT1078-手机号 */
    private String jtMobileNo;

    /** JT1078-车牌号 */
    private String jtPlateNo;

    /** JT1078-车牌颜色 */
    private String jtPlateColor;

    /** 回放流状态(0=停止,1=回放中) */
    private String playbackStreamStatus;

    /** 回放使用的流媒体服务ID */
    private String playbackMediaServerId;

    /** 回放时zlm返回的key，用于停止回放 */
    private String playbackStreamKey;

    /** 国标-设备厂商 */
    private String gbManufacturer;

    /** 国标-设备型号 */
    private String gbModel;

    /** 国标-设备归属 */
    private String gbOwner;

    /** 国标-警区 */
    private String gbBlock;

    /** 国标-安装地址 */
    private String gbAddress;

    /** 国标-是否有子设备(必选)1有,0没有 */
    private Integer gbParental;

    /** 国标-信令安全模式 */
    private Integer gbSafetyWay;

    /** 国标-注册方式 */
    private Integer gbRegisterWay;

    /** 国标-证书序列号 */
    private String gbCertNum;

    /** 国标-证书有效标识, 缺省为0;证书有效标识:0:无效 1:有效 */
    private Integer gbCertifiable;

    /** 国标-无效原因码(有证书且证书无效的设备必选) */
    private Integer gbErrCode;

    /** 国标-证书终止有效期(有证书且证书无效的设备必选) */
    private String gbEndTime;

    /** 国标-保密属性(必选)缺省为0;0-不涉密,1-涉密 */
    private Integer gbSecrecy;

    /** 国标-设备/系统IPv4/IPv6地址 */
    private String gbIpAddress;

    /** 国标-设备/系统端口 */
    private Integer gbPort;

    /** 国标-设备口令 */
    private String gbPassword;

    /** 国标-设备状态 */
    private String gbStatus;

    /** 国标-经度 WGS-84坐标系 */
    private Double gbLongitudeDouble;

    /** 国标-纬度 WGS-84坐标系 */
    private Double gbLatitudeDouble;

    /** 国标-摄像机位置类型扩展。1-省际检查站、2-党政机关、3-车站码头、4-中心广场、5-体育场馆、6-商业中心、7-宗教场所、8-校园周边、9-治安复杂区域、10-交通干线 */
    private Integer gbPositionType;

    /** 国标-摄像机安装位置室外、室内属性。1-室外、2-室内。 */
    private Integer gbRoomType;

    /** 国标-用途属性， 1-治安、2-交通、3-重点。 */
    private Integer gbUseType;

    /** 国标-摄像机补光属性。1-无补光;2-红外补光;3-白光补光;4-激光补光;9-其他 */
    private Integer gbSupplyLightType;

    /** 国标-摄像机监视方位(光轴方向)属性。1-东(西向东)、2-西(东向西)、3-南(北向南)、4-北(南向北)、5-东南(西北到东南)、6-东北(西南到东北)、7-西南(东北到西南)、8-西北(东南到西北) */
    private Integer gbDirectionType;

    /** 国标-摄像机支持的分辨率,可多值 */
    private String gbResolution;

    /** 国标-下载倍速(可选),可多值 */
    private String gbDownloadSpeed;

    /** 国标-空域编码能力,取值0-不支持;1-1级增强(1个增强层);2-2级增强(2个增强层);3-3级增强(3个增强层) */
    private Integer gbSvcSpaceSupportMod;

    /** 国标-时域编码能力,取值0-不支持;1-1级增强;2-2级增强;3-3级增强(可选) */
    private Integer gbSvcTimeSupportMode;

    /** GPS高度 */
    private Double gpsAltitude;

    /** GPS速度 */
    private Double gpsSpeed;

    /** GPS方向 */
    private Double gpsDirection;

    /** GPS更新时间 */
    private String gpsTime;

    /** 是否支持对讲 1支持,0不支持 */
    private Integer enableBroadcast;
}
