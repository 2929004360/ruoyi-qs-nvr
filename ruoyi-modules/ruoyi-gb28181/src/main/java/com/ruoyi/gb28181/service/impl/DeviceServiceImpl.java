package com.ruoyi.gb28181.service.impl;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.gb28181.api.bean.SipTransactionInfo;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.gb28181.api.domain.GbCode;
import com.ruoyi.gb28181.api.domain.SsrcTransaction;
import com.ruoyi.gb28181.api.utils.DateUtil;
import com.ruoyi.gb28181.config.UserSetting;
import com.ruoyi.gb28181.service.IDeviceService;
import com.ruoyi.gb28181.service.IRedisCatchStorage;
import com.ruoyi.gb28181.service.ISIPCommander;
import com.ruoyi.gb28181.session.SipInviteSessionManager;
import com.ruoyi.gb28181.task.deviceStatus.DeviceStatusTask;
import com.ruoyi.gb28181.task.deviceStatus.DeviceStatusTaskRunner;
import com.ruoyi.gb28181.task.deviceSubscribe.deviceSubscribe.SubscribeTaskRunner;
import com.ruoyi.gb28181.task.deviceSubscribe.deviceSubscribe.impl.SubscribeTaskForCatalog;
import com.ruoyi.gb28181.task.deviceSubscribe.deviceSubscribe.impl.SubscribeTaskForMobilPosition;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.zlm.api.RemoteZlmService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 设备业务
 *
 * @author lin
 */
