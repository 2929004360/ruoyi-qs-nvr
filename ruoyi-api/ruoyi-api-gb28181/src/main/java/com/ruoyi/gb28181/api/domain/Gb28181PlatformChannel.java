package com.ruoyi.gb28181.api.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 国标GB28181平台通道关联对象 gb28181_platform_channel
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Gb28181PlatformChannel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 国标GB28181平台ID
     */
    private Long platformId;

    /**
     * 设备ID
     */
    private Long deviceId;
}
