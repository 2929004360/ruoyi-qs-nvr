package com.ruoyi.zlm.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 录像计划对象 zlm_record_plan
 *
 * @author fengcheng
 * @date 2026-04-11
 */
@Data
public class ZlmRecordPlan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 是否开启定时截图
     */
    private String snap;

    /**
     * 计划关联通道数量
     */
    private Integer channelCount;

    /**
     * 计划名称
     */
    private String name;

    /**
     * 是否启用
     */
    private String status;

    /**
     * 计划内容
     */
    private List<ZlmRecordPlanItem> planItemList;
}
