import type { PageDomain, BaseEntity } from "../common";

/** 录像计划管理设备配置分页查询参数 */
export interface RecordPlanItemQueryParams extends PageDomain {
  /** ${column.columnComment} */
  start?: number;
  /** ${column.columnComment} */
  stop?: number;
  /** ${column.columnComment} */
  weekDay?: number;
  /** ${column.columnComment} */
  planId?: number;
}

/** 录像计划管理设备配置信息 */
export interface ZlmRecordPlanItem extends BaseEntity {
  /** ${column.columnComment} */
  id?: number;
  /** ${column.columnComment} */
  start?: number;
  /** ${column.columnComment} */
  stop?: number;
  /** ${column.columnComment} */
  weekDay?: number;
  /** ${column.columnComment} */
  planId?: number;
}
