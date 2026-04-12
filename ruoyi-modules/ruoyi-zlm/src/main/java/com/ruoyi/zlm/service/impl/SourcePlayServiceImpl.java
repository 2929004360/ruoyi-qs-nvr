package com.ruoyi.zlm.service.impl;

import com.ruoyi.common.core.constant.HttpStatus;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.service.ISourcePlayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资源能力接入-实时录像
 *
 * @FileName SourcePlayServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-04-12
 **/
@Slf4j
@Service(DevicePlayServiceImpl.PLAY_SERVICE)
public class SourcePlayServiceImpl implements ISourcePlayService {

    @Autowired
    private IMediaServerService mediaServerService;

    @Override
    public void play(QsDevice device, Boolean record, ErrorCallback<StreamInfo> callback) {
        try {
            mediaServerService.play(device, record, callback);
        } catch (Exception e) {
            log.error("[点播失败] {}({})", device.getDeviceName(), device.getId(), e);
            callback.run(HttpStatus.ERROR, "播放失败", null);
        }
    }
}
