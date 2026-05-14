package com.ruoyi.gb28181.controller;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.gb28181.api.domain.CatalogRequest;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.service.IPlatformSIPCommander;
import com.ruoyi.gb28181.task.platform.PlatformCascadeTaskManager;
import com.ruoyi.qs.api.RemoteQsGb28181PlatformService;
import com.ruoyi.qs.api.domain.QsGb28181Platform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 平台级联 Controller
 *
 * @author ruoyi
 */
@Slf4j
@RestController
@RequestMapping("/api/gb28181/platform/cascade")
public class PlatformCascadeController {

    @Autowired
    private IPlatformSIPCommander platformSIPCommander;

    @Autowired
    private PlatformCascadeTaskManager platformCascadeTaskManager;

    @Autowired
    private RemoteQsGb28181PlatformService remoteQsPlatformService;

    /**
     * 注册
     */
    @PostMapping("/register")
    public R<Void> register(@RequestBody Gb28181Platform platform) {
        try {
            platformSIPCommander.register(platform);
            return R.ok();
        } catch (Exception e) {
            log.error("[平台级联] 注册失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 注销
     */
    @PostMapping("/unregister")
    public R<Void> unregister(@RequestBody Gb28181Platform platform) {
        try {
            platformSIPCommander.unregister(platform);
            return R.ok();
        } catch (Exception e) {
            log.error("[平台级联] 注销失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 手动注销平台
     */
    @PostMapping("/unregister/{platformId}")
    public R<Void> unregisterPlatform(@PathVariable Long platformId) {
        try {
            platformCascadeTaskManager.unregisterPlatform(platformId);
            return R.ok();
        } catch (Exception e) {
            log.error("[平台级联] 手动注销失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 发送心跳
     */
    @PostMapping("/heartbeat")
    public R<Void> sendHeartbeat(@RequestBody Gb28181Platform platform) {
        try {
            platformSIPCommander.keepAlive(platform);
            return R.ok();
        } catch (Exception e) {
            log.error("[平台级联] 发送心跳失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 发送设备信息
     */
    @PostMapping("/deviceInfo")
    public R<Void> sendDeviceInfo(@RequestBody Gb28181Platform platform) {
        try {
            platformSIPCommander.sendDeviceInfo(platform);
            return R.ok();
        } catch (Exception e) {
            log.error("[平台级联] 发送设备信息失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 发送目录
     */
    @PostMapping("/catalog")
    public R<Void> sendCatalog(@RequestBody CatalogRequest catalogRequest) {
        try {
            platformSIPCommander.sendCatalog(catalogRequest.getPlatform(), catalogRequest.getDeviceList());
            return R.ok();
        } catch (Exception e) {
            log.error("[平台级联] 发送目录失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 手动推送目录
     */
    @PostMapping("/catalog/{platformId}")
    public R<Void> pushCatalog(@PathVariable Long platformId) {
        try {
            platformCascadeTaskManager.pushCatalog(platformId);
            return R.ok();
        } catch (Exception e) {
            log.error("[平台级联] 手动推送目录失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 启动单个平台级联任务
     */
    @PostMapping("/start/{platformId}")
    public R<Void> startPlatformCascade(@PathVariable Long platformId) {
        try {
            QsGb28181Platform qsPlatform = remoteQsPlatformService.selectPlatformById(platformId, SecurityConstants.INNER).getData();
            if (qsPlatform != null) {
                Gb28181Platform platform = com.ruoyi.gb28181.util.PlatformConvertUtil.convertToGbPlatform(qsPlatform);
                platformCascadeTaskManager.startPlatform(platform);
            }
            return R.ok();
        } catch (Exception e) {
            log.error("[平台级联] 启动失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 停止单个平台级联任务
     */
    @PostMapping("/stop/{platformId}")
    public R<Void> stopPlatformCascade(@PathVariable Long platformId) {
        try {
            platformCascadeTaskManager.stopPlatform(platformId);
            return R.ok();
        } catch (Exception e) {
            log.error("[平台级联] 停止失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 重启单个平台级联任务
     */
    @PostMapping("/restart/{platformId}")
    public R<Void> restartPlatformCascade(@PathVariable Long platformId) {
        try {
            platformCascadeTaskManager.restartPlatform(platformId);
            return R.ok();
        } catch (Exception e) {
            log.error("[平台级联] 重启失败", e);
            return R.fail(e.getMessage());
        }
    }
}
