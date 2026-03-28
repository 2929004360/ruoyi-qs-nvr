package com.ruoyi.haikang.api.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

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
public class LoginDevice {

    /**
     * 设备IP
     */
    @NotBlank(groups = {LoginDevice.class}, message = "IP不能为空")
    private String ipAddress;

    /**
     * 设备端口
     */
    @NotNull(groups = {LoginDevice.class}, message = "端口不能为空")
    private Short port;

    /**
     * 用户名
     */
    @NotBlank(groups = {LoginDevice.class}, message = "用户名不能为空")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(groups = {LoginDevice.class}, message = "密码不能为空")
    private String password;
}
