package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  存储组件全局配置 
* @date 2022/09/22 17:24:07
*/
public class NAS_DIRECTORY_CFG_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
名称 用于NASDirectory组件实例化的参数name
*/
    public			byte[]         szName = new byte[16];
/** 
存储池名称
*/
    public			byte[]         szGroupName = new byte[32];
/** 
共享用户名
*/
    public			BYTE_32[]      szUserName = new BYTE_32[64];
/** 
共享用户的读写权限, 是一个数组, 下标与用户名对应:1 可读, 2 可新建, 4 可删除
*/
    public			int[]          nUserAuth = new int[64];
/** 
说明
*/
    public			byte[]         szMemo = new byte[1024];
/** 
有效IP
*/
    public			byte[]         szValidIP = new byte[16];
/** 
共享用户名数组实际有效数量
*/
    public			int            nUserNameRealNum;
/** 
共享用户的读写权限实际有效数量
*/
    public			int            nUserAuthRealNum;
/** 
总容量(单位M), 总容量, 创建卷时要用到. 只在新增时可以添加, 后续不允许修改
*/
    public			int            nTotalSpace;
/** 
设置虚拟磁盘最小的块大小, 仅限于IPSAN使用, 单位字节.默认4096字节,必须是512的整数倍
*/
    public			int            nBlockSize;
/** 
共享类型 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_NAS_DIRECTORY_PROTOCAL}
*/
    public			int            emProtocal;
/** 
缓存类型 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_NAS_DIRECTORY_CACHE_TYPE}
*/
    public			int            emCacheType;

public NAS_DIRECTORY_CFG_INFO(){
	for(int i=0;i<szUserName.length;i++){
		szUserName[i]=new BYTE_32();
	}
	this.dwSize=this.size();
}
}

