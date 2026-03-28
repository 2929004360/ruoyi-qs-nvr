package com.ruoyi.qs.api.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

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
    @Excel(name = "设备唯一标识")
    private String deviceCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** IP地址 */
    @Excel(name = "IP地址")
    private String ipAddress;

    /** 端口号 */
    @Excel(name = "端口号")
    private Short port;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 直播流接入类型(1=RTSP,2=RTMP,3=FLV,4=HLS,5=ONVIF,6=视频文件,7=海康SDK,8=海康ISUP,9=大华SDK,10=宇视SDK,11=天地伟业SDK,12=国标28181,13=PUSH,14=部标1078) */
    @Excel(name = "直播流接入类型(1=RTSP,2=RTMP,3=FLV,4=HLS,5=ONVIF,6=视频文件,7=海康SDK,8=海康ISUP,9=大华SDK,10=宇视SDK,11=天地伟业SDK,12=国标28181,13=PUSH,14=部标1078)")
    private String type;

    /** 设备类型(0=IPC, 1=NVR) */
    @Excel(name = "设备类型(0=IPC, 1=NVR)")
    private Integer deviceType;

    /** 设备型号/型号代码 */
    @Excel(name = "设备型号/型号代码")
    private String deviceModel;

    /** 直播流地址 */
    @Excel(name = "直播流地址")
    private String liveAddress;

    /** 通道号 */
    @Excel(name = "通道号")
    private Integer channel;

    /** 报警通道号 */
    @Excel(name = "报警通道号")
    private Integer alarmChannelId;

    /** 上线类型(1=主动添加, 2=主动注册) */
    @Excel(name = "上线类型(1=主动添加, 2=主动注册)")
    private String onlineType;

    /** 协议版本 */
    @Excel(name = "协议版本")
    private String protocolVersion;

    /** 状态(ENABLE/DEACTIVATE) */
    @Excel(name = "状态(ENABLE/DEACTIVATE)")
    private String status;

    /** 上线时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "上线时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastOnlineTime;

    /** 离线时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "离线时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastOfflineTime;
}
