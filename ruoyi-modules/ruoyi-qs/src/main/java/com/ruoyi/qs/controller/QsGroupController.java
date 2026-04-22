package com.ruoyi.qs.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.qs.api.domain.QsGroup;
import com.ruoyi.qs.domain.QsGroupTree;
import com.ruoyi.qs.service.IQsGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分组管理
 *
 * @FileName QsGroupController
 * @Description
 * @Author fengcheng
 * @date 2026-04-14
 **/
@Slf4j
@RestController
@RequestMapping("/group")
public class QsGroupController extends BaseController {
    @Autowired
    private IQsGroupService qsGroupService;

    /**
     * 添加分组
     *
     * @param group 分组信息
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody QsGroup group) {
        qsGroupService.add(group);
        return success();
    }

    /**
     * 更新分组
     *
     * @param group 分组信息
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody QsGroup group) {
        qsGroupService.update(group);
        return success();
    }

    /**
     * 删除分组
     *
     * @param id 分组id
     */
    @DeleteMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable Integer id) {
        Assert.notNull(id, "分组id（deviceId）不需要存在");
        boolean result = qsGroupService.delete(id);
        if (!result) {
            throw new RuntimeException("移除失败");
        }
        return success();
    }

    /**
     * 查询分组节点
     *
     * @param query     要搜索的内容
     * @param parent    所属分组编号
     * @param hasDevice 是否查询设备
     * @return
     */
    @GetMapping("/tree/list")
    public AjaxResult queryForTree(@RequestParam(required = false) String query, @RequestParam(required = false) Integer parent, @RequestParam(required = false) Boolean hasDevice) {
        if (ObjectUtils.isEmpty(query)) {
            query = null;
        }
        List<QsGroupTree> list = qsGroupService.queryForTree(query, parent, hasDevice);
        return success(list);
    }

    /**
     * 查询分组节点设备
     *
     * @return
     */
    @GetMapping("/device/list")
    public AjaxResult queryForDevice() {
        List<QsGroupTree> list = qsGroupService.queryForDevice();
        return success(list);
    }

    /**
     * 查询分组
     *
     * @param query 要搜索的内容
     * @return
     */
    @GetMapping("/tree/query")
    public TableDataInfo queryTree(String query) {
        startPage();
        List<QsGroup> list = qsGroupService.queryList(query);
        return getDataTable(list);
    }
}
