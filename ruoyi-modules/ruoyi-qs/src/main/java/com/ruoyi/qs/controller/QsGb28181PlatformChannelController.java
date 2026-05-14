package com.ruoyi.qs.controller;

import lombok.Data;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsGb28181PlatformChannel;
import com.ruoyi.qs.mapper.QsDeviceMapper;
import com.ruoyi.qs.service.IQsGb28181PlatformChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 国标GB28181平台通道关联Controller
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/platformChannel")
public class QsGb28181PlatformChannelController extends BaseController {

    private final IQsGb28181PlatformChannelService qsGb28181PlatformChannelService;
    private final QsDeviceMapper qsDeviceMapper;

    /**
     * 查询平台通道关联列表
     */
    @RequiresPermissions("qs:platformChannel:list")
    @GetMapping("/list")
    public TableDataInfo list(QsGb28181PlatformChannel qsGb28181PlatformChannel) {
        startPage();
        List<QsGb28181PlatformChannel> list = qsGb28181PlatformChannelService.selectQsGb28181PlatformChannelList(qsGb28181PlatformChannel);
        return getDataTable(list);
    }

    /**
     * 查询设备列表（用于关联）
     */
    @RequiresPermissions("qs:platformChannel:query")
    @GetMapping("/deviceList")
    public TableDataInfo deviceList(QsDevice qsDevice, @RequestParam(required = false) Long platformId, @RequestParam(required = false) Boolean hasLink) {
        startPage();
        List<QsDevice> result = qsDeviceMapper.selectQsDeviceByPlatform(qsDevice, platformId, hasLink);
        return getDataTable(result);
    }

    /**
     * 获取平台通道关联详细信息
     */
    @RequiresPermissions("qs:platformChannel:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(qsGb28181PlatformChannelService.selectQsGb28181PlatformChannelById(id));
    }

    /**
     * 批量关联设备
     */
    @RequiresPermissions("qs:platformChannel:add")
    @Log(title = "平台通道关联", businessType = BusinessType.INSERT)
    @PostMapping("/link")
    public AjaxResult link(@RequestBody LinkRequest request) {
        if (Boolean.TRUE.equals(request.getAllLink())) {
            return toAjax(qsGb28181PlatformChannelService.linkAllDevices(request.getPlatformId()));
        }
        return toAjax(qsGb28181PlatformChannelService.batchLinkDevice(request.getPlatformId(), request.getDeviceIds()));
    }

    /**
     * 批量取消关联设备
     */
    @RequiresPermissions("qs:platformChannel:remove")
    @Log(title = "平台通道关联", businessType = BusinessType.DELETE)
    @PostMapping("/unlink")
    public AjaxResult unlink(@RequestBody LinkRequest request) {
        if (Boolean.FALSE.equals(request.getAllLink())) {
            return toAjax(qsGb28181PlatformChannelService.unlinkAllDevices(request.getPlatformId()));
        }
        return toAjax(qsGb28181PlatformChannelService.batchUnlinkDevice(request.getPlatformId(), request.getDeviceIds()));
    }

    /**
     * 删除平台通道关联
     */
    @RequiresPermissions("qs:platformChannel:remove")
    @Log(title = "平台通道关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(qsGb28181PlatformChannelService.deleteQsGb28181PlatformChannelByIds(ids));
    }

    @Data
    public static class LinkRequest {
        private Long platformId;
        private List<Long> deviceIds;
        private Boolean allLink;
    }


}
