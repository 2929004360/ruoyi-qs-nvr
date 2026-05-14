package com.ruoyi.qs.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.qs.api.domain.QsGb28181PlatformChannel;
import com.ruoyi.qs.mapper.QsDeviceMapper;
import com.ruoyi.qs.mapper.QsGb28181PlatformChannelMapper;
import com.ruoyi.qs.service.IQsGb28181PlatformChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 国标GB28181平台通道关联Service业务层处理
 *
 * @author ruoyi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QsGb28181PlatformChannelServiceImpl implements IQsGb28181PlatformChannelService {

    private final QsGb28181PlatformChannelMapper qsGb28181PlatformChannelMapper;
    private final QsDeviceMapper qsDeviceMapper;

    /**
     * 查询平台通道关联列表
     *
     * @param qsGb28181PlatformChannel 平台通道关联
     * @return 平台通道关联
     */
    @Override
    public List<QsGb28181PlatformChannel> selectQsGb28181PlatformChannelList(QsGb28181PlatformChannel qsGb28181PlatformChannel) {
        return qsGb28181PlatformChannelMapper.selectQsGb28181PlatformChannelList(qsGb28181PlatformChannel);
    }

    /**
     * 根据ID查询平台通道关联
     *
     * @param id 平台通道关联主键
     * @return 平台通道关联
     */
    @Override
    public QsGb28181PlatformChannel selectQsGb28181PlatformChannelById(Long id) {
        Assert.notNull(id, "ID不能为空");
        return qsGb28181PlatformChannelMapper.selectQsGb28181PlatformChannelById(id);
    }

    /**
     * 根据平台ID查询关联的设备ID列表
     *
     * @param platformId 平台ID
     * @return 设备ID列表
     */
    @Override
    public List<Long> selectDeviceIdsByPlatformId(Long platformId) {
        Assert.notNull(platformId, "平台ID不能为空");
        return qsGb28181PlatformChannelMapper.selectDeviceIdsByPlatformId(platformId);
    }

    /**
     * 新增平台通道关联
     *
     * @param qsGb28181PlatformChannel 平台通道关联
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertQsGb28181PlatformChannel(QsGb28181PlatformChannel qsGb28181PlatformChannel) {
        Assert.notNull(qsGb28181PlatformChannel.getPlatformId(), "平台ID不能为空");
        Assert.notNull(qsGb28181PlatformChannel.getDeviceId(), "设备ID不能为空");

        qsGb28181PlatformChannel.setCreateTime(DateUtils.getNowDate());
        return qsGb28181PlatformChannelMapper.insertQsGb28181PlatformChannel(qsGb28181PlatformChannel);
    }

    /**
     * 批量关联设备到平台
     *
     * @param platformId 平台ID
     * @param deviceIds 设备ID列表
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchLinkDevice(Long platformId, List<Long> deviceIds) {
        Assert.notNull(platformId, "平台ID不能为空");
        Assert.notEmpty(deviceIds, "设备ID列表不能为空");

        List<Long> existingDeviceIds = qsGb28181PlatformChannelMapper.selectDeviceIdsByPlatformId(platformId);

        List<QsGb28181PlatformChannel> list = new ArrayList<>();
        for (Long deviceId : deviceIds) {
            if (!existingDeviceIds.contains(deviceId)) {
                QsGb28181PlatformChannel channel = new QsGb28181PlatformChannel();
                channel.setPlatformId(platformId);
                channel.setDeviceId(deviceId);
                channel.setCreateTime(DateUtils.getNowDate());
                list.add(channel);
            }
        }

        if (list.isEmpty()) {
            return 0;
        }

        int result = qsGb28181PlatformChannelMapper.batchInsertQsGb28181PlatformChannel(list);
        // 异步推送目录到所有在线平台（带防抖）
        gb28181PlatformSyncService.triggerPushCatalog();
        return result;
    }

    /**
     * 批量取消关联设备
     *
     * @param platformId 平台ID
     * @param deviceIds 设备ID列表
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUnlinkDevice(Long platformId, List<Long> deviceIds) {
        Assert.notNull(platformId, "平台ID不能为空");
        Assert.notEmpty(deviceIds, "设备ID列表不能为空");

        int result = qsGb28181PlatformChannelMapper.deleteQsGb28181PlatformChannelByDeviceIds(platformId, deviceIds);
        // 异步推送目录到所有在线平台（带防抖）
        gb28181PlatformSyncService.triggerPushCatalog();
        return result;
    }

    /**
     * 删除平台通道关联
     *
     * @param id 平台通道关联主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteQsGb28181PlatformChannelById(Long id) {
        Assert.notNull(id, "ID不能为空");
        return qsGb28181PlatformChannelMapper.deleteQsGb28181PlatformChannelById(id);
    }

    /**
     * 批量删除平台通道关联
     *
     * @param ids 需要删除的平台通道关联主键集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteQsGb28181PlatformChannelByIds(Long[] ids) {
        Assert.notEmpty(ids, "ID不能为空");
        return qsGb28181PlatformChannelMapper.deleteQsGb28181PlatformChannelByIds(ids);
    }

    /**
     * 关联所有设备
     *
     * @param platformId 平台ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int linkAllDevices(Long platformId) {
        Assert.notNull(platformId, "平台ID不能为空");

        List<Long> allDeviceIds = qsDeviceMapper.selectAllDeviceIds();
        if (allDeviceIds.isEmpty()) {
            return 0;
        }

        List<Long> existingDeviceIds = qsGb28181PlatformChannelMapper.selectDeviceIdsByPlatformId(platformId);

        List<QsGb28181PlatformChannel> list = new ArrayList<>();
        for (Long deviceId : allDeviceIds) {
            if (!existingDeviceIds.contains(deviceId)) {
                QsGb28181PlatformChannel channel = new QsGb28181PlatformChannel();
                channel.setPlatformId(platformId);
                channel.setDeviceId(deviceId);
                channel.setCreateTime(DateUtils.getNowDate());
                list.add(channel);
            }
        }

        if (list.isEmpty()) {
            return 0;
        }

        int result = qsGb28181PlatformChannelMapper.batchInsertQsGb28181PlatformChannel(list);
        // 异步推送目录到所有在线平台（带防抖）
        gb28181PlatformSyncService.triggerPushCatalog();
        return result;
    }

    /**
     * 取消所有设备关联
     *
     * @param platformId 平台ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int unlinkAllDevices(Long platformId) {
        Assert.notNull(platformId, "平台ID不能为空");
        int result = qsGb28181PlatformChannelMapper.deleteQsGb28181PlatformChannelByPlatformId(platformId);
        // 异步推送目录到所有在线平台（带防抖）
        gb28181PlatformSyncService.triggerPushCatalog();
        return result;
    }
}
