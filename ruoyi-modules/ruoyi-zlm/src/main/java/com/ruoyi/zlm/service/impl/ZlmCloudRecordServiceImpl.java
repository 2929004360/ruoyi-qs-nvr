package com.ruoyi.zlm.service.impl;

import com.alibaba.nacos.api.model.v2.ErrorCode;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.zlm.api.domain.DownloadFileInfo;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.api.domain.ZlmCloudRecord;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.common.InviteErrorCode;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.domain.CloudRecordUrl;
import com.ruoyi.zlm.domain.RecordInfo;
import com.ruoyi.zlm.domain.dto.ZLMResult;
import com.ruoyi.zlm.hook.Hook;
import com.ruoyi.zlm.hook.HookSubscribe;
import com.ruoyi.zlm.hook.HookType;
import com.ruoyi.zlm.mapper.ZlmCloudRecordMapper;
import com.ruoyi.zlm.service.*;
import com.ruoyi.zlm.utils.DateUtil;
import com.ruoyi.zlm.utils.ZLMRESTfulUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * 云端录像Service业务层处理
 *
 * @author fengcheng
 * @date 2026-04-10
 */
@Slf4j
@Service
public class ZlmCloudRecordServiceImpl implements IZlmCloudRecordService {
    @Autowired
    private ZlmCloudRecordMapper zlmCloudRecordMapper;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    @Lazy
    private IMediaServerService mediaServerService;

    @Autowired
    private Map<String, IMediaNodeServerService> nodeServerServiceMap;

    @Autowired
    private HookSubscribe subscribe;

    @Autowired
    private ZLMRESTfulUtils zlmresTfulUtils;

    @Autowired
    private IRedisRpcPlayService redisRpcPlayService;

    /**
     * 查询云端录像
     *
     * @param id 云端录像主键
     * @return 云端录像
     */
    @Override
    public ZlmCloudRecord selectZlmCloudRecordById(Long id) {
        return zlmCloudRecordMapper.selectZlmCloudRecordById(id);
    }

    /**
     * 查询云端录像列表
     *
     * @param zlmCloudRecord 云端录像
     * @return 云端录像
     */
    @Override
    public List<ZlmCloudRecord> selectZlmCloudRecordList(ZlmCloudRecord zlmCloudRecord) {
        if (zlmCloudRecord.getMediaServerId() != null) {
            ZlmMediaServer mediaServer = mediaServerService.getOne(zlmCloudRecord.getMediaServerId());
            if (mediaServer == null) {
                throw new RuntimeException("未找到流媒体: " + zlmCloudRecord.getMediaServerId());
            }
        }
        if (zlmCloudRecord.getQueryStartTime() != null) {
            if (!DateUtil.verification(zlmCloudRecord.getQueryStartTime(), DateUtil.formatter)) {
                throw new RuntimeException("开始时间格式错误，正确格式为： " + DateUtil.formatter);
            }
            zlmCloudRecord.setStartTime(DateUtil.yyyy_MM_dd_HH_mm_ssToTimestampMs(zlmCloudRecord.getQueryStartTime()));
        }
        if (zlmCloudRecord.getQueryEndTime() != null) {
            if (!DateUtil.verification(zlmCloudRecord.getQueryEndTime(), DateUtil.formatter)) {
                throw new RuntimeException("结束时间格式错误，正确格式为： " + DateUtil.formatter);
            }
            zlmCloudRecord.setEndTime(DateUtil.yyyy_MM_dd_HH_mm_ssToTimestampMs(zlmCloudRecord.getQueryEndTime()));
        }

        return zlmCloudRecordMapper.selectZlmCloudRecordList(zlmCloudRecord);
    }

    /**
     * 新增云端录像
     *
     * @param zlmCloudRecord 云端录像
     * @return 结果
     */
    @Override
    public int insertZlmCloudRecord(ZlmCloudRecord zlmCloudRecord) {
        zlmCloudRecord.setCreateTime(DateUtils.getNowDate());
        return zlmCloudRecordMapper.insertZlmCloudRecord(zlmCloudRecord);
    }

    /**
     * 修改云端录像
     *
     * @param zlmCloudRecord 云端录像
     * @return 结果
     */
    @Override
    public int updateZlmCloudRecord(ZlmCloudRecord zlmCloudRecord) {
        zlmCloudRecord.setUpdateTime(DateUtils.getNowDate());
        return zlmCloudRecordMapper.updateZlmCloudRecord(zlmCloudRecord);
    }

