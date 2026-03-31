package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_StartMultiFindFaceRecognitionEx 接口输出参数
 * @date 2022/11/21 17:53:19
 */
public class NET_OUT_STARTMULTIFIND_FACERECONGNITION_EX extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 返回的符合查询条件的记录个数
     * -1表示总条数未生成,要推迟获取
     * 使用CLIENT_AttachFaceFindState接口状态
     */
    public int              nTotalCount;
    /**
     * 查询句柄
     */
    public NetSDKLib.LLong  lFindHandle;
    /**
     * 获取到的查询令牌
     */
    public int              nToken;
    /**
     * 结果详细信息的个数, 最大值为64
     */
    public int              nCountDetailNum;
    /**
     * 结果详细信息
     */
    public NET_COUNT_DETAIL_INFO[] stuCountDetail = new NET_COUNT_DETAIL_INFO[64];

    public NET_OUT_STARTMULTIFIND_FACERECONGNITION_EX() {
        for (int i = 0; i < stuCountDetail.length; i++) {
            stuCountDetail[i] = new NET_COUNT_DETAIL_INFO();
        }
        this.dwSize = this.size();
    }
}

