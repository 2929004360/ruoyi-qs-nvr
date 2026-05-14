package com.ruoyi.gb28181.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.gb28181.api.domain.Gb28181PlatformChannel;
import com.ruoyi.gb28181.service.IGb28181PlatformChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 国标GB28181平台通道关联Controller
 *
 * @author ruoyi
 */
@Slf4j
@RestController
@RequestMapping("/platformChannel")
@RequiredArgsConstructor
@Tag(name = "国标GB28181平台通道关联管理", description = "国标GB28181平台通道关联管理")
public class Gb28181PlatformChannelController extends BaseController {

    private final IGb28181PlatformChannelService gb28181PlatformChannelService;

    /**
     * 查询平台通道关联列表
     */
    @Operation(summary = "查询平台通道关联列表", description = "查询平台通道关联列表")
    @RequiresPermissions("gb28181:platformChannel:list")
    @GetMapping("/list")
    public R<List<Gb28181PlatformChannel>> list(Gb28181PlatformChannel gb28181PlatformChannel) {
        List<Gb28181PlatformChannel> list = gb28181PlatformChannelService.selectGb28181PlatformChannelList(gb28181PlatformChannel);
        return R.ok(list);
    }

    /**
     * 根据平台ID获取关联的设备ID列表
     */
    @Operation(summary = "根据平台ID获取关联的设备ID列表", description = "根据平台ID获取关联的设备ID列表")
    @RequiresPermissions("gb28181:platformChannel:query")
    @GetMapping("/deviceIds/{platformId}")
    public R<List<Long>> selectDeviceIdsByPlatformId(@PathVariable("platformId") Long platformId) {
        return R.ok(gb28181PlatformChannelService.selectDeviceIdsByPlatformId(platformId));
    }

    /**
     * 获取平台通道关联详细信息
     */
    @Operation(summary = "获取平台通道关联详细信息", description = "获取平台通道关联详细信息")
    @RequiresPermissions("gb28181:platformChannel:query")
    @GetMapping(value = "/{id}")
    public R<Gb28181PlatformChannel> getInfo(@PathVariable("id") Long id) {
        return R.ok(gb28181PlatformChannelService.selectGb28181PlatformChannelById(id));
    }

    /**
     * 批量关联设备到平台
     */
    @Operation(summary = "批量关联设备到平台", description = "批量关联设备到平台")
    @RequiresPermissions("gb28181:platformChannel:add")
    @Log(title = "平台通道关联", businessType = BusinessType.INSERT)
    @PostMapping("/link")
    public AjaxResult link(@RequestParam Long platformId, @RequestBody List<Long> deviceIds) {
        return toAjax(gb28181PlatformChannelService.batchLinkDevice(platformId, deviceIds));
    }

    /**
     * 批量取消关联设备
     */
    @Operation(summary = "批量取消关联设备", description = "批量取消关联设备")
    @RequiresPermissions("gb28181:platformChannel:remove")
    @Log(title = "平台通道关联", businessType = BusinessType.DELETE)
    @PostMapping("/unlink")
    public AjaxResult unlink(@RequestParam Long platformId, @RequestBody List<Long> deviceIds) {
        return toAjax(gb28181PlatformChannelService.batchUnlinkDevice(platformId, deviceIds));
    }

    /**
     * 关联所有设备
     */
    @Operation(summary = "关联所有设备", description = "关联所有设备")
    @RequiresPermissions("gb28181:platformChannel:add")
    @Log(title = "平台通道关联", businessType = BusinessType.INSERT)
    @PostMapping("/linkAll")
    public AjaxResult linkAll(@RequestParam Long platformId) {
        return toAjax(gb28181PlatformChannelService.linkAllDevices(platformId));
    }

    /**
     * 取消所有设备关联
     */
    @Operation(summary = "取消所有设备关联", description = "取消所有设备关联")
    @RequiresPermissions("gb28181:platformChannel:remove")
    @Log(title = "平台通道关联", businessType = BusinessType.DELETE)
    @PostMapping("/unlinkAll")
    public AjaxResult unlinkAll(@RequestParam Long platformId) {
        return toAjax(gb28181PlatformChannelService.unlinkAllDevices(platformId));
    }
}
