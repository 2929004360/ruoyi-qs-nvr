package com.ruoyi.zlm.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.service.IZlmCloudRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 云端录像Controller
 *
 * @author fengcheng
 * @date 2026-04-10
 */
@Slf4j
@RestController
@RequestMapping("/api/cloudRecord")
public class ZlmCloudRecordApiController extends BaseController {
    @Autowired
    private IZlmCloudRecordService zlmCloudRecordService;

    @Autowired
    private UserSetting userSetting;

    /**
     * 定时查询待删除的录像文件
     *
     */
    @GetMapping("/task")
    public R<Void> task() {
        zlmCloudRecordService.task();
        return R.ok();
    }
}
