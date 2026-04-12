package com.ruoyi.zlm.mapper;

import com.ruoyi.zlm.domain.ZlmRecordPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 录像计划Mapper接口
 *
 * @author fengcheng
 * @date 2026-04-11
 */
public interface ZlmRecordPlanMapper {
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
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZlmRecordPlanByIds(Long[] ids);

    /**
     * 查询现在需要录像的通道Id
     *
     * @param week  星期几
     * @param index 第几个
     * @return
     */
    List<Long> queryRecordIng(@Param("week") int week, @Param("index") int index);
}
