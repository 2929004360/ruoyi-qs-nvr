package com.ruoyi.zlm.service.impl;

import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IDevicePlayService;
import com.ruoyi.zlm.service.ISourcePlayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @FileName DevicePlayServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-04-12
 **/
@Slf4j
@Service
public class DevicePlayServiceImpl implements IDevicePlayService {

    @Autowired
    private Map<String, ISourcePlayService> sourcePlayServiceMap;

    public final static String PLAY_SERVICE = "sourceDevicePlayService";

    @Override
    public void play(QsDevice device, Boolean record, ErrorCallback<StreamInfo> callback) {
        log.info("[设备] 播放， 类型： {}", LiveStreamType.getByCode(device.getType()));
        ISourcePlayService sourceChannelPlayService = sourcePlayServiceMap.get(PLAY_SERVICE);
        if (sourceChannelPlayService == null) {
            // 设备数据异常
            log.error("[点播通用设备] 类型编号： {} 不支持实时流预览", LiveStreamType.getByCode(device.getType()));
            throw new RuntimeException("Device not supported");
        }
        sourceChannelPlayService.play(device, record, (code, msg, data) -> {
            callback.run(code, msg, data);
        });
    }
}
