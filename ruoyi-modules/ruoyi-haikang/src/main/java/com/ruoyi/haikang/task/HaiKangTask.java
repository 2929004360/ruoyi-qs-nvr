package com.ruoyi.haikang.task;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.haikang.service.impl.HaiKangServiceImpl;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 海康sdk 任务
 *
 * @FileName HaiKangTask
 * @Description
 * @Author fengcheng
 * @date 2026-04-07
 **/
@Component
public class HaiKangTask {

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Scheduled(cron = "0 * * * * ?")
    public void sayHello() {
        QsDevice qsDevice = new QsDevice();
        qsDevice.setType(LiveStreamType.HIK_SDK.getCode());
        R<List<QsDevice>> r = remoteQsDeviceService.list(qsDevice, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }

        List<QsDevice> deviceList = r.getData();

        Set<String> validIps = new HashSet<>();
        for (QsDevice device : deviceList) {
            String ip = device.getIpAddress();
            if (ip != null && !ip.isEmpty()) {
                validIps.add(ip);
            }
        }

        Iterator<Map.Entry<String, Integer>> iterator = HaiKangServiceImpl.userIdMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String mapIp = entry.getKey();

            if (!validIps.contains(mapIp)) {
                iterator.remove();
            }
        }
    }
}
