package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 大图信息(大图抠小图功能)
 * @date 2022/11/21 17:53:13
 */
public class NET_DETECT_BIG_PIC_INFO_EX extends NetSDKLib.SdkStructure {
    /**
     * 大图ID
     */
    public int              nPicID;
    /**
     * 文件在二进制数据块中的偏移位置, 单位:字节
     */
    public int              dwOffSet;
    /**
     * 文件大小, 单位:字节
     */
    public int              dwFileLenth;
    /**
     * 图片宽度, 单位:像素
     */
    public int              dwWidth;
    /**
     * 图片高度, 单位:像素
     */
    public int              dwHeight;
    /**
     * 规则检测区域顶点数
     */
    public int              nDetectRegionNum;
    /**
     * 规则检测区域
     */
    public NetSDKLib.NET_POINT[] stuDetectRegion = new NetSDKLib.NET_POINT[20];
    /**
     * 请求图片ID
     */
    public byte[]           szRequestID = new byte[64];
    /**
     * 通过URL下载图片
     */
    public byte[]           szPath = new byte[256];
    /**
     * 坐标系类型 {@link com.ruoyi.dahua.lib.enumeration.EM_COORDINATE_TYPE}
     */
    public int              emCoordinateType;
    /**
     * 目标类型 {@link com.ruoyi.dahua.lib.enumeration.EM_DETECT_OBJECT_TYPE}
     */
    public int              emObjectType;
    /**
     * 按位组合，全1表示全检测,1表示人脸，2表示人体，4表示机动车，8表示非机动整体, 16表示非机动车
     */
    public int              nTargetType;
    /**
     * 处理类型个数
     */
    public int              nProcessTypeNum;
    /**
     * 处理类型
     */
    public int[]            emProcessType = new int[8];
    /**
     * 全景大图数据，经过Base64后的字符串 无该字段或该字段为空则使用二进制图片数据
     */
    public Pointer          szData;
    /**
     * 全景大图数据长度
     */
    public int              nDataLen;
    /**
     * 保留字节
     */
    public byte[]           bReserved = new byte[1016];

    public NET_DETECT_BIG_PIC_INFO_EX() {
        for (int i = 0; i < stuDetectRegion.length; i++) {
            stuDetectRegion[i] = new NetSDKLib.NET_POINT();
        }
    }
}

