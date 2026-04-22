package com.ruoyi.qs.api.domain;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.domain.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 区域
 */
@Data
public class QsRegion extends BaseEntity implements Comparable<QsRegion> {
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
     * 父区域国标ID
     */
    private Integer parentId;

    /**
     * 父区域国标ID
     */
    private String parentDeviceId;

    @Override
    public int compareTo(@NotNull QsRegion qsRegion) {
        return Integer.compare(Integer.parseInt(this.deviceId), Integer.parseInt(qsRegion.getDeviceId()));
    }

    public static QsRegion getInstance(String commonRegionDeviceId, String commonRegionName, String commonRegionParentId) {
        QsRegion region = new QsRegion();
        region.setDeviceId(commonRegionDeviceId);
        region.setName(commonRegionName);
        region.setParentDeviceId(commonRegionParentId);
        region.setCreateTime(DateUtils.getNowDate());
        region.setUpdateTime(DateUtils.getNowDate());
        return region;
    }
}
