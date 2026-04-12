import type { PageDomain, BaseEntity } from "../common";

/** 视频监控设备配置分页查询参数 */
export interface DeviceQueryParams extends PageDomain {
  /** 设备名称 */
  deviceName?: string;
  /** IP地址 */
  ipAddress?: string;
  /** 直播流接入类型(1=RTSP,2=RTMP,3=FLV,4=HLS,5=ONVIF,6=视频文件,7=海康SDK,8=海康ISUP,9=大华SDK,10=宇视SDK,11=天地伟业SDK,12=国标28181,13=PUSH,14=部标1078) */
  type?: string;
  /** 状态(ENABLE/DEACTIVATE) */
  status?: string;
  /** 设备状态(OFFLINE=离线,ON=在线) */
  deviceStatus?: string;
}

/** 视频监控设备配置信息 */
export interface QsDevice extends BaseEntity {
  /** 主键ID */
  id?: number;
  /** 设备唯一标识 */
  deviceCode?: string;
  /** 设备名称 */
  deviceName?: string;
  /** IP地址 */
  ipAddress?: string;
  /** 端口号 */
  port?: number;
  /** 用户名 */
  userName?: string;
  /** 密码 */
  password?: string;
  /** 直播流接入类型(1=RTSP,2=RTMP,3=FLV,4=HLS,5=ONVIF,6=视频文件,7=海康SDK,8=海康ISUP,9=大华SDK,10=宇视SDK,11=天地伟业SDK,12=国标28181,13=PUSH,14=部标1078) */
  type?: string;
  /** 设备类型(0=IPC, 1=NVR) */
  deviceType?: number;
  /** 直播流地址 */
  liveAddress?: string;
  /** 通道号 */
  channel?: number;
  /** 报警通道号 */
  alarmChannelId?: number;
  /** 状态(ENABLE/DEACTIVATE) */
  status?: string;
  /** 码流类型(1=主码流,2=子码流,3=第三码流) */
  streamType?: string;
  /** 经度 */
  longitude?: string;
  /** 纬度 */
  latitude?: string;
  /** 国标编码 */
  gbCode?: string;
  /** 传输协议(UDP/TCP) */
  protocol?: string;
  /** 设备状态(OFFLINE=离线,ON=在线) */
  deviceStatus?: string;
  /** 上线类型(1=主动添加, 2=主动注册) */
  onlineType?: string;
  /** 开启音频(0=关闭, 1=开启) */
  enableAudio?: string;
  /** 开启mp4录制(0=关闭, 1=开启) */
  enableMp4?: string;
  /** 流状态(0=停止,1=直播中) */
  streamStatus?: string;
  /** 当前拉流使用的流媒体服务ID */
  mediaServerId?: string;
  /** 拉流代理时zlm返回的key，用于停止拉流代理 */
  streamKey?: string;
  /** 是否 无人观看时自动停用 */
  enableDisableNoneReader?: string;
  /** 截图 */
  snap?: string;
  /** flv 类型（ws/flv） */
  flvType?: string;
  /** omvif 验证类型（1=WS-UsernameToken,2=Digest） */
  onvifAuth?: string;
  /** onvif 主机名 */
  onvifHostName?: string;
  /** 创建者 */
  createBy?: string;
  /** 创建时间 */
  createTime?: string;
  /** 更新者 */
  updateBy?: string;
  /** 更新时间 */
  updateTime?: string;
  /** 备注 */
  remark?: string;
}

/**
 * 录制计划-添加/编辑参数
 */
export interface RecordPlanParam {
  /**
   * 关联的设备ID列表
   * 注意：会为设备下的所有通道关联此录制计划
   * 如果 channelId 存在，则此项不生效
   */
  deviceIds?: number[];

  /**
   * 全部关联 / 全部取消关联
   */
  allLink?: boolean;

  /**
   * 录制计划ID
   * 如果 ID 为空，则表示删除关联的计划
   */
  planId?: number;
}