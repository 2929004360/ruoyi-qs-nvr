package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description CLIENT_DoFindThingsHistoryData 输出参数
 * @date 2022/04/20 10:16:55
 */
public class NET_OUT_THINGS_DOFIND_HISTORYDATA extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 查询到的数量
     */
    public int              nCount;
    /**
     * 用户分配的pstuHistoryData个数
     */
    public int              nMaxHisDataNum;
    /**
     * 历史数据内容,用户分配内存,大小为 nMaxHisDataNum * sizeof{@link NET_THINGS_HISTORYDATA}
     */
    public Pointer          pstuHistoryData;

    public NET_OUT_THINGS_DOFIND_HISTORYDATA() {
        this.dwSize = this.size();
    }
}

