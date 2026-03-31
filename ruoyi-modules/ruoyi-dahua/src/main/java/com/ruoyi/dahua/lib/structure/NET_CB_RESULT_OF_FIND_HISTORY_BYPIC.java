package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 历史库以图搜图回调信息
*/
public class NET_CB_RESULT_OF_FIND_HISTORY_BYPIC extends NetSDKLib.SdkStructure
{
    /**
     * 查询令牌
    */
    public int              nToken;
    /**
     * 查询进度百分比
    */
    public int              nProgress;
    /**
     * 满足条件的总条数
    */
    public int              nTotalCount;
    /**
     * 历史库以图搜图查询结果数量
    */
    public int              nHistoryResultNum;
    /**
     * 历史库以图搜图查询结果信息,参见结构体定义 {@link NET_RESULT_OF_FINDHISTORY_BYPIC}
    */
    public NET_RESULT_OF_FINDHISTORY_BYPIC[] stuHistoryResult = new NET_RESULT_OF_FINDHISTORY_BYPIC[32];
    /**
     * 预留字节数
    */
    public byte[]           bReserved = new byte[1024];

    public NET_CB_RESULT_OF_FIND_HISTORY_BYPIC() {
        for(int i = 0; i < stuHistoryResult.length; i++){
            stuHistoryResult[i] = new NET_RESULT_OF_FINDHISTORY_BYPIC();
        }
    }
}

