package com.ruoyi.gb28181.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.mapper.Gb28181PlatformMapper;
import com.ruoyi.gb28181.service.IGb28181PlatformService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 国标GB28181平台配置Service业务层处理
 *
 * @author ruoyi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class Gb28181PlatformServiceImpl implements IGb28181PlatformService {

    private final Gb28181PlatformMapper gb28181PlatformMapper;

    /**
     * 查询国标GB28181平台配置列表
     *
     * @param gb28181Platform 国标GB28181平台配置
     * @return 国标GB28181平台配置
     */
    @Override
    public List<Gb28181Platform> selectGb28181PlatformList(Gb28181Platform gb28181Platform) {
        return gb28181PlatformMapper.selectGb28181PlatformList(gb28181Platform);
    }

    /**
     * 查询国标GB28181平台配置
     *
     * @param id 国标GB28181平台配置主键
     * @return 国标GB28181平台配置
     */
    @Override
    public Gb28181Platform selectGb28181PlatformById(Long id) {
        return gb28181PlatformMapper.selectGb28181PlatformById(id);
    }

    /**
     * 根据设备国标编码查询平台
     *
     * @param deviceGbId 设备国标编码
     * @return 国标GB28181平台配置
     */
    @Override
    public Gb28181Platform selectGb28181PlatformByDeviceGbId(String deviceGbId) {
        return gb28181PlatformMapper.selectGb28181PlatformByDeviceGbId(deviceGbId);
    }

    /**
     * 新增国标GB28181平台配置
     *
     * @param gb28181Platform 国标GB28181平台配置
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertGb28181Platform(Gb28181Platform gb28181Platform) {
        Assert.hasText(gb28181Platform.getName(), "平台名称不能为空");
        Assert.hasText(gb28181Platform.getServerGbId(), "平台国标编码不能为空");

        Gb28181Platform existing = gb28181PlatformMapper.selectGb28181PlatformByServerGbId(gb28181Platform.getServerGbId());
        Assert.isNull(existing, "平台国标编码已存在");

        gb28181Platform.setCreateTime(DateUtils.getNowDate());
        if (gb28181Platform.getEnable() == null) {
            gb28181Platform.setEnable(0);
        }
        if (gb28181Platform.getStatus() == null) {
            gb28181Platform.setStatus(0);
        }

        return gb28181PlatformMapper.insertGb28181Platform(gb28181Platform);
    }

    /**
     * 修改国标GB28181平台配置
     *
     * @param gb28181Platform 国标GB28181平台配置
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateGb28181Platform(Gb28181Platform gb28181Platform) {
        Assert.notNull(gb28181Platform.getId(), "ID不能为空");

        Gb28181Platform existing = gb28181PlatformMapper.selectGb28181PlatformById(gb28181Platform.getId());
        Assert.notNull(existing, "平台配置不存在");

        if (StringUtils.isNotEmpty(gb28181Platform.getServerGbId()) && !gb28181Platform.getServerGbId().equals(existing.getServerGbId())) {
            Gb28181Platform byServerGbId = gb28181PlatformMapper.selectGb28181PlatformByServerGbId(gb28181Platform.getServerGbId());
            Assert.isNull(byServerGbId, "平台国标编码已存在");
        }

        gb28181Platform.setUpdateTime(DateUtils.getNowDate());
        return gb28181PlatformMapper.updateGb28181Platform(gb28181Platform);
    }

    /**
     * 批量删除国标GB28181平台配置
     *
     * @param ids 需要删除的国标GB28181平台配置主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteGb28181PlatformByIds(Long[] ids) {
        Assert.notEmpty(ids, "ID不能为空");
        return gb28181PlatformMapper.deleteGb28181PlatformByIds(ids);
    }

    /**
     * 删除国标GB28181平台配置信息
     *
     * @param id 国标GB28181平台配置主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteGb28181PlatformById(Long id) {
        Assert.notNull(id, "ID不能为空");
        return gb28181PlatformMapper.deleteGb28181PlatformById(id);
    }
}
