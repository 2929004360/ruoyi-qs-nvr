package com.ruoyi.zlm.service.impl;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.RTPServerParam;
import com.ruoyi.zlm.api.domain.StreamPullPlay;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.constants.VideoManagerConstants;
import com.ruoyi.zlm.domain.StreamAuthorityInfo;
import com.ruoyi.zlm.hook.ResultForOnPublish;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.service.IMediaService;
import com.ruoyi.zlm.utils.DateUtil;
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
        if ("rtsp".equals(app) || "rtmp".equals(app) || "flv".equals(app) || "hls".equals(app))  {
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
        }else if("haikang".equals(app) || "haikang_isup".equals(app) || "dahua".equals(app)){
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
                RTPServerParam rtpServerParam = new RTPServerParam();
                rtpServerParam.setId(data.getId());
                rtpServerParam.setType(data.getType());
                rtpServerParam.setStreamId(stream);
                mediaServerService.stopRtpPlay(rtpServerParam);
                return true;
            }else {
                return false;
            }
        }


        return true;
    }

    @Override
    public ResultForOnPublish authenticatePublish(ZlmMediaServer mediaServer, String app, String stream, String params) {
        ResultForOnPublish result = new ResultForOnPublish();
        result.setEnable_audio(true);
        result.setEnable_mp4(false);

        return result;
    }
}
