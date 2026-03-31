package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 智能分析图片信息扩展
*/
public class NET_PUSH_PICTURE_INFO_EXTERN extends NetSDKLib.SdkStructure
{
    /**
     * 算法模型路径
    */
    public byte[]           szModelPath = new byte[512];
    /**
     * 图片序号, 如果使用此字段，回调的事件将在图片数组赋值该值，仅表示序号值不代表索引
    */
    public int              nIndexInData;
    /**
     * 图片类型， 0: 未知 1：抓图； 2：底图
    */
    public int              nPictureType;
    /**
     * 底图身份证ID
    */
    public byte[]           szCitizenIDNo = new byte[32];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[512];

    public NET_PUSH_PICTURE_INFO_EXTERN() {
    }
}

