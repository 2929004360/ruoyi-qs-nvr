package com.ruoyi.jt1078.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 部标1078设备
 *
 * @author fengcheng
 */
@Data
@Schema(description = "部标1078设备")
public class Jt1078Device implements Serializable {

    @Schema(description = "设备id")
    private String deviceId;

    @Schema(description = "设备手机号")
    private String mobileNo;

    @Schema(description = "车牌号")
    private String plateNo;

    @Schema(description = "省域ID")
    private Integer provinceId;

    @Schema(description = "市县域ID")
    private Integer cityId;

    @Schema(description = "制造商ID")
    private String makerId;

    @Schema(description = "终端型号")
    private String deviceModel;

    @Schema(description = "车牌颜色")
    private Integer plateColor;

    @Schema(description = "是否在线")
    private Boolean online;

    @Schema(description = "协议版本号")
    private Integer protocolVersion;
}
