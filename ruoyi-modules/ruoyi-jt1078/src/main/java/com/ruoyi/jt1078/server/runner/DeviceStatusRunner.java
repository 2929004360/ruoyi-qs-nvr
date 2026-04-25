package com.ruoyi.jt1078.server.runner;

import com.ruoyi.jt1078.server.model.entity.DeviceDO;
import com.ruoyi.jt1078.server.service.IRedisCatchStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceStatusRunner implements CommandLineRunner {

    private final IRedisCatchStorage redisCatchStorage;

    @Override
    public void run(String... args) throws Exception {
        List<DeviceDO> list = redisCatchStorage.getAllDevice();

        for (DeviceDO device : list) {
            device.setOnline(false);
            redisCatchStorage.updateDevice(device);
        }
    }
}
