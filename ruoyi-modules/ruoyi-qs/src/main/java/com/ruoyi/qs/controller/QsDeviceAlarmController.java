package com.ruoyi.qs.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.qs.api.domain.QsDeviceAlarm;
import com.ruoyi.qs.service.IQsDeviceAlarmService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备告警Controller
 *
 * @author ruoyi
 * @date 2026-05-18
 */
@Slf4j
@RestController
@RequestMapping("/alarm")
public class QsDeviceAlarmController extends BaseController {
    @Autowired
    private IQsDeviceAlarmService qsDeviceAlarmService;

    /**
     * 查询设备告警列表
     */
    @RequiresPermissions("qs:alarm:list")
    @GetMapping("/list")
    public TableDataInfo list(QsDeviceAlarm qsDeviceAlarm) {
        startPage();
        List<QsDeviceAlarm> list = qsDeviceAlarmService.selectQsDeviceAlarmList(qsDeviceAlarm);
        return getDataTable(list);
    }

    /**
     * 导出设备告警列表
     */
    @RequiresPermissions("qs:alarm:export")
    @Log(title = "设备告警", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QsDeviceAlarm qsDeviceAlarm) {
        List<QsDeviceAlarm> list = qsDeviceAlarmService.selectQsDeviceAlarmList(qsDeviceAlarm);
        ExcelUtil<QsDeviceAlarm> util = new ExcelUtil<QsDeviceAlarm>(QsDeviceAlarm.class);
        util.exportExcel(response, list, "设备告警数据");
    }

    /**
     * 获取设备告警详细信息
     */
    @RequiresPermissions("qs:alarm:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(qsDeviceAlarmService.selectQsDeviceAlarmById(id));
    }

    /**
     * 新增设备告警
     */
    @RequiresPermissions("qs:alarm:add")
    @Log(title = "设备告警", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QsDeviceAlarm qsDeviceAlarm) {
        return toAjax(qsDeviceAlarmService.insertQsDeviceAlarm(qsDeviceAlarm));
    }

    /**
     * 修改设备告警
     */
    @RequiresPermissions("qs:alarm:edit")
    @Log(title = "设备告警", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QsDeviceAlarm qsDeviceAlarm) {
        return toAjax(qsDeviceAlarmService.updateQsDeviceAlarm(qsDeviceAlarm));
    }

    /**
     * 删除设备告警
     */
    @RequiresPermissions("qs:alarm:remove")
    @Log(title = "设备告警", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(qsDeviceAlarmService.deleteQsDeviceAlarmByIds(ids));
    }

    /**
     * 批量处理设备告警
     */
    @RequiresPermissions("qs:alarm:edit")
    @Log(title = "设备告警", businessType = BusinessType.UPDATE)
    @PutMapping("/batchHandle")
    public AjaxResult batchHandle(@RequestBody QsDeviceAlarm qsDeviceAlarm) {
        log.info("=== 批量处理告警请求 ===");
        log.info("请求参数: {}", qsDeviceAlarm);
        // 从请求中获取 ids 和 handler
        Long[] ids = qsDeviceAlarm.getIds();
        String handler = qsDeviceAlarm.getHandler();
        if (ids == null || ids.length == 0) {
            return error("请选择要处理的告警");
        }
        if (handler == null || handler.isEmpty()) {
            try {
                handler = com.ruoyi.common.security.utils.SecurityUtils.getUsername();
            } catch (Exception e) {
                handler = "system";
            }
        }
        int result = qsDeviceAlarmService.batchHandleQsDeviceAlarm(ids, handler);
        log.info("批量处理结果: {}", result);
        return toAjax(result);
    }
}
