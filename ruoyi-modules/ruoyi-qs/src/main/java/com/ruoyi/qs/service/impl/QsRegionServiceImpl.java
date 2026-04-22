package com.ruoyi.qs.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.qs.api.domain.QsRegion;
import com.ruoyi.qs.domain.QsRegionTree;
import com.ruoyi.qs.mapper.QsRegionMapper;
import com.ruoyi.qs.service.IQsDeviceService;
import com.ruoyi.qs.service.IQsRegionService;
import com.ruoyi.qs.api.utils.CivilCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 区域管理Service接口 实现类
 *
 * @FileName QsRegionServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-04-13
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class QsRegionServiceImpl implements IQsRegionService {

    @Autowired
    private QsRegionMapper qsRegionMapper;
    ;

    @Autowired
    private IQsDeviceService qsDeviceService;

    /**
     * 添加区域
     *
     * @param region 区域信息
     */
    @Override
    public void add(QsRegion region) {
        Assert.hasLength(region.getName(), "名称必须存在");
        Assert.hasLength(region.getDeviceId(), "国标编号必须存在");
        if (ObjectUtils.isEmpty(region.getParentDeviceId()) || ObjectUtils.isEmpty(region.getParentDeviceId().trim())) {
            region.setParentDeviceId(null);
        }
        region.setCreateTime(DateUtils.getNowDate());

        try {
            qsRegionMapper.add(region);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("此行政区划已存在");
        }
    }

    /**
     * 更新区域
     *
     * @param region 区域信息
     */
    @Override
    public void update(QsRegion region) {
        Assert.notNull(region.getDeviceId(), "编号不可为NULL");
        Assert.notNull(region.getName(), "名称不可为NULL");
        QsRegion regionInDb = qsRegionMapper.queryOne(region.getId());
        Assert.notNull(regionInDb, "待更新行政区划在数据库中不存在");
        region.setUpdateTime(DateUtils.getNowDate());

        if (!regionInDb.getDeviceId().equals(region.getDeviceId())) {
            QsRegion regionNewInDb = qsRegionMapper.queryByDeviceId(region.getDeviceId());
            Assert.isNull(regionNewInDb, "此行政区划已存在");
            // 编号发生变化，把分配了这个行政区划的设备全部更新，并发送数据
            qsDeviceService.updateCivilCode(regionInDb.getDeviceId(), region.getDeviceId());
            // 子节点信息更新
            qsRegionMapper.updateChild(region.getId(), region.getDeviceId());
        }
        qsRegionMapper.update(region);
    }

    /**
     * 删除区域
     *
     * @param id 区域ID
     * @return
     */
    @Override
    public boolean deleteByDeviceId(Integer id) {
        QsRegion region = qsRegionMapper.queryOne(id);
        // 获取所有子节点
        List<QsRegion> allChildren = getAllChildren(id);
        allChildren.add(region);
        // 设置使用这些节点的设备的civilCode为null,
        qsDeviceService.removeCivilCode(allChildren);
        qsRegionMapper.batchDelete(allChildren);
        return true;
    }

    /**
     * 查询区域节点
     *
     * @param parent    所属行政区划编号
     * @param hasDevice 是否查询设备
     * @return
     */
    @Override
    public List<QsRegionTree> queryForTree(Integer parent, Boolean hasDevice) {
        List<QsRegionTree> regionList = qsRegionMapper.queryForTree(parent);
        if (parent != null && hasDevice != null && hasDevice) {
            QsRegion parentRegion = qsRegionMapper.queryOne(parent);
            if (parentRegion != null) {
                List<QsRegionTree> channelList = qsDeviceService.queryForRegionTreeByCivilCode(parentRegion.getDeviceId());
                regionList.addAll(channelList);
            }
        }
        return regionList;
    }

    /**
     * 查询区域节点设备
     *
     * @return
     */
    @Override
    public List<QsRegionTree> queryForDevice() {
        return qsRegionMapper.queryForDevice();
    }

    /**
     * 查询区域
     *
     * @param query 要搜索的内容
     * @return
     */
    @Override
    public List<QsRegion> queryList(Integer pageNum, Integer pageSize, String query) {
        if (query != null) {
            query = query.replaceAll("/", "//")
                    .replaceAll("%", "/%")
                    .replaceAll("_", "/_");
        }
        return qsRegionMapper.query(query, null);
    }

    /**
     * 获取所属的行政区划下的行政区划
     *
     * @param parent 所属的行政区划
     * @return
     */
    @Override
    public List<QsRegion> getAllChild(String parent) {
        List<QsRegion> allChild = CivilCodeUtil.INSTANCE.getAllChild(parent);
        Collections.sort(allChild);
        return allChild;
    }

    /**
     * 获取所有子节点
     *
     * @param deviceId
     * @return
     */
    private List<QsRegion> getAllChildren(Integer deviceId) {
        if (deviceId == null) {
            return new ArrayList<>();
        }
        List<QsRegion> children = qsRegionMapper.getChildren(deviceId);
        if (ObjectUtils.isEmpty(children)) {
            return children;
        }
        List<QsRegion> regions = new ArrayList<>(children);
        for (QsRegion region : children) {
            if (region.getDeviceId().length() < 8) {
                regions.addAll(getAllChildren(region.getId()));
            }
        }
        return regions;
    }
}
