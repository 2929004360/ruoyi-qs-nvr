package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 目标图片信息
*/
public class NET_FACE_IMAGE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 图片在二进制数据块中的偏移位置, 单位:字节
    */
    public int              dwOffSet;
    /**
     * 图片大小, 单位:字节
    */
    public int              dwFileLength;
    /**
     * 图片宽度
    */
    public int              dwWidth;
    /**
     * 图片高度
    */
    public int              dwHeight;
    /**
     * 图片是否算法检测出来的,检测过的提交识别服务器时,, 则不需要再时检测定位抠图,TRUE:检测过的,FALSE:没有检测过
    */
    public int              bIsDetected;
    /**
     * 目标图片文件路径, 与dwOffSet和dwFileLenth互斥
    */
    public byte[]           szPicFilePath = new byte[256];
    /**
     * 预留字节数
    */
    public byte[]           bReserved = new byte[512];

    public NET_FACE_IMAGE_INFO() {
    }
}

