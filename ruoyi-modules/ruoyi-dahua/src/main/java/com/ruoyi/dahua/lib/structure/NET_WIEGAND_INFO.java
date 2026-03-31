package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description 韦根配置
* @date 2022/03/08 09:56:03
*/
public class NET_WIEGAND_INFO extends NetSDKLib.SdkStructure {
/** 
工作模式  {@link com.ruoyi.dahua.lib.enumeration.EM_NET_WIEGAND_MODE}
*/
    public			int            emMode;
/** 
脉冲宽度,单位us
*/
    public			int            nPulseWidth;
/** 
脉冲间隔,单位us
*/
    public			int            nPulseStep;
/** 
传输模式   {@link com.ruoyi.dahua.lib.enumeration.EM_NET_WIEGAND_TRANSFER_MODE}
 */
    public			int            emTransferMode;
/** 
输出类型  {@link com.ruoyi.dahua.lib.enumeration.EM_NET_WIEGAND_OUT_TYPE}
*/
    public			int            emOutType;
/** 
掩码值，每一位代表一个接收输入的掩码值，全为0表示不接收输入
*/
    public			int            dwInputType;
/** 
门编号, 例如 0: 门1编号, 1: 门2编号
*/
    public			int            nDoorNo;
/** 
保留字节
*/
    public			byte[]         bReserverd = new byte[1024];
}

