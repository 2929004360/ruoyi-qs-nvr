package com.ruoyi.zlm.service.impl;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.StreamPullPlay;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.service.IMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MediaServiceImpl implements IMediaService {

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private IMediaServerService mediaServerService;

    @Override
    public boolean closeStreamOnNoneReader(String mediaServerId, String app, String stream, String schema) {
        // 拉流代理
        if ("rtsp".equals(app) || "rtmp".equals(app))  {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceStream(stream, SecurityConstants.INNER);
            if(r.getCode() != Constants.SUCCESS){
                return false;
            }

            QsDevice data = r.getData();
            if(data == null){
                return false;
            }

            if ("1".equals(data.getEnableDisableNoneReader())) {
                // 无人观看停用
                // 修改数据
                StreamPullPlay streamPullPlay = new StreamPullPlay();
                streamPullPlay.setDeviceId(data.getId());
                streamPullPlay.setStreamKey(data.getStreamKey());
                streamPullPlay.setMediaServerId(data.getMediaServerId());

                mediaServerService.stopStreamPullPlay(streamPullPlay);
                return true;
            }else {
                return false;
            }
        }
        return true;
    }
}
