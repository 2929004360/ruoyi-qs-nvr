package com.ruoyi.zlm.service;

import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.api.domain.ZlmCloudRecord;

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
     * @return 结果
     */
    public int deleteZlmCloudRecordByIds(Long[] ids);

    /**
     * 删除云端录像信息
     *
     * @param id 云端录像主键
     * @return 结果
     */
    public int deleteZlmCloudRecordById(Long id);

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
}
