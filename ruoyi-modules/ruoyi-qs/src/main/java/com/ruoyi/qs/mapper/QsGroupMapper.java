package com.ruoyi.qs.mapper;

import com.ruoyi.qs.api.domain.QsGroup;
import com.ruoyi.qs.domain.QsGroupTree;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName QsGroupMapper
 * @Description
 * @Author fengcheng
 * @date 2026-04-14
 **/
public interface QsGroupMapper {

    /**
     * 根据设备id查询分组
     *
     * @param groupList
     * @return
     */
    List<QsGroup> queryInGroupListByDeviceId(@Param("groupList") ArrayList<QsGroup> groupList);

    /**
     * 新增分组
     *
     * @param group
     */
    void addBusinessGroup(QsGroup group);

    /**
     * 根据分组名称查询分组
     *
     * @param businessGroup
     * @return
     */
    QsGroup queryBusinessGroup(@Param("businessGroup") String businessGroup);

    /**
     * 根据设备id查询分组
     *
     * @param deviceId
     * @param businessGroup
     * @return
     */
    QsGroup queryOneByDeviceId(@Param("deviceId") String deviceId, @Param("businessGroup") String businessGroup);

    /**
     * 新增分组
     *
     * @param group
     */
    void add(QsGroup group);

    /**
     * 根据id查询分组
     *
     * @param id
     * @return
     */
    QsGroup queryOne(int id);

    /**
     * 更新分组
     *
     * @param group 分组信息
     */
    void update(QsGroup group);

    /**
     * 获取分组子节点
     *
     * @param id
     * @return
     */
    List<QsGroup> getChildren(int id);

    /**
     * 更新子节点
     *
     * @param parentId
     * @param group
     * @return
     */
    int updateChild(@Param("parentId") Integer parentId, @Param("group") QsGroup group);

    /**
     * 根据分组名称查询分组
     *
     * @param businessGroup
     * @return
     */
    List<QsGroup> queryByBusinessGroup(@Param("businessGroup") String businessGroup);

    /**
     * 批量删除
     *
     * @param allChildren 所有子节点
     */
    void batchDelete(@Param("allChildren") List<QsGroup> allChildren);

    /**
     * 查询分组树
     *
     * @param query    查询条件
     * @param parentId 父节点
     * @return
     */
    List<QsGroupTree> queryForTree(@Param("query") String query, @Param("parentId") Integer parentId);

    /**
     * 查询分组节点设备
     *
     * @return
     */
    List<QsGroupTree> queryForDevice();

    /**
     * 查询分组
     *
     * @param query
     * @param parentId
     * @param businessGroup
     * @return
     */
    List<QsGroup> query(@Param("query") String query, @Param("parentId") String parentId, @Param("businessGroup") String businessGroup);
}
