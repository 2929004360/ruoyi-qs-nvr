package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 门禁测温配置
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/09 17:05
 */
public class NET_CFG_ACCESSCONTROL_MEASURE_TEMP_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 测温功能是否启用
	 */
    public int              bEnable;
	/**
	 * 仅测温模式是否启用
	 */
    public int              bOnlyTempMode;
	/**
	 * 温度值是否在提示结果中显示
	 */
    public int              bDisplayTemp;
	/**
	 * 测温类型,参考枚举{ @link EM_MASK_DETECT_MODE }
	 */
    public int              emMaskDetectMode;
	/**
	 * 测温类型,参考枚举{ @link EM_TEMP_MEASURE_TYPE }
	 */
    public int              emMeasureType;
	/**
	 * 红外测温模式参数, emMeasureType 为 EM_TEMP_MEASURE_TYPE_INFRARED 时有效
	 */
    public NET_INFRARED_MEASURE_MODE_PARAM stuInfraredTempParam = new NET_INFRARED_MEASURE_MODE_PARAM();
	/**
	 * 热成像测温模式参数, emMeasureType 为 EM_TEMP_MEASURE_TYPE_THERMAL_IMAGE 时有效
	 */
    public NET_THERMAL_IMAGE_MEASURE_MODE_PARAM stuThermalImageTempParam = new NET_THERMAL_IMAGE_MEASURE_MODE_PARAM();
	/**
	 * 高德模块测温模式参数, emMeasureType 为 EM_TEMP_MEASURE_TYPE_GUIDE_MODULE 时有效
	 */
    public NET_GUIDE_MODULE_MEASURE_MODE_PARAM stuGuideModuleTempParam = new NET_GUIDE_MODULE_MEASURE_MODE_PARAM();
	/**
	 * 手腕单点测温模式参数, emMeasureType 为 EM_TEMP_MEASURE_TYPE_WRIST 时有效
	 */
    public NET_WRIST_MEASURE_MODE_PARAM stuWristTempParam = new NET_WRIST_MEASURE_MODE_PARAM();

	public NET_CFG_ACCESSCONTROL_MEASURE_TEMP_INFO() {
		dwSize = this.size();
	}
}

