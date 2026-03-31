package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 遮罩数据
*/
public class NET_GROUND_THING_DETECTION_DATA extends NetSDKLib.SdkStructure
{
    /**
     * 每个MaskInfo遮罩数据在二进制数据包中的偏移量
    */
    public long             nOffset;
    /**
     * 遮罩数据内存长度weight*height*depth
    */
    public int              nLength;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[60];

    public NET_GROUND_THING_DETECTION_DATA() {
    }
}