    /**
     * 批量删除云端录像
     *
     * @param ids 需要删除的云端录像主键
     * @return 结果
     */
    @Override
    public void deleteZlmCloudRecordByIds(Long[] ids) {
        log.info("[删除录像文件] ids: {}", Arrays.stream(ids).toArray());

        List<ZlmCloudRecord> cloudRecordItemList = zlmCloudRecordMapper.queryZlmCloudRecordByIds(ids);
        if (cloudRecordItemList.isEmpty()) {
            return;
        }
        List<ZlmCloudRecord> cloudRecordItemIdListForDelete = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (ZlmCloudRecord cloudRecordItem : cloudRecordItemList) {
            String date = new File(cloudRecordItem.getFilePath()).getParentFile().getName();
            ZlmMediaServer mediaServer = mediaServerService.getOne(cloudRecordItem.getMediaServerId());
            if (mediaServer == null) {
                throw new RuntimeException("未找到流媒体: " + cloudRecordItem.getMediaServerId());
            }
            try {
                boolean deleteResult = mediaServerService.deleteRecordDirectory(mediaServer, cloudRecordItem.getApp(),
                        cloudRecordItem.getStream(), date, cloudRecordItem.getFileName());
                if (deleteResult) {
                    log.warn("[录像文件] 删除磁盘文件成功： {}", cloudRecordItem.getFilePath());
                    cloudRecordItemIdListForDelete.add(cloudRecordItem);
                }
            } catch (RuntimeException e) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(cloudRecordItem.getFileName());
            }

        }
        if (!cloudRecordItemIdListForDelete.isEmpty()) {
            zlmCloudRecordMapper.deleteZlmCloudRecordByIds(ids);
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append(" 删除失败");
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    /**
     * 播放云端录像
     *
     * @param id
     * @param callback
     */
    @Override
    public void loadRecord(Long id, ErrorCallback<StreamInfo> callback) {
        ZlmCloudRecord zlmCloudRecord = zlmCloudRecordMapper.selectZlmCloudRecordById(id);
        if (zlmCloudRecord == null) {
            callback.run(InviteErrorCode.FAIL.getCode(), "录像不存在", null);
            return;
        }

        ZlmMediaServer mediaServer = mediaServerService.getOne(zlmCloudRecord.getMediaServerId());

        if (mediaServer == null) {
            callback.run(InviteErrorCode.FAIL.getCode(), "无可用的节点", null);
            return;
        }

        loadMP4File(mediaServer, "record_file", zlmCloudRecord, ((code, msg, streamInfo) -> {
            callback.run(code, msg, streamInfo);
        }));
    }

    @Override
    public void closeStreams(Long id) {
        ZlmCloudRecord zlmCloudRecord = zlmCloudRecordMapper.selectZlmCloudRecordById(id);
        if (zlmCloudRecord == null) {
            throw new RuntimeException("录像不存在");
        }

        ZlmMediaServer mediaServer = mediaServerService.getOne(zlmCloudRecord.getMediaServerId());

        if (mediaServer == null) {
            throw new RuntimeException("无可用的节点");
        }

        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[closeStreams] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            return;
        }
        mediaNodeServerService.closeStreams(mediaServer, "record_file", zlmCloudRecord.getStream());
    }

    /**
     * 定时查询待删除的录像文件
     */
    @Override
    public void task() {
        log.info("[录像文件定时清理] 开始清理过期录像文件");

        // 获取配置了assist的流媒体节点
        List<ZlmMediaServer> mediaServerItemList = mediaServerService.getAllOnlineMediaServe();
        if (mediaServerItemList.isEmpty()) {
            return;
        }

        long result = 0;
        for (ZlmMediaServer mediaServerItem : mediaServerItemList) {
            Calendar lastCalendar = Calendar.getInstance();
            if (mediaServerItem.getRecordDay() > 0) {
                lastCalendar.setTime(new Date());
                // 获取保存的最后截至日[期，因为每个节点都有一个日期，也就是支持每个节点设置不同的保存日期，
                lastCalendar.add(Calendar.DAY_OF_MONTH, -mediaServerItem.getRecordDay());
                Long lastDate = lastCalendar.getTimeInMillis();

                // 获取到截至日期之前的录像文件列表，文件列表满足未被收藏和保持的。这两个字段目前共能一致，
                // 为我自己业务系统相关的代码，大家使用的时候直接使用收藏（collect）这一个类型即可
                List<ZlmCloudRecord> cloudRecordItemList = zlmCloudRecordMapper.queryCloudRecordListForDelete(lastDate, mediaServerItem.getId());
                if (cloudRecordItemList.isEmpty()) {
                    continue;
                }
                // TODO 后续可以删除空了的过期日期文件夹
                for (ZlmCloudRecord cloudRecordItem : cloudRecordItemList) {
                    String date = new File(cloudRecordItem.getFilePath()).getParentFile().getName();
                    try {
                        boolean deleteResult = mediaServerService.deleteRecordDirectory(mediaServerItem, cloudRecordItem.getApp(),
                                cloudRecordItem.getStream(), date, cloudRecordItem.getFileName());
                        if (deleteResult) {
                            log.warn("[录像文件定时清理] 删除磁盘文件成功： {}", cloudRecordItem.getFilePath());
                        }
                    } catch (Exception ignored) {

                    }

                }
                List<Long> idList = cloudRecordItemList.stream().map(ZlmCloudRecord::getId).toList();
                result += zlmCloudRecordMapper.deleteZlmCloudRecordByIds(idList.toArray(Long[]::new));
            }
        }
        log.info("[录像文件定时清理] 共清理{}个过期录像文件", result);
    }

