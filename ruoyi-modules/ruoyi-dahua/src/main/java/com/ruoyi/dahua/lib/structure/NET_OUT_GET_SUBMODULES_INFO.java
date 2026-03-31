package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 接口CLIENT_GetSubModuleInfo 输出参数
*/
public class NET_OUT_GET_SUBMODULES_INFO extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 返回的子模块数量
    */
    public int              nRetNum;
    /**
     * 子模块信息,参见结构体定义 {@link NET_SUBMODULES_INFO}
    */
    public NET_SUBMODULES_INFO[] stuSubModuleInfo = new NET_SUBMODULES_INFO[32];
    /**
     * 子模块扩展信息,参见结构体定义 {@link NET_SUBMODULES_INFO_EX}
    */
    public NET_SUBMODULES_INFO_EX[] stuSubModuleInfoEx = new NET_SUBMODULES_INFO_EX[32];

    public NET_OUT_GET_SUBMODULES_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuSubModuleInfo.length; i++){
            stuSubModuleInfo[i] = new NET_SUBMODULES_INFO();
        }
        for(int i = 0; i < stuSubModuleInfoEx.length; i++){
            stuSubModuleInfoEx[i] = new NET_SUBMODULES_INFO_EX();
        }
    }
}

