package com.ruoyi.haikang.isup.service.haikang;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupDeviceInfo;
import com.ruoyi.haikang.isup.api.domain.HaiKangIsupPresetInfo;
import com.ruoyi.haikang.isup.api.domain.HaikangIsupRecordDownloadRequest;
import com.ruoyi.haikang.isup.api.domain.HaikangIsupRecordDownloadResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 海康isup 服务接口
 * @FileName IHaiKangIsupService
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 */
public interface IHaiKangIsupService {

    /**
     * 获取设备信息
     *
     * @param lUserID 用户id
     * @return
     */
    HaiKangIsupDeviceInfo getDevInfo(Integer lUserID);

    /**
     * 开始播放
     *
     * @param rtpServerParam
     */
    void startPlay(RtpServerParam rtpServerParam);

    /**
     * 停止播放
     *
     * @param id 设备id
     */
    void stopPlay(Long id);

    /**
     * 开始云台控制
     *
     * @param deviceId
     * @param channelId
     * @param ptzCmd
     * @param speed
     */
    void startPtz(Long deviceId, Integer channelId, int ptzCmd, int speed);

    /**
     * 结束云台控制
     *
     * @param deviceId
     * @param channelId
     * @param ptzCmd
     * @param speed
     */
    void endPtz(Long deviceId, Integer channelId, int ptzCmd, int speed);

    /**
     * 设置预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     */
    void setPreset(Long deviceId, Integer channelId, int presetIndex);

    /**
     * 清除预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     */
    void clearPreset(Long deviceId, Integer channelId, int presetIndex);

    /**
     * 调用预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     */
    void gotoPreset(Long deviceId, Integer channelId, int presetIndex);

    /**
     * 辅助设备控制（灯光、雨刮、风扇等）
     *
     * @param deviceId
     * @param channelId
     * @param operation
     * @param isStart
     */
    void cameraAuxControl(Long deviceId, Integer channelId, String operation, boolean isStart);

    /**
     * 巡航控制
     *
     * @param deviceId
     * @param channelId
     * @param operation
     * @param param
     */
    void cruiseControl(Long deviceId, Integer channelId, String operation, Integer param);

    /**
     * 获取预置点列表
     *
     * @param deviceId
     * @param channelId
     * @return
     */
    List<HaiKangIsupPresetInfo> getPresetList(Long deviceId, Integer channelId);

    /**
     * 海康设备查询录像
     *
     * @param deviceId 设备id
     * @param channelId 通道id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    ArrayList<HashMap<String, Object>> queryRecord(Long deviceId, Integer channelId, String startTime, String endTime);

    /**
     * 开始回放
     *
     * @param rtpServerParam
     */
    void startPlayback(RtpServerParam rtpServerParam);

    /**
     * 停止回放
     *
     * @param id 设备id
     */
    void stopPlayback(Long id);

    /**
     * 重启设备
     *
     * @param deviceId 设备id
     */
    void restartDevice(Long deviceId);

    /**
     * 获取设备时间
     *
     * @param deviceId 设备id
     * @return 设备时间，格式：yyyy-MM-dd HH:mm:ss
     */
    String getDevTime(Long deviceId);

    /**
     * 设置设备时间
     *
     * @param deviceId 设备id
     * @param time     设备时间，格式：yyyy-MM-dd HH:mm:ss
     */
    void setDevTime(Long deviceId, String time);

    /**
     * 抓图并保存
     *
     * @param deviceId     设备id
     * @param channelId    通道id
     * @param snapshotType 抓图类型
     * @return 抓图记录id
     */
    Long captureAndSave(Long deviceId, int channelId, String snapshotType) throws IOException;

    /**
     * 海康设备录像下载（保存到后端）
     *
     * @param request 下载请求
     * @return 下载结果
     */
    HaikangIsupRecordDownloadResponse downloadRecord(HaikangIsupRecordDownloadRequest request);

    /**
     * 海康设备录像下载（保存到临时文件，直接返回给前端）
     *
     * @param request 下载请求
     * @return 临时文件
     */
    java.io.File downloadRecordFile(HaikangIsupRecordDownloadRequest request) throws Exception;
}
