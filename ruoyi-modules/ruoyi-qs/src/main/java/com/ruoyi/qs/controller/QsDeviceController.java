package com.ruoyi.qs.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.service.IQsDeviceService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视频监控设备Controller
 * 
 * @author fengcheng
 * @date 2026-03-27
 */
@RestController
@RequestMapping("/device")
public class QsDeviceController extends BaseController
{
    @Autowired
    private IQsDeviceService qsDeviceService;

    /**
     * 查询视频监控设备列表
     */
    @RequiresPermissions("qs:device:list")
    @GetMapping("/list")
    public TableDataInfo list(QsDevice qsDevice)
    {
        startPage();
        List<QsDevice> list = qsDeviceService.selectQsDeviceList(qsDevice);
        return getDataTable(list);
    }

    /**
     * 导出视频监控设备列表
     */
    @RequiresPermissions("qs:device:export")
    @Log(title = "视频监控设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QsDevice qsDevice)
    {
        List<QsDevice> list = qsDeviceService.selectQsDeviceList(qsDevice);
        ExcelUtil<QsDevice> util = new ExcelUtil<QsDevice>(QsDevice.class);
        util.exportExcel(response, list, "视频监控设备数据");
    }

    /**
     * 获取视频监控设备详细信息
     */
    @RequiresPermissions("qs:device:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(qsDeviceService.selectQsDeviceById(id));
    }

    /**
     * 新增视频监控设备
     */
    @RequiresPermissions("qs:device:add")
    @Log(title = "视频监控设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QsDevice qsDevice)
    {
        return toAjax(qsDeviceService.insertQsDevice(qsDevice));
    }

    /**
     * 修改视频监控设备
     */
    @RequiresPermissions("qs:device:edit")
    @Log(title = "视频监控设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QsDevice qsDevice)
    {
        return toAjax(qsDeviceService.updateQsDevice(qsDevice));
    }

    /**
     * 删除视频监控设备
     */
    @RequiresPermissions("qs:device:remove")
    @Log(title = "视频监控设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qsDeviceService.deleteQsDeviceByIds(ids));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("qs:device:edit")
    @Log(title = "视频监控设备", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody QsDevice qsDevice)
    {
        qsDevice.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(qsDeviceService.updateQsDeviceStatus(qsDevice));
    }
}
