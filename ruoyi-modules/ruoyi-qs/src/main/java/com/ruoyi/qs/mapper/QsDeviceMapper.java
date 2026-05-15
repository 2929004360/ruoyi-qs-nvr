package com.ruoyi.qs.mapper;

import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsGroup;
import com.ruoyi.qs.api.domain.QsRegion;
import com.ruoyi.qs.api.domain.QsGroupTree;
import com.ruoyi.qs.domain.*;
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

    /**
     * 根据行政区划查询设备信息
     *
     * @param civilCode 行政区划
     * @return
     */
    List<QsDevice> queryByCivilCode(@Param("civilCode") String civilCode);

    /**
     * 根据行政区划更新设备信息
     *
     * @param civilCode  行政区划
     * @param deviceList 设备信息
     * @return
     */
    int updateCivilCodeByDeviceList(@Param("civilCode") String civilCode, @Param("deviceList") List<QsDevice> deviceList);

    /**
     * 根据行政区划编码删除设备
     *
     * @param allChildren 所有子节点
     */
    void removeCivilCode(@Param("allChildren") List<QsRegion> allChildren);

    /**
     * 根据设备id查询设备关联的行政区划树
     *
     * @param deviceId 区域国标编号
     * @return
     */
    List<QsRegionTree> queryForRegionTreeByCivilCode(String deviceId);

    /**
     * 根据设备id集合查询设备关联的行政区划树
     *
     * @param businessGroup
     * @param deviceList
     * @return
     */
    int updateBusinessGroupBydeviceList(@Param("businessGroup") String businessGroup, @Param("deviceList") List<QsDevice> deviceList);

    /**
     * 根据业务分组查询设备信息
     *
     * @param businessGroup
     * @return
     */
    List<QsDevice> queryByBusinessGroup(@Param("businessGroup") String businessGroup);

    /**
     * 根据父节点查询设备信息
     *
     * @param parentId
     * @return
     */
    List<QsDevice> queryByParentId(@Param("parentId") String parentId);

    /**
     * 根据父节点更新设备信息
     *
     * @param parentId
     * @param deviceList
     * @return
     */
    int updateParentIdByDeviceList(@Param("parentId") String parentId, @Param("deviceList") List<QsDevice> deviceList);

    /**
     * 根据通道id集合更新设备信息
     *
     * @param deviceList
     * @return
     */
    int removeParentIdByDevices(@Param("deviceList") List<QsDevice> deviceList);

    /**
     * 根据分组查询设备信息
     *
     * @param groupList
     * @return
     */
    List<QsDevice> queryByGroupList(List<QsGroup> groupList);

    /**
     * 根据业务分组查询设备关联的业务分组树
     *
     * @param query
     * @param parent
     * @return
     */
    List<QsGroupTree> queryForGroupTreeByParentId(@Param("query") String query, @Param("parent") String parent);

    /**
     * 根据行政区域获取视频监控设备列表
     *
     * @param qsDevice
     * @return
     */
    List<QsDevice> queryListByCivilCode(QsDevice qsDevice);

    /**
     * 根据行政区划编码添加设备
     *
     * @param civilCode
     * @param deviceList
     */
    int updateRegion(@Param("civilCode") String civilCode, @Param("deviceList") List<QsDevice> deviceList);

    /**
     * 根据行政区划编码删除设备
     *
     * @param deviceList
     * @return
     */
    int removeCivilCodeByDeletes(@Param("deviceList") List<QsDevice> deviceList);

    /**
     * 存在行政区划但无法挂载的通道列表
     *
     * @param qsDevice
     * @return
     */
    List<QsDevice> queryListByCivilCodeForUnusual(QsDevice qsDevice);

    /**
     * 获取所有异常的行政区划编码
     *
     * @return
     */
    List<Long> queryAllForUnusualCivilCode();

    /**
     * 根据设备id集合更新行政区划
     *
     * @param deviceList
     */
    void removeCivilCodeByDeviceIds(@Param("deviceList") List<Long> deviceList);

    /**
     * 获取关联业务分组通道列表
     *
     * @param qsDevice
     * @return
     */
    List<QsDevice> queryListByParentId(QsDevice qsDevice);

    /**
     * 设备设置业务分组
     *
     * @param parentId
     * @param businessGroup
     * @param deviceList
     * @return
     */
    int updateGroup(@Param("parentId") String parentId, @Param("businessGroup") String businessGroup, @Param("deviceList") List<QsDevice> deviceList);

    /**
     * 存在父节点编号但无法挂载的设备列表
     *
     * @param qsDevice
     * @return
     */
    List<QsDevice> queryListByParentForUnusual(QsDevice qsDevice);

    /**
     * 获取所有异常的父节点编号
     *
     * @return
     */
    List<Long> queryAllForUnusualParent();

    /**
     * 根据设备id集合更新父节点
     *
     * @param deviceIdsForClear
     */
    void removeParentIdByDeviceIds(@Param("deviceIdsForClear") List<Long> deviceIdsForClear);

    /**
     * 获取设备统计信息
     *
     * @return
     */
    DeviceStats getDeviceStatistics();

    /**
     * 根据国标编码查询设备
     *
     * @param gbCode 国标编码
     * @return 设备列表
     */
    List<QsDevice> selectQsDeviceByGbCode(String gbCode);

    List<QsDevice> selectQsDeviceByPlatform(QsDevice qsDevice, Long platformId, Boolean hasLink);

    /**
     * 查询所有设备ID
     *
     * @return 设备ID列表
     */
    List<Long> selectAllDeviceIds();

    /**
     * 根据 gbCode 查询设备
     *
     * @param gbCode 国标设备编号
     * @return
     */
    QsDevice getDeviceByGbCode(String gbCode);
}
