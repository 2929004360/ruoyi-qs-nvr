 package com.ruoyi.dahua.lib.structure;


 import com.ruoyi.dahua.lib.NetSDKLib;

 /**
 * @author ： 260611
 * @description ： 动物统计信息
 * @since ： Created in 2021/11/05 14:46
 */
public class NET_ANIMAL_OBJECTS_STATISTICS extends NetSDKLib.SdkStructure {
     /** 
      * 动物总数
      */
    public			int            nAnimalsAmount;
 	/**
 	 * 动物类型
 	 */
    public VA_OBJECT_ANIMAL[] stuAnimalTypes = (VA_OBJECT_ANIMAL[]) new VA_OBJECT_ANIMAL().toArray(32);
     /** 
      * 保留字节
      */
    public			byte[]         bReserved = new byte[132];
}

