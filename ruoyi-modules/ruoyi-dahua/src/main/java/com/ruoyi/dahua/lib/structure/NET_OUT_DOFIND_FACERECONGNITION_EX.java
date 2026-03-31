package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description CLIENT_DoFindFaceRecognitionEx接口输出参数
 * @date 2022/11/21 17:53:19
 */
public class NET_OUT_DOFIND_FACERECONGNITION_EX extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 缓冲地址
     */
    public Pointer          pBuffer;
    /**
     * 缓冲数据长度
     */
    public int              nBufferLen;
    /**
     * 设备实际返回的候选信息结构体个数, 最大值是50, nRetCadidateEx2Num必须要小于等于nCadidateEx2Num
     */
    public int              nRetCadidateEx2Num;
    /**
     * 需要用户输入的候选信息结构体个数, 最大值是50
     */
    public int              nCadidateEx2Num;
    /**
     * 候选人员信息列表, 需要用户进行申请和释放，申请内存大小为: sizeof(NET_CANDIDATE_INFOEX2) * nCadidateEx2Num
     */
    public Pointer          pstuCandidatesEx2;

    public NET_OUT_DOFIND_FACERECONGNITION_EX() {
        this.dwSize = this.size();
    }
}

