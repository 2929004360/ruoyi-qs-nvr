package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 物体动作支持类型
 * @date 2022/06/16 11:14:19
 */
public enum EM_ACTION {
	/**
	 * 未知类型
	 */
    EM_ACTION_UNKNOWN(0, "未知类型"),
	/**
	 * 第一次出现在检测区域中，或者跟踪时物理分离动作中尚未确认的新物体
	 */
    EM_ACTION_APPEAR(1, "第一次出现在检测区域中，或者跟踪时物理分离动作中尚未确认的新物体"),
	/**
	 * 正在运动，物体被正常跟踪
	 */
    EM_ACTION_MOVE(2, "正在运动，物体被正常跟踪"),
	/**
	 * 物体停止运动，这个物体不会在出现在下一帧物体列表中，物体开始移动后再恢复在列表中
	 */
    EM_ACTION_STAY(3, "物体停止运动，这个物体不会在出现在下一帧物体列表中，物体开始移动后再恢复在列表中"),
	/**
	 * 物体从原来的区域移除，或者被遮盖，或者跟踪失败，移除的物体ID不会被自动删除，并且物体重现出现是有可能再次被使用
	 */
    EM_ACTION_REMOVE(4, "物体从原来的区域移除，或者被遮盖，或者跟踪失败，移除的物体ID不会被自动删除，并且物体重现出现是有可能再次被使用"),
	/**
	 * 运动到跟踪区域之外，或者达到了算法跟踪物体的上限而被清除，消失的物体ID将不再出现
	 */
    EM_ACTION_DISAPPEAR(5, "运动到跟踪区域之外，或者达到了算法跟踪物体的上限而被清除，消失的物体ID将不再出现"),
	/**
	 * 从其他物体中分离出来，可以用来检测物体遗留，关联ID表示从这个ID对应物体分离
	 */
    EM_ACTION_SPLIT(6, "从其他物体中分离出来，可以用来检测物体遗留，关联ID表示从这个ID对应物体分离"),
	/**
	 * 合并到其他物体，可以用来检查物体保全，关联ID表示合并到这个ID对相应的物体
	 */
    EM_ACTION_MERGE(7, "合并到其他物体，可以用来检查物体保全，关联ID表示合并到这个ID对相应的物体"),
	/**
	 * 如果算法无法确定分离动作中某个物体是原先同一个物体，应该创建先一个新的物体，证据充分后再重命名为旧的物体ID，关联ID表示暂时使用的新的ID。
	 */
    EM_ACTION_RENAME(8, "如果算法无法确定分离动作中某个物体是原先同一个物体，应该创建先一个新的物体，证据充分后再重命名为旧的物体ID，关联ID表示暂时使用的新的ID。");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_ACTION(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_ACTION enumType : EM_ACTION.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ACTION enumType : EM_ACTION.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

