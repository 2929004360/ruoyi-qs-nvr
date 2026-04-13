package com.ruoyi.qs.mapper;

import com.ruoyi.qs.api.domain.QsDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 视频监控设备Mapper接口
 *
 * @author fengcheng
 * @date 2026-03-27
 */
public interface QsDeviceMapper {
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

    /**
     * 状态修改
     *
     * @param id     视频监控设备主键
     * @param status 状态
     * @return
     */
    int updateQsDeviceStatus(@Param("id") Long id, @Param("status") String status);

    /**
     * 更新设备在线状态
     *
     * @param onlineDeviceSet 在线设备集合
     * @param deviceStatus    设备状态
     * @return
     */
    Boolean updateQsDeviceStatusList(@Param("list") Set<Long> onlineDeviceSet, @Param("deviceStatus") String deviceStatus);

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
     * 获取计划记录对应的视频监控设备
     *
     * @param qsDevice 视频监控设备
     * @return
     */
    List<QsDevice> listPlanRecordQsDevice(QsDevice qsDevice);

    /**
     * 录制计划关联所有设备
     *
     * @param planId
     */
    void linkAll(Long planId);

    /**
     * 录制计划取消关联所有设备
     *
     * @param planId
     */
    void cleanAll(Long planId);

    /**
     * 设备关联录制计划
     *
     * @param deviceIds
     * @param planId
     */
    void link(@Param("deviceIds") List<Long> deviceIds, @Param("planId") Long planId);

    /**
     * 清理设备计划id
     *
     * @param planId 设备id
     */
    void cleanRecordPlanId(Long planId);

    /**
     * 根据设备id集合查询设备信息
     *
     * @param startDeviceIdList 设备id集合
     * @return
     */
    List<QsDevice> queryByIds(@Param("startDeviceIdList") List<Long> startDeviceIdList);

    /**
     * 根据计划id查询设备数量
     *
     * @param planId 设备id
     * @return
     */
    Integer countRecordPlanDevice(Long planId);
}
