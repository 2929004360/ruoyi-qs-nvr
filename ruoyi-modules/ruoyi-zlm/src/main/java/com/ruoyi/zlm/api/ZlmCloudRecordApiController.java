package com.ruoyi.zlm.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.zlm.api.domain.ZlmCloudRecord;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.service.IZlmCloudRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 查询云端录像列表
     *
     * @param zlmCloudRecord 云端录像
     * @return
     */
    @PostMapping("/list")
    public R<List<ZlmCloudRecord>> selectZlmCloudRecordList(@RequestBody ZlmCloudRecord zlmCloudRecord) {
        log.info("[云端录像] 查询云端录像列表, zlmCloudRecord: {}", zlmCloudRecord);
        List<ZlmCloudRecord> list = zlmCloudRecordService.selectZlmCloudRecordList(zlmCloudRecord);
        return R.ok(list);
    }
}
