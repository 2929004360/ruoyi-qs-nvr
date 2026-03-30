package com.ruoyi.qs.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.service.IQsDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 视频监控设备Controller
 *
 * @author fengcheng
 * @date 2026-03-27
 */
@RestController
@RequestMapping("/api/device")
public class QsDeviceApiController {
    @Autowired
    private IQsDeviceService qsDeviceService;

    /**
     * 查询视频监控设备
     */
    @InnerAuth
    @PostMapping("/allList")
    public R<List<QsDevice>> list(@RequestBody QsDevice qsDevice) {
        List<QsDevice> list = qsDeviceService.selectQsDeviceList(qsDevice);
        return R.ok(list);
    }

    /**
     * 更新设备在线状态
     *
     * @param onlineDeviceSet 在线设备集合
     * @param deviceStatus    设备状态
     * @return
     */
    @InnerAuth
    @PostMapping("/updateDeviceStatusList/{deviceStatus}")
    public R<Boolean> updateQsDeviceStatusList(@RequestBody Set<Long> onlineDeviceSet, @PathVariable String deviceStatus) {
        Boolean b = qsDeviceService.updateQsDeviceStatusList(onlineDeviceSet, deviceStatus);
        return R.ok(b);
    }
}
