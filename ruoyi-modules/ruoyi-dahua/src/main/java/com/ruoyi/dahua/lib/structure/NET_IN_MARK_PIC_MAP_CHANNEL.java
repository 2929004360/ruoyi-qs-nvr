package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_MarkPicMapChannel 接口输入参数
*/
public class NET_IN_MARK_PIC_MAP_CHANNEL extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 地图已标记信息个数
    */
    public int              nMarkInfosCount;
    /**
     * 地图已标记信息,参见结构体定义 {@link NET_MAP_PIC_MARK_INFOS}
    */
    public NET_MAP_PIC_MARK_INFOS[] stuMarkInfos = new NET_MAP_PIC_MARK_INFOS[1024];

    public NET_IN_MARK_PIC_MAP_CHANNEL() {
        this.dwSize = this.size();
        for(int i = 0; i < stuMarkInfos.length; i++){
            stuMarkInfos[i] = new NET_MAP_PIC_MARK_INFOS();
        }
    }
}

