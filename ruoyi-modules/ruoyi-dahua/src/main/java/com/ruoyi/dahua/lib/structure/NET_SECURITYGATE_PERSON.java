package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 251823
 * @since ： Created in 2021/11/07 安全门注册人员信息
 */
public class NET_SECURITYGATE_PERSON extends NetSDKLib.SdkStructure {
	/**
     *  人员唯一标识符,首次由服务端生成,区别于ID字段
     */
    public byte[]           szUID = new byte[32];
    /**
     *  人员所属组ID
     */
    public byte[]           szGroupID = new byte[64];
    /**
     *  性别,参考{ @link EM_SEX_TYPE}
     */
    public int              emSexType;
    /**
     *  姓名 
     */
    public byte[]           szPersonName = new byte[64];
    /**
     *  生日
     */
    public NET_TIME         stuBirthday;
    /**
     *  国籍,符合ISO3166规范
     */
    public byte[]           szCountry = new byte[3];
    /**
     *  省份
     */
    public byte[]           szProvince = new byte[64];
    /**
     *  城市
     */
    public byte[]           szCity = new byte[64];
    /**
     *  注册人员家庭地址
     */
    public byte[]           szHomeAddress = new byte[128];
    /**
     *  证件类型，参考{ @link EM_CERTIFICATE_TYPE }
     */
    public int              emCertificateType;
    /**
     *  人员证件号码,工号,或其他编号
     */
    public byte[]           szID = new byte[32];
    /**
     * 考生考试信息,参见结构体定义 {@link FACERECOGNITION_CUSTOM_EXAMINEE_INFO}
    */
    public FACERECOGNITION_CUSTOM_EXAMINEE_INFO stuCustomExamineeInfo = new FACERECOGNITION_CUSTOM_EXAMINEE_INFO();
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[572];
}

