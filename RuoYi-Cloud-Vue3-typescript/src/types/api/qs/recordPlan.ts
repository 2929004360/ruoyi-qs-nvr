import type { PageDomain, BaseEntity } from "../common";

/** 录像计划配置分页查询参数 */
export interface RecordPlanQueryParams extends PageDomain {
  /** 计划名称 */
  snap?: number;
  /** 是否启用 */
  status?: number;
}

/** 录像计划配置信息 */
export interface ZlmRecordPlan extends BaseEntity {
  /** ID */
  id?: number;
  /** 计划名称 */
  snap?: string;
  /** 开启定时截图 */
  name?: string;
  /** 启用 */
  status?: string;
  /** 创建时间 */
  createTime?: string;
  /** 更新时间 */
  updateTime?: string;
}
