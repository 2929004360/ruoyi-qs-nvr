package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  门禁控制-呼梯(对应CLIENT_ControlDevice接口的 DH_CTRL_ACCESS_CALL_LIFT 命令) 
* @date 2022/10/19 14:01:08
*/
public class NET_CTRL_ACCESS_CALL_LIFT extends NetSDKLib.SdkStructure {
    public			int            dwSize;
/** 
通道号
*/
    public			int            nChannelID;
/** 
起始地址(楼层+房间号)
*/
    public			byte[]         szSrcFloor = new byte[16];
/** 
目标地址(楼层+房间号)
*/
    public			byte[]         szDestFloor = new byte[16];
/** 
呼叫电梯命令 {@link com.ruoyi.dahua.lib.enumeration.EM_CALLLIFT_CMD}
*/
    public			int            emCallLiftCmd;
/** 
呼叫电梯的方式 {@link com.ruoyi.dahua.lib.enumeration.EM_CALLLIFT_ACTION}
*/
    public			int            emCallLiftAction;
/** 
用户ID
*/
    public			byte[]         szUserID = new byte[16];
/** 
呼叫电梯个数
*/
    public			int            nCallLiftCount;
/** 
呼叫电梯号
*/
    public			int[]          nCallLiftNo = new int[16];
/** 
呼梯类型 {@link com.ruoyi.dahua.lib.enumeration.EM_LIFT_CALLER_TYPE}
*/
    public			int            emLiftCaller;

public NET_CTRL_ACCESS_CALL_LIFT(){
		this.dwSize=this.size();
}
}

