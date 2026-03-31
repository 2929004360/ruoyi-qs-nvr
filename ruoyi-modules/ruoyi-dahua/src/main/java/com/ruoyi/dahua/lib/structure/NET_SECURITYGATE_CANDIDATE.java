package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 251823
 * @since ： Created in 2021/11/07 10:06 安全门候选人信息
 */
public class NET_SECURITYGATE_CANDIDATE extends NetSDKLib.SdkStructure {
	/**
     *  人员信息
     */
    public NET_SECURITYGATE_PERSON stuPerson;
    /**
     *  抓拍人脸的相似度 1~100
     */
    public int              nSimilarity;
    /**
     *  保留字节 
     */
    public byte[]           byReserved = new byte[260];
}

