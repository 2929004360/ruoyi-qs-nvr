package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 47081
 * @version 1.0
 * @description 目标识别数据库操作
 * @date 2021/4/26
 */
public enum EM_OPERATE_FACERECONGNITIONDB_TYPE {
    NET_FACERECONGNITIONDB_UNKOWN,
  /** 删除人员信息和人脸样本 */
    NET_FACERECONGNITIONDB_ADD,
  /** 添加人员信息和人脸样本,如果人员已经存在,图片数据和原来的数据合并 */
    NET_FACERECONGNITIONDB_DELETE,
  /** 修改人员信息和人脸样本,人员的UID标识必填 */
    NET_FACERECONGNITIONDB_MODIFY,
  /** 通过UID删除人员信息和人脸样本 */
    NET_FACERECONGNITIONDB_DELETE_BY_UID;
}