@Slf4j
@Service
@Order(value = 16)
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    private IRedisCatchStorage redisCatchStorage;

    @Autowired
    private ISIPCommander commander;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private DeviceStatusTaskRunner deviceStatusTaskRunner;

    @Autowired
    private SubscribeTaskRunner subscribeTaskRunner;

    @Autowired
    private SipInviteSessionManager sessionManager;

    @Autowired
    private RemoteZlmService remoteZlmService;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    /**
     * 查询设备信息
     *
     * @param deviceId 设备编号
     * @return 设备信息
     */
    @Override
    public Device getDeviceByDeviceId(String deviceId) {
        return redisCatchStorage.getDevice(deviceId);
    }

    /**
     * 设备上线
     *
     * @param device 设备信息
     */
    @Override
    public void online(Device device, SipTransactionInfo sipTransactionInfo) {
        log.info("[设备上线] deviceId：{}->{}:{}", device.getDeviceId(), device.getIp(), device.getPort());
        Device deviceInRedis = redisCatchStorage.getDevice(device.getDeviceId());

        String now = DateUtil.getNow();

        device.setUpdateTime(now);
        device.setKeepaliveTime(now);
        if (device.getHeartBeatCount() == null) {
            // 读取设备配置， 获取心跳间隔和心跳超时次数， 在次之前暂时设置为默认值
            device.setHeartBeatCount(3);
            device.setHeartBeatInterval(60);
            device.setPositionCapability(0);

        }
        if (sipTransactionInfo != null) {
            device.setSipTransactionInfo(sipTransactionInfo);
        } else {
            if (deviceInRedis != null) {
                device.setSipTransactionInfo(deviceInRedis.getSipTransactionInfo());
            }
        }

        // 第一次上线 或则设备之前是离线状态--进行通道同步和设备信息查询
        if (deviceInRedis == null) {
            device.setOnLine(true);
            device.setCreateTime(now);
            device.setUpdateTime(now);
            log.info("[设备上线,首次注册]: {}，查询设备信息以及通道信息", device.getDeviceId());
            if (device.getStreamMode() == null) {
                device.setStreamMode("TCP-PASSIVE");
            }
            redisCatchStorage.updateDevice(device);
            try {
                commander.deviceInfoQuery(device, null);
                commander.deviceConfigQuery(device, null, "BasicParam", null);
            } catch (InvalidArgumentException | SipException | ParseException e) {
                log.error("[命令发送失败] 查询设备信息: {}", e.getMessage());
            }
            log.info("[设备上线]: {}，查询通道信息", device.getDeviceId());
            sync(device);
        } else {
            device.setServerId(userSetting.getServerId());
            if (!deviceInRedis.isOnLine()) {
                device.setOnLine(true);
                device.setCreateTime(now);
                redisCatchStorage.updateDevice(device);

                if (userSetting.getSyncChannelOnDeviceOnline()) {
                    log.info("[设备上线,离线状态下重新注册]: {}，查询设备信息以及通道信息", device.getDeviceId());
                    try {
                        commander.deviceInfoQuery(device, null);
                    } catch (InvalidArgumentException | SipException | ParseException e) {
                        log.error("[命令发送失败] 查询设备信息: {}", e.getMessage());
                    }
                    log.info("[设备上线]: {}，查询通道信息", device.getDeviceId());
                    sync(device);
                } else {
                    if (isDevice(device.getDeviceId())) {
                        log.info("[设备上线]: {}，查询通道信息", device.getDeviceId());
                        sync(device);
                    }
                }
            } else {
                redisCatchStorage.updateDevice(device);
            }
        }

        long expiresTime = Math.min(device.getExpires(), device.getHeartBeatInterval() * device.getHeartBeatCount()) * 1000L;
        if (deviceStatusTaskRunner.containsKey(device.getDeviceId())) {
            if (sipTransactionInfo == null) {
                deviceStatusTaskRunner.updateDelay(device.getDeviceId(), expiresTime + System.currentTimeMillis());
            } else {
                deviceStatusTaskRunner.removeTask(device.getDeviceId());
                DeviceStatusTask task = DeviceStatusTask.getInstance(device.getDeviceId(), sipTransactionInfo, expiresTime + System.currentTimeMillis(), this::deviceStatusExpire);
                deviceStatusTaskRunner.addTask(task);
            }
        } else {
            DeviceStatusTask task = DeviceStatusTask.getInstance(device.getDeviceId(), sipTransactionInfo, expiresTime + System.currentTimeMillis(), this::deviceStatusExpire);
            deviceStatusTaskRunner.addTask(task);
        }
        // 同步设备状态到 QS 模块
        try {
            remoteQsDeviceService.updateDeviceStatusByGbDeviceId(device.getDeviceId(), "ON", SecurityConstants.INNER);
        } catch (Exception e) {
            log.error("[同步设备状态] 设备上线，同步到 QS 模块失败：{}", device.getDeviceId(), e);
        }
    }

    @Override
    public void offline(String deviceId, String reason) {
        Device device = getDeviceByDeviceId(deviceId);
        if (device == null) {
            log.warn("[设备不存在] device：{}", deviceId);
            return;
        }

        // 主动查询设备状态, 没有 HostAddress 无法发送请求，可能是手动添加的设备
        if (device.getHostAddress() != null) {
            Boolean deviceStatus = getDeviceStatus(device);
            if (deviceStatus != null && deviceStatus) {
                log.info("[设备离线] 主动探测发现设备在线，暂不处理  device：{}", deviceId);
                online(device, null);
                return;
            }
        }
        log.info("[设备离线] {}, device：{}， 心跳间隔： {}，心跳超时次数： {}， 上次心跳时间：{}， 上次注册时间： {}", reason, deviceId,
                device.getHeartBeatInterval(), device.getHeartBeatCount(), device.getKeepaliveTime(), device.getRegisterTime());
        device.setOnLine(false);
        cleanOfflineDevice(device);
        redisCatchStorage.updateDevice(device);
        if (isDevice(deviceId)) {
            channelOfflineByDevice(device);
        }
        // 同步设备状态到 QS 模块
        try {
            remoteQsDeviceService.updateDeviceStatusByGbDeviceId(deviceId, "OFFLINE", SecurityConstants.INNER);
        } catch (Exception e) {
            log.error("[同步设备状态] 设备离线，同步到 QS 模块失败：{}", deviceId, e);
        }
    }

    /**
     * 更新设备心跳信息
     *
     * @param device
     */
    @Override
    public void updateDeviceHeartInfo(Device device) {
        Device deviceInDb = getDeviceByDeviceId(device.getDeviceId());
        if (deviceInDb == null) {
            return;
        }
        if (!Objects.equals(deviceInDb.getHeartBeatCount(), device.getHeartBeatCount())
                || !Objects.equals(deviceInDb.getHeartBeatInterval(), device.getHeartBeatInterval())) {

            deviceInDb.setHeartBeatCount(device.getHeartBeatCount());
            deviceInDb.setHeartBeatInterval(device.getHeartBeatInterval());
            deviceInDb.setPositionCapability(device.getPositionCapability());
            updateDevice(deviceInDb);

            long expiresTime = Math.min(device.getExpires(), device.getHeartBeatInterval() * device.getHeartBeatCount()) * 1000L;
            if (deviceStatusTaskRunner.containsKey(device.getDeviceId())) {
                deviceStatusTaskRunner.updateDelay(device.getDeviceId(), expiresTime + System.currentTimeMillis());
            }
        }
    }

    /**
     * 根据设备id和通道获取设备通道
     *
     * @param gbDeviceId
     * @param gbChannelId
     * @return
     */
    @Override
    public DeviceChannel getDeviceChannelByChannelId(String gbDeviceId, String gbChannelId) {
        List<DeviceChannel> deviceChannels = redisCatchStorage.queryAllChannelsForRefresh(gbDeviceId);
        if (deviceChannels.isEmpty()) {
            throw new RuntimeException("设备【" + gbDeviceId + "】没有通道");
        }

        DeviceChannel deviceChannel = null;
        for (DeviceChannel channel : deviceChannels) {
            if (gbChannelId.equals(channel.getDeviceId())) {
                deviceChannel = channel;
            }
        }
        if (deviceChannel == null) {
            throw new RuntimeException("设备【" + gbDeviceId + "】找不到【" + gbChannelId + "】通道");
        }
        return deviceChannel;
    }

    private void deviceStatusExpire(String deviceId, SipTransactionInfo transactionInfo) {
        log.info("[设备状态] 到期， 编号： {}", deviceId);
        offline(deviceId, "保活到期");
    }

    public Boolean getDeviceStatus(@NotNull Device device) {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        try {
            commander.deviceStatusQuery(device, ((code, msg, data) -> {
                queue.offer(msg);
            }));
            String data = queue.poll(10, TimeUnit.SECONDS);
            if (data != null && "ONLINE".equalsIgnoreCase(data.trim())) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }

        } catch (InvalidArgumentException | SipException | ParseException | InterruptedException e) {
            log.error("[命令发送失败] 设备状态查询: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 批量修改设备
     *
     * @param deviceList
     */
    @Override
    public void updateDeviceList(List<Device> deviceList) {
        for (Device device : deviceList) {
            redisCatchStorage.updateDevice(device);
        }
    }

    /**
     * 修改设备
     *
     * @param device
     */
    @Override
    public void updateDevice(Device device) {
        redisCatchStorage.updateDevice(device);
    }

    public void sync(Device device) {
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        try {
            commander.catalogQuery(device, sn, ((code, msg, data) -> {
//                log.info("[获取通道]失败, deviceId： {}", device.getDeviceId());
            }));
        } catch (SipException | InvalidArgumentException | ParseException e) {
            log.error("[获取通道]失败,信令发送失败, deviceId： {}", device.getDeviceId());
        }
    }

    private boolean isDevice(String deviceId) {
        GbCode decode = GbCode.decode(deviceId);
        if (decode == null) {
            return true;
        }
        int code = Integer.parseInt(decode.getTypeCode());
        return code <= 199;
    }

    private void cleanOfflineDevice(Device device) {
        if (subscribeTaskRunner.containsKey(SubscribeTaskForCatalog.getKey(device))) {
            subscribeTaskRunner.removeSubscribe(SubscribeTaskForCatalog.getKey(device));
        }
        if (subscribeTaskRunner.containsKey(SubscribeTaskForMobilPosition.getKey(device))) {
            subscribeTaskRunner.removeSubscribe(SubscribeTaskForMobilPosition.getKey(device));
        }

        // 清理设备相关的视频流会话
        List<SsrcTransaction> ssrcTransactions = sessionManager.getSsrcTransactionByDeviceId(device.getDeviceId());
        if (ssrcTransactions != null && !ssrcTransactions.isEmpty()) {
            log.info("[设备离线] 清理设备相关的视频流会话, deviceId: {}, 会话数量: {}", 
                    device.getDeviceId(), ssrcTransactions.size());
            for (SsrcTransaction ssrcTransaction : ssrcTransactions) {
                try {
                    log.info("[BYE 清理资源] deviceId: {}, channelId: {}, app: {}, stream: {}, ssrc: {}", 
                            ssrcTransaction.getDeviceId(), 
                            ssrcTransaction.getChannelId(), 
                            ssrcTransaction.getApp(), 
                            ssrcTransaction.getStream(), 
                            ssrcTransaction.getSsrc());

                    sessionManager.removeByCallId(ssrcTransaction.getCallId());
                    remoteZlmService.releaseSsrc(ssrcTransaction.getMediaServerId(), ssrcTransaction.getSsrc(), SecurityConstants.INNER);
                    
                    RtpServerParam rtpServerParam = new RtpServerParam();
                    rtpServerParam.setMediaServerId(ssrcTransaction.getMediaServerId());
                    rtpServerParam.setApp(ssrcTransaction.getApp());
                    rtpServerParam.setStream(ssrcTransaction.getStream());
                    rtpServerParam.setSsrc(ssrcTransaction.getSsrc());
                    rtpServerParam.setGbDeviceId(ssrcTransaction.getDeviceId());
                    rtpServerParam.setGbChannelId(ssrcTransaction.getChannelId());
                    remoteZlmService.closeRTPServer(ssrcTransaction.getMediaServerId(), rtpServerParam, SecurityConstants.INNER);
                } catch (Exception e) {
                    log.error("[设备离线] 清理视频流会话异常, deviceId: {}, ssrc: {}", 
                            device.getDeviceId(), ssrcTransaction.getSsrc(), e);
                }
            }
        }
    }

    private void channelOfflineByDevice(Device device) {
        // 进行通道离线
        List<DeviceChannel> channelList = redisCatchStorage.queryAllChannelsForRefresh(device.getDeviceId());
        if (channelList.isEmpty()) {
            return;
        }
        for (DeviceChannel deviceChannel : channelList) {
            deviceChannel.setStatus("OFF");
        }
        redisCatchStorage.batchUpdate(device.getDeviceId(), channelList);
    }

    /**
     * 获取所有国标设备
     *
     * @return 设备列表
     */
    @Override
    public List<Device> getAllDevices() {
        return redisCatchStorage.getAllDevices();
    }

    /**
     * 根据设备id获取所有通道
     *
     * @param gbDeviceId 设备编号
     * @return 通道列表
     */
    @Override
    public List<DeviceChannel> getChannelsByDeviceId(String gbDeviceId) {
        return redisCatchStorage.queryAllChannelsForRefresh(gbDeviceId);
    }
}
