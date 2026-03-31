package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;
import com.sun.jna.Pointer;
/**
 * @description 任务全局配置
 * @author 119178
 * @date 2021/3/16
 */
public class NET_ANALYSE_TASK_GLOBAL extends SdkStructure {
	/**
	 * 车道信息数量
	 */
    public int              nLanesNum;
	/**
	 * 车道信息 每个车道两条边界线
	 */
    public	NET_ANALYSE_TASK_GLOBAL_LANES[] stuLanes = new NET_ANALYSE_TASK_GLOBAL_LANES[8];
	/**
	 * 标定区域个数
	 */
    public int              nCalibrateArea;
	/**
	 * 标定区域
	 */
    public	NET_ANALYSE_TASK_GLOBAL_CALIBRATEAREA[] stuCalibrateArea = new NET_ANALYSE_TASK_GLOBAL_CALIBRATEAREA[32];
    /**
     * 目标检测区域数组,由用户申请内存，申请大小为sizeof(NET_OBJECT_AREA)*nObjectAreasNum,参见结构体定义 {@link NET_OBJECT_AREA}
    */
    public Pointer          pstuObjectAreas;
    /**
     * 目标检测区域数组有效个数
    */
    public int              nObjectAreasNum;
	/**
	 * 保留字节
	 */
    public	byte[]           byReserved = new byte[1016-NetSDKLib.POINTERSIZE];

	public  NET_ANALYSE_TASK_GLOBAL(){
		for(int i=0;i<stuLanes.length;i++){
			stuLanes[i]=new NET_ANALYSE_TASK_GLOBAL_LANES();
		}

		for(int i=0;i<stuCalibrateArea.length;i++){
			stuCalibrateArea[i]=new NET_ANALYSE_TASK_GLOBAL_CALIBRATEAREA();
		}

	}
}

