package com.ruoyi.qs.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.qs.api.domain.QsDeviceSnapshot;
import com.ruoyi.qs.service.IQsDeviceSnapshotService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备抓图Controller
 *
 * @author ruoyi
 * @date 2026-05-17
 */
@RestController
@RequestMapping("/snapshot")
public class QsDeviceSnapshotController extends BaseController {
    @Autowired
    private IQsDeviceSnapshotService qsDeviceSnapshotService;

    /**
     * 查询设备抓图列表
     */
    @RequiresPermissions("qs:snapshot:list")
    @GetMapping("/list")
    public TableDataInfo list(QsDeviceSnapshot qsDeviceSnapshot) {
        startPage();
        List<QsDeviceSnapshot> list = qsDeviceSnapshotService.selectQsDeviceSnapshotList(qsDeviceSnapshot);
        return getDataTable(list);
    }

    /**
     * 导出设备抓图列表
     */
    @RequiresPermissions("qs:snapshot:export")
    @Log(title = "设备抓图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QsDeviceSnapshot qsDeviceSnapshot) {
        List<QsDeviceSnapshot> list = qsDeviceSnapshotService.selectQsDeviceSnapshotList(qsDeviceSnapshot);
        ExcelUtil<QsDeviceSnapshot> util = new ExcelUtil<QsDeviceSnapshot>(QsDeviceSnapshot.class);
        util.exportExcel(response, list, "设备抓图数据");
    }

    /**
     * 获取设备抓图详细信息
     */
    @RequiresPermissions("qs:snapshot:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(qsDeviceSnapshotService.selectQsDeviceSnapshotById(id));
    }

    /**
     * 新增设备抓图
     */
    @RequiresPermissions("qs:snapshot:add")
    @Log(title = "设备抓图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QsDeviceSnapshot qsDeviceSnapshot) {
        return toAjax(qsDeviceSnapshotService.insertQsDeviceSnapshot(qsDeviceSnapshot));
    }

    /**
     * 修改设备抓图
     */
    @RequiresPermissions("qs:snapshot:edit")
    @Log(title = "设备抓图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QsDeviceSnapshot qsDeviceSnapshot) {
        return toAjax(qsDeviceSnapshotService.updateQsDeviceSnapshot(qsDeviceSnapshot));
    }

    /**
     * 删除设备抓图
     */
    @RequiresPermissions("qs:snapshot:remove")
    @Log(title = "设备抓图", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(qsDeviceSnapshotService.deleteQsDeviceSnapshotByIds(ids));
    }
}
