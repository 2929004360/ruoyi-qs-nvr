package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.NET_POLY_POINTS;
import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;
/**
 * @description 任务模块配置
 * @author 119178
 * @date 2021/3/16
 */
public class NET_ANALYSE_TASK_MODULE extends SdkStructure {
	/**
	 * 规则特定的尺寸过滤器
	 */
    public	NET_CFG_SIZEFILTER_INFO stuSizeFileter = new NET_CFG_SIZEFILTER_INFO();
	/**
	 * 排除区域
	 */
    public	NET_POLY_POINTS[] stuExcludeRegion = new NET_POLY_POINTS[10];
	/**
	 * 排除区域数
	 */
    public	int              nExcludeRegionNum;
	/**
	 * 保留字节
	 */
    public	byte[]           byReserved = new byte[1020];

	public NET_ANALYSE_TASK_MODULE(){

		for(int i=0;i<stuExcludeRegion.length;i++){
			stuExcludeRegion[i]=new NET_POLY_POINTS();
		}

	}
}

