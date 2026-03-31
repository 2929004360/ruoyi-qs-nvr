package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 主动跟踪主操作命令 EM_MSGROUP_OPERATE_RECTLOCATE 入参
 * @date 2022/09/14 14:06:45
 */
public class NET_IN_MSGROUP_RECTLOCATE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 主从跟踪组句柄, 从 EM_MSGROUP_OPERATE_OPEN 操作获取
	 */
    public int              dwToken;
	/**
	 * 选定的矩形框坐标
	 */
    public NetSDKLib.NET_RECT stuRect = new NetSDKLib.NET_RECT();
	/**
	 * 框选的方向, 0-从左上到右下, 表示放大; 1-从右下到左上，表示缩小
	 */
    public int              nRectDirection;

	public NET_IN_MSGROUP_RECTLOCATE_INFO() {
		this.dwSize = this.size();
	}
}

