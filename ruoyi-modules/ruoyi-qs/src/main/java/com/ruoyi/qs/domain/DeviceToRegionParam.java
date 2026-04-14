package com.ruoyi.qs.domain;

import lombok.Data;

import java.util.List;

/**
 * 提交行政区划关联多个设备的参数
 */
@Data
public class DeviceToRegionParam {

    /**
     * 行政区划编号
     */
    private String civilCode;

    /**
     * 选择的设备， 和all参数二选一
     */
    private List<Long> deviceIds;

    /**
     * 所有通道， 和channelIds参数二选一
     */
    private Boolean all;

}
