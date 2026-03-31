package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 421657
 * @description CLIENT_DoFindTalkDev 接口输出参数
 * @origin autoTool
 * @date 2023/10/26 10:05:01
 */
public class NET_OUT_DO_FIND_TALK_DEV extends NetSDKLib.SdkStructure {
    /**
     * / 结构体大小
     */
    public int              dwSize;
    /**
     * / 用户指定分配结构体个数,需大于等于NET_IN_DO_FIND_TALK_DEV的nCount
     */
    public int              nMaxNum;
    /**
     * / 查询信息数据, 内存由用户申请释放, 大小为NET_TALK_DEV_DO_FIND_INFO*nMaxNum, refer to {@link NET_TALK_DEV_DO_FIND_INFO}
     */
    public Pointer          pstuInfo;
    /**
     * / 实际返回的查询数量
     */
    public int              nRetNum;

    public NET_OUT_DO_FIND_TALK_DEV() {
        this.dwSize = this.size();
    }
}

