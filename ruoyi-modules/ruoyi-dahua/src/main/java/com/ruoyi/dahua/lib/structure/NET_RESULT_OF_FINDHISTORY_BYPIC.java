package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 历史库以图搜图主动上报结果信息
*/
public class NET_RESULT_OF_FINDHISTORY_BYPIC extends NetSDKLib.SdkStructure
{
    /**
     * 小图ID
    */
    public int              nSmallPicID;
    /**
     * 目标图ID
    */
    public int              nPictureID;
    /**
     * 候选人员数量
    */
    public int              nCandidateNum;
    /**
     * 候选人员信息,参见结构体定义 {@link NET_HISTORY_ACTIVE_CANDIDATE}
    */
    public NET_HISTORY_ACTIVE_CANDIDATE[] stuCandidateInfo = new NET_HISTORY_ACTIVE_CANDIDATE[50];
    /**
     * 预留字节数
    */
    public byte[]           bReserved = new byte[1024];

    public NET_RESULT_OF_FINDHISTORY_BYPIC() {
        for(int i = 0; i < stuCandidateInfo.length; i++){
            stuCandidateInfo[i] = new NET_HISTORY_ACTIVE_CANDIDATE();
        }
    }
}

