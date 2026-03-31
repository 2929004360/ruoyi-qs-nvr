package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description CLIENT_OperateUserInfoNew接口参数 nOperateType 对应的值含义
 * @date 2023/03/10 15:08:40
 */
public enum EM_OPERATE_USERINFO_TYPE {
	/**
	 * 增加用户,opParam对应结构体 USER_INFO_NEW
	 */
    EM_OPERATE_USERINFO_TYPE_ADD_USER(3, "增加用户"),
	/**
	 * 删除用户,opParam对应结构体 USER_INFO_NEW
	 */
    EM_OPERATE_USERINFO_TYPE_DEL_USER(4, "删除用户"),
	/**
	 * 修改用户,opParam为修改后的用户信息 对应结构体 USER_INFO_NEW, subParam为原始用户信息 对应结构体
	 * USER_INFO_NEW
	 */
    EM_OPERATE_USERINFO_TYPE_MODIFY_USER(5, "修改用户"),
	/**
	 * 修改密码,opParam为修改后的密码 对应结构体 USER_INFO_NEW, subParam为原始密码 对应结构体 USER_INFO_NEW
	 */
    EM_OPERATE_USERINFO_TYPE_MODIFY_PWD(6, "修改密码"),
	/**
	 * 增加用户组,opParam对应结构体 USER_GROUP_INFO_EX2
	 */
    EM_OPERATE_USERINFO_TYPE_ADD_GROUP(7, "增加用户组"),
	/**
	 * 删除用户组,opParam对应结构体 USER_GROUP_INFO_EX2
	 */
    EM_OPERATE_USERINFO_TYPE_DEL_GROUP(8, "删除用户组"),
	/**
	 * 修改用户组,opParam为修改后的用户组信息 对应结构体 USER_GROUP_INFO_EX2,subParam为原始用户组信息 对应结构体
	 * USER_GROUP_INFO_EX2
	 */
    EM_OPERATE_USERINFO_TYPE_MODIFY_GROUP(9, "修改用户组");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_OPERATE_USERINFO_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_OPERATE_USERINFO_TYPE enumType : EM_OPERATE_USERINFO_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_OPERATE_USERINFO_TYPE enumType : EM_OPERATE_USERINFO_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

