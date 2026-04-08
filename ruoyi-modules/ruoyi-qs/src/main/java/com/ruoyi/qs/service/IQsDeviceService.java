package com.ruoyi.qs.service;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.task.StreamDetector;

import java.util.List;
import java.util.Set;

/**
 * 视频监控设备Service接口
 * 
 * @author fengcheng
 * @date 2026-03-27
 */
public interface IQsDeviceService 
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
     * 批量删除视频监控设备
     * 
     * @param ids 需要删除的视频监控设备主键集合
     * @return 结果
     */
    public int deleteQsDeviceByIds(Long[] ids);

    /**
     * 删除视频监控设备信息
     * 
     * @param id 视频监控设备主键
     * @return 结果
     */
    public int deleteQsDeviceById(Long id);

    /**
     * 状态修改
     *
     * @param qsDevice 视频监控设备
     * @return
     */
    int updateQsDeviceStatus(QsDevice qsDevice);

    /**
     * 更新设备在线状态
     *
     * @param onlineDeviceSet 在线设备集合
     * @param deviceStatus    设备状态
     * @return
     */
    Boolean updateQsDeviceStatusList(Set<Long> onlineDeviceSet, String deviceStatus);

    /**
     * 修改视频监控设备
     *
     * @param qsDevice 视频监控设备
     * @return
     */
    int editQsDevice(QsDevice qsDevice);

    /**
     * 更具流id获取视频监控设备
     *
     * @param stream 流id
     * @return
     */
    QsDevice getQsDeviceStream(String stream);

    /**
     * 修改所有设备播状态离线和设备状态离线
     */
    void updateAllQsDevicesToOffline();

    /**
     * 获取所有视频监控设备流地址
     *
     * @return
     */
    List<QsDevice> fetchAllQsDeviceStreamUrls();

    /**
     * 更新所有视频监控设备流地址
     *
     * @param newQsDeviceList
     */
    void updateAllQsDeviceStreamUrls(List<QsDevice> newQsDeviceList);

    /**
     * 任务
     */
    void task();
}
