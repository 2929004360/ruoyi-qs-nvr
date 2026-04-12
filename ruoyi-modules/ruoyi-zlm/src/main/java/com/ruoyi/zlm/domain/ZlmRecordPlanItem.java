package com.ruoyi.zlm.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 录像计划管理通道对象 zlm_record_plan_item
 *
 * @author fengcheng
 * @date 2026-04-11
 */
@Data
public class ZlmRecordPlanItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long start;

    private Long stop;

    private Long weekDay;

    /**
     * 计划id
     */
    private Long planId;
}
