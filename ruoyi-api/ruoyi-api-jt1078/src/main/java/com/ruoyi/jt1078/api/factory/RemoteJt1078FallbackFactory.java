package com.ruoyi.jt1078.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.jt1078.api.RemoteJt1078Service;
import com.ruoyi.jt1078.api.domain.Jt1078Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * jt1078服务降级处理
 *
 * @author fengcheng
 */
@Slf4j
@Component
public class RemoteJt1078FallbackFactory implements FallbackFactory<RemoteJt1078Service> {

    @Override
    public RemoteJt1078Service create(Throwable throwable) {
        log.error("jt1078服务调用失败:{}", throwable.getMessage());
        return new RemoteJt1078Service() {
            @Override
            public R<Jt1078Device> getDeviceByMobileNo(String mobileNo, String inner) {
                return R.fail("获取jt1078设备失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> playStreamCmd(RtpServerParam rtpServer, String inner) {
                return R.fail("jt1078请求预览视频流失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> streamByeCmd(RtpServerParam rtpServer, String inner) {
                return R.fail("jt1078停止视频流失败:" + throwable.getMessage());
            }

            @Override
            public R<List<Jt1078Device>> getAllDevices(String inner) {
                return R.fail("jt1078获取全部设备失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> ptzRotate(String mobileNo, int channelNo, int direction, int speed, String inner) {
                return R.fail("jt1078云台旋转失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> ptzFocus(String mobileNo, int channelNo, int direction, int speed, String inner) {
                return R.fail("jt1078云台调整焦距失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> ptzIris(String mobileNo, int channelNo, int direction, int speed, String inner) {
                return R.fail("jt1078云台调整光圈失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> ptzWiper(String mobileNo, int channelNo, int control, String inner) {
                return R.fail("jt1078云台雨刷控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> ptzInfrared(String mobileNo, int channelNo, int control, String inner) {
                return R.fail("jt1078红外补光控制失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> ptzZoom(String mobileNo, int channelNo, int direction, int speed, String inner) {
                return R.fail("jt1078云台变倍控制失败:" + throwable.getMessage());
            }

            @Override
            public R<ArrayList<HashMap<String, Object>>> queryRecord(String mobileNo, int channelNo, String startTime, String endTime, String inner) {
                return R.fail("jt1078查询录像文件列表失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> playback(RtpServerParam rtpServer, String startTime, String endTime, int playbackMode, int playbackSpeed, String inner) {
                return R.fail("jt1078远程录像回放失败:" + throwable.getMessage());
            }

            @Override
            public R<Void> playbackControl(String mobileNo, int channelNo, int playbackMode, int playbackSpeed, String playbackTime, String inner) {
                return R.fail("jt1078录像回放控制失败:" + throwable.getMessage());
            }
        };
    }
}
