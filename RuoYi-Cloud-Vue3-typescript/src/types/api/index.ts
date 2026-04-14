/**
 * API 类型统一导出
 */
export * from "./common";

// 登录模块
export * from "./login";
export * from "./menu";

// System 模块
export * from "./system/user";
export * from "./system/role";
export * from "./system/menu";
export * from "./system/dept";
export * from "./system/post";
export * from "./system/dict";
export * from "./system/config";
export * from "./system/notice";
export * from "./system/logininfor";
export * from "./system/operlog";

// monitor 模块
export * from "./monitor/job";
export * from "./monitor/jobLog";
export * from "./monitor/online";

// 代码生成模块
export * from "./tool/gen";

// qs 模块
export * from "./qs/device";
export * from "./qs/haikang-isup";
export * from "./qs/zlm";
export * from "./qs/dahua";
export * from "./qs/onvif";
export * from "./qs/cloudRecord";
export * from "./qs/recordPlan";
export * from "./qs/recordPlanItem";
export * from "./qs/region";
export * from "./qs/group";