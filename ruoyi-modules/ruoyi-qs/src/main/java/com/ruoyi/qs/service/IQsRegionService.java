package com.ruoyi.qs.service;

import com.ruoyi.qs.api.domain.QsRegion;
import com.ruoyi.qs.domain.QsRegionTree;

import java.util.List;

/**
 * 区域管理Service接口
 *
 * @FileName IQsRegionService
 * @Description
 * @Author fengcheng
 * @date 2026-04-13
 **/
public interface IQsRegionService {

    /**
     * 添加区域
     *
     * @param region 区域信息
     */
    void add(QsRegion region);

    /**
     * 更新区域
     *
     * @param region 区域信息
     */
    void update(QsRegion region);

    /**
     * 区域ID
     *
     * @param id 区域ID
     * @return
     */
    boolean deleteByDeviceId(Integer id);

    /**
     * @param parent
     * @param hasDevice
     * @return
     */
    List<QsRegionTree> queryForTree(Integer parent, Boolean hasDevice);

    /**
     * 查询区域节点设备
     *
     * @return
     */
    List<QsRegionTree> queryForDevice();

    /**
     * 查询区域
     *
     * @param query 要搜索的内容
     * @return
     */
    List<QsRegion> queryList(Integer pageNum, Integer pageSize, String query);

    /**
     * 获取所属的行政区划下的行政区划
     *
     * @param parent 所属的行政区划
     * @return
     */
    List<QsRegion> getAllChild(String parent);
}
