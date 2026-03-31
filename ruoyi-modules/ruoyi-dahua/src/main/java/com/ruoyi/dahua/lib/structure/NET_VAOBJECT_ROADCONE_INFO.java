package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 路障目标信息
*/
public class NET_VAOBJECT_ROADCONE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 包围盒, 路锥对象在全景图中的框坐标,为0~8191相对坐标,参见结构体定义 {@link NET_RECT_EX}
    */
    public NET_RECT_EX      stuBoundingBox = new NET_RECT_EX();
    /**
     * 包围盒, 绝对坐标,参见结构体定义 {@link NET_RECT_EX}
    */
    public NET_RECT_EX      stuOriginalBoundingBox = new NET_RECT_EX();
    /**
     * 物体型心（不是包围盒中心）,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX     stuCenter = new NET_POINT_EX();
    /**
     * 物体ID
    */
    public int              nObjectID;
    /**
     * 相关物体ID, 如果物体动作为Split/Merge/Rename的需要相关ID，否则被忽略
    */
    public int              nRelativeID;
    /**
     * 物体动作支持类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_VAOBJECT_ROADCONE_ACTION}
    */
    public int              emAction;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[512];

    public NET_VAOBJECT_ROADCONE_INFO() {
    }
}

