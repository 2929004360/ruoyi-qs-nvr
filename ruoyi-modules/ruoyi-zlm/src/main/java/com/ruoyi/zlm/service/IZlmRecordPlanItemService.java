package com.ruoyi.zlm.service;

import java.util.List;
import com.ruoyi.zlm.domain.ZlmRecordPlanItem;

/**
 * 录像计划管理通道Service接口
 * 
 * @author fengcheng
 * @date 2026-04-11
 */
public interface IZlmRecordPlanItemService 
{
    /**
     * 查询录像计划管理通道
     * 
     * @param id 录像计划管理通道主键
     * @return 录像计划管理通道
     */
    public ZlmRecordPlanItem selectZlmRecordPlanItemById(Long id);

    /**
     * 查询录像计划管理通道列表
     * 
     * @param zlmRecordPlanItem 录像计划管理通道
     * @return 录像计划管理通道集合
     */
    public List<ZlmRecordPlanItem> selectZlmRecordPlanItemList(ZlmRecordPlanItem zlmRecordPlanItem);

    /**
     * 新增录像计划管理通道
     * 
     * @param zlmRecordPlanItem 录像计划管理通道
     * @return 结果
     */
    public int insertZlmRecordPlanItem(ZlmRecordPlanItem zlmRecordPlanItem);

    /**
     * 修改录像计划管理通道
     * 
     * @param zlmRecordPlanItem 录像计划管理通道
     * @return 结果
     */
    public int updateZlmRecordPlanItem(ZlmRecordPlanItem zlmRecordPlanItem);

    /**
     * 批量删除录像计划管理通道
     * 
     * @param ids 需要删除的录像计划管理通道主键集合
     * @return 结果
     */
    public int deleteZlmRecordPlanItemByIds(Long[] ids);

    /**
     * 删除录像计划管理通道信息
     * 
     * @param id 录像计划管理通道主键
     * @return 结果
     */
    public int deleteZlmRecordPlanItemById(Long id);
}
