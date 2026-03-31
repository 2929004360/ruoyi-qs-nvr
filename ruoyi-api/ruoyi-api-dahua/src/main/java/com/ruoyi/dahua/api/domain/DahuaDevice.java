package com.ruoyi.dahua.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 大华设备对象 dahua_device
 *
 * @author fengcheng
 * @date 2025-06-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DahuaDevice implements Serializable {

    /**
     * ip
     */
    private String ip;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 端口
     */
    private String port;
}
