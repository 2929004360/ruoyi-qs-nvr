package com.ruoyi.dahua.lib.enumeration;

public enum EM_VSP_GAVI_PLATFORM_CODE {
    EM_VSP_GAVI_PLATFORM_CODE_UNKNOWN(-1, "未知"),
    EM_VSP_GAVI_PLATFORM_CODE_DEFAULT(0, "默认，标准视图库接入平台"),
    EM_VSP_GAVI_PLATFORM_CODE_DAHUA(1, "DH视图库"),
    EM_VSP_GAVI_PLATFORM_CODE_HKTDA_CLOUDSTORAGE(2, "TDA+云存储"),
    EM_VSP_GAVI_PLATFORM_CODE_UNIVIEW(3, "宇视"),
    EM_VSP_GAVI_PLATFORM_CODE_HKTEST(4, "接口测试"),
    EM_VSP_GAVI_PLATFORM_CODE_SHQPNETPOSE(5, "上海清浦东方网力"),
    EM_VSP_GAVI_PLATFORM_CODE_HKTDA(6, "TDA"),
    EM_VSP_GAVI_PLATFORM_CODE_HARZONE(7, "华尊"),
    EM_VSP_GAVI_PLATFORM_CODE_NETPOSE(8, "东方网力"),
    EM_VSP_GAVI_PLATFORM_CODE_TAOAN(9, "韬安"),
    EM_VSP_GAVI_PLATFORM_CODE_RESERVED(10, "预留"),
    EM_VSP_GAVI_PLATFORM_CODE_HKVIEWGATEWAY(11, "视图网关"),
    EM_VSP_GAVI_PLATFORM_CODE_KODAK(12, "柯达");

	private int value;
	private String note;

	private EM_VSP_GAVI_PLATFORM_CODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SIGNLIGHT_TYPE enumType : EM_SIGNLIGHT_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SIGNLIGHT_TYPE enumType : EM_SIGNLIGHT_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

