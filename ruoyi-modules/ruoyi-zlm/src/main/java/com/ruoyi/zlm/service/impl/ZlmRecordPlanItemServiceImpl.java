package com.ruoyi.zlm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zlm.mapper.ZlmRecordPlanItemMapper;
import com.ruoyi.zlm.domain.ZlmRecordPlanItem;
import com.ruoyi.zlm.service.IZlmRecordPlanItemService;

/**
 * 录像计划管理通道Service业务层处理
 * 
 * @author fengcheng
 * @date 2026-04-11
 */
@Service
public class ZlmRecordPlanItemServiceImpl implements IZlmRecordPlanItemService 
{
    @Autowired
    private ZlmRecordPlanItemMapper zlmRecordPlanItemMapper;

    /**
     * 查询录像计划管理通道
     * 
     * @param id 录像计划管理通道主键
     * @return 录像计划管理通道
     */
    @Override
    public ZlmRecordPlanItem selectZlmRecordPlanItemById(Long id)
    {
        return zlmRecordPlanItemMapper.selectZlmRecordPlanItemById(id);
    }

    /**
     * 查询录像计划管理通道列表
     * 
     * @param zlmRecordPlanItem 录像计划管理通道
     * @return 录像计划管理通道
     */
    @Override
    public List<ZlmRecordPlanItem> selectZlmRecordPlanItemList(ZlmRecordPlanItem zlmRecordPlanItem)
    {
        return zlmRecordPlanItemMapper.selectZlmRecordPlanItemList(zlmRecordPlanItem);
    }

    /**
     * 新增录像计划管理通道
     * 
     * @param zlmRecordPlanItem 录像计划管理通道
     * @return 结果
     */
    @Override
    public int insertZlmRecordPlanItem(ZlmRecordPlanItem zlmRecordPlanItem)
    {
        return zlmRecordPlanItemMapper.insertZlmRecordPlanItem(zlmRecordPlanItem);
    }

    /**
     * 修改录像计划管理通道
     * 
     * @param zlmRecordPlanItem 录像计划管理通道
     * @return 结果
     */
    @Override
    public int updateZlmRecordPlanItem(ZlmRecordPlanItem zlmRecordPlanItem)
    {
        return zlmRecordPlanItemMapper.updateZlmRecordPlanItem(zlmRecordPlanItem);
    }

    /**
     * 批量删除录像计划管理通道
     * 
     * @param ids 需要删除的录像计划管理通道主键
     * @return 结果
     */
    @Override
    public int deleteZlmRecordPlanItemByIds(Long[] ids)
    {
        return zlmRecordPlanItemMapper.deleteZlmRecordPlanItemByIds(ids);
    }

    /**
     * 删除录像计划管理通道信息
     * 
     * @param id 录像计划管理通道主键
     * @return 结果
     */
    @Override
    public int deleteZlmRecordPlanItemById(Long id)
    {
        return zlmRecordPlanItemMapper.deleteZlmRecordPlanItemById(id);
    }
}
