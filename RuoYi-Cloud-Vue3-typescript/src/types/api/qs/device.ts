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
  /** 上线时间 */
  lastOnlineTime?: string;
  /** 离线时间 */
  lastOfflineTime?: string;
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
  /** 设备型号/型号代码 */
  deviceModel?: string;
  /** 直播流地址 */
  liveAddress?: string;
  /** 通道号 */
  channel?: number;
  /** 报警通道号 */
  alarmChannelId?: number;
  /** 上线类型(1=主动添加, 2=主动注册) */
  onlineType?: string;
  /** 协议版本 */
  protocolVersion?: string;
  /** 状态(ENABLE/DEACTIVATE) */
  status?: string;
  /** 上线时间 */
  lastOnlineTime?: string;
  /** 离线时间 */
  lastOfflineTime?: string;
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
