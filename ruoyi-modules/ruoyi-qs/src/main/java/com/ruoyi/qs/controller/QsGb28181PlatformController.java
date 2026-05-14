package com.ruoyi.qs.controller;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.gb28181.api.RemoteGb28181Service;
import com.ruoyi.qs.api.domain.QsGb28181Platform;
import com.ruoyi.qs.api.domain.QsGb28181PlatformChannel;
import com.ruoyi.qs.service.IQsGb28181PlatformChannelService;
import com.ruoyi.qs.service.IQsGb28181PlatformService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 国标GB28181平台配置Controller
 *
 * @author ruoyi
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/platform")
public class QsGb28181PlatformController extends BaseController {

    private final IQsGb28181PlatformService qsGb28181PlatformService;
    private final IQsGb28181PlatformChannelService qsGb28181PlatformChannelService;
    private final RemoteGb28181Service remoteGb28181Service;

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
        int result = qsGb28181PlatformService.insertQsGb28181Platform(qsGb28181Platform);
        if (result > 0 && qsGb28181Platform.getEnable() != null && qsGb28181Platform.getEnable() == 1) {
            remoteGb28181Service.startPlatformCascade(qsGb28181Platform.getId(), SecurityConstants.INNER);
        }
        return toAjax(result);
    }

    /**
     * 修改国标GB28181平台配置
     */
    @RequiresPermissions("qs:platform:edit")
    @Log(title = "国标GB28181平台配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QsGb28181Platform qsGb28181Platform) {
        int result = qsGb28181PlatformService.updateQsGb28181Platform(qsGb28181Platform);
        if (result > 0) {
            remoteGb28181Service.restartPlatformCascade(qsGb28181Platform.getId(), SecurityConstants.INNER);
        }
        return toAjax(result);
    }

    /**
     * 删除国标GB28181平台配置
     */
    @RequiresPermissions("qs:platform:remove")
    @Log(title = "国标GB28181平台配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {
            remoteGb28181Service.stopPlatformCascade(id, SecurityConstants.INNER);
        }
        return toAjax(qsGb28181PlatformService.deleteQsGb28181PlatformByIds(ids));
    }

    /**
     * 启动平台级联
     */
    @RequiresPermissions("qs:platform:edit")
    @Log(title = "启动平台级联", businessType = BusinessType.OTHER)
    @PostMapping("/cascade/start/{id}")
    public AjaxResult startCascade(@PathVariable Long id) {
        QsGb28181Platform platform = qsGb28181PlatformService.selectQsGb28181PlatformById(id);
        if (platform == null) {
            return error("平台不存在");
        }
        log.info("上线平台: id={}, name={}, 当前状态={}", id, platform.getName(), platform.getStatus());
        // 先更新状态为在线
        platform.setStatus(1);
        int updateResult = qsGb28181PlatformService.updateQsGb28181Platform(platform);
        log.info("更新平台状态结果: id={}, updateResult={}", id, updateResult);
        // 再调用远程服务
        remoteGb28181Service.startPlatformCascade(id, SecurityConstants.INNER);
        log.info("上线平台完成: id={}", id);
        return success();
    }

    /**
     * 停止平台级联
     */
    @RequiresPermissions("qs:platform:edit")
    @Log(title = "停止平台级联", businessType = BusinessType.OTHER)
    @PostMapping("/cascade/stop/{id}")
    public AjaxResult stopCascade(@PathVariable Long id) {
        // 先更新状态为离线
        QsGb28181Platform platform = qsGb28181PlatformService.selectQsGb28181PlatformById(id);
        if (platform != null) {
            log.info("离线平台: id={}, name={}, 当前状态={}", id, platform.getName(), platform.getStatus());
            platform.setStatus(0);
            int updateResult = qsGb28181PlatformService.updateQsGb28181Platform(platform);
            log.info("更新平台状态结果: id={}, updateResult={}", id, updateResult);
        }
        // 再调用远程服务
        remoteGb28181Service.stopPlatformCascade(id, SecurityConstants.INNER);
        log.info("离线平台完成: id={}", id);
        return success();
    }

    /**
     * 重启平台级联
     */
    @RequiresPermissions("qs:platform:edit")
    @Log(title = "重启平台级联", businessType = BusinessType.OTHER)
    @PostMapping("/cascade/restart/{id}")
    public AjaxResult restartCascade(@PathVariable Long id) {
        remoteGb28181Service.restartPlatformCascade(id, SecurityConstants.INNER);
        return success();
    }

    /**
     * 推送目录到上级平台
     */
    @RequiresPermissions("qs:platform:edit")
    @Log(title = "推送目录到上级平台", businessType = BusinessType.OTHER)
    @PostMapping("/cascade/pushCatalog/{id}")
    public AjaxResult pushCatalog(@PathVariable Long id) {
        QsGb28181Platform platform = qsGb28181PlatformService.selectQsGb28181PlatformById(id);
        if (platform == null) {
            return error("平台不存在");
        }
        remoteGb28181Service.pushCatalog(id, SecurityConstants.INNER);
        return success();
    }


}
