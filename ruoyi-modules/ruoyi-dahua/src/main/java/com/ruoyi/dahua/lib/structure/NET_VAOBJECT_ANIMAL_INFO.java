package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description 智慧养殖对象信息
* @date 2022/04/15 15:29:30
*/
public class NET_VAOBJECT_ANIMAL_INFO extends NetSDKLib.SdkStructure {
/** 
物体ID，每个ID表示一个唯一的物体
*/
    public			int            nObjectID;
/** 
智慧养殖检测目标子类型 {@link com.ruoyi.dahua.lib.enumeration.EM_BREED_DETECT_CATEGORY_TYPE}
*/
    public			int            emCategoryType;
/** 
包围盒
*/
    public NET_RECT         stuBoundingBox = new NET_RECT();
/** 
对象重量, 单位: g
*/
    public			int            nObjectWeight;
/** 
物体截图
*/
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuImageData = new NetSDKLib.SCENE_IMAGE_INFO_EX();
/** 
预留字节
*/
    public			byte[]         byReserved = new byte[1024];
}

