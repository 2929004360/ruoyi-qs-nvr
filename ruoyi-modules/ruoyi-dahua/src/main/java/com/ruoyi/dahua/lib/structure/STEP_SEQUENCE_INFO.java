package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 色步时间序列信息
 * @date 2021/09/01
 */
public class STEP_SEQUENCE_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  灯色个数
     */
    public int              nLampColorCount;
    /**
     * // 灯色 当灯组类型EM_LAMP_TYPE为EM_LAMP_TYPE_STRAIGHT-EM_LAMP_TYPE_FLASH_WARN时，int[0]用于表示红色发光单元，int[1]用于表示黄色发光单元，int[2]用于表示绿色发光单元，int[3]保留
	   // 当灯组类型为EM_LAMP_TYPE_TRAM_DEDICATED_STRAIGHT-EM_LAMP_TYPE_TRAM_DEDICATED_RIGHT时，int[0]用于表示禁止通行信号发光单元，int[1]用于表示过渡信号发光单元，int[2]用于表示通行信号发光单元，int[3]保留 
		// 具体取值0：无灯1：灭灯2：亮灯3：闪烁
     */
    public int[]            nLampColor = new int[4];
    /**
     *  时间长度，单位为秒（s）
     */
    public int              nTime;
    /**
     *  预留字节 
     */
    public byte[]           byReserved = new byte[128];
}

