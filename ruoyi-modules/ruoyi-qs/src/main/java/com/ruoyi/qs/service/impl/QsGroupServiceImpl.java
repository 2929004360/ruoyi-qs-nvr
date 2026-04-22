package com.ruoyi.qs.service.impl;

import com.google.common.collect.Lists;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.qs.common.GbCode;
import com.ruoyi.qs.api.domain.QsGroup;
import com.ruoyi.qs.domain.QsGroupTree;
import com.ruoyi.qs.mapper.QsGroupMapper;
import com.ruoyi.qs.service.IQsDeviceService;
import com.ruoyi.qs.service.IQsGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组管理服务层处理
 *
 * @FileName QsGroupServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-04-14
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class QsGroupServiceImpl implements IQsGroupService {

    @Autowired
    private QsGroupMapper qsGroupMapper;

    @Autowired
    private IQsDeviceService qsDeviceService;

    /**
     * 添加分组
     *
     * @param group 分组信息
     */
    @Override
    public void add(QsGroup group) {
        Assert.notNull(group, "参数不可为NULL");
        Assert.notNull(group.getDeviceId(), "分组编号不可为NULL");
        Assert.isTrue(group.getDeviceId().trim().length() == 20, "分组编号必须为20位");
        Assert.notNull(group.getName(), "分组名称不可为NULL");

        GbCode gbCode = GbCode.decode(group.getDeviceId());
        Assert.notNull(gbCode, "分组编号不满足国标定义");

        // 查询数据库中已经存在的.
        List<QsGroup> groupListInDb = qsGroupMapper.queryInGroupListByDeviceId(Lists.newArrayList(group));
        if (!ObjectUtils.isEmpty(groupListInDb)) {
            throw new RuntimeException(String.format("该节点编号 %s 已存在", group.getDeviceId()));
        }

        if ("215".equals(gbCode.getTypeCode())) {
            // 添加业务分组
            addBusinessGroup(group);
        } else {
            Assert.isTrue("216".equals(gbCode.getTypeCode()), "创建虚拟组织时设备编号11-13位应使用216");
            // 添加虚拟组织
            addGroup(group);
        }
    }

    /**
     * 更新分组
     *
     * @param group 分组信息
     */
    @Override
    public void update(QsGroup group) {
        Assert.isTrue(group.getId() > 0, "更新必须携带分组ID");
        Assert.notNull(group.getDeviceId(), "编号不可为NULL");
        Assert.notNull(group.getBusinessGroup(), "业务分组不可为NULL");
        QsGroup groupInDb = qsGroupMapper.queryOne(group.getId());
        Assert.notNull(groupInDb, "分组不存在");

        // 查询数据库中已经存在的.
        List<QsGroup> groupListInDb = qsGroupMapper.queryInGroupListByDeviceId(Lists.newArrayList(group));
        if (!ObjectUtils.isEmpty(groupListInDb) && groupListInDb.get(0).getId() != group.getId()) {
            throw new RuntimeException(String.format("该该节点编号 %s 已存在", group.getDeviceId()));
        }

        group.setName(group.getName());
        group.setUpdateTime(DateUtils.getNowDate());
        qsGroupMapper.update(group);

        // 修改他的子节点
        if (!group.getDeviceId().equals(groupInDb.getDeviceId())
                || !group.getBusinessGroup().equals(groupInDb.getBusinessGroup())) {
            List<QsGroup> groupList = queryAllChildren(groupInDb.getId());
            if (!groupList.isEmpty()) {
                int result = qsGroupMapper.updateChild(groupInDb.getId(), group);
                if (result > 0) {
                    for (QsGroup chjildGroup : groupList) {
                        chjildGroup.setParentDeviceId(group.getDeviceId());
                        chjildGroup.setBusinessGroup(group.getBusinessGroup());
                    }
                }
            }
        }

        // 由于编号变化，会需要处理太多内容以及可能发送大量消息，所以目前更新只只支持重命名
        GbCode decode = GbCode.decode(group.getDeviceId());
        if (!groupInDb.getDeviceId().equals(group.getDeviceId())) {
            if (decode.getTypeCode().equals("215")) {
                // 业务分组变化。需要将其下的所有业务分组修改
                qsDeviceService.updateBusinessGroup(groupInDb.getDeviceId(), group.getDeviceId());
            } else {
                // 虚拟组织修改，需要把其下的子节点修改父节点ID
                qsDeviceService.updateParentIdGroup(groupInDb.getDeviceId(), group.getDeviceId());
            }
        }
    }

    /**
     * 删除分组
     *
     * @param id 分组id
     */
    @Override
    public boolean delete(Integer id) {
        QsGroup group = qsGroupMapper.queryOne(id);
        Assert.notNull(group, "分组不存在");
        List<QsGroup> groupListForDelete = new ArrayList<>();
        GbCode gbCode = GbCode.decode(group.getDeviceId());
        if (gbCode.getTypeCode().equals("215")) {
            List<QsGroup> groupList = qsGroupMapper.queryByBusinessGroup(group.getDeviceId());
            if (!groupList.isEmpty()) {
                groupListForDelete.addAll(groupList);
            }
            // 业务分组
            qsDeviceService.removeParentIdByBusinessGroup(group.getDeviceId());
        } else {
            List<QsGroup> groupList = queryAllChildren(group.getId());
            if (!groupList.isEmpty()) {
                groupListForDelete.addAll(groupList);
            }
            groupListForDelete.add(group);
            qsDeviceService.removeParentIdByGroupList(groupListForDelete);
        }
        qsGroupMapper.batchDelete(groupListForDelete);

        return true;
    }

    /**
     * 查询分组树
     *
     * @param query     查询条件
     * @param parentId  父节点
     * @param hasDevice 是否包含设备
     * @return
     */
    @Override
    public List<QsGroupTree> queryForTree(String query, Integer parentId, Boolean hasDevice) {
        List<QsGroupTree> groupTrees = qsGroupMapper.queryForTree(query, parentId);
        if (parentId == null) {
            return groupTrees;
        }
        // 查询含有的通道
        QsGroup parentGroup = qsGroupMapper.queryOne(parentId);
        if (parentGroup != null && hasDevice != null && hasDevice) {
            List<QsGroupTree> groupTreesForChannel = qsDeviceService.queryForGroupTreeByParentId(query, parentGroup.getDeviceId());
            if (!ObjectUtils.isEmpty(groupTreesForChannel)) {
                groupTrees.addAll(groupTreesForChannel);
            }
        }
        return groupTrees;
    }

    /**
     * 查询分组节点设备
     *
     * @return
     */
    @Override
    public List<QsGroupTree> queryForDevice() {
        return qsGroupMapper.queryForDevice();
    }

    /**
     * 查询分组
     *
     * @param query 要搜索的内容
     * @return
     */
    @Override
    public List<QsGroup> queryList(String query) {
        if (query != null) {
            query = query.replaceAll("/", "//")
                    .replaceAll("%", "/%")
                    .replaceAll("_", "/_");
        }
        List<QsGroup> list = qsGroupMapper.query(query, null, null);
        return list;
    }

    private List<QsGroup> queryAllChildren(int id) {
        List<QsGroup> children = qsGroupMapper.getChildren(id);
        if (ObjectUtils.isEmpty(children)) {
            return children;
        }
        for (int i = 0; i < children.size(); i++) {
            children.addAll(queryAllChildren(children.get(i).getId()));
        }
        return children;
    }

    private void addBusinessGroup(QsGroup group) {
        group.setBusinessGroup(group.getDeviceId());
        group.setCreateTime(DateUtils.getNowDate());
        qsGroupMapper.addBusinessGroup(group);
    }

    private void addGroup(QsGroup group) {
        // 建立虚拟组织
        Assert.notNull(group.getBusinessGroup(), "所属的业务分组分组不存在");
        QsGroup businessGroup = qsGroupMapper.queryBusinessGroup(group.getBusinessGroup());
        Assert.notNull(businessGroup, "所属的业务分组分组不存在");
        if (!ObjectUtils.isEmpty(group.getParentDeviceId())) {
            QsGroup parentGroup = qsGroupMapper.queryOneByDeviceId(group.getParentDeviceId(), group.getBusinessGroup());
            Assert.notNull(parentGroup, "所属的上级分组分组不存在");
        } else {
            group.setParentDeviceId(null);
        }
        group.setCreateTime(DateUtils.getNowDate());
        qsGroupMapper.add(group);
    }
}
