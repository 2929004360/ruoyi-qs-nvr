package com.ruoyi.qs.domain;

import lombok.Data;

import java.util.List;

/**
 * 录制计划-添加/编辑参数
 */
@Data
public class RecordPlanParam {

    /**
     * 关联的设备ID，会为设备下的所有通道关联此录制计划，channelId存在是此项不生效，
     */
    private List<Long> deviceIds;

    /**
     * 全部关联/全部取消关联
     */
    private Boolean allLink;

    /**
     * 录制计划ID, ID为空是删除关联的计划
     */
    private Long planId;
}
