package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 失败的原因
 * @date 2021/11/07
 */
public enum EM_RECORD_BACKUP_FAIL_REASON {
	/**
	 未知
	 */
    EM_RECORD_BACKUP_FAIL_REASON_UNKNOW(0, "未知"),
	/**
	 登入失败
	 */
    EM_RECORD_BACKUP_FAIL_REASON_LOGIN_FAIL(1, "登入失败"),
	/**
	 没有查询到录像
	 */
    EM_RECORD_BACKUP_FAIL_REASON_NO_FILES(2, "没有查询到录像"),
	/**
	 下载过程中网络断开
	 */
    EM_RECORD_BACKUP_FAIL_REASON_DOWNLOAD_NET_ERROR(3, "下载过程中网络断开"),
	/**
	 未知错误
	 */
    EM_RECORD_BACKUP_FAIL_REASON_UNKNOW_ERROR(4, "未知错误"),
	/**
	 用户名无效
	 */
    EM_RECORD_BACKUP_FAIL_REASON_USER_NAME_NOT_VALID(5, "用户名无效"),
	/**
	 密码错误
	 */
    EM_RECORD_BACKUP_FAIL_REASON_PASSWORD_NOT_VALID(6, "密码错误"),
	/**
	 地址被屏蔽
	 */
    EM_RECORD_BACKUP_FAIL_REASON_PEER_HAS_BEEN_BLOCKED(7, "地址被屏蔽"),
	/**
	 地址在禁止访问名单内
	 */
    EM_RECORD_BACKUP_FAIL_REASON_PEER_IN_BLOCK_LIST(8, "地址在禁止访问名单内"),
	/**
	 超出最大连接数
	 */
    EM_RECORD_BACKUP_FAIL_REASON_TOO_MANY_CONNECTIONS(9, "超出最大连接数"),
	/**
	 账号被锁定
	 */
    EM_RECORD_BACKUP_FAIL_REASON_ACCOUNT_HAS_BEEN_BLOCKED(10, "账号被锁定"),
	/**
	 登入时网络连接失败
	 */
    EM_RECORD_BACKUP_FAIL_REASON_LOGIN_CONNECT_FAILED(11, "登入时网络连接失败"),
	/**
	 登入用户名或者密码错误
	 */
    EM_RECORD_BACKUP_FAIL_REASON_USER_OR_PWD_NOT_VALID(12, "登入用户名或者密码错误"),
	/**
	 登入超时
	 */
    EM_RECORD_BACKUP_FAIL_REASON_LOGIN_CONNECT_TIMEOUT(13, "登入超时");

	private int value;
	private String note;

	private EM_RECORD_BACKUP_FAIL_REASON(int givenValue, String note) {
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
		for (EM_RECORD_BACKUP_FAIL_REASON enumType : EM_RECORD_BACKUP_FAIL_REASON.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_RECORD_BACKUP_FAIL_REASON enumType : EM_RECORD_BACKUP_FAIL_REASON.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

