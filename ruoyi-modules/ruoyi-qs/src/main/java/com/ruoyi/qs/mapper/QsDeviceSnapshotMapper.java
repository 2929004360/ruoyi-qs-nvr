
package com.ruoyi.qs.mapper;

import com.ruoyi.qs.api.domain.QsDeviceSnapshot;

import java.util.List;

/**
 * 设备抓图Mapper接口
 *
 * @author ruoyi
 * @date 2026-05-17
 */
public interface QsDeviceSnapshotMapper {
    /**
     * 查询设备抓图
     *
     * @param id 设备抓图主键
     * @return 设备抓图
     */
    public QsDeviceSnapshot selectQsDeviceSnapshotById(Long id);

    /**
     * 查询设备抓图列表
     *
     * @param qsDeviceSnapshot 设备抓图
     * @return 设备抓图集合
     */
    public List<QsDeviceSnapshot> selectQsDeviceSnapshotList(QsDeviceSnapshot qsDeviceSnapshot);

    /**
     * 新增设备抓图
     *
     * @param qsDeviceSnapshot 设备抓图
     * @return 结果
     */
    public int insertQsDeviceSnapshot(QsDeviceSnapshot qsDeviceSnapshot);

    /**
     * 修改设备抓图
     *
     * @param qsDeviceSnapshot 设备抓图
     * @return 结果
     */
    public int updateQsDeviceSnapshot(QsDeviceSnapshot qsDeviceSnapshot);

    /**
     * 删除设备抓图
     *
     * @param id 设备抓图主键
     * @return 结果
     */
    public int deleteQsDeviceSnapshotById(Long id);

    /**
     * 批量删除设备抓图
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQsDeviceSnapshotByIds(Long[] ids);
}

