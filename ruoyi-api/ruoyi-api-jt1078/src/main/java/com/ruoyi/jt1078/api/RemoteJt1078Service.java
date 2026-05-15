package com.ruoyi.jt1078.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.jt1078.api.domain.Jt1078Device;
import com.ruoyi.jt1078.api.factory.RemoteJt1078FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * jt1078 服务
 *
 * @author fengcheng
 */
@FeignClient(contextId = "remoteJt1078Service", value = ServiceNameConstants.JT1078_SERVICE, fallbackFactory = RemoteJt1078FallbackFactory.class)
public interface RemoteJt1078Service {

    /**
     * 根据设备手机号获取设备
     *
     * @param mobileNo 设备手机号
     * @param inner
     * @return
     */
    @GetMapping("/api/jt1078/getDeviceByMobileNo/{mobileNo}")
    R<Jt1078Device> getDeviceByMobileNo(@PathVariable String mobileNo, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 请求预览视频流
     *
     * @param rtpServer
     * @param inner
     * @return
     */
    @PostMapping("/api/jt1078/playStreamCmd")
    R<Void> playStreamCmd(@RequestBody RtpServerParam rtpServer, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 停止视频流
     *
     * @param rtpServer
     * @param inner
     * @return
     */
    @PostMapping("/api/jt1078/streamByeCmd")
    R<Void> streamByeCmd(@RequestBody RtpServerParam rtpServer, @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 获取全部设备
     *
     * @param inner
     * @return
     */
    @GetMapping("/api/jt1078/getAllDevices")
    R<List<Jt1078Device>> getAllDevices(@RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 云台旋转
     *
     * @param mobileNo
     * @param channelNo
     * @param direction
     * @param speed
     * @param inner
     * @return
     */
    @GetMapping("/api/jt1078/ptzRotate/{mobileNo}/{channelNo}")
    R<Void> ptzRotate(@PathVariable String mobileNo, @PathVariable int channelNo,
                      @RequestParam int direction, @RequestParam(defaultValue = "50") int speed,
                      @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 云台调整焦距控制
     *
     * @param mobileNo
     * @param channelNo
     * @param direction
     * @param speed
     * @param inner
     * @return
     */
    @GetMapping("/api/jt1078/ptzFocus/{mobileNo}/{channelNo}")
    R<Void> ptzFocus(@PathVariable String mobileNo, @PathVariable int channelNo,
                     @RequestParam int direction, @RequestParam(defaultValue = "50") int speed,
                     @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 云台调整光圈控制
     *
     * @param mobileNo
     * @param channelNo
     * @param direction
     * @param speed
     * @param inner
     * @return
     */
    @GetMapping("/api/jt1078/ptzIris/{mobileNo}/{channelNo}")
    R<Void> ptzIris(@PathVariable String mobileNo, @PathVariable int channelNo,
                    @RequestParam int direction, @RequestParam(defaultValue = "50") int speed,
                    @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 云台雨刷控制
     *
     * @param mobileNo
     * @param channelNo
     * @param control
     * @param inner
     * @return
     */
    @GetMapping("/api/jt1078/ptzWiper/{mobileNo}/{channelNo}")
    R<Void> ptzWiper(@PathVariable String mobileNo, @PathVariable int channelNo,
                     @RequestParam int control,
                     @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 红外补光控制
     *
     * @param mobileNo
     * @param channelNo
     * @param control
     * @param inner
     * @return
     */
    @GetMapping("/api/jt1078/ptzInfrared/{mobileNo}/{channelNo}")
    R<Void> ptzInfrared(@PathVariable String mobileNo, @PathVariable int channelNo,
                        @RequestParam int control,
                        @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 云台变倍控制
     *
     * @param mobileNo
     * @param channelNo
     * @param direction
     * @param speed
     * @param inner
     * @return
     */
    @GetMapping("/api/jt1078/ptzZoom/{mobileNo}/{channelNo}")
    R<Void> ptzZoom(@PathVariable String mobileNo, @PathVariable int channelNo,
                    @RequestParam int direction, @RequestParam(defaultValue = "50") int speed,
                    @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 查询录像文件列表
     *
     * @param mobileNo
     * @param channelNo
     * @param startTime
     * @param endTime
     * @param inner
     * @return
     */
    @GetMapping("/api/jt1078/queryRecord/{mobileNo}/{channelNo}")
    R<ArrayList<HashMap<String, Object>>> queryRecord(@PathVariable String mobileNo,
                                                        @PathVariable int channelNo,
                                                        @RequestParam String startTime,
                                                        @RequestParam String endTime,
                                                        @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 远程录像回放
     *
     * @param rtpServer
     * @param startTime
     * @param endTime
     * @param playbackMode
     * @param playbackSpeed
     * @param inner
     * @return
     */
    @PostMapping("/api/jt1078/playback")
    R<Void> playback(@RequestBody RtpServerParam rtpServer,
                     @RequestParam String startTime,
                     @RequestParam String endTime,
                     @RequestParam(defaultValue = "0") int playbackMode,
                     @RequestParam(defaultValue = "0") int playbackSpeed,
                     @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);

    /**
     * 录像回放控制
     *
     * @param mobileNo
     * @param channelNo
     * @param playbackMode
     * @param playbackSpeed
     * @param playbackTime
     * @param inner
     * @return
     */
    @GetMapping("/api/jt1078/playbackControl/{mobileNo}/{channelNo}")
    R<Void> playbackControl(@PathVariable String mobileNo,
                            @PathVariable int channelNo,
                            @RequestParam int playbackMode,
                            @RequestParam(defaultValue = "0") int playbackSpeed,
                            @RequestParam(required = false) String playbackTime,
                            @RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}
