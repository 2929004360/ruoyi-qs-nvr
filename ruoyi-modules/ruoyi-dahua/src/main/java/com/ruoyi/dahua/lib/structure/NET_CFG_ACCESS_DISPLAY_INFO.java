package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 门禁设备显示配置
*/
public class NET_CFG_ACCESS_DISPLAY_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小
    */
    public int              dwSize;
    /**
     * 主页配置选项,参见结构体定义 {@link NET_HOME_PAGE}
    */
    public NET_HOME_PAGE    stuHomePage = new NET_HOME_PAGE();
    /**
     * 广告分屏模式：0：不分屏  1：广告显示在前（左边或者上边） 2：广告显示在后（右边或者下边） 3.预览模式，只展示上传的广告图片
    */
    public int              nAdvertiseSplitType;
    /**
     * 门禁显示主题：0：显示广告内容  2：显示公告文字信息
    */
    public int              nAccessDisplayObject;
    /**
     * 广告分屏模式设备广告显示配置,参见结构体定义 {@link NET_ADVERTISE_DISPLAY}
    */
    public NET_ADVERTISE_DISPLAY stuAdvertiseDisplay = new NET_ADVERTISE_DISPLAY();
    /**
     * 广告分屏模式列表，最大32个,参见结构体定义 {@link NET_ADVERTISE_SPLITLIST}
    */
    public NET_ADVERTISE_SPLITLIST[] stuAdvertiseSplitList = new NET_ADVERTISE_SPLITLIST[32];
    /**
     * 广告分屏模式列表的个数
    */
    public int              nAdvertiseSplitListNum;

    public NET_CFG_ACCESS_DISPLAY_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuAdvertiseSplitList.length; i++){
            stuAdvertiseSplitList[i] = new NET_ADVERTISE_SPLITLIST();
        }
    }
}

