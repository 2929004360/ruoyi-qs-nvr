package com.ruoyi.qs.api.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 国标GB28181平台通道关联对象 qs_gb28181_platform_channel
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QsGb28181PlatformChannel extends BaseEntity {
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
