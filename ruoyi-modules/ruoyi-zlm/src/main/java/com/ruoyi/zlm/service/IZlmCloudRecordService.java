package com.ruoyi.zlm.service;

import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.api.domain.ZlmCloudRecord;
import com.ruoyi.zlm.domain.CloudRecordUrl;

import java.util.List;

/**
 * 云端录像Service接口
 *
 * @author fengcheng
 * @date 2026-04-10
 */
public interface IZlmCloudRecordService {
    /**
     * 查询云端录像
     *
     * @param id 云端录像主键
     * @return 云端录像
     */
    public ZlmCloudRecord selectZlmCloudRecordById(Long id);

    /**
     * 查询云端录像列表
     *
     * @param zlmCloudRecord 云端录像
     * @return 云端录像集合
     */
    public List<ZlmCloudRecord> selectZlmCloudRecordList(ZlmCloudRecord zlmCloudRecord);

    /**
     * 新增云端录像
     *
     * @param zlmCloudRecord 云端录像
     * @return 结果
     */
    public int insertZlmCloudRecord(ZlmCloudRecord zlmCloudRecord);

    /**
     * 修改云端录像
     *
     * @param zlmCloudRecord 云端录像
     * @return 结果
     */
    public int updateZlmCloudRecord(ZlmCloudRecord zlmCloudRecord);

    /**
     * 批量删除云端录像
     *
     * @param ids 需要删除的云端录像主键集合
     */
    public void deleteZlmCloudRecordByIds(Long[] ids);

    /**
     * 播放云端录像
     *
     * @param id
     * @param callback
     */
    void loadRecord(Long id, ErrorCallback<StreamInfo> callback);

    /**
     * 关闭流
     *
     * @param id
     */
    void closeStreams(Long id);

    /**
     * 定时查询待删除的录像文件
     */
    void task();

    /**
     * 根据id获取url
     *
     * @param ids
     * @return
     */
    List<CloudRecordUrl> getUrlListByIds(List<Long> ids);

    /**
     * 设置录像播放速度
     *
     * @param mediaServerId 使用的节点Id
     * @param app           应用名
     * @param stream        流id
     * @param speed         播放速度
     * @param schema        播放协议
     */
    void setRecordSpeed(String mediaServerId, String app, String stream, Integer speed, String schema);

    /**
     * 定位录像播放到制定位置
     *
     * @param mediaServerId 使用的节点Id
     * @param app           应用名
     * @param stream        流ID
     * @param stamp          要定位的时间位置，从录像开始的时间算起
     * @param schema        播放协议
     */
    void seekRecord(String mediaServerId, String app, String stream, Double stamp, String schema);
}
