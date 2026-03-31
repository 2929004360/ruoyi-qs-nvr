package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 291189
 * @description 车牌类型
 * @date 2022/05/30 14:16:15
 */
public enum EM_NET_PLATE_TYPE {
	/**
	
	*/
    NET_PLATE_TYPE_UNKNOWN(0, ""),
	/**
	 * Normal 蓝牌黑牌
	 */
    NET_PLATE_TYPE_NORMAL(1, " Normal  蓝牌黑牌"),
	/**
	 * Yellow 黄牌
	 */
    NET_PLATE_TYPE_YELLOW(2, " Yellow  黄牌"),
	/**
	 * DoubleYellow 双层黄尾牌
	 */
    NET_PLATE_TYPE_DOUBLEYELLOW(3, " DoubleYellow  双层黄尾牌"),
	/**
	 * Police 警牌
	 */
    NET_PLATE_TYPE_POLICE(4, " Police  警牌"),
	/**
	
	*/
    NET_PLATE_TYPE_WJ(5, ""),
	/**
	
	*/
    NET_PLATE_TYPE_OUTERGUARD(6, ""),
	/**
	
	*/
    NET_PLATE_TYPE_DOUBLEOUTERGUARD(7, ""),
	/**
	 * SAR 港澳特区号牌
	 */
    NET_PLATE_TYPE_SAR(8, " SAR  港澳特区号牌"),
	/**
	 * Trainning 教练车号牌
	 */
    NET_PLATE_TYPE_TRAINNING(9, " Trainning  教练车号牌"),
	/**
	 * Personal 个性号牌
	 */
    NET_PLATE_TYPE_PERSONAL(10, " Personal  个性号牌"),
	/**
	 * Agri 农用牌
	 */
    NET_PLATE_TYPE_AGRI(11, " Agri  农用牌"),
	/**
	 * Embassy 使馆号牌
	 */
    NET_PLATE_TYPE_EMBASSY(12, " Embassy  使馆号牌"),
	/**
	 * Moto 摩托车号牌
	 */
    NET_PLATE_TYPE_MOTO(13, " Moto  摩托车号牌"),
	/**
	 * Tractor 拖拉机号牌
	 */
    NET_PLATE_TYPE_TRACTOR(14, " Tractor  拖拉机号牌"),
	/**
	 * OfficialCar 公务车
	 */
    NET_PLATE_TYPE_OFFICIALCAR(15, " OfficialCar  公务车"),
	/**
	 * PersonalCar 私家车
	 */
    NET_PLATE_TYPE_PERSONALCAR(16, " PersonalCar  私家车"),
	/**
	
	*/
    NET_PLATE_TYPE_WARCAR(17, ""),
	/**
	 * Other 其他号牌
	 */
    NET_PLATE_TYPE_OTHER(18, " Other  其他号牌"),
	/**
	 * Civilaviation 民航号牌
	 */
    NET_PLATE_TYPE_CIVILAVIATION(19, " Civilaviation  民航号牌"),
	/**
	 * Black 黑牌
	 */
    NET_PLATE_TYPE_BLACK(20, " Black  黑牌"),
	/**
	 * PureNewEnergyMicroCar 纯电动新能源小车
	 */
    NET_PLATE_TYPE_PURENEWENERGYMICROCAR(21, " PureNewEnergyMicroCar  纯电动新能源小车"),
	/**
	 * MixedNewEnergyMicroCar 混合新能源小车
	 */
    NET_PLATE_TYPE_MIXEDNEWENERGYMICROCAR(22, " MixedNewEnergyMicroCar  混合新能源小车"),
	/**
	 * PureNewEnergyLargeCar 纯电动新能源大车
	 */
    NET_PLATE_TYPE_PURENEWENERGYLARGECAR(23, " PureNewEnergyLargeCar  纯电动新能源大车"),
	/**
	 * MixedNewEnergyLargeCar 混合新能源大车
	 */
    NET_PLATE_TYPE_MIXEDNEWENERGYLARGECAR(24, " MixedNewEnergyLargeCar  混合新能源大车"),
	/**
	 * Hongkong 香港号牌
	 */
    NET_PLATE_TYPE_HONGKONG(25, " Hongkong  香港号牌"),
	/**
	 * Makao 澳门号牌
	 */
    NET_PLATE_TYPE_MAKAO(26, " Makao  澳门号牌");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_NET_PLATE_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_NET_PLATE_TYPE enumType : EM_NET_PLATE_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_NET_PLATE_TYPE enumType : EM_NET_PLATE_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_NET_PLATE_TYPE getEnum(int value) {
		for (EM_NET_PLATE_TYPE e : EM_NET_PLATE_TYPE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_NET_PLATE_TYPE.NET_PLATE_TYPE_UNKNOWN;
	}
}

