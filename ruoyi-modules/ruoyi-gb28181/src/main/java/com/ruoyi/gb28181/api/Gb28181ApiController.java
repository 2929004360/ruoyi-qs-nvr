package com.ruoyi.gb28181.api;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.gb28181.api.utils.SipUtils;
import com.ruoyi.gb28181.config.UserSetting;
import com.ruoyi.gb28181.service.IDeviceService;
import com.ruoyi.gb28181.service.ISIPCommander;
import com.ruoyi.gb28181.session.SipInviteSessionManager;
import com.ruoyi.zlm.api.RemoteZlmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sdp.*;
import javax.sip.ResponseEvent;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * gb28181 Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/gb28181")
public class Gb28181ApiController {

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private ISIPCommander sipCommander;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private SipInviteSessionManager sessionManager;

    @Autowired
    private RemoteZlmService remoteZlmService;

    /**
     * 根据设备id获取设备
     *
     * @param gbDeviceId
     * @return
     */
    @GetMapping("/getDeviceByDeviceId/{gbDeviceId}")
    R<Device> getDeviceByDeviceId(@PathVariable String gbDeviceId) {
        return R.ok(deviceService.getDeviceByDeviceId(gbDeviceId));
    }

    /**
     * 请求预览视频流
     *
     * @param rtpServer
     * @return
     */
    @PostMapping("/playStreamCmd")
    R<Boolean> playStreamCmd(@RequestBody RtpServerParam rtpServer) throws ExecutionException, InterruptedException, TimeoutException {
        Device device = deviceService.getDeviceByDeviceId(rtpServer.getGbDeviceId());

        if (device == null) {
            throw new RuntimeException("国标设备不存在 deviceId：" + rtpServer.getGbDeviceId());
        }
        CompletableFuture<R<Boolean>> future = new CompletableFuture<>();

        try {
            sipCommander.playStreamCmd(device, rtpServer, (eventResult) -> {
                // 处理收到200ok后的TCP主动连接以及SSRC不一致的问题
                ResponseEvent responseEvent = (ResponseEvent) eventResult.event;
                String contentString = new String(responseEvent.getResponse().getRawContent());
                String ssrcInResponse = SipUtils.getSsrcFromSdp(contentString);

                // 兼容回复的消息中缺少ssrc(y字段)的情况
                if (ssrcInResponse == null) {
                    ssrcInResponse = rtpServer.getSsrc();
                }

                if (rtpServer.getSsrc().equals(ssrcInResponse)) {
                    // 多端口
                    if (device.getStreamMode().equalsIgnoreCase("TCP-ACTIVE")) {
                        if (!device.getStreamMode().equalsIgnoreCase("TCP-ACTIVE")) {
                            return;
                        }

                        String substring;
                        if (contentString.indexOf("y=") > 0) {
                            substring = contentString.substring(0, contentString.indexOf("y="));
                        } else {
                            substring = contentString;
                        }

                        try {
                            SessionDescription sdp = SdpFactory.getInstance().createSessionDescription(substring);
                            int port = -1;
                            Vector mediaDescriptions = sdp.getMediaDescriptions(true);
                            for (Object description : mediaDescriptions) {
                                MediaDescription mediaDescription = (MediaDescription) description;
                                Media media = mediaDescription.getMedia();

                                Vector mediaFormats = media.getMediaFormats(false);
                                if (mediaFormats.contains("96")) {
                                    port = media.getMediaPort();
                                    break;
                                }
                            }
                            log.info("[TCP主动连接对方] deviceId: {}, channelId: {}, 连接对方的地址：{}:{}, 收流模式：{}, SSRC: {}, SSRC校验：{}", rtpServer.getGbDeviceId(), rtpServer.getGbChannelId(), sdp.getConnection().getAddress(), port, device.getStreamMode(), rtpServer.getSsrc(), device.isSsrcCheck());
                            R<Boolean> r = remoteZlmService.connectRtpServer(rtpServer.getMediaServerId(), sdp.getConnection().getAddress(), port, rtpServer.getStream(), SecurityConstants.INNER);

                            if (r.getCode() != Constants.SUCCESS) {
                                sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
                                remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
                                remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
                                future.complete(R.ok(false, "[TCP主动连接对方] deviceId: " + rtpServer.getGbDeviceId() + ", channelId: " + rtpServer.getGbChannelId() + ""));
                                return;
                            }
                            Boolean result = r.getData();
                            log.info("[TCP主动连接对方] 结果： {}", result);
                            if (!result) {
                                // 主动连接失败，结束流程， 清理数据
                                sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
                                remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
                                remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
                                future.complete(R.ok(false, "[TCP主动连接对方] deviceId: " + rtpServer.getGbDeviceId() + ", channelId: " + rtpServer.getGbChannelId() + ""));
                            }
                        } catch (SdpException e) {
                            log.error("[TCP主动连接对方] deviceId: {}, channelId: {}, 解析200OK的SDP信息失败", rtpServer.getGbDeviceId(), rtpServer.getGbChannelId(), e);
                            sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
                            remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
                            remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);

                            future.complete(R.fail(false, "[TCP主动连接对方] deviceId: " + rtpServer.getGbDeviceId() + ", channelId: " + rtpServer.getGbChannelId() + ", 解析200OK的SDP信息失败"));
                        }
                    }
                }

                future.complete(R.ok(true, "国标28181请求预览视频流成功"));
            }, (event) -> {
                sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
                remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
                remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
                future.complete(R.fail("国标28181请求预览视频流失败"));
            }, userSetting.getPlayTimeout().longValue());
        } catch (Exception e) {
            log.error("发送国标播放sip错误 deviceId：{}", rtpServer.getGbDeviceId(), e);
            future.complete(R.fail(false, "国标28181请求预览视频流失败"));
            remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
            sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
        }
        // 阻塞等待结果
        try {
            return future.get(userSetting.getPlayTimeout().longValue(), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("等待播放响应超时或出错 deviceId：{}", rtpServer.getGbDeviceId(), e);
            // 超时或者异常，需要清理资源
            remoteZlmService.releaseSsrc(rtpServer.getMediaServerId(), rtpServer.getSsrc(), SecurityConstants.INNER);
            remoteZlmService.closeRTPServer(rtpServer.getMediaServerId(), rtpServer, SecurityConstants.INNER);
            sessionManager.removeByStream(rtpServer.getApp(), rtpServer.getStream());
            return R.fail(false, "国标28181请求预览视频流超时或出错");
        }
    }

