package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.DH_POINT;
import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;
/**
 * @description 标定区域
 * @author 119178
 * @date 2021/3/16
 */
public class NET_ANALYSE_TASK_GLOBAL_CALIBRATEAREA extends SdkStructure {
	/**
	 * 标尺线个数
	 */
    public int              nStaffs;
	/**
	 * 标尺线  
	 */
    public	NET_STAFF_INFO[] stuStaffs = new NET_STAFF_INFO[32];
	/**
	 * 标定多边形区域
	 */
    public	DH_POINT[]       stuCalibratePloygonArea = new DH_POINT[20];
	/**
	 * 标定多边形区域顶点个数
	 */
    public	int              nCalibratePloygonAreaNum;
	/**
	 * 保留字节
	 */
    public	byte[]           byReserved = new byte[1024];

	public  NET_ANALYSE_TASK_GLOBAL_CALIBRATEAREA(){
		for(int i=0;i<stuStaffs.length;i++){
			stuStaffs[i]=new NET_STAFF_INFO();
		}

		for(int i=0;i<stuCalibratePloygonArea.length;i++){
			stuCalibratePloygonArea[i]=new DH_POINT();
		}

	}
}

