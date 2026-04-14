package com.ruoyi.qs.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 业务分组
 */
@Data
public class QsGroup extends BaseEntity implements Comparable<QsGroup> {
    /**
     * 数据库自增ID
     */
    private int id;

    /**
     * 区域国标编号
     */
    private String deviceId;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 父分组ID
     */
    private Integer parentId;

    /**
     * 父区域国标ID
     */
    private String parentDeviceId;

    /**
     * 所属的业务分组国标编号
     */
    private String businessGroup;

    /**
     * 行政区划
     */
    private String civilCode;

    /**
     * 别名
     */
    private String alias;

    @Override
    public int compareTo(@NotNull QsGroup region) {
        return Integer.compare(Integer.parseInt(this.deviceId), Integer.parseInt(region.getDeviceId()));
    }
}
