package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 目标检测区域
*/
public class NET_OBJECT_AREA extends NetSDKLib.SdkStructure
{
    /**
     * 区域数组有效个数
    */
    public int              nAreaNum;
    /**
     * 区域
    */
    public INT_ARRAY_2[]    nArea = new INT_ARRAY_2[20];
    /**
     * 目标区域的类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_AREA_TYPE}
    */
    public int              emAreaType;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_OBJECT_AREA() {
        for(int i = 0; i < nArea.length; i++){
            nArea[i] = new INT_ARRAY_2();
        }
    }
}

