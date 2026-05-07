package com.ruoyi.zlm.service;

import java.util.List;
import com.ruoyi.zlm.domain.ZlmRecordPlan;

/**
 * 录像计划Service接口
 * 
 * @author fengcheng
 * @date 2026-04-11
 */
public interface IZlmRecordPlanService 
{
    /**
     * 查询录像计划
     * 
     * @param id 录像计划主键
     * @return 录像计划
     */
    public ZlmRecordPlan selectZlmRecordPlanById(Long id);

    /**
     * 查询录像计划列表
     * 
     * @param zlmRecordPlan 录像计划
     * @return 录像计划集合
     */
    public List<ZlmRecordPlan> selectZlmRecordPlanList(ZlmRecordPlan zlmRecordPlan);

    /**
     * 新增录像计划
     * 
     * @param zlmRecordPlan 录像计划
     * @return 结果
     */
    public int insertZlmRecordPlan(ZlmRecordPlan zlmRecordPlan);

    /**
     * 修改录像计划
     * 
     * @param zlmRecordPlan 录像计划
     * @return 结果
     */
    public int updateZlmRecordPlan(ZlmRecordPlan zlmRecordPlan);

    /**
     * 批量删除录像计划
     * 
     * @param ids 需要删除的录像计划主键集合
     * @return 结果
     */
    public int deleteZlmRecordPlanByIds(Long[] ids);

    /**
     * 录像计划任务
     */
    void task();

    /**
     * 修改录像计划状态
     * 
     * @param zlmRecordPlan 录像计划
     * @return 结果
     */
    int updateZlmRecordPlanStatus(ZlmRecordPlan zlmRecordPlan);
}
