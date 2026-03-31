package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_AttachResultOfFindHistoryByPic接口输入参数
*/
public class NET_IN_ATTACH_RESULT_FINDHISTORY_BYPIC extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 查询令牌, 由CLIENT_StartFindFaceRecognition返回
    */
    public int              nToken;
    /**
     * 订阅历史库以图搜图回调函数,参见回调函数定义 {@link NetSDKLib.fResultOfFindHistory}
    */
    public NetSDKLib.fResultOfFindHistory cbResultOfFindHistory;
    /**
     * 用户数据
    */
    public Pointer          dwUser;

    public NET_IN_ATTACH_RESULT_FINDHISTORY_BYPIC() {
        this.dwSize = this.size();
    }
}

