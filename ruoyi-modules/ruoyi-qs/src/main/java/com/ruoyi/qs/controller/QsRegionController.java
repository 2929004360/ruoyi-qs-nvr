package com.ruoyi.qs.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.qs.domain.QsRegion;
import com.ruoyi.qs.domain.QsRegionTree;
import com.ruoyi.qs.service.IQsRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 区域管理
 *
 * @FileName QsRegionController
 * @Description
 * @Author fengcheng
 * @date 2026-04-13
 **/
@Slf4j
@RestController
@RequestMapping("/region")
public class QsRegionController extends BaseController {

    @Autowired
    private IQsRegionService qsRegionService;

    /**
     * 添加区域
     *
     * @param region 区域信息
     */
    @Log(title = "区域管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody QsRegion region) {
        qsRegionService.add(region);
        return AjaxResult.success();
    }

    /**
     * 更新区域
     *
     * @param region
     */
    @Log(title = "区域管理", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult update(@RequestBody QsRegion region) {
        qsRegionService.update(region);
        return AjaxResult.success();
    }

    /**
     * 删除区域
     *
     * @param id 区域ID
     */
    @Log(title = "区域管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable Integer id) {
        Assert.notNull(id, "区域ID需要存在");
        boolean result = qsRegionService.deleteByDeviceId(id);
        if (!result) {
            throw new RuntimeException("移除失败");
        }
        return AjaxResult.success();
    }

    /**
     * 查询区域节点
     *
     * @param parent    所属行政区划编号
     * @param hasDevice 是否查询设备
     * @return
     */
    @GetMapping("/tree/list")
    public AjaxResult queryForTree(@RequestParam(required = false) Integer parent, @RequestParam(required = false) Boolean hasDevice) {
        List<QsRegionTree> list = qsRegionService.queryForTree(parent, hasDevice);
        return success(list);
    }

    /**
     * 查询区域节点设备
     *
     * @return
     */
    @GetMapping("/device/list")
    public AjaxResult queryForDevice() {
        List<QsRegionTree> list = qsRegionService.queryForDevice();
        return success(list);
    }

    /**
     * 查询区域
     *
     * @param query 要搜索的内容
     * @return
     */

    @GetMapping("/tree/query")
    public TableDataInfo queryTree(Integer pageNum, Integer pageSize, String query) {
        startPage();
        List<QsRegion> list = qsRegionService.queryList(pageNum, pageSize, query);
        return getDataTable(list);
    }

    /**
     * 获取所属的行政区划下的行政区划
     *
     * @param parent 所属的行政区划
     * @return
     */
    @GetMapping("/base/child/list")
    public AjaxResult getAllChild(@RequestParam(required = false) String parent) {
        if (ObjectUtils.isEmpty(parent)) {
            parent = null;
        }
        List<QsRegion> list = qsRegionService.getAllChild(parent);
        return success(list);
    }
}
