package com.ruoyi.zlm.api.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 云端录像对象 zlm_cloud_record
 * 
 * @author fengcheng
 * @date 2026-04-10
 */
@Data
public class ZlmCloudRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 应用名 */
    private String app;

    /** 流id */
    private String stream;

    /** 健全ID */
    private String callId;

    /** 开始时间 */
    private Long startTime;

    /** 结束时间 */
    private Long endTime;

    /** ZLM Id */
    private String mediaServerId;

    /** 所属服务ID */
    private String serverId;

    /** 文件名称 */
    private String fileName;

    /** 文件夹 */
    private String folder;

    /** 文件路径 */
    private String filePath;

    /** 收藏，收藏的文件不移除 */
    private Boolean collect;

    /** 文件大小 */
    private Long fileSize;

    /** 文件时长 */
    private Double timeLen;

    /** 查询开始时间 */
    private String queryStartTime;

    /** 查询结束时间 */
    private String queryEndTime;
}
