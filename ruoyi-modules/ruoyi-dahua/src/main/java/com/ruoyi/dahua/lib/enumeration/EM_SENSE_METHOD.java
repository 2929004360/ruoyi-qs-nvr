package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 传感器感应方式枚举类型
 * @date 2023/04/18 10:57:31
 */
public enum EM_SENSE_METHOD {
	/**
	 * 未知类型
	 */
    EM_SENSE_UNKNOWN(-1, "未知类型"),
	/**
	 * 门磁
	 */
    EM_SENSE_DOOR(0, "门磁"),
	/**
	 * 被动红外
	 */
    EM_SENSE_PASSIVEINFRA(1, "被动红外"),
	/**
	 * 气感
	 */
    EM_SENSE_GAS(2, "气感"),
	/**
	 * 烟感
	 */
    EM_SENSE_SMOKING(3, "烟感"),
	/**
	 * 水感
	 */
    EM_SENSE_WATER(4, "水感"),
	/**
	 * 主动红外
	 */
    EM_SENSE_ACTIVEFRA(5, "主动红外"),
	/**
	 * 玻璃破碎
	 */
    EM_SENSE_GLASS(6, "玻璃破碎"),
	/**
	 * 紧急开关
	 */
    EM_SENSE_EMERGENCYSWITCH(7, "紧急开关"),
	/**
	 * 震动
	 */
    EM_SENSE_SHOCK(8, "震动"),
	/**
	 * 双鉴(红外+微波)
	 */
    EM_SENSE_DOUBLEMETHOD(9, "双鉴(红外+微波)"),
	/**
	 * 三技术
	 */
    EM_SENSE_THREEMETHOD(10, "三技术"),
	/**
	 * 温度
	 */
    EM_SENSE_TEMP(11, "温度"),
	/**
	 * 湿度
	 */
    EM_SENSE_HUMIDITY(12, "湿度"),
	/**
	 * 风速
	 */
    EM_SENSE_WIND(13, "风速"),
	/**
	 * 呼叫按钮
	 */
    EM_SENSE_CALLBUTTON(14, "呼叫按钮"),
	/**
	 * 气体压力
	 */
    EM_SENSE_GASPRESSURE(15, "气体压力"),
	/**
	 * 燃气浓度
	 */
    EM_SENSE_GASCONCENTRATION(16, "燃气浓度"),
	/**
	 * 气体流量
	 */
    EM_SENSE_GASFLOW(17, "气体流量"),
	/**
	 * 油量检测
	 */
    EM_SENSE_OIL(18, "油量检测"),
	/**
	 * 里程数检测
	 */
    EM_SENSE_MILEAGE(19, "里程数检测"),
	/**
	 * 其他
	 */
    EM_SENSE_OTHER(20, "其他"),
	/**
	 * 二氧化碳浓度检测
	 */
    EM_SEHSE_CO2(21, "二氧化碳浓度检测"),
	/**
	 * 噪音检测
	 */
    EM_SEHSE_SOUND(22, "噪音检测"),
	/**
	 * PM2.5检测
	 */
    EM_SEHSE_PM25(23, "PM2.5检测"),
	/**
	 * 六氟化硫浓度检测
	 */
    EM_SEHSE_SF6(24, "六氟化硫浓度检测"),
	/**
	 * 臭氧
	 */
    EM_SEHSE_O3(25, "臭氧"),
	/**
	 * 环境光照检测
	 */
    EM_SEHSE_AMBIENTLIGHT(26, "环境光照检测"),
	/**
	 * 红外报警
	 */
    EM_SEHSE_INFRARED(27, "红外报警"),
	/**
	 * 1500温度传感器
	 */
    EM_SEHSE_TEMP1500(28, "1500温度传感器"),
	/**
	 * DS18B20温度传感器
	 */
    EM_SEHSE_TEMPDS18B20(29, "DS18B20温度传感器"),
	/**
	 * 1500湿度传感器
	 */
    EM_SEHSE_HUMIDITY1500(30, "1500湿度传感器"),
	/**
	 * 紧急按钮
	 */
    EM_SEHSE_URGENCYBUTTON(31, "紧急按钮"),
	/**
	 * 盗窃
	 */
    EM_SEHSE_STEAL(32, "盗窃"),
	/**
	 * 周界
	 */
    EM_SEHSE_PERIMETER(33, "周界"),
	/**
	 * 防拆
	 */
    EM_SEHSE_PREVENTREMOVE(34, "防拆"),
	/**
	 * 门铃
	 */
    EM_SEHSE_DOORBELL(35, "门铃"),
	/**
	 * 交流电压传感器
	 */
    EM_SEHSE_ALTERVOLT(36, "交流电压传感器"),
	/**
	 * 直流电压传感器
	 */
    EM_SEHSE_DIRECTVOLT(37, "直流电压传感器"),
	/**
	 * 交流电流传感器
	 */
    EM_SEHSE_ALTERCUR(38, "交流电流传感器"),
	/**
	 * 直流电流传感器
	 */
    EM_SEHSE_DIRECTCUR(39, "直流电流传感器"),
	/**
	 * 通用模拟量
	 */
    EM_SEHSE_RSUGENERAL(40, "通用模拟量"),
	/**
	 * 门禁感应
	 */
    EM_SEHSE_RSUDOOR(41, "门禁感应"),
	/**
	 * 断电感应
	 */
    EM_SEHSE_RSUPOWEROFF(42, "断电感应"),
	/**
	 * 幕帘传感器
	 */
    EM_SEHSE_CURTAINSENSOR(43, "幕帘传感器"),
	/**
	 * 移动传感器
	 */
    EM_SEHSE_MOBILESENSOR(44, "移动传感器"),
	/**
	 * 火警
	 */
    EM_SEHSE_FIREALARM(45, "火警"),
	/**
	 * 锁舌
	 */
    EM_SEHSE_LOCKTONGUE(46, "锁舌"),
	/**
	 * 枚举类型总数,注意：这个值不能作为常量使用
	 */
    EM_SENSE_NU(47, "枚举类型总数,注意：这个值不能作为常量使用");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SENSE_METHOD(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SENSE_METHOD enumType : EM_SENSE_METHOD.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SENSE_METHOD enumType : EM_SENSE_METHOD.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

