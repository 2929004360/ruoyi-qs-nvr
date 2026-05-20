
package com.ruoyi.qs.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.qs.api.domain.QsDeviceAlarm;
import com.ruoyi.qs.mapper.QsDeviceAlarmMapper;
import com.ruoyi.qs.service.IQsDeviceAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备告警Service业务层处理
 *
 * @author ruoyi
 * @date 2026-05-18
 */
@Service
public class QsDeviceAlarmServiceImpl implements IQsDeviceAlarmService {
    @Autowired
    private QsDeviceAlarmMapper qsDeviceAlarmMapper;

    /**
     * 查询设备告警
     *
     * @param id 设备告警主键
     * @return 设备告警
     */
    @Override
    public QsDeviceAlarm selectQsDeviceAlarmById(Long id) {
        return qsDeviceAlarmMapper.selectQsDeviceAlarmById(id);
    }

    /**
     * 查询设备告警列表
     *
     * @param qsDeviceAlarm 设备告警
     * @return 设备告警
     */
    @Override
    public List<QsDeviceAlarm> selectQsDeviceAlarmList(QsDeviceAlarm qsDeviceAlarm) {
        return qsDeviceAlarmMapper.selectQsDeviceAlarmList(qsDeviceAlarm);
    }

    /**
     * 新增设备告警
     *
     * @param qsDeviceAlarm 设备告警
     * @return 结果
     */
    @Override
    public int insertQsDeviceAlarm(QsDeviceAlarm qsDeviceAlarm) {
        qsDeviceAlarm.setCreateTime(DateUtils.getNowDate());
        try {
            qsDeviceAlarm.setCreateBy(SecurityUtils.getUsername());
        } catch (Exception e) {
            // 内部调用时可能没有用户信息，设置默认值
            qsDeviceAlarm.setCreateBy("system");
        }
        return qsDeviceAlarmMapper.insertQsDeviceAlarm(qsDeviceAlarm);
    }

    /**
     * 修改设备告警
     *
     * @param qsDeviceAlarm 设备告警
     * @return 结果
     */
    @Override
    public int updateQsDeviceAlarm(QsDeviceAlarm qsDeviceAlarm) {
        qsDeviceAlarm.setUpdateTime(DateUtils.getNowDate());
        qsDeviceAlarm.setUpdateBy(SecurityUtils.getUsername());
        return qsDeviceAlarmMapper.updateQsDeviceAlarm(qsDeviceAlarm);
    }

    /**
     * 批量删除设备告警
     *
     * @param ids 需要删除的设备告警主键
     * @return 结果
     */
    @Override
    public int deleteQsDeviceAlarmByIds(Long[] ids) {
        return qsDeviceAlarmMapper.deleteQsDeviceAlarmByIds(ids);
    }

    /**
     * 删除设备告警信息
     *
     * @param id 设备告警主键
     * @return 结果
     */
    @Override
    public int deleteQsDeviceAlarmById(Long id) {
        return qsDeviceAlarmMapper.deleteQsDeviceAlarmById(id);
    }

    /**
     * 批量处理设备告警
     *
     * @param ids 需要处理的设备告警主键集合
     * @param handler 处理人
     * @return 结果
     */
    @Override
    public int batchHandleQsDeviceAlarm(Long[] ids, String handler) {
        return qsDeviceAlarmMapper.batchHandleQsDeviceAlarm(ids, handler, DateUtils.getNowDate());
    }
}
