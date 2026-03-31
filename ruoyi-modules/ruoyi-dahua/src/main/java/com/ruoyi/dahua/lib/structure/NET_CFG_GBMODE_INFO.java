package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 视频OSD国标模式配置, 对应枚举NET_EM_CFG_GBMODE
*/
public class NET_CFG_GBMODE_INFO extends NetSDKLib.SdkStructure
{
    public int              dwSize;
    /**
     * 是否叠加到主码流视频编码
    */
    public int              bEncodeBlend;
    /**
     * 是否叠加到预览显示
    */
    public int              bPreviewBlend;
    /**
     * 前景颜色
    */
    public int[]            nFrontColor = new int[4];
    /**
     * 背景颜色
    */
    public int[]            nBackColor = new int[4];
    /**
     * 距离视频边缘距离，单位：字符
    */
    public int              nMargin;
    /**
     * 同一区域OSD行间距，如右下角5行OSD之间的行间距，单位：字符。
    */
    public int              nRowSpacing;
    /**
     * 时间标题，固定在右上角，且右上角仅允许有时间标题，和原来功能一致,参见结构体定义 {@link NET_GBMODE_TIMETITLE_INFO}
    */
    public NET_GBMODE_TIMETITLE_INFO stuTimeTitle = new NET_GBMODE_TIMETITLE_INFO();
    /**
     * 自定义左上角、左下角及右下角标题信息有效个数
    */
    public int              nCustomTitleNum;
    /**
     * 自定义左上角、左下角及右下角标题信息,参见结构体定义 {@link NET_GBMODE_CUSTOMTITLE_INFO}
    */
    public NET_GBMODE_CUSTOMTITLE_INFO[] stuCustomTitle = new NET_GBMODE_CUSTOMTITLE_INFO[8];
    /**
     * 通道标题,参见结构体定义 {@link NET_GBMODE_CHANNELTITLE_INFO}
    */
    public NET_GBMODE_CHANNELTITLE_INFO stuChannelTitle = new NET_GBMODE_CHANNELTITLE_INFO();

    public NET_CFG_GBMODE_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuCustomTitle.length; i++){
            stuCustomTitle[i] = new NET_GBMODE_CUSTOMTITLE_INFO();
        }
    }
}

