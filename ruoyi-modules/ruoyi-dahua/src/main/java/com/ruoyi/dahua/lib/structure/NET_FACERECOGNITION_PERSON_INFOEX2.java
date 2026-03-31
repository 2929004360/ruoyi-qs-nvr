package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 目标信息，包括以图搜图的图片数据和属性等
 * @date 2022/11/21 17:53:18
 */
public class NET_FACERECOGNITION_PERSON_INFOEX2 extends NetSDKLib.SdkStructure {
    /**
     * 人员信息查询条件字段stPersonInfoEx是否有效, 并使用人员信息扩展结构体
     */
    public int              bPersonExEnable;
    /**
     * 人员信息扩展
     */
    public NetSDKLib.FACERECOGNITION_PERSON_INFOEX stPersonInfoEx = new NetSDKLib.FACERECOGNITION_PERSON_INFOEX();
    /**
     * 目标图片数据信息的个数, 最大值为48
     */
    public int              nFacePicNumEx;
    /**
     * 目标图片数据信息
     */
    public NET_FACE_PIC_INFO[] stuFacePicInfoEx = new NET_FACE_PIC_INFO[48];
    /**
     * 目标区域信息的个数, 最大值为48
     */
    public int              nBoundingBoxNum;
    /**
     * 目标区域信息, 若字段不存在或均为0则表示为全图区域, 若有该字段则与stuFacePicInfoEx[48]通过数组下标匹配
     */
    public NET_RECT[]       stuBoundingBox = new NET_RECT[48];
    /**
     * 车辆以图搜图时可选择的属性过滤条件的个数, 最大值为48
     */
    public int              nTrafficCarAttributeNum;
    /**
     * 车辆以图搜图时可选择的属性过滤条件, 若有该字段则与stuFacePicInfoEx[48]通过数组下标匹配
     */
    public NET_TRAFFIC_CAR_ATTRIBUTE_INFO[] stuTrafficCarAttribute = new NET_TRAFFIC_CAR_ATTRIBUTE_INFO[48];
    /**
     * 保留字节
     */
    public byte[]           szReserved = new byte[1024];

    public NET_FACERECOGNITION_PERSON_INFOEX2() {
        for (int i = 0; i < stuFacePicInfoEx.length; i++) {
            stuFacePicInfoEx[i] = new NET_FACE_PIC_INFO();
        }
        for (int i = 0; i < stuBoundingBox.length; i++) {
            stuBoundingBox[i] = new NET_RECT();
        }
        for (int i = 0; i < stuTrafficCarAttribute.length; i++) {
            stuTrafficCarAttribute[i] = new NET_TRAFFIC_CAR_ATTRIBUTE_INFO();
        }
    }
}

