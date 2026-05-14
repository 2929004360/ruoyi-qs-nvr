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


    /**
     * 修改视频监控设备
     */
    @InnerAuth
    @PutMapping("/updateQsDevice")
    public R<Boolean> updateQsDevice(@RequestBody QsDevice qsDevice) {
        return qsDeviceService.editQsDevice(qsDevice) > 0 ? R.ok(true) : R.ok(false);
    }

    /**
     * 更具流id获取视频监控设备
     */
    @InnerAuth
    @GetMapping("/getQsDeviceStream/{stream}")
    public R<QsDevice> getQsDeviceStream(@PathVariable String stream) {
        return R.ok(qsDeviceService.getQsDeviceStream(stream));
    }

    /**
     * 获取视频监控设备详细信息
     */
    @InnerAuth
    @GetMapping("/getQsDeviceInfo/{id}")
    public R<QsDevice> getQsDeviceInfo(@PathVariable Long id) {
        return R.ok(qsDeviceService.selectQsDeviceById(id));
    }

    /**
     * 设备状态任务
     */
    @InnerAuth
    @GetMapping("/task")
    public R<Void> task() {
        qsDeviceService.task();
        return R.ok();
    }

    /**
     * 清理设备计划id
     *
     * @param planId 设备id
     * @return
     */
    @InnerAuth
    @GetMapping("/cleanRecordPlanId/{planId}")
    public R<Void> cleanRecordPlanId(@PathVariable Long planId) {
        qsDeviceService.cleanRecordPlanId(planId);
        return R.ok();
    }

    /**
     * 根据设备id集合查询设备信息
     *
     * @param startDeviceIdList 设备id集合
     * @return
     */
    @InnerAuth
    @GetMapping("/queryByIds/{startDeviceIdList}")
    public R<List<QsDevice>> queryByIds(@PathVariable List<Long> startDeviceIdList) {
        List<QsDevice> deviceList = qsDeviceService.queryByIds(startDeviceIdList);
        return R.ok(deviceList);
    }

    /**
     * 根据计划id查询设备数量
     *
     * @param planId 设备id
     * @return
     */
    @InnerAuth
    @GetMapping("/countRecordPlanDevice/{planId}")
    public R<Integer> countRecordPlanDevice(@PathVariable Long planId) {
        Integer i = qsDeviceService.countRecordPlanDevice(planId);
        return R.ok(i);
    }

    /**
     * 新增视频监控设备
     */
    @InnerAuth
    @PostMapping("/addQsDevice")
    public R<Boolean> addQsDevice(@RequestBody QsDevice qsDevice) {
        return qsDeviceService.addQsDevice(qsDevice) > 0 ? R.ok(true) : R.ok(false);
    }

    /**
     * 根据 gbDeviceId 更新设备在线状态
     *
     * @param gbDeviceId   国标设备编号
     * @param deviceStatus 设备状态
     * @return
     */
    @InnerAuth
    @PostMapping("/updateDeviceStatusByGbDeviceId/{gbDeviceId}/{deviceStatus}")
    public R<Boolean> updateDeviceStatusByGbDeviceId(@PathVariable String gbDeviceId, @PathVariable String deviceStatus) {
        Boolean result = qsDeviceService.updateDeviceStatusByGbDeviceId(gbDeviceId, deviceStatus);
        return R.ok(result);
    }

    /**
     * 根据 jtMobileNo 更新设备在线状态
     *
     * @param jtMobileNo   设备手机号
     * @param deviceStatus 设备状态
     * @return
     */
    @InnerAuth
    @PostMapping("/updateDeviceStatusByJtMobileNo/{jtMobileNo}/{deviceStatus}")
    public R<Boolean> updateDeviceStatusByJtMobileNo(@PathVariable String jtMobileNo, @PathVariable String deviceStatus) {
        Boolean result = qsDeviceService.updateDeviceStatusByJtMobileNo(jtMobileNo, deviceStatus);
        return R.ok(result);
    }

    /**
     * 根据 gbCode 查询设备
     *
     * @param gbCode 国标设备编号
     * @return
     */
    @InnerAuth
    @GetMapping("/getDeviceByGbCode/{gbCode}")
    public R<QsDevice> getDeviceByGbCode(@PathVariable String gbCode) {
        QsDevice qsDevice = qsDeviceService.getDeviceByGbCode(gbCode);
        if (qsDevice == null){
            return R.fail("设备不存在");
        }
        return R.ok(qsDevice);
    }
}
