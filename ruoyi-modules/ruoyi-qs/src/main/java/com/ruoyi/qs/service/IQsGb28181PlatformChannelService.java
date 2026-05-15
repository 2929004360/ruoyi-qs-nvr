package com.ruoyi.qs.service;

import com.ruoyi.qs.api.domain.QsGb28181PlatformChannel;

import java.util.List;
import java.util.Map;

/**
 * 国标GB28181平台通道关联Service接口
 *
 * @author ruoyi
 */
public interface IQsGb28181PlatformChannelService {

    /**
     * 查询平台通道关联列表
     *
     * @param qsGb28181PlatformChannel 平台通道关联
     * @return 平台通道关联集合
     */
    List<QsGb28181PlatformChannel> selectQsGb28181PlatformChannelList(QsGb28181PlatformChannel qsGb28181PlatformChannel);

    /**
     * 根据ID查询平台通道关联
     *
     * @param id 平台通道关联主键
     * @return 平台通道关联
     */
    QsGb28181PlatformChannel selectQsGb28181PlatformChannelById(Long id);

    /**
     * 根据平台ID查询关联的设备ID列表
     *
     * @param platformId 平台ID
     * @return 设备ID列表
     */
    List<Long> selectDeviceIdsByPlatformId(Long platformId);

    /**
     * 新增平台通道关联
     *
     * @param qsGb28181PlatformChannel 平台通道关联
     * @return 结果
     */
    int insertQsGb28181PlatformChannel(QsGb28181PlatformChannel qsGb28181PlatformChannel);

    /**
     * 批量关联设备到平台
     *
     * @param platformId 平台ID
     * @param deviceIds 设备ID列表
     * @return 结果
     */
    int batchLinkDevice(Long platformId, List<Long> deviceIds);

    /**
     * 批量取消关联设备
     *
     * @param platformId 平台ID
     * @param deviceIds 设备ID列表
     * @return 结果
     */
    int batchUnlinkDevice(Long platformId, List<Long> deviceIds);

    /**
     * 删除平台通道关联
     *
     * @param id 平台通道关联主键
     * @return 结果
     */
    int deleteQsGb28181PlatformChannelById(Long id);

    /**
     * 批量删除平台通道关联
     *
     * @param ids 需要删除的平台通道关联主键集合
     * @return 结果
     */
    int deleteQsGb28181PlatformChannelByIds(Long[] ids);

    /**
     * 关联所有设备
     *
     * @param platformId 平台ID
     * @return 结果
     */
    int linkAllDevices(Long platformId);

    /**
     * 取消所有设备关联
     *
     * @param platformId 平台ID
     * @return 结果
     */
    int unlinkAllDevices(Long platformId);

    /**
     * 根据平台ID统计关联设备数量
     *
     * @param platformId 平台ID
     * @return 设备数量
     */
    int countDeviceByPlatformId(Long platformId);

    /**
     * 批量统计各平台关联设备数量
     *
     * @param platformIds 平台ID列表
     * @return 平台ID到设备数量的映射
     */
    Map<Long, Integer> countDeviceByPlatformIds(List<Long> platformIds);
}
