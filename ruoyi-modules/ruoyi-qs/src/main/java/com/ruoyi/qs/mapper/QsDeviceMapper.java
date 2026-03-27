package com.ruoyi.qs.mapper;

import java.util.List;
import com.ruoyi.qs.domain.QsDevice;

/**
 * 视频监控设备Mapper接口
 * 
 * @author fengcheng
 * @date 2026-03-27
 */
public interface QsDeviceMapper 
{
    /**
     * 查询视频监控设备
     * 
     * @param id 视频监控设备主键
     * @return 视频监控设备
     */
    public QsDevice selectQsDeviceById(Long id);

    /**
     * 查询视频监控设备列表
     * 
     * @param qsDevice 视频监控设备
     * @return 视频监控设备集合
     */
    public List<QsDevice> selectQsDeviceList(QsDevice qsDevice);

    /**
     * 新增视频监控设备
     * 
     * @param qsDevice 视频监控设备
     * @return 结果
     */
    public int insertQsDevice(QsDevice qsDevice);

    /**
     * 修改视频监控设备
     * 
     * @param qsDevice 视频监控设备
     * @return 结果
     */
    public int updateQsDevice(QsDevice qsDevice);

    /**
     * 删除视频监控设备
     * 
     * @param id 视频监控设备主键
     * @return 结果
     */
    public int deleteQsDeviceById(Long id);

    /**
     * 批量删除视频监控设备
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQsDeviceByIds(Long[] ids);
}
