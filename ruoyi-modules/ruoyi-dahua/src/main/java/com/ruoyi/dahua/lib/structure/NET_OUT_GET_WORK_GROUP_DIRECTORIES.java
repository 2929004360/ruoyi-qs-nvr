package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * CLIENT_GetWorkGroupDirectories 接口输出参数
*/
public class NET_OUT_GET_WORK_GROUP_DIRECTORIES extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 工作组包含的工作目录名称最大个数
    */
    public int              nMaxListNum;
    /**
     * 工作组包含的工作目录名称，内存由用户申请释放,参见结构体定义 {@link NET_DIRECTORIES_LIST_NAME}
    */
    public Pointer          pstuList;
    /**
     * 工作组包含的工作目录名称返回有效个数
    */
    public int              nRetListNum;

    public NET_OUT_GET_WORK_GROUP_DIRECTORIES() {
        this.dwSize = this.size();
    }
}

