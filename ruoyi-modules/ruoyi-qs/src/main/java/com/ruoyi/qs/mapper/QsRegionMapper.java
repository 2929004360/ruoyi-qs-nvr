package com.ruoyi.qs.mapper;

import com.ruoyi.qs.api.domain.QsRegion;
import com.ruoyi.qs.api.domain.QsRegionTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @FileName QsRegionMapper
 * @Description
 * @Author fengcheng
 * @date 2026-04-13
 **/
public interface QsRegionMapper {

    /**
     * 添加区域
     *
     * @param region 区域信息
     */
    void add(QsRegion region);

    /**
     * 查询区域
     *
     * @param id 区域id
     * @return
     */
    QsRegion queryOne(int id);

    /**
     * 根据设备id查询区域
     *
     * @param deviceId 设备id
     * @return
     */
    QsRegion queryByDeviceId(String deviceId);

    /**
     * 更新父节点区域
     *
     * @param parentId       父节点id
     * @param parentDeviceId 父节点设备id
     */
    void updateChild(@Param("parentId") int parentId, @Param("parentDeviceId") String parentDeviceId);

    /**
     * 更新区域
     *
     * @param region
     */
    void update(QsRegion region);

    /**
     * 查询子节点
     *
     * @param parentId 父节点id
     * @return
     */
    List<QsRegion> getChildren(@Param("parentId") Integer parentId);

    /**
     * 删除所有子节点
     *
     * @param allChildren 所有子节点
     */
    void batchDelete(@Param("allChildren") List<QsRegion> allChildren);

    /**
     * 查询树形结构
     *
     * @param parent 所属行政区划编号
     * @return
     */
    List<QsRegionTree> queryForTree(Integer parent);

    /**
     * 查询区域节点设备
     *
     * @return
     */
    List<QsRegionTree> queryForDevice();

    /**
     * 查询区域
     *
     * @param query    要搜索的内容
     * @param parentId 父节点id
     * @return
     */
    List<QsRegion> query(@Param("query") String query, @Param("parentId") String parentId);

    /**
     * 查询所有区域
     *
     * @param query 查询条件
     * @return
     */
    List<QsRegionTree> queryAllRegions(@Param("query") String query);
}
