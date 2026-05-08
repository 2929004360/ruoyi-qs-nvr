package com.ruoyi.jt1078.server.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.jt1078.protocol.t1078.T1205;
import com.ruoyi.jt1078.protocol.t1078.T9205;
import com.ruoyi.jt1078.server.endpoint.MessageManager;
import com.ruoyi.jt1078.server.model.entity.DeviceDO;
import com.ruoyi.jt1078.server.service.IRedisCatchStorage;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class DeviceController {

    private final IRedisCatchStorage redisCatchStorage;

    private final MessageManager messageManager;

    private static final DateTimeFormatter BCD_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    @Operation(summary = "获取全部设备")
    @GetMapping("allList")
    public AjaxResult getAllDevice() {
        return AjaxResult.success(redisCatchStorage.getAllDevice());
    }

        /**
     * 查询录像文件列表
     */
    @GetMapping("/queryRecord/{mobileNo}/{channelNo}")
    public R<ArrayList<HashMap<String, Object>>> queryRecord(
            @PathVariable String mobileNo,
            @PathVariable int channelNo,
            @RequestParam @NotBlank(message = "开始时间不能为空") String startTime,
            @RequestParam @NotBlank(message = "结束时间不能为空") String endTime) {
        log.info("[JT1078 查询录像] mobileNo:{}, channelNo:{}, startTime:{}, endTime:{}", mobileNo, channelNo, startTime, endTime);

        DeviceDO deviceDO = redisCatchStorage.getDevice(mobileNo);
        if (deviceDO == null) {
            return R.fail("jt1078 设备不存在 mobileNo:" + mobileNo);
        }

        try {
            String bcdStartTime = convertToBcdTime(startTime);
            String bcdEndTime = convertToBcdTime(endTime);

            T9205 t9205 = new T9205()
                    .setChannelNo(channelNo)
                    .setStartTime(bcdStartTime)
                    .setEndTime(bcdEndTime)
                    .setMediaType(0)
                    .setStreamType(0)
                    .setStorageType(0);
            t9205.setClientId(mobileNo);

            messageManager.notify(mobileNo, t9205).block();

            T1205 recordList = null;
            for (int i = 0; i < 10; i++) {
                Thread.sleep(500);
                recordList = redisCatchStorage.getRecordList(mobileNo);
                if (recordList != null) {
                    break;
                }
            }

            if (recordList == null) {
                return R.fail("获取录像列表超时");
            }

            ArrayList<HashMap<String, Object>> result = new ArrayList<>();
            if (recordList.getItems() != null) {
                for (T1205.Item item : recordList.getItems()) {
                    HashMap<String, Object> record = new HashMap<>();
                    record.put("channel", item.getChannelNo());
                    record.put("startTime", item.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    record.put("endTime", item.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    record.put("type", item.getMediaType());
                    record.put("fileName", item.getStartTime().format(BCD_FORMATTER) + "_" + item.getEndTime().format(BCD_FORMATTER));
                    record.put("fileSize", item.getSize());
                    record.put("streamType", item.getStreamType());
                    record.put("storageType", item.getStorageType());
                    record.put("warnBit", item.getWarnBit());
                    result.add(record);
                }
            }

            return R.ok(result);
        } catch (Exception e) {
            log.error("[JT1078 查询录像失败] mobileNo:{}", mobileNo, e);
            return R.fail("jt1078 查询录像失败:" + e.getMessage());
        }
    }


    private String convertToBcdTime(String timeStr) {
        LocalDateTime time;
        if (timeStr.length() == 19) {
            time = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else if (timeStr.length() == 14) {
            time = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        } else {
            time = LocalDateTime.now();
        }
        return time.format(BCD_FORMATTER);
    }
}
