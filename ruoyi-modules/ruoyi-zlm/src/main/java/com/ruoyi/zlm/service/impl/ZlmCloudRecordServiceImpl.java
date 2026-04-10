package com.ruoyi.zlm.service.impl;

import com.alibaba.nacos.api.model.v2.ErrorCode;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.api.domain.ZlmCloudRecord;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.common.InviteErrorCode;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.domain.dto.ZLMResult;
import com.ruoyi.zlm.hook.Hook;
import com.ruoyi.zlm.hook.HookSubscribe;
import com.ruoyi.zlm.hook.HookType;
import com.ruoyi.zlm.mapper.ZlmCloudRecordMapper;
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IMediaNodeServerService;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.service.IZlmCloudRecordService;
import com.ruoyi.zlm.utils.ZLMRESTfulUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public int deleteZlmCloudRecordByIds(Long[] ids) {
        return zlmCloudRecordMapper.deleteZlmCloudRecordByIds(ids);
    }

    /**
     * 删除云端录像信息
     *
     * @param id 云端录像主键
     * @return 结果
     */
    @Override
    public int deleteZlmCloudRecordById(Long id) {
        return zlmCloudRecordMapper.deleteZlmCloudRecordById(id);
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
