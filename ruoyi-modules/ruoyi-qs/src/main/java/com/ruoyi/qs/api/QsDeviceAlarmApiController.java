package com.ruoyi.qs.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.qs.api.domain.QsDeviceAlarm;
import com.ruoyi.qs.service.IQsDeviceAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备告警Api接口
 *
 * @author ruoyi
 * @date 2026-05-18
 */
@RestController
@RequestMapping("/api/alarm")
public class QsDeviceAlarmApiController {
    @Autowired
    private IQsDeviceAlarmService qsDeviceAlarmService;

    /**
     * 查询设备告警列表
     */
    @InnerAuth
    @PostMapping("/list")
    public R<List<QsDeviceAlarm>> list(@RequestBody QsDeviceAlarm qsDeviceAlarm) {
        List<QsDeviceAlarm> list = qsDeviceAlarmService.selectQsDeviceAlarmList(qsDeviceAlarm);
        return R.ok(list);
    }

    /**
     * 获取设备告警详细信息
     */
    @InnerAuth
    @GetMapping("/getInfo/{id}")
    public R<QsDeviceAlarm> getInfo(@PathVariable("id") Long id) {
        return R.ok(qsDeviceAlarmService.selectQsDeviceAlarmById(id));
    }

    /**
     * 新增设备告警(内部调用)
     */
    @InnerAuth
    @PostMapping("/add")
    public R<Long> add(@RequestBody QsDeviceAlarm qsDeviceAlarm) {
        int result = qsDeviceAlarmService.insertQsDeviceAlarm(qsDeviceAlarm);
        return result > 0 ? R.ok(qsDeviceAlarm.getId()) : R.fail("保存失败");
    }

    /**
     * 修改设备告警
     */
    @InnerAuth
    @PutMapping("/edit")
    public R<Boolean> edit(@RequestBody QsDeviceAlarm qsDeviceAlarm) {
        return qsDeviceAlarmService.updateQsDeviceAlarm(qsDeviceAlarm) > 0 ? R.ok(true) : R.fail("更新失败");
    }

    /**
     * 删除设备告警
     */
    @InnerAuth
    @DeleteMapping("/remove/{ids}")
    public R<Boolean> remove(@PathVariable Long[] ids) {
        return qsDeviceAlarmService.deleteQsDeviceAlarmByIds(ids) > 0 ? R.ok(true) : R.fail("删除失败");
    }
}
