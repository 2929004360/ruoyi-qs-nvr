package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description CLIENT_StartMultiFindFaceRecognitionEx 接口输入参数
 * @date 2022/11/21 17:53:18
 */
public class NET_IN_STARTMULTIFIND_FACERECONGNITION_EX extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 通道号
     */
    public Pointer          pChannelID;
    /**
     * 通道申请个数
     */
    public int              nChannelCount;
    /**
     * 搜索的目标类型 {@link NetSDKLib.EM_OBJECT_TYPE}
     */
    public int              emObjectType;
    /**
     * 人脸匹配选项
     */
    public NetSDKLib.NET_FACE_MATCH_OPTIONS stMatchOptions = new NetSDKLib.NET_FACE_MATCH_OPTIONS();
    /**
     * 查询过滤条件
     */
    public NetSDKLib.NET_FACE_FILTER_CONDTION stFilterInfo = new NetSDKLib.NET_FACE_FILTER_CONDTION();
    /**
     * 缓冲地址
     */
    public Pointer          pBuffer;
    /**
     * 缓冲数据长度
     */
    public int              nBufferLen;
    /**
     * 目标信息是否有效, 并使用目标信息结构体
     */
    public int              bPersonEx2Enable;
    /**
     * 目标信息, 包括以图搜图的图片数据和属性等
     */
    public NET_FACERECOGNITION_PERSON_INFOEX2 stPersonInfoEx2 = new NET_FACERECOGNITION_PERSON_INFOEX2();

    public NET_IN_STARTMULTIFIND_FACERECONGNITION_EX() {
        this.dwSize = this.size();
    }
}

