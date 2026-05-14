package com.ruoyi.zlm.service.impl;

import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IDevicePlayService;
import com.ruoyi.zlm.service.IMediaServerService;
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

    @Autowired
    private IMediaServerService mediaServerService;

    public final static String PLAY_SERVICE = "sourceDevicePlayService";

    @Override
    public void play(QsDevice device, Boolean record, ErrorCallback<StreamInfo> callback) {
        log.info("[设备] 播放， 类型： {}", LiveStreamType.getByCode(device.getType()));
        mediaServerService.play(device, record, callback);
    }
}
