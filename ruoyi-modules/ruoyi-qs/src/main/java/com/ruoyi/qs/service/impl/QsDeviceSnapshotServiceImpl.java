
package com.ruoyi.qs.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.qs.api.domain.QsDeviceSnapshot;
import com.ruoyi.qs.mapper.QsDeviceSnapshotMapper;
import com.ruoyi.qs.service.IQsDeviceSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备抓图Service业务层处理
 *
 * @author ruoyi
 * @date 2026-05-17
 */
@Service
public class QsDeviceSnapshotServiceImpl implements IQsDeviceSnapshotService {
    @Autowired
    private QsDeviceSnapshotMapper qsDeviceSnapshotMapper;

    /**
     * 查询设备抓图
     *
     * @param id 设备抓图主键
     * @return 设备抓图
     */
    @Override
    public QsDeviceSnapshot selectQsDeviceSnapshotById(Long id) {
        return qsDeviceSnapshotMapper.selectQsDeviceSnapshotById(id);
    }

    /**
     * 查询设备抓图列表
     *
     * @param qsDeviceSnapshot 设备抓图
     * @return 设备抓图
     */
    @Override
    public List<QsDeviceSnapshot> selectQsDeviceSnapshotList(QsDeviceSnapshot qsDeviceSnapshot) {
        return qsDeviceSnapshotMapper.selectQsDeviceSnapshotList(qsDeviceSnapshot);
    }

    /**
     * 新增设备抓图
     *
     * @param qsDeviceSnapshot 设备抓图
     * @return 结果
     */
    @Override
    public int insertQsDeviceSnapshot(QsDeviceSnapshot qsDeviceSnapshot) {
        qsDeviceSnapshot.setCreateTime(DateUtils.getNowDate());
        qsDeviceSnapshot.setCreateBy(SecurityUtils.getUsername());
        return qsDeviceSnapshotMapper.insertQsDeviceSnapshot(qsDeviceSnapshot);
    }

    /**
     * 修改设备抓图
     *
     * @param qsDeviceSnapshot 设备抓图
     * @return 结果
     */
    @Override
    public int updateQsDeviceSnapshot(QsDeviceSnapshot qsDeviceSnapshot) {
        qsDeviceSnapshot.setUpdateTime(DateUtils.getNowDate());
        qsDeviceSnapshot.setUpdateBy(SecurityUtils.getUsername());
        return qsDeviceSnapshotMapper.updateQsDeviceSnapshot(qsDeviceSnapshot);
    }

    /**
     * 批量删除设备抓图
     *
     * @param ids 需要删除的设备抓图主键
     * @return 结果
     */
    @Override
    public int deleteQsDeviceSnapshotByIds(Long[] ids) {
        return qsDeviceSnapshotMapper.deleteQsDeviceSnapshotByIds(ids);
    }

    /**
     * 删除设备抓图信息
     *
     * @param id 设备抓图主键
     * @return 结果
     */
    @Override
    public int deleteQsDeviceSnapshotById(Long id) {
        return qsDeviceSnapshotMapper.deleteQsDeviceSnapshotById(id);
    }
}

