package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_PERSON_FEATURE_ERRCODE;
import com.sun.jna.Pointer;

import static com.ruoyi.dahua.lib.NetSDKLib.*;

/**
 * className：FACERECOGNITION_PERSON_INFOEX description： 人员信息扩展结构体 author：251589
 * createTime：2020/12/28 10:47
 *
 * @version v1.0
 */
public class FACERECOGNITION_PERSON_INFOEX extends SdkStructure {
  /** 姓名 */
    public byte[]           szPersonName = new byte[NET_MAX_PERSON_NAME_LEN];
  /** 出生年,作为查询条件时,此参数填0,则表示此参数无效 */
    public short            wYear;
  /** 出生月,作为查询条件时,此参数填0,则表示此参数无效 */
    public byte             byMonth;
  /** 出生日,作为查询条件时,此参数填0,则表示此参数无效 */
    public byte             byDay;
  /** 人员重要等级,1~10,数值越高越重要,作为查询条件时,此参数填0,则表示此参数无效 */
    public byte             bImportantRank;
  /** 性别,1-男,2-女,作为查询条件时,此参数填0,则表示此参数无效 */
    public byte             bySex;
  /** 人员唯一标示(证件号码,工号,或其他编号) */
    public byte[]           szID = new byte[NET_MAX_PERSON_ID_LEN];
  /** 图片张数 */
    public short            wFacePicNum;
  /** 当前人员对应的图片信息 */
    public Net_PIC_INFO[]   szFacePicInfo = new Net_PIC_INFO[NET_MAX_PERSON_IMAGE_NUM];
  /** 人员类型,详见 EM_PERSON_TYPE */
    public byte             byType;
  /** 证件类型,详见 EM_CERTIFICATE_TYPE */
    public byte             byIDType;
  /** 是否戴眼镜，0-未知 1-不戴 2-戴 */
    public byte             byGlasses;
  /** 年龄,0表示未知 */
    public byte             byAge;
  /** 省份 */
    public byte[]           szProvince = new byte[NET_MAX_PROVINCE_NAME_LEN];
  /** 城市 */
    public byte[]           szCity = new byte[NET_MAX_CITY_NAME_LEN];
  /** 人员唯一标识符,首次由服务端生成,区别于ID字段 */
    public byte[]           szUID = new byte[NET_MAX_PERSON_ID_LEN];
  /** 国籍,符合ISO3166规范 */
    public byte[]           szCountry = new byte[NET_COUNTRY_LENGTH];
  /** 人员类型是否为自定义: 0 使用Type规定的类型 1 自定义,使用szCustomType字段 */
    public byte             byIsCustomType;
  /** 人员自定义类型 */
    public byte[]           szCustomType = new byte[NET_COMMON_STRING_16];
  /** 备注信息 */
    public byte[]           szComment = new byte[NET_COMMENT_LENGTH];
  /** 人员所属组ID */
    public byte[]           szGroupID = new byte[NET_GROUPID_LENGTH];
  /** 人员所属组名, 用户自己申请内存的情况时, */
    public byte[]           szGroupName = new byte[NET_GROUPNAME_LENGTH];
  /** 表情,对应{@link EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE} */
    public int              emEmotion;
  /** 注册人员家庭地址 */
    public byte[]           szHomeAddress = new byte[NET_COMMON_STRING_128];
  /** 眼镜类型 */
    public int              emGlassesType;
  /**  */
    public byte[]           szReserved1 = new byte[4];
  /** 眼睛状态,对应{@link EM_EYE_STATE_TYPE} */
    public int              emEye;
  /** 嘴巴状态,对应{@link EM_MOUTH_STATE_TYPE} */
    public int              emMouth;
  /** 口罩状态,对应{@link EM_MASK_STATE_TYPE} */
    public int              emMask;
  /** 胡子状态,对应{@link EM_BEARD_STATE_TYPE} */
    public int              emBeard;
  /** 魅力值, -1表示无效, 0未识别，识别时范围1-100,得分高魅力高,对应{@link EM_PERSON_FEATURE_STATE} */
    public int              nAttractive;
  /** 人员建模状态,对应{@link EM_PERSON_FEATURE_STATE} */
    public int              emFeatureState;
  /** 是否指定年龄段 */
    public int              bAgeEnable;
  /** 年龄范围 */
    public int[]            nAgeRange = new int[2];
  /** 人脸特征数组有效个数,与 emFeature 结合使用, 如果为0则表示查询所有表情 */
    public int              nEmotionValidNum;
  /** 人脸特征数组,与 byFeatureValidNum 结合使用 设置查询条件的时候使用 */
    public int[]            emEmotions = new int[NET_MAX_FACEDETECT_FEATURE_NUM];
  /** 注册人员信息扩展个数 */
    public int              nCustomPersonInfoNum;
  /** 注册人员信息扩展 */
    public CUSTOM_PERSON_INFO[] szCustomPersonInfo = new CUSTOM_PERSON_INFO[NET_MAX_CUSTOM_PERSON_INFO_NUM];
  /** 注册库类型,对应{@link EM_REGISTER_DB_TYPE} */
    public int              emRegisterDbType;
  /** 有效期时间 */
    public NET_TIME         stuEffectiveTime;
  /** 建模失败原因,对应{@link EM_PERSON_FEATURE_ERRCODE} */
    public int              emFeatureErrCode;
  /** 人脸图片张数 */
    public int              wFacePicNumEx;
  /** 当前人员对应的图片信息 */
    public NET_FACE_PIC_INFO[] szFacePicInfoEx = new NET_FACE_PIC_INFO[6];
  /** 人员特征信息 */
    public NET_PERSON_FEATURE_VALUE_INFO stuPersonFeatureValue;
  /** 人员冻结状态 */
    public int              bFrozenStatus;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[4];
    /**
     * 频次报警信息,参见结构体定义 {@link NET_PERSON_FREQUENCY_INFO}
    */
    public NET_PERSON_FREQUENCY_INFO stuFrequencyInfo = new NET_PERSON_FREQUENCY_INFO();
    /**
     * 平台唯一标识目标字段，区别于UID,IVSS根据faceRecognitionServer.getCaps能力，SupportIDFromServer值为true时，支持UUID有效
    */
    public byte[]           szUUID = new byte[64];
    /**
     * 路人信息,由用户申请内存, 一次申请一个,参见结构体定义 {@link FACERECOGNITION_CUSTOM_PASSER_BY_INFO}
    */
    public Pointer          pstuCustomPasserbyInfo;
    /**
     * 考生考试信息, 由用户申请内存, 一次申请一个,参见结构体定义 {@link FACERECOGNITION_CUSTOM_EXAMINEE_INFO}
    */
    public Pointer          pstuCustomExamineeInfo;
    /**
     * 住户信息, 由用户申请内存, 一次申请一个,参见结构体定义 {@link FACERECOGNITION_CUSTOM_RESIDENTS_INFO}
    */
    public Pointer          pstuCustomResidentsInfo;
    /**
     * 对应抓拍的唯一id
    */
    public byte[]           szRecordID = new byte[64];
    /**
     * 人员信息扩展2, 由用户申请内存, 一次申请一个,参见结构体定义 {@link FACERECOGNITION_PERSON_INFOEX2}
    */
    public Pointer          pstuPersonInfoEx2;
    /**
     * 人员特征信息使能
    */
    public int              bPersonFeatureValue;
  /** 保留字节 */
    public byte[]           byReserved = new byte[120-NetSDKLib.POINTERSIZE*4];
}

