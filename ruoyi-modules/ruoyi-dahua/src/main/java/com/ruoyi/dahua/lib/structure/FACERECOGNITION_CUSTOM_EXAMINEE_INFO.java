package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 考生考试信息
*/
public class FACERECOGNITION_CUSTOM_EXAMINEE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 准考证号
    */
    public byte[]           szExamineeNumber = new byte[64];
    /**
     * 考场号
    */
    public byte[]           szExamineeRoomNumber = new byte[64];
    /**
     * 座位号
    */
    public byte[]           szExamineeSeatNumber = new byte[64];
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[256];

    public FACERECOGNITION_CUSTOM_EXAMINEE_INFO() {
    }
}

