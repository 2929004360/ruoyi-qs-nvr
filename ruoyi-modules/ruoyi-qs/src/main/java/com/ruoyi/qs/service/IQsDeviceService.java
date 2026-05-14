package com.ruoyi.qs.service;

import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsGroup;
import com.ruoyi.qs.api.domain.QsRegion;
import com.ruoyi.qs.domain.*;

import java.util.List;
import java.util.Set;

/**
 * 视频监控设备Service接口
 *
 * @author fengcheng
 * @date 2026-03-27
 */
public interface IQsDeviceService {
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
    void link(List<Long> deviceIds, Long planId);

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
    List<QsDevice> queryByIds(List<Long> startDeviceIdList);

    /**
     * 根据计划id查询设备数量
     *
     * @param planId 设备id
     * @return
     */
    Integer countRecordPlanDevice(Long planId);

    /**
     * 根据行政区划编码更新设备行政区划编码
     *
     * @param oldCivilCode 旧的行政区划编码
     * @param newCivilCode 新的行政区划编码
     */
    void updateCivilCode(String oldCivilCode, String newCivilCode);

    /**
     * 根据行政区划编码删除设备
     *
     * @param allChildren 所有子节点
     */
    void removeCivilCode(List<QsRegion> allChildren);

    /**
     * 根据设备id查询设备关联的行政区划树
     *
     * @param deviceId 区域国标编号
     * @return
     */
    List<QsRegionTree> queryForRegionTreeByCivilCode(String deviceId);

    /**
     * 根据业务分组更新设备业务分组
     *
     * @param oldBusinessGroup
     * @param newBusinessGroup
     */
    void updateBusinessGroup(String oldBusinessGroup, String newBusinessGroup);

    /**
     * 根据业务分组更新设备
     *
     * @param oldParentId
     * @param newParentId
     */
    void updateParentIdGroup(String oldParentId, String newParentId);

    /**
     * 根据业务分组删除设备
     *
     * @param businessGroup
     */
    void removeParentIdByBusinessGroup(String businessGroup);

    /**
     * 根据业务分组删除设备
     *
     * @param groupList
     */
    void removeParentIdByGroupList(List<QsGroup> groupList);

    /**
     * 根据业务分组查询设备关联的业务分组树
     *
     * @param query
     * @param parent
     * @return
     */
    List<QsGroupTree> queryForGroupTreeByParentId(String query, String parent);

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
     * @param deviceIds
     */
    void addDeviceToRegion(String civilCode, List<Long> deviceIds);

    /**
     * 设备删除行政区划
     *
     * @param civilCode
     * @param deviceIds
     */
    void deleteDeviceToRegion(String civilCode, List<Long> deviceIds);

    /**
     * 存在行政区划但无法挂载的通道列表
     *
     * @param qsDevice
     * @return
     */
    List<QsDevice> queryListByCivilCodeForUnusual(QsDevice qsDevice);

    /**
     * 清除存在行政区划但无法挂载的设备列表
     *
     * @param all
     * @param deviceIds
     */
    void clearDeviceCivilCode(Boolean all, List<Long> deviceIds);

    /**
     * 获取编码列表
     *
     * @return
     */
    List<NetworkIdentificationType> getNetworkIdentificationTypeList();

    /**
     * 获取编码列表
     *
     * @return
     */
    List<DeviceType> getDeviceTypeList();

    /**
     * 获取行业编码列表
     *
     * @return
     */
    List<IndustryCodeType> getIndustryCodeList();

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
     * @param deviceIds
     */
    void addChannelToGroup(String parentId, String businessGroup, List<Long> deviceIds);

    /**
     * 删除业务分组设备
     *
     * @param parentId
     * @param businessGroup
     * @param deviceIds
     */
    void deleteDeviceToGroup(String parentId, String businessGroup, List<Long> deviceIds);

    /**
     * 存在父节点编号但无法挂载的设备列表
     *
     * @param qsDevice
     * @return
     */
    List<QsDevice> queryListByParentForUnusual(QsDevice qsDevice);

    /**
     * 清除存在分组节点但无法挂载的设备列表
     *
     * @param all
     * @param deviceIds
     */
    void clearDeviceParent(Boolean all, List<Long> deviceIds);

    /**
     * 获取设备统计信息
     *
     * @return
     */
    DeviceStats getDeviceStatistics();

    /**
     * 新增视频监控设备
     *
     * @param qsDevice
     * @return
     */
    int addQsDevice(QsDevice qsDevice);

    /**
     * 根据 gbDeviceId 更新设备在线状态
     *
     * @param gbDeviceId   国标设备编号
     * @param deviceStatus 设备状态
     * @return
     */
    Boolean updateDeviceStatusByGbDeviceId(String gbDeviceId, String deviceStatus);

    /**
     * 根据 jtMobileNo 更新设备在线状态
     *
     * @param jtMobileNo   设备手机号
     * @param deviceStatus 设备状态
     * @return
     */
    Boolean updateDeviceStatusByJtMobileNo(String jtMobileNo, String deviceStatus);

    /**
     * 开始云台控制
     *
     * @param id        设备id
     * @param direction 方向
     * @param controlSpeed 控制速度
     */
    void startPtz(Long id, String direction, Integer controlSpeed);

    /**
     * 结束云台控制
     *
     * @param id        设备id
     * @param direction 方向
     * @param controlSpeed 控制速度
     */
    void endPtz(Long id, String direction, Integer controlSpeed);

    /**
     * 获取预置点列表
     *
     * @param id         设备id
     * @param channelId  通道id
     * @return 预置点列表
     */
    List<Preset> getPresetList(Long id, Integer channelId);

    /**
     * 设置预置点
     *
     * @param id         设备id
     * @param channelId  通道id
     * @param presetIndex 预置点索引
     * @param presetName 预置点名称
     */
    void setPreset(Long id, Integer channelId, Integer presetIndex, String presetName);

    /**
     * 调用预置点
     *
     * @param id         设备id
     * @param channelId  通道id
     * @param presetIndex 预置点索引
     * @param speed      速度
     */
    void gotoPreset(Long id, Integer channelId, Integer presetIndex, Integer speed);

    /**
     * 删除预置点
     *
     * @param id         设备id
     * @param channelId  通道id
     * @param presetIndex 预置点索引
     */
    void deletePreset(Long id, Integer channelId, Integer presetIndex);

    /**
     * 灯光控制
     *
     * @param id         设备id
     * @param channelId  通道id
     * @param isOn       true-开, false-关
     */
    void controlLight(Long id, Integer channelId, Boolean isOn);

    /**
     * 雨刷控制
     *
     * @param id         设备id
     * @param channelId  通道id
     * @param isOn       true-开, false-关
     */
    void controlWiper(Long id, Integer channelId, Boolean isOn);

    /**
     * 根据 gbCode 查询设备
     *
     * @param gbCode 国标设备编号
     * @return
     */
    QsDevice getDeviceByGbCode(String gbCode);
}
