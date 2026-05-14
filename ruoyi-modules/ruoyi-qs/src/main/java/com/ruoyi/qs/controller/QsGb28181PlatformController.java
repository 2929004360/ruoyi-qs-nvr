package com.ruoyi.qs.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.qs.api.domain.QsGb28181Platform;
import com.ruoyi.qs.service.IQsGb28181PlatformService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 国标GB28181平台配置Controller
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/platform")
public class QsGb28181PlatformController extends BaseController {

    private final IQsGb28181PlatformService qsGb28181PlatformService;

    /**
     * 查询国标GB28181平台配置列表
     */
    @RequiresPermissions("qs:platform:list")
    @GetMapping("/list")
    public TableDataInfo list(QsGb28181Platform qsGb28181Platform) {
        startPage();
        List<QsGb28181Platform> list = qsGb28181PlatformService.selectQsGb28181PlatformList(qsGb28181Platform);
        return getDataTable(list);
    }

    /**
     * 导出国标GB28181平台配置列表
     */
    @RequiresPermissions("qs:platform:export")
    @Log(title = "国标GB28181平台配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QsGb28181Platform qsGb28181Platform) {
        List<QsGb28181Platform> list = qsGb28181PlatformService.selectQsGb28181PlatformList(qsGb28181Platform);
        ExcelUtil<QsGb28181Platform> util = new ExcelUtil<QsGb28181Platform>(QsGb28181Platform.class);
        util.exportExcel(response, list, "国标GB28181平台配置数据");
    }

    /**
     * 获取国标GB28181平台配置详细信息
     */
    @RequiresPermissions("qs:platform:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(qsGb28181PlatformService.selectQsGb28181PlatformById(id));
    }

    /**
     * 新增国标GB28181平台配置
     */
    @RequiresPermissions("qs:platform:add")
    @Log(title = "国标GB28181平台配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QsGb28181Platform qsGb28181Platform) {
        return toAjax(qsGb28181PlatformService.insertQsGb28181Platform(qsGb28181Platform));
    }

    /**
     * 修改国标GB28181平台配置
     */
    @RequiresPermissions("qs:platform:edit")
    @Log(title = "国标GB28181平台配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QsGb28181Platform qsGb28181Platform) {
        return toAjax(qsGb28181PlatformService.updateQsGb28181Platform(qsGb28181Platform));
    }

    /**
     * 删除国标GB28181平台配置
     */
    @RequiresPermissions("qs:platform:remove")
    @Log(title = "国标GB28181平台配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(qsGb28181PlatformService.deleteQsGb28181PlatformByIds(ids));
    }
}
