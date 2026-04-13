package com.ruoyi.zlm.service.impl;

import com.google.common.base.Joiner;
import com.ruoyi.common.core.constant.HttpStatus;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.domain.MediaInfo;
import com.ruoyi.zlm.api.domain.RTPServerParam;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.api.domain.StreamPullPlay;
import com.ruoyi.zlm.common.InviteErrorCode;
import com.ruoyi.zlm.domain.ZlmRecordPlan;
import com.ruoyi.zlm.domain.ZlmRecordPlanItem;
import com.ruoyi.zlm.event.MediaDepartureEvent;
import com.ruoyi.zlm.mapper.ZlmRecordPlanItemMapper;
import com.ruoyi.zlm.mapper.ZlmRecordPlanMapper;
import com.ruoyi.zlm.service.IDevicePlayService;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.service.IZlmRecordPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 录像计划Service业务层处理
 *
 * @author fengcheng
 * @date 2026-04-11
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ZlmRecordPlanServiceImpl implements IZlmRecordPlanService {
    @Autowired
    private ZlmRecordPlanMapper zlmRecordPlanMapper;

    @Autowired
    private ZlmRecordPlanItemMapper zlmRecordPlanItemMapper;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private IMediaServerService mediaServerService;

    @Autowired
    private IDevicePlayService devicePlayService;


    Map<Long, StreamInfo> recordStreamMap = new HashMap<>();

    /**
     * 流离开的处理
     */
    @Async("taskExecutor")
    @EventListener
    public void onApplicationEvent(MediaDepartureEvent event) {
        // 流断开，检查是否还处于录像状态， 如果是则继续录像
        Long deviceId = recording(event.getApp(), event.getStream());
        if (deviceId == null) {
            return;
        }
        // 重新拉起
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != HttpStatus.SUCCESS) {
            throw new RuntimeException("根据设备id查询设备信息失败");
        }

        QsDevice device = r.getData();
        if (device == null) {
            log.warn("[录制计划] 流离开时拉起需要录像的流时, 发现设备不存在, id: {}", deviceId);
        }

        if (device == null) {
            log.warn("[录制计划] 流离开时拉起需要录像的流时, 发现设备不存在, id: {}", deviceId);
            return;
        }

        if("OFFLINE".equals(device.getDeviceStatus())){
            log.warn("[录制计划] 流离开时拉起需要录像的流时, 发现设备不在线, id: {}", deviceId);
            return;
        }
        // 开启点播,
        devicePlayService.play(device, true, ((code, msg, streamInfo) -> {
            if (code == InviteErrorCode.SUCCESS.getCode() && streamInfo != null) {
                log.info("[录像] 流离开时拉起需要录像的流, 开启成功, 设备ID: {}", device.getId());
                recordStreamMap.put(device.getId(), streamInfo);
            } else {
                recordStreamMap.remove(deviceId);
                log.info("[录像] 流离开时拉起需要录像的流, 开启失败, 十分钟后重试,  设备ID: {}", device.getId());
            }
        }));
    }

    public Long recording(String app, String stream) {
        for (Long deviceId : recordStreamMap.keySet()) {
            StreamInfo streamInfo = recordStreamMap.get(deviceId);
            if (streamInfo != null && streamInfo.getApp().equals(app) && streamInfo.getStream().equals(stream)) {
                return deviceId;
            }
        }
        return null;
    }


    /**
     * 查询录像计划
     *
     * @param id 录像计划主键
     * @return 录像计划
     */
    @Override
    public ZlmRecordPlan selectZlmRecordPlanById(Long id) {
        ZlmRecordPlan zlmRecordPlan = zlmRecordPlanMapper.selectZlmRecordPlanById(id);
        if (zlmRecordPlan == null) {
            throw new RuntimeException("录像计划不存在");
        }
        List<ZlmRecordPlanItem> recordPlanItemList = zlmRecordPlanItemMapper.getItemList(id);
        if (!recordPlanItemList.isEmpty()) {
            zlmRecordPlan.setPlanItemList(recordPlanItemList);
        }
        return zlmRecordPlan;
    }

    /**
     * 查询录像计划列表
     *
     * @param zlmRecordPlan 录像计划
     * @return 录像计划
     */
    @Override
    public List<ZlmRecordPlan> selectZlmRecordPlanList(ZlmRecordPlan zlmRecordPlan) {
        return zlmRecordPlanMapper.selectZlmRecordPlanList(zlmRecordPlan);
    }

    /**
     * 新增录像计划
     *
     * @param zlmRecordPlan 录像计划
     * @return 结果
     */
    @Override
    public int insertZlmRecordPlan(ZlmRecordPlan zlmRecordPlan) {
        zlmRecordPlan.setCreateTime(DateUtils.getNowDate());
        int i = zlmRecordPlanMapper.insertZlmRecordPlan(zlmRecordPlan);
        if (zlmRecordPlan.getId() > 0 && !zlmRecordPlan.getPlanItemList().isEmpty()) {
            for (ZlmRecordPlanItem recordPlanItem : zlmRecordPlan.getPlanItemList()) {
                recordPlanItem.setPlanId(zlmRecordPlan.getId());
            }
            zlmRecordPlanItemMapper.batchAddItem(zlmRecordPlan.getId(), zlmRecordPlan.getPlanItemList());
        }
        return i;
    }

    /**
     * 修改录像计划
     *
     * @param zlmRecordPlan 录像计划
     * @return 结果
     */
    @Override
    public int updateZlmRecordPlan(ZlmRecordPlan zlmRecordPlan) {
        zlmRecordPlan.setUpdateTime(DateUtils.getNowDate());
        zlmRecordPlanItemMapper.cleanItems(zlmRecordPlan.getId());
        if (zlmRecordPlan.getPlanItemList() != null && !zlmRecordPlan.getPlanItemList().isEmpty()) {
            List<ZlmRecordPlanItem> planItemList = new ArrayList<>();
            for (ZlmRecordPlanItem recordPlanItem : zlmRecordPlan.getPlanItemList()) {
                if (recordPlanItem.getStart() == null || recordPlanItem.getStop() == null || recordPlanItem.getWeekDay() == null) {
                    continue;
                }
                if (recordPlanItem.getPlanId() == null) {
                    recordPlanItem.setPlanId(zlmRecordPlan.getId());
                }
                planItemList.add(recordPlanItem);
            }
            if (!planItemList.isEmpty()) {
                zlmRecordPlanItemMapper.batchAddItem(zlmRecordPlan.getId(), planItemList);
            }
        }
        return zlmRecordPlanMapper.updateZlmRecordPlan(zlmRecordPlan);
    }

    /**
     * 批量删除录像计划
     *
     * @param ids 需要删除的录像计划主键
     * @return 结果
     */
    @Override
    public int deleteZlmRecordPlanByIds(Long[] ids) {
        for (Long id : ids) {
            zlmRecordPlanItemMapper.cleanItems(id);
            R<Void> r = remoteQsDeviceService.cleanRecordPlanId(id, SecurityConstants.INNER);
            if (r.getCode() != HttpStatus.SUCCESS) {
                throw new RuntimeException("删除录像计划失败");
            }
        }
        return zlmRecordPlanMapper.deleteZlmRecordPlanByIds(ids);
    }

    /**
     * 录像计划任务
     */
    @Override
    public void task() {
        List<Long> startDeviceIdList = queryCurrentChannelRecord();
        if (startDeviceIdList.isEmpty()) {
            // 当前没有录像任务, 如果存在旧的正在录像的就移除
            if (!recordStreamMap.isEmpty()) {
                Set<Long> recordStreamSet = new HashSet<>(recordStreamMap.keySet());
                stopStreams(recordStreamSet, recordStreamMap);
                recordStreamMap.clear();
            }
        } else {
            // 当前存在录像任务, 获取正在录像中存在但是当前录制列表不存在的内容,进行停止; 获取正在录像中没有但是当前需录制的列表中存在的进行开启.
            Set<Long> recordStreamSet = new HashSet<>(recordStreamMap.keySet());
            startDeviceIdList.forEach(recordStreamSet::remove);
            if (!recordStreamSet.isEmpty()) {
                // 正在录像中存在但是当前录制列表不存在的内容,进行停止;
                stopStreams(recordStreamSet, recordStreamMap);
            }

            // 移除startDeviceIdList中已经在录像的部分, 剩下的都是需要新添加的(正在录像中没有但是当前需录制的列表中存在的进行开启)
            recordStreamMap.keySet().forEach(startDeviceIdList::remove);
            if (!startDeviceIdList.isEmpty()) {
                // 获取所有的关联的设备
                R<List<QsDevice>> r = remoteQsDeviceService.queryByIds(startDeviceIdList, SecurityConstants.INNER);
                if (r.getCode() != HttpStatus.SUCCESS) {
                    throw new RuntimeException("根据设备id集合查询设备信息失败");
                }
                List<QsDevice> deviceList = r.getData();
                if (!deviceList.isEmpty()) {
                    // 查找是否已经开启录像, 如果没有则开启录像
                    for (QsDevice device : deviceList) {
                        if("OFFLINE".equals(device.getDeviceStatus())){
                            log.warn("[录制计划] 流离开时拉起需要录像的流时, 发现设备不在线, id: {}", device.getId());
                            return;
                        }
                        // 开启点播,
                        devicePlayService.play(device, true, ((code, msg, streamInfo) -> {
                            if (code == InviteErrorCode.SUCCESS.getCode() && streamInfo != null) {
                                log.info("[录像] 开启成功, 设备ID: {}", device.getId());
                                recordStreamMap.put(device.getId(), streamInfo);
                            } else {
                                log.info("[录像] 开启失败, 十分钟后重试,  设备ID: {}", device.getId());
                            }
                        }));
                    }
                } else {
                    log.error("[录制计划] 数据异常, 这些关联的设备已经不存在了: {}", Joiner.on(",").join(startDeviceIdList));
                }
            }
        }
    }

    /**
     * 停止录像
     *
     * @param devices         设备ID列表
     * @param recordStreamMap 正在录制的流信息
     */
    private void stopStreams(Collection<Long> devices, Map<Long, StreamInfo> recordStreamMap) {
        for (Long deviceId : devices) {
            try {
                StreamInfo streamInfo = recordStreamMap.get(deviceId);
                if (streamInfo == null) {
                    continue;
                }

                // 查看是否有人观看,存在则不做处理,等待后续自然处理,如果无人观看,则关闭该流
                MediaInfo mediaInfo = mediaServerService.getMediaInfo(streamInfo.getMediaServer(), streamInfo.getApp(), streamInfo.getStream());
                if (mediaInfo.getReaderCount() == null || mediaInfo.getReaderCount() == 0) {
                    R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
                    if (r.getCode() != HttpStatus.SUCCESS) {
                        throw new RuntimeException("根据设备id查询设备信息失败");
                    }

                    QsDevice device = r.getData();
                    if (device == null) {
                        throw new RuntimeException("设备不存在");
                    }


                    // 播放海康sdk/播放海康isup/播放大华sdk
                    if (LiveStreamType.HIK_SDK.getCode().equals(device.getType())
                            || LiveStreamType.HIK_ISUP.getCode().equals(device.getType())
                            || LiveStreamType.DAHUA_SDK.getCode().equals(device.getType())
                    ) {
                        RTPServerParam rtpServerParam = new RTPServerParam();
                        rtpServerParam.setType(device.getType());
                        rtpServerParam.setStreamId(device.getDeviceCode());
                        rtpServerParam.setId(device.getId());

                        mediaServerService.stopRtpPlay(rtpServerParam);
                    }

                    // rtsp/rtmp/flv/hls/onvif
                    if (LiveStreamType.RTSP.getCode().equals(device.getType())
                            || LiveStreamType.RTMP.getCode().equals(device.getType())
                            || LiveStreamType.FLV.getCode().equals(device.getType())
                            || LiveStreamType.HLS.getCode().equals(device.getType())
                            || LiveStreamType.ONVIF.getCode().equals(device.getType())
                    ) {
                        StreamPullPlay streamPullPlay = new StreamPullPlay();
                        streamPullPlay.setDeviceId(device.getId());
                        streamPullPlay.setMediaServerId(device.getMediaServerId());
                        streamPullPlay.setStreamKey(device.getStreamKey());
                        mediaServerService.stopStreamPullPlay(streamPullPlay);
                    }

                    // 视频文件
                    if (LiveStreamType.VIDEO_FILE.getCode().equals(device.getType())) {
                        mediaServerService.closeStreams(device.getId());
                    }
                    log.info("[录制计划] 停止, 设备ID: {}", deviceId);
                }
            } catch (Exception e) {
                log.error("[录制计划] 停止时异常", e);
            } finally {
                recordStreamMap.remove(deviceId);
            }
        }
    }

    /**
     * 获取当前时间段应该录像的设备Id列表
     */
    private List<Long> queryCurrentChannelRecord() {
        // 获取当前时间在一周内的序号, 数据库存储的从第几个30分钟开始, 0-47, 包括首尾
        LocalDateTime now = LocalDateTime.now();
        int week = now.getDayOfWeek().getValue();

        int hour = now.getHour();
        int minute = now.getMinute();

        int index;
        if (minute < 30) {
            // 00-29 分：属于该小时的第 1 个 30 分钟 (偶数索引)
            index = hour * 2;
        } else {
            // 30-59 分：属于该小时的第 2 个 30 分钟 (奇数索引)
            index = hour * 2 + 1;
        }

        // 查询现在需要录像的设备Id
        return zlmRecordPlanMapper.queryRecordIng(week, index);
    }
}
