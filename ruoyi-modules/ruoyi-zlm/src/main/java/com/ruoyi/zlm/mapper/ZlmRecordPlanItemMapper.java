package com.ruoyi.zlm.mapper;

import com.ruoyi.zlm.domain.ZlmRecordPlanItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 录像计划管理通道Mapper接口
 *
 * @author fengcheng
 * @date 2026-04-11
 */
public interface ZlmRecordPlanItemMapper {
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
     * 删除录像计划管理通道
     *
     * @param id 录像计划管理通道主键
     * @return 结果
     */
    public int deleteZlmRecordPlanItemById(Long id);

    /**
     * 批量删除录像计划管理通道
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZlmRecordPlanItemByIds(Long[] ids);

    /**
     * 批量新增录像计划管理通道
     *
     * @param planId       录像计划id
     * @param planItemList 录像计划管理通道集合
     */
    void batchAddItem(@Param("planId") Long planId, @Param("planItemList") List<ZlmRecordPlanItem> planItemList);

    /**
     * 根据录像计划id删除通道
     *
     * @param planId 录像计划id
     */
    void cleanItems(Long planId);

    /**
     * 根据录像计划id获取通道列表
     *
     * @param planId
     * @return
     */
    List<ZlmRecordPlanItem> getItemList(Long planId);
}
