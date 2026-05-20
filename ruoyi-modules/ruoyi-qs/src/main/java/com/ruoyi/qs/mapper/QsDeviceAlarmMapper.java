
package com.ruoyi.qs.mapper;

import com.ruoyi.qs.api.domain.QsDeviceAlarm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备告警Mapper接口
 *
 * @author ruoyi
 * @date 2026-05-18
 */
public interface QsDeviceAlarmMapper {
    /**
     * 查询设备告警
     *
     * @param id 设备告警主键
     * @return 设备告警
     */
    public QsDeviceAlarm selectQsDeviceAlarmById(Long id);

    /**
     * 查询设备告警列表
     *
     * @param qsDeviceAlarm 设备告警
     * @return 设备告警集合
     */
    public List<QsDeviceAlarm> selectQsDeviceAlarmList(QsDeviceAlarm qsDeviceAlarm);

    /**
     * 新增设备告警
     *
     * @param qsDeviceAlarm 设备告警
     * @return 结果
     */
    public int insertQsDeviceAlarm(QsDeviceAlarm qsDeviceAlarm);

    /**
     * 修改设备告警
     *
     * @param qsDeviceAlarm 设备告警
     * @return 结果
     */
    public int updateQsDeviceAlarm(QsDeviceAlarm qsDeviceAlarm);

    /**
     * 删除设备告警
     *
     * @param id 设备告警主键
     * @return 结果
     */
    public int deleteQsDeviceAlarmById(Long id);

    /**
     * 批量删除设备告警
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQsDeviceAlarmByIds(Long[] ids);

    /**
     * 批量处理设备告警
     *
     * @param ids 需要处理的数据主键集合
     * @param handler 处理人
     * @param handleTime 处理时间
     * @return 结果
     */
    public int batchHandleQsDeviceAlarm(@Param("ids") Long[] ids, @Param("handler") String handler, @Param("handleTime") java.util.Date handleTime);
}