    /**
     * 根据id获取url
     *
     * @param ids
     * @return
     */
    @Override
    public List<CloudRecordUrl> getUrlListByIds(List<Long> ids) {
        List<ZlmCloudRecord> cloudRecordItems = zlmCloudRecordMapper.queryZlmCloudRecordByIds(ids.toArray(Long[]::new));
        if (cloudRecordItems.isEmpty()) {
            return List.of();
        }
        return getCloudRecordUrl(cloudRecordItems);
    }

    /**
     * 设置录像播放速度
     *
     * @param mediaServerId 使用的节点Id
     * @param app           应用名
     * @param stream        流id
     * @param speed         播放速度
     * @param schema        播放协议
     */
    @Override
    public void setRecordSpeed(String mediaServerId, String app, String stream, Integer speed, String schema) {
        ZlmMediaServer mediaServer = mediaServerService.getOne(mediaServerId);
        if (mediaServer == null) {
            throw new RuntimeException("媒体节点不存在： " + mediaServerId);
        }
        mediaServerService.setRecordSpeed(mediaServer, app, stream, speed, schema);
    }

    /**
     * 定位录像播放到制定位置
     *
     * @param mediaServerId 使用的节点Id
     * @param app           应用名
     * @param stream        流ID
     * @param stamp         要定位的时间位置，从录像开始的时间算起
     * @param schema        播放协议
     */
    @Override
    public void seekRecord(String mediaServerId, String app, String stream, Double stamp, String schema) {
        ZlmMediaServer mediaServer = mediaServerService.getOne(mediaServerId);
        if (mediaServer == null) {
            throw new RuntimeException("媒体节点不存在： " + mediaServerId);
        }
        mediaServerService.seekRecordStamp(mediaServer, app, stream, stamp, schema);
    }

    private List<CloudRecordUrl> getCloudRecordUrl(List<ZlmCloudRecord> cloudRecordItems) {
        if (cloudRecordItems.isEmpty()) {
            return List.of();
        }
        List<CloudRecordUrl> resultList = new ArrayList<>();
        for (ZlmCloudRecord cloudRecordItem : cloudRecordItems) {
            CloudRecordUrl cloudRecordUrl = new CloudRecordUrl();
            cloudRecordUrl.setId(cloudRecordItem.getId());
            cloudRecordUrl.setFileName(cloudRecordItem.getStartTime() + ".mp4");
            cloudRecordUrl.setFilePath(cloudRecordItem.getFilePath());
            if (!userSetting.getServerId().equals(cloudRecordItem.getServerId())) {
                cloudRecordUrl.setDownloadUrl(redisRpcPlayService.getRecordPlayUrl(cloudRecordItem.getServerId(), cloudRecordItem.getId()).getHttpPath());
            } else {
                ZlmMediaServer mediaServer = mediaServerService.getOne(cloudRecordItem.getMediaServerId());
                if (mediaServer == null) {
                    throw new RuntimeException("媒体节点不存在： " + cloudRecordItem.getMediaServerId());
                }
                mediaServer.setStreamIp(mediaServer.getIp());
                DownloadFileInfo downloadFilePath = mediaServerService.getDownloadFilePath(mediaServer, RecordInfo.getInstance(cloudRecordItem));
                cloudRecordUrl.setDownloadUrl(downloadFilePath.getHttpPath());
            }
            resultList.add(cloudRecordUrl);
        }

        return resultList;
    }

    private void loadMP4File(ZlmMediaServer mediaServer, String app, ZlmCloudRecord zlmCloudRecord, ErrorCallback<StreamInfo> callback) {
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(mediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[loadMP4File] 失败, mediaServer的类型： {}，未找到对应的实现类", mediaServer.getType());
            throw new RuntimeException("未找到mediaServer对应的实现类");
        }

        StreamInfo streamData = mediaServerService.getStreamInfoByAppAndStreamWithCheck(app, zlmCloudRecord.getStream(), mediaServer.getId(), null, false);
        if (streamData != null) {
            callback.run(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), streamData);
            return;
        }

        Hook hook = Hook.getInstance(HookType.on_media_arrival, app, zlmCloudRecord.getStream(), mediaServer.getServerId());
        subscribe.addSubscribe(hook, (hookData) -> {
            StreamInfo streamInfo = mediaServerService.getStreamInfoByAppAndStream(mediaServer, app, zlmCloudRecord.getStream(), hookData.getMediaInfo());
            if (callback != null) {
                callback.run(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), streamInfo);
            }
        });

        ZLMResult<?> zlmResult = zlmresTfulUtils.loadMP4File(mediaServer, app, zlmCloudRecord.getStream(), zlmCloudRecord.getFilePath());

        if (zlmResult == null) {
            throw new RuntimeException("请求失败");
        }
        if (zlmResult.getCode() != 0) {
            throw new RuntimeException(zlmResult.getMsg());
        }
    }
}
