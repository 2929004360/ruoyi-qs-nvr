package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 47081
 * @version 1.0
 * @description 操作错误码
 * @date 2021/3/31
 */
public enum NET_EM_FAILCODE {
    NET_EM_FAILCODE_NOERROR(0, "没有错误"),
    NET_EM_FAILCODE_UNKNOWN(1, "未知错误"),
    NET_EM_FAILCODE_INVALID_PARAM(2, "参数错误"),
    NET_EM_FAILCODE_INVALID_PASSWORD(3, "无效密码"),
    NET_EM_FAILCODE_INVALID_FP(4, "无效信息数据"),
    NET_EM_FAILCODE_INVALID_FACE(5, "无效人脸数据"),
    NET_EM_FAILCODE_INVALID_CARD(6, "无效卡数据"),
    NET_EM_FAILCODE_INVALID_USER(7, "无效人数据"),
    NET_EM_FAILCODE_FAILED_GET_SUBSERVICE(8, "能力集子服务获取失败"),
    NET_EM_FAILCODE_FAILED_GET_METHOD(9, "获取组件的方法集失败"),
    NET_EM_FAILCODE_FAILED_GET_SUBCAPS(10, "获取资源实体能力集失败"),
    NET_EM_FAILCODE_ERROR_INSERT_LIMIT(11, "已达插入上限"),
    NET_EM_FAILCODE_ERROR_MAX_INSERT_RATE(12, "已达最大插入速度"),
    NET_EM_FAILCODE_FAILED_ERASE_FP(13, "清除信息数据失败"),
    NET_EM_FAILCODE_FAILED_ERASE_FACE(14, "清除人脸数据失败"),
    NET_EM_FAILCODE_FAILED_ERASE_CARD(15, "清除卡数据失败"),
    NET_EM_FAILCODE_NO_RECORD(16, "没有记录"),
    NET_EM_FAILCODE_NOMORE_RECORD(17, "查找到最后，没有更多记录"),
    NET_EM_FAILCODE_RECORD_ALREADY_EXISTS(18, "下发卡或信息时，数据重复"),
    NET_EM_FAILCODE_MAX_FP_PERUSER(19, "超过个人最大信息记录数"),
    NET_EM_FAILCODE_MAX_CARD_PERUSER(20, "超过个人最大卡片记录数"),
    NET_EM_FAILCODE_EXCEED_MAX_PHOTOSIZE(21, "超出最大照片大小"),
    NET_EM_FAILCODE_INVALID_USERID(22, "用户ID无效（未找到客户）"),
    NET_EM_FAILCODE_EXTRACTFEATURE_FAIL(23, "提取人脸特征失败"),
    NET_EM_FAILCODE_PHOTO_EXIST(24, "人脸照片已存在"),
    NET_EM_FAILCODE_PHOTO_OVERFLOW(25, "超出最大人脸照片数"),
    NET_EM_FAILCODE_INVALID_PHOTO_FORMAT(26, "照片格式无效"),
    NET_EM_FAILCODE_EXCEED_ADMINISTRATOR_LIMIT(27, "超出管理员人数限制"),
    /**
     * /// 特征已存在
    */
    NET_EM_FAILECODE_FACE_FEATURE_ALREADY_EXIST(28, "/// 特征已存在"),
    /**
     * /// 信息已存在
    */
    NET_EM_FAILECODE_FINGERPRINT_EXIST(29, "/// 信息已存在"),
    /**
     * /// 证件号已存在
    */
    NET_EM_FAILECODE_CITIZENID_EXIST(30, "/// 证件号已存在"),
    /**
     * /// 不支持普通用户下发
    */
    NET_EM_FAILECODE_NORMAL_USER_NOTSUPPORT(31, "/// 不支持普通用户下发"),
    /**
     * /// 图片中检测到0个目标
    */
    NET_EM_FAILCODE_NO_FACE_DETECTED(32, "/// 图片中检测到0个目标"),
    /**
     * /// 图片中检测到多个目标，无法返回特征
    */
    NET_EM_FAILCODE_MULTI_FACE_DETECTED(33, "/// 图片中检测到多个目标，无法返回特征"),
    /**
     * /// 图片解码错误
    */
    NET_EM_FAILCODE_PICTURE_DECODING_ERROR(34, "/// 图片解码错误"),
    /**
     * /// 图片质量太低
    */
    NET_EM_FAILCODE_LOW_PICTURE_QUALITY(35, "/// 图片质量太低"),
    /**
     * /// 结果不推荐使用，比如：对外国人，特征提取成功，但算法支持不好，容易造成误识别
    */
    NET_EM_FAILCODE_NOT_RECOMMENDED(36, "/// 结果不推荐使用，比如：对外国人，特征提取成功，但算法支持不好，容易造成误识别"),
    /**
     * /// 角度超过配置阈值
    */
    NET_EM_FAILCODE_FACE_ANGLE_OVER_THRESHOLDS(37, "/// 角度超过配置阈值"),
    /**
     * /// 占比超出范围，算法建议占比:不要超过2/3;不要小于1/3
    */
    NET_EM_FAILCODE_FACE_RADIO_EXCEEDS_RANGE(38, "/// 占比超出范围，算法建议占比:不要超过2/3;不要小于1/3"),
    /**
     * /// 过爆
    */
    NET_EM_FAILCODE_FACE_OVER_EXPOSED(39, "/// 过爆"),
    /**
     * /// 欠爆
    */
    NET_EM_FAILCODE_FACE_UNDER_EXPOSED(40, "/// 欠爆"),
    /**
     * /// 亮度不均衡 ,用于判断阴阳脸
    */
    NET_EM_FAILCODE_BRIGHTNESS_IMBALANCE(41, "/// 亮度不均衡 ,用于判断阴阳脸"),
    /**
     * /// 置信度低
    */
    NET_EM_FAILCODE_FACE_LOWER_CONFIDENCE(42, "/// 置信度低"),
    /**
     * /// 对齐分数低
    */
    NET_EM_FAILCODE_FACE_LOW_ALIGN(43, "/// 对齐分数低"),
    /**
     * /// 存在遮挡、残缺不全
    */
    NET_EM_FAILCODE_FRAGMENTARY_FACE_DETECTED(44, "/// 存在遮挡、残缺不全"),
    /**
     * /// 瞳距小于阈值
    */
    NET_EM_FAILCODE_PUPIL_DISTANCE_NOT_ENOUGH(45, "/// 瞳距小于阈值"),
    /**
     * /// 数据下载失败
    */
    NET_EM_FAILCODE_FACE_DATA_DOWNLOAD_FAILED(46, "/// 数据下载失败"),
    /**
     * /// 可检测，但特征值提取失败（算法场景）
    */
    NET_EM_FAILCODE_FACE_FFE_FAILED(47, "/// 可检测，但特征值提取失败（算法场景）"),
    /**
     * /// 照片因口罩，帽子，墨镜等属性不符合而提取特征值错误
    */
    NET_EM_FAILCODE_PHOTO_FEATURE_FAILED_FOR_FA(48, "/// 照片因口罩，帽子，墨镜等属性不符合而提取特征值错误"),
    /**
     * /// 照片不完整
    */
    NET_EM_FAILCODE_FACE_DATA_PHOTO_INCOMPLETE(49, "/// 照片不完整"),
    /**
     * /// 	数据库插入越上限
    */
    NET_EM_FAILCODE_DATABASE_ERROR_INSERT_OVERFLOW(50, "/// 	数据库插入越上限"),
    /**
     * /// 卡号不存在
    */
    NET_EM_CARD_NOT_EXIST(51, "/// 卡号不存在"),
    /**
     * /// User已存在
    */
    NET_EM_FAILCODE_USER_EXIST(52, "/// User已存在"),
    /**
     * /// 卡号已存在
    */
    NET_EM_FAILCODE_CARD_NUM_EXIST(53, "/// 卡号已存在"),
    /**
     * /// 信息通过URL下载方式下载失败
    */
    NET_EM_FAILCODE_FINGERPRINT_DOWNLOAD_FAIL(54, "/// 信息通过URL下载方式下载失败"),
    /**
     * /// 账户登录中
    */
    NET_EM_FAILCODE_ACCOUNT_IN_USE(55, "/// 账户登录中"),
    /**
     * /// 更新用户眼睛信息时,用户不存在眼睛信息
    */
    NET_EM_FAILCODE_IRIS_INFO_NOT_EXISTED(56, "/// 更新用户眼睛信息时,用户不存在眼睛信息"),
    /**
     * /// 下发的眼睛信息数据格式、特征值大小错误
    */
    NET_EM_FAILCODE_INVALID_IRIS_DATA(57, "/// 下发的眼睛信息数据格式、特征值大小错误"),
    /**
     * /// 眼睛信息已存在
    */
    NET_EM_FAILCODE_IRIS_ALREADY_EXIST(58, "/// 眼睛信息已存在"),
    /**
     * /// 眼睛信息信息删除失败
    */
    NET_EM_FAILCODE_ERASE_IRIS_FAILED(59, "/// 眼睛信息信息删除失败"),
    /**
     * /// 超出个人所支持的眼睛信息组数量，两个眼睛(左右眼)为一组
    */
    NET_EM_FAILCODE_EXCEED_MAX_IRIS_GROUP_COUNT_PER_USER(60, "/// 超出个人所支持的眼睛信息组数量，两个眼睛(左右眼)为一组"),
    /**
     * /// 超出个人单组所能记录的最大数量
    */
    NET_EM_FAILCODE_EXCEED_MAX_IRIS_COUNT_PER_GROUP(61, "/// 超出个人单组所能记录的最大数量");

  private int failCode;
  private String desc;

  NET_EM_FAILCODE(int failCode, String desc) {
    this.failCode = failCode;
    this.desc = desc;
  }

  public static NET_EM_FAILCODE FailCode(int failCode) {
    for (NET_EM_FAILCODE code : NET_EM_FAILCODE.values()) {
      if (failCode == code.getFailCode()) {
        return code;
      }
    }
    return NET_EM_FAILCODE_UNKNOWN;
  }

  public static String FailCodeMessage(int failCode) {
    return FailCode(failCode).getDesc();
  }

  public int getFailCode() {
    return failCode;
  }

  public void setFailCode(int failCode) {
    this.failCode = failCode;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}

