package com.ruoyi.dahua.api.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * 海康设备登录信息
 *
 * @FileName LoginDevice
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class LoginDevice implements Serializable {

    /**
     * 设备IP
     */
    @NotBlank(groups = {LoginDevice.class}, message = "IP不能为空")
    private String ipAddress;

    /**
     * 设备端口
     */
    @NotNull(groups = {LoginDevice.class}, message = "端口不能为空")
    private Integer port;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 设备id
     */
    private String deviceId;

    /** 上线类型(1=主动添加, 2=主动注册) */
    private String onlineType;
}
