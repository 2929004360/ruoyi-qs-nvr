package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_GetInstalledAppInfo 接口输出参数
*/
public class NET_OUT_GET_INSTALLED_APP_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 返回以安装的应用列表信息的数量
    */
    public int              nListCount;
    /**
     * 安装的应用列表信息,参见结构体定义 {@link NET_INSTALLED_APP_INFO}
    */
    public NET_INSTALLED_APP_INFO[] stuAppInfoList = new NET_INSTALLED_APP_INFO[16];

    public NET_OUT_GET_INSTALLED_APP_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuAppInfoList.length; i++){
            stuAppInfoList[i] = new NET_INSTALLED_APP_INFO();
        }
    }
}

