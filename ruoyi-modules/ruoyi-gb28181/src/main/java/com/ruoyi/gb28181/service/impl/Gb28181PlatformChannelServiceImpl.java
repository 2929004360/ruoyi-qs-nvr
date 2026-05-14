package com.ruoyi.gb28181.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.gb28181.api.domain.Gb28181PlatformChannel;
import com.ruoyi.gb28181.mapper.Gb28181PlatformChannelMapper;
import com.ruoyi.gb28181.service.IGb28181PlatformChannelService;
import com.ruoyi.qs.api.domain.QsDevice;
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
public class Gb28181PlatformChannelServiceImpl implements IGb28181PlatformChannelService {

    private final Gb28181PlatformChannelMapper gb28181PlatformChannelMapper;

    /**
     * 查询平台通道关联列表
     *
     * @param gb28181PlatformChannel 平台通道关联
     * @return 平台通道关联
     */
    @Override
    public List<Gb28181PlatformChannel> selectGb28181PlatformChannelList(Gb28181PlatformChannel gb28181PlatformChannel) {
        return gb28181PlatformChannelMapper.selectGb28181PlatformChannelList(gb28181PlatformChannel);
    }

    /**
     * 根据ID查询平台通道关联
     *
     * @param id 平台通道关联主键
     * @return 平台通道关联
     */
    @Override
    public Gb28181PlatformChannel selectGb28181PlatformChannelById(Long id) {
        Assert.notNull(id, "ID不能为空");
        return gb28181PlatformChannelMapper.selectGb28181PlatformChannelById(id);
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
        return gb28181PlatformChannelMapper.selectDeviceIdsByPlatformId(platformId);
    }

    /**
     * 新增平台通道关联
     *
     * @param gb28181PlatformChannel 平台通道关联
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertGb28181PlatformChannel(Gb28181PlatformChannel gb28181PlatformChannel) {
        Assert.notNull(gb28181PlatformChannel.getPlatformId(), "平台ID不能为空");
        Assert.notNull(gb28181PlatformChannel.getDeviceId(), "设备ID不能为空");

        gb28181PlatformChannel.setCreateTime(DateUtils.getNowDate());
        return gb28181PlatformChannelMapper.insertGb28181PlatformChannel(gb28181PlatformChannel);
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

        List<Long> existingDeviceIds = gb28181PlatformChannelMapper.selectDeviceIdsByPlatformId(platformId);

        List<Gb28181PlatformChannel> list = new ArrayList<>();
        for (Long deviceId : deviceIds) {
            if (!existingDeviceIds.contains(deviceId)) {
                Gb28181PlatformChannel channel = new Gb28181PlatformChannel();
                channel.setPlatformId(platformId);
                channel.setDeviceId(deviceId);
                channel.setCreateTime(DateUtils.getNowDate());
                list.add(channel);
            }
        }

        if (list.isEmpty()) {
            return 0;
        }

        return gb28181PlatformChannelMapper.batchInsertGb28181PlatformChannel(list);
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

        return gb28181PlatformChannelMapper.deleteGb28181PlatformChannelByDeviceIds(platformId, deviceIds);
    }

    /**
     * 删除平台通道关联
     *
     * @param id 平台通道关联主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteGb28181PlatformChannelById(Long id) {
        Assert.notNull(id, "ID不能为空");
        return gb28181PlatformChannelMapper.deleteGb28181PlatformChannelById(id);
    }

    /**
     * 批量删除平台通道关联
     *
     * @param ids 需要删除的平台通道关联主键集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteGb28181PlatformChannelByIds(Long[] ids) {
        Assert.notEmpty(ids, "ID不能为空");
        return gb28181PlatformChannelMapper.deleteGb28181PlatformChannelByIds(ids);
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

        // 这里暂时不实现，因为需要依赖 QsDeviceMapper，而 QsDeviceMapper 在 ruoyi-qs 模块中
        // 后续可以通过远程调用或者将 mapper 移到公共模块中实现
        log.warn("linkAllDevices 方法暂未实现，因为依赖 QsDeviceMapper 在 ruoyi-qs 模块中");
        return 0;
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
        return gb28181PlatformChannelMapper.deleteGb28181PlatformChannelByPlatformId(platformId);
    }
}
