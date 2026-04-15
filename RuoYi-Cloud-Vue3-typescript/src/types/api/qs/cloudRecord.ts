import type { PageDomain, BaseEntity } from "../common";

/** 云端录像配置分页查询参数 */
export interface CloudRecordQueryParams extends PageDomain {
    /** 应用名 */
    app?: string;
    /** 流id */
    stream?: string;
    /** 健全ID */
    callId?: string;
    /** 开始时间 */
    startTime?: number;
    /** 结束时间 */
    endTime?: number;
    /** ZLM Id */
    mediaServerId?: string;
    /** 所属服务ID */
    serverId?: string;
    /** 文件名称 */
    fileName?: string;
    /** 文件夹 */
    folder?: string;
    /** 文件路径 */
    filePath?: string;
    /** 收藏，收藏的文件不移除 */
    collect?: number;
    /** 文件大小 */
    fileSize?: number;
    /** 文件时长 */
    timeLen?: number;
    /** 查询开始时间 */
    queryStartTime?: number;
    /** 查询结束时间 */
    queryEndTime?: number;
}

/** 云端录像配置信息 */
export interface ZlmCloudRecord extends BaseEntity {
    /** 主键 */
    id?: number;
    /** 应用名 */
    app?: string;
    /** 流id */
    stream?: string;
    /** 健全ID */
    callId?: string;
    /** 开始时间 */
    startTime?: number;
    /** 结束时间 */
    endTime?: number;
    /** ZLM Id */
    mediaServerId?: string;
    /** 所属服务ID */
    serverId?: string;
    /** 文件名称 */
    fileName?: string;
    /** 文件夹 */
    folder?: string;
    /** 文件路径 */
    filePath?: string;
    /** 收藏，收藏的文件不移除 */
    collect?: number;
    /** 文件大小 */
    fileSize?: number;
    /** 文件时长 */
    timeLen?: number;
    /** 创建时间 */
    createTime?: string;
    /** 更新时间 */
    updateTime?: string;
}
