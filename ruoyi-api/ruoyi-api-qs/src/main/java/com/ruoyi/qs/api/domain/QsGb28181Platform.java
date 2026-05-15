package com.ruoyi.qs.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 国标GB28181平台配置对象 qs_gb28181_platform
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QsGb28181Platform extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 是否启用：0-禁用，1-启用
     */
    private Integer enable;

    /**
     * 平台名称
     */
    private String name;

    /**
     * 平台国标编码（SIP服务器ID）
     */
    private String serverGbId;

    /**
     * 平台域（SIP域）
     */
    private String serverGbDomain;

    /**
     * 平台服务器IP地址
     */
    private String serverIp;

    /**
     * 平台服务器端口
     */
    private Integer serverPort;

    /**
     * 设备国标编码（本地SIP设备ID）
     */
    private String deviceGbId;

    /**
     * 设备IP地址
     */
    private String deviceIp;

    /**
     * 设备端口
     */
    private String devicePort;

    /**
     * SIP认证用户名
     */
    private String username;

    /**
     * SIP认证密码
     */
    private String password;

    /**
     * 注册有效期（秒）
     */
    private String expires;

    /**
     * 心跳超时时间（秒）
     */
    private String keepTimeout;

    /**
     * 传输协议：UDP/TCP
     */
    private String transport;

    /**
     * 行政区划编码
     */
    private String civilCode;

    /**
     * 设备厂商
     */
    private String manufacturer;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 安装地址
     */
    private String address;

    /**
     * 字符编码：GB2312/UTF-8
     */
    private String characterSet;

    /**
     * 是否支持云台控制：0-不支持，1-支持
     */
    private Integer ptz;

    /**
     * 是否启用RTCP：0-否，1-是
     */
    private Integer rtcp;

    /**
     * 状态：0-离线，1-在线
     */
    private Integer status;

    /**
     * 目录分组
     */
    private Integer catalogGroup;

    /**
     * 注册方式：1-IP注册，2-动态域名，3-主动上报
     */
    private Integer registerWay;

    /**
     * 保密属性
     */
    private Integer secrecy;

    /**
     * 是否作为消息通道：0-否，1-是
     */
    private Integer asMessageChannel;

    /**
     * 是否查询平台目录：0-否，1-是
     */
    private Integer catalogWithPlatform;

    /**
     * 是否查询分组目录：0-否，1-是
     */
    private Integer catalogWithGroup;

    /**
     * 是否查询区域目录：0-否，1-是
     */
    private Integer catalogWithRegion;

    /**
     * 是否自动推送通道：0-否，1-是
     */
    private Integer autoPushChannel;

    /**
     * 推流IP地址
     */
    private String sendStreamIp;

    /**
     * 流媒体服务器ID
     */
    private String serverId;

    /**
     * 关联设备数量
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer deviceCount;
}
