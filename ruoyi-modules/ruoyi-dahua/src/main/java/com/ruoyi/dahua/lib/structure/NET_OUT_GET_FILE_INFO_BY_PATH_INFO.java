package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description  CLIENT_GetFileManagerExFileInfoByPath接口出参
 * @date 2022/10/12 20:23:54
 */
public class NET_OUT_GET_FILE_INFO_BY_PATH_INFO extends NetSDKLib.SdkStructure {
	/**
	 此结构体大小,必须赋值
	 */
    public			int            dwSize;
	/**
	 文件上传状态 {@link com.ruoyi.dahua.lib.enumeration.EM_FILE_UPDATE_STATE}
	 */
    public			int            emState;
	/**
	 文件开始时间
	 */
    public			NET_TIME_EX    stuStartTime = new NET_TIME_EX();
	/**
	 文件结束时间
	 */
    public			NET_TIME_EX    stuEndTime = new NET_TIME_EX();
	/**
	 文件上传进度
	 */
    public			int            nProgress;
	/**
	 当前分片(切片上传模式所需数据)
	 */
    public			int            nCurrentSlice;
	/**
	 文件大小
	 */
    public			long           nFileLength;
	/**
	 错误原因
	 0: 未知原因
	 1: 文件个数超出
	 2: 文件存储空间已满
	 3: 该文件太大
	 4: 无效的文件类型
	 5: 没有上传权限
	 6: 文件命名异常
	 7: 文件名太长
	 8: 文件名已经存在
	 9: 验签失败
	 */
    public			int            nReason;
	/**
	 *字节对齐
	 */
    public			byte[]         szReserved = new byte[4];

	public			NET_OUT_GET_FILE_INFO_BY_PATH_INFO(){
		this.dwSize=this.size();
	}
}

