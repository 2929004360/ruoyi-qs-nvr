package com.ruoyi.gb28181.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.service.IGb28181PlatformService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 国标GB28181平台配置Controller
 *
 * @author ruoyi
 */
@Slf4j
@RestController
@RequestMapping("/platform")
@RequiredArgsConstructor
@Tag(name = "国标GB28181平台配置管理", description = "国标GB28181平台配置管理")
public class Gb28181PlatformController extends BaseController {

    private final IGb28181PlatformService gb28181PlatformService;

    /**
     * 查询国标GB28181平台配置列表
     */
    @Operation(summary = "查询国标GB28181平台配置列表", description = "查询国标GB28181平台配置列表")
    @RequiresPermissions("gb28181:platform:list")
    @GetMapping("/list")
    public TableDataInfo list(Gb28181Platform gb28181Platform) {
        startPage();
        List<Gb28181Platform> list = gb28181PlatformService.selectGb28181PlatformList(gb28181Platform);
        return getDataTable(list);
    }

    /**
     * 导出国标GB28181平台配置列表
     */
    @Operation(summary = "导出国标GB28181平台配置列表", description = "导出国标GB28181平台配置列表")
    @RequiresPermissions("gb28181:platform:export")
    @Log(title = "国标GB28181平台配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Gb28181Platform gb28181Platform) {
        List<Gb28181Platform> list = gb28181PlatformService.selectGb28181PlatformList(gb28181Platform);
        ExcelUtil<Gb28181Platform> util = new ExcelUtil<>(Gb28181Platform.class);
        util.exportExcel(response, list, "国标GB28181平台配置数据");
    }

    /**
     * 获取国标GB28181平台配置详细信息
     */
    @Operation(summary = "获取国标GB28181平台配置详细信息", description = "获取国标GB28181平台配置详细信息")
    @RequiresPermissions("gb28181:platform:query")
    @GetMapping(value = "/{id}")
    public R<Gb28181Platform> getInfo(@PathVariable("id") Long id) {
        return R.ok(gb28181PlatformService.selectGb28181PlatformById(id));
    }

    /**
     * 新增国标GB28181平台配置
     */
    @Operation(summary = "新增国标GB28181平台配置", description = "新增国标GB28181平台配置")
    @RequiresPermissions("gb28181:platform:add")
    @Log(title = "国标GB28181平台配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Gb28181Platform gb28181Platform) {
        return toAjax(gb28181PlatformService.insertGb28181Platform(gb28181Platform));
    }

    /**
     * 修改国标GB28181平台配置
     */
    @Operation(summary = "修改国标GB28181平台配置", description = "修改国标GB28181平台配置")
    @RequiresPermissions("gb28181:platform:edit")
    @Log(title = "国标GB28181平台配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Gb28181Platform gb28181Platform) {
        return toAjax(gb28181PlatformService.updateGb28181Platform(gb28181Platform));
    }

    /**
     * 删除国标GB28181平台配置
     */
    @Operation(summary = "删除国标GB28181平台配置", description = "删除国标GB28181平台配置")
    @RequiresPermissions("gb28181:platform:remove")
    @Log(title = "国标GB28181平台配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(gb28181PlatformService.deleteGb28181PlatformByIds(ids));
    }
}
