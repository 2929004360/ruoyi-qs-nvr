package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 门禁的AB互锁功能, 多个门通道，只有其他B通道都关闭，唯一的A通道才打开
*/
public class CFG_ABLOCK_INFO_EX extends NetSDKLib.SdkStructure
{
    /**
     * 使能
    */
    public int              bEnable;
    /**
     * 有效互锁组数
    */
    public int              nDoorsCount;
    /**
     * 互锁组信息,参见结构体定义 {@link CFG_ABLOCK_DOOR_INFO_EX}
    */
    public CFG_ABLOCK_DOOR_INFO_EX[] stuDoors = new CFG_ABLOCK_DOOR_INFO_EX[8];
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[64];

    public CFG_ABLOCK_INFO_EX() {
        for(int i = 0; i < stuDoors.length; i++){
            stuDoors[i] = new CFG_ABLOCK_DOOR_INFO_EX();
        }
    }
}

