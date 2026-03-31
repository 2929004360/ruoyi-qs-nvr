package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description CLIENT_FaceRecognitionDetectMultiFace 接口输入参数
 * @date 2022/11/21 17:53:13
 */
public class NET_IN_FACE_RECOGNITION_DETECT_MULTI_FACE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 此结构体的大小
     */
    public int              dwSize;
    /**
     * 大图张数
     */
    public int              nBigPicNum;
    /**
     * 大图信息（推荐使用stuBigPicInfoEx）
     */
    public NET_DETECT_BIG_PIC_INFO[] stuBigPicInfo = new NET_DETECT_BIG_PIC_INFO[50];
    /**
     * 缓冲地址
     */
    public Pointer          pBuffer;
    /**
     * 缓冲数据长度
     */
    public int              nBufferLen;
    /**
     * 目标类型 {@link NetSDKLib.EM_OBJECT_TYPE}
     */
    public int              emDetectObjType;
    /**
     * stuBigPicInfoEx是否有效
     */
    public int              bBigPicInfoExEnable;
    /**
     * 大图张数
     */
    public int              nBigPicNumEx;
    /**
     * 大图信息(扩展)
     */
    public NET_DETECT_BIG_PIC_INFO_EX[] stuBigPicInfoEx = new NET_DETECT_BIG_PIC_INFO_EX[50];
    /**
     * 查询令牌,没有则为0
     */
    public int              nToken;

    public NET_IN_FACE_RECOGNITION_DETECT_MULTI_FACE_INFO() {
        for (int i = 0; i < stuBigPicInfo.length; i++) {
            stuBigPicInfo[i] = new NET_DETECT_BIG_PIC_INFO();
        }
        for (int i = 0; i < stuBigPicInfoEx.length; i++) {
            stuBigPicInfoEx[i] = new NET_DETECT_BIG_PIC_INFO_EX();
        }
        this.dwSize = this.size();
    }
}