    /**
     * 根据设备id和通道获取设备通道
     *
     * @param gbDeviceId
     * @param gbChannelId
     * @return
     */
    @GetMapping("/getDeviceChannelByChannelId/{gbDeviceId}/{gbChannelId}")
    R<DeviceChannel> getDeviceChannelByChannelId(@PathVariable String gbDeviceId, @PathVariable String gbChannelId) {
        Device device = deviceService.getDeviceByDeviceId(gbDeviceId);
        if (device == null) {
            return R.fail("gb28181 设备不存在 deviceId:" + gbDeviceId);
        }

        DeviceChannel deviceChannel = deviceService.getDeviceChannelByChannelId(gbDeviceId, gbChannelId);

        return R.ok(deviceChannel);
    }

    /**
     * 停止视频流
     *
     * @param rtpServer
     * @return
     */
    @PostMapping("/streamByeCmd")
    R<Void> streamByeCmd(@RequestBody RtpServerParam rtpServer) {
        Device device = deviceService.getDeviceByDeviceId(rtpServer.getGbDeviceId());

        if (device == null) {
            return R.fail("gb28181 设备不存在 deviceId:" + rtpServer.getGbDeviceId());
        }

        try {
            sipCommander.stopStreamCmd(device, rtpServer);
            return R.ok();
        } catch (Exception e) {
            log.error("停止播放失败 deviceId:" + rtpServer.getGbDeviceId(), e);
            return R.fail("停止播放失败:" + e.getMessage());
        }
    }
}
