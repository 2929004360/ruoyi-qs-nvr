package com.ruoyi.dahua.lib.enumeration;


import com.ruoyi.dahua.lib.structure.NET_IN_SPLIT_SET_HIGHLIGHT;
import com.ruoyi.dahua.lib.structure.NET_OUT_SPLIT_SET_HIGHLIGHT;

/**
 * @author 260611
 * @description 视频分割操作类型
 * @date 2022/06/22 09:56:21
 */
public enum NET_SPLIT_OPERATE_TYPE {
    /**
     * 设置背景图片, 对应 NET_IN_SPLIT_SET_BACKGROUND 和 NET_OUT_SPLIT_SET_BACKBROUND
     */
    NET_SPLIT_OPERATE_SET_BACKGROUND(0, "设置背景图片, 对应 NET_IN_SPLIT_SET_BACKGROUND 和 NET_OUT_SPLIT_SET_BACKBROUND"),
    /**
     * 获取背景图片, 对应 NET_IN_SPLIT_GET_BACKGROUND 和 NET_OUT_SPLIT_GET_BACKGROUND
     */
    NET_SPLIT_OPERATE_GET_BACKGROUND(1, "获取背景图片, 对应 NET_IN_SPLIT_GET_BACKGROUND 和 NET_OUT_SPLIT_GET_BACKGROUND"),
    /**
     * 设置预拉流源, 对应 NET_IN_SPLIT_SET_PREPULLSRC 和 NET_OUT_SPLIT_SET_PREPULLSRC
     */
    NET_SPLIT_OPERATE_SET_PREPULLSRC(2, "设置预拉流源, 对应 NET_IN_SPLIT_SET_PREPULLSRC 和 NET_OUT_SPLIT_SET_PREPULLSRC"),
    /**
     * 设置源边框高亮使能开关, 对应 {@link NET_IN_SPLIT_SET_HIGHLIGHT} 和 {@link NET_OUT_SPLIT_SET_HIGHLIGHT}
     */
    NET_SPLIT_OPERATE_SET_HIGHLIGHT(3, "设置源边框高亮使能开关, 对应 NET_IN_SPLIT_SET_HIGHLIGHT 和 NET_OUT_SPLIT_SET_HIGHLIGHT"),
    /**
     * 调整窗口Z序, 对应 NET_IN_SPLIT_SET_ZORDER 和 NET_OUT_SPLIT_SET_ZORDER
     */
    NET_SPLIT_OPERATE_SET_ZORDER(4, "调整窗口Z序, 对应 NET_IN_SPLIT_SET_ZORDER 和 NET_OUT_SPLIT_SET_ZORDER"),
    /**
     * 窗口轮巡控制, 对应 NET_IN_SPLIT_SET_TOUR 和 NET_OUT_SPLIT_SET_TOUR
     */
    NET_SPLIT_OPERATE_SET_TOUR(5, "窗口轮巡控制, 对应 NET_IN_SPLIT_SET_TOUR 和 NET_OUT_SPLIT_SET_TOUR"),
    /**
     * 获取窗口轮巡状态, 对应 NET_IN_SPLIT_GET_TOUR_STATUS 和 NET_OUT_SPLIT_GET_TOUR_STATUS
     */
    NET_SPLIT_OPERATE_GET_TOUR_STATUS(6, "获取窗口轮巡状态, 对应 NET_IN_SPLIT_GET_TOUR_STATUS 和 NET_OUT_SPLIT_GET_TOUR_STATUS"),
    /**
     * 获取屏内窗口信息, 对应 NET_IN_SPLIT_GET_SCENE 和 NET_OUT_SPLIT_GET_SCENE
     */
    NET_SPLIT_OPERATE_GET_SCENE(7, "获取屏内窗口信息, 对应 NET_IN_SPLIT_GET_SCENE 和 NET_OUT_SPLIT_GET_SCENE"),
    /**
     * 批量开窗, 对应 NET_IN_SPLIT_OPEN_WINDOWS 和 NET_OUT_SPLIT_OPEN_WINDOWS
     */
    NET_SPLIT_OPERATE_OPEN_WINDOWS(8, "批量开窗, 对应 NET_IN_SPLIT_OPEN_WINDOWS 和 NET_OUT_SPLIT_OPEN_WINDOWS"),
    /**
     * 设置工作模式, 对应 NET_IN_SPLIT_SET_WORK_MODE 和 NET_OUT_SPLIT_SET_WORK_MODE
     */
    NET_SPLIT_OPERATE_SET_WORK_MODE(9, "设置工作模式, 对应 NET_IN_SPLIT_SET_WORK_MODE 和 NET_OUT_SPLIT_SET_WORK_MODE"),
    /**
     * 获取播放器实例,对应 NET_IN_SPLIT_GET_PLAYER 和 NET_OUT_SPLIT_GET_PLAYER
     */
    NET_SPLIT_OPERATE_GET_PLAYER(10, "获取播放器实例,对应 NET_IN_SPLIT_GET_PLAYER 和 NET_OUT_SPLIT_GET_PLAYER"),
    /**
     * 设置窗口工作模式,对应 NET_IN_WM_SET_WORK_MODE 和 NET_OUT_WM_SET_WORK_MODE
     */
    NET_WM_OPERATE_SET_WORK_MODE(11, "设置窗口工作模式,对应 NET_IN_WM_SET_WORK_MODE 和 NET_OUT_WM_SET_WORK_MODE"),
    /**
     * 获取窗口工作模式,对应 NET_IN_WM_GET_WORK_MODE 和 NET_OUT_WM_GET_WORK_MODE
     */
    NET_WM_OPERATE_GET_WORK_MODE(12, "获取窗口工作模式,对应 NET_IN_WM_GET_WORK_MODE 和 NET_OUT_WM_GET_WORK_MODE"),
    /**
     * 批量关窗, 对应 NET_IN_SPLIT_CLOSE_WINDOWS 和 NET_OUT_SPLIT_CLOSE_WINDOWS
     */
    NET_SPLIT_OPERATE_CLOSE_WINDOWS(13, "批量关窗, 对应 NET_IN_SPLIT_CLOSE_WINDOWS 和 NET_OUT_SPLIT_CLOSE_WINDOWS"),
    /**
     * 设置输出屏的鱼眼矫正规则 , 对应NET_IN_WM_SET_FISH_EYE_PARAM 和 NET_OUT_WM_SET_FISH_EYE_PARAM
     */
    NET_WM_OPERATE_SET_FISH_EYE_PARAM(14, "设置输出屏的鱼眼矫正规则 , 对应NET_IN_WM_SET_FISH_EYE_PARAM 和 NET_OUT_WM_SET_FISH_EYE_PARAM"),
    /**
     * 设置窗口走廊模式，对应NET_IN_WM_SET_CORRIDOR_MODE和NET_OUT_WM_SET_CORRIDOR_MODE
     */
    NET_WM_OPERATE_SET_CORRIDOR_MODE(15, "设置窗口走廊模式，对应NET_IN_WM_SET_CORRIDOR_MODE和NET_OUT_WM_SET_CORRIDOR_MODE"),
    /**
     * 获取窗口走廊模式，对应NET_IN_WM_GET_CORRIDOR_MODE和NET_OUT_WM_GET_CORRIDOR_MODE
     */
    NET_WM_OPERATE_GET_CORRIDOR_MODE(16, "获取窗口走廊模式，对应NET_IN_WM_GET_CORRIDOR_MODE和NET_OUT_WM_GET_CORRIDOR_MODE"),
    /**
     * 设置显示音量柱使能模式，对应NET_IN_WM_SET_VOLUME_COLUMN和NET_OUT_WM_SET_VOLUME_COLUMN
     */
    NET_WM_OPERATE_SET_VOLUME_COLUMN(17, "设置显示音量柱使能模式，对应NET_IN_WM_SET_VOLUME_COLUMN和NET_OUT_WM_SET_VOLUME_COLUMN"),
    /**
     * 获取显示音量柱使能模式，对应NET_IN_WM_GET_VOLUME_COLUMN和NET_OUT_WM_GET_VOLUME_COLUMN
     */
    NET_WM_OPERATE_GET_VOLUME_COLUMN(18, "获取显示音量柱使能模式，对应NET_IN_WM_GET_VOLUME_COLUMN和NET_OUT_WM_GET_VOLUME_COLUMN"),
    /**
     * 设置窗口背景图片，对应NET_IN_WM_SET_BACKGROUND和NET_OUT_WM_SET_BACKGROUND
     */
    NET_WM_OPERATE_SET_BACKGROUND(19, "设置窗口背景图片，对应NET_IN_WM_SET_BACKGROUND和NET_OUT_WM_SET_BACKGROUND"),
    /**
     * 获取窗口背景图片，对应NET_IN_WM_GET_BACKGROUND和NET_OUT_WM_GET_BACKGROUND
     */
    NET_WM_OPERATE_GET_BACKGROUND(20, "获取窗口背景图片，对应NET_IN_WM_GET_BACKGROUND和NET_OUT_WM_GET_BACKGROUND"),
    /**
     * 设置图像画面旋转模式，对应NET_IN_WM_SET_ROTATE_MODE和NET_OUT_WM_SET_ROTATE_MODE
     */
    NET_WM_OPERATE_SET_ROTATE_MODE(21, "设置图像画面旋转模式，对应NET_IN_WM_SET_ROTATE_MODE和NET_OUT_WM_SET_ROTATE_MODE"),
    /**
     * 设置视频源显示区域的模式，对应NET_IN_WM_SET_MEETING_MODE和NET_OUT_WM_SET_MEETING_MODE
     */
    NET_WM_OPERATE_SET_MEETING_MODE(22, "设置视频源显示区域的模式，对应NET_IN_WM_SET_MEETING_MODE和NET_OUT_WM_SET_MEETING_MODE"),
    /**
     * 设置锁定窗口当前位置，对应NET_IN_WM_SET_LOCK_POSITION和NET_OUT_WM_SET_LOCK_POSITION
     */
    NET_WM_OPERATE_SET_LOCK_POSITION(23, "设置锁定窗口当前位置，对应NET_IN_WM_SET_LOCK_POSITION和NET_OUT_WM_SET_LOCK_POSITION"),
    /**
     * 修改预案控制ID，对应NET_IN_WM_SET_COLLECTION_CTRL_ID和NET_OUT_WM_SET_COLLECTION_CTRL_ID
     */
    NET_WM_OPERATE_SET_COLLECTION_CTRL_ID(24, "修改预案控制ID，对应NET_IN_WM_SET_COLLECTION_CTRL_ID和NET_OUT_WM_SET_COLLECTION_CTRL_ID"),
    /**
     * 设置信号源属性，对应NET_IN_SPLIT_SET_SOURCE_ATTRIBUTE和NET_OUT_SPLIT_SET_SOURCE_ATTRIBUTE
     */
    NET_SPLIT_OPERATES_SET_SOURCE_ATTRIBUTE(25, "设置信号源属性，对应NET_IN_SPLIT_SET_SOURCE_ATTRIBUTE和NET_OUT_SPLIT_SET_SOURCE_ATTRIBUTE"),
    /**
     * 设置窗口粘附状态，对应NET_IN_SPLIT_SET_DOCK_CONTROL和NET_OUT_SPLIT_SET_DOCK_CONTROL
     */
    NET_SPLIT_OPERATES_SET_DOCK_CONTROL(26, "设置窗口粘附状态，对应NET_IN_SPLIT_SET_DOCK_CONTROL和NET_OUT_SPLIT_SET_DOCK_CONTROL"),
    /**
     * 设置窗口分割模式，对应NET_IN_WM_SET_MODE和NET_OUT_WM_SET_MODE
     */
    NET_WM_OPERATE_SET_MODE(27, "设置窗口分割模式，对应NET_IN_WM_SET_MODE和NET_OUT_WM_SET_MODE");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_SPLIT_OPERATE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_SPLIT_OPERATE_TYPE enumType : NET_SPLIT_OPERATE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_SPLIT_OPERATE_TYPE enumType : NET_SPLIT_OPERATE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_SPLIT_OPERATE_TYPE getEnum(int value) {
        for (NET_SPLIT_OPERATE_TYPE e : NET_SPLIT_OPERATE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_SPLIT_OPERATE_TYPE.NET_SPLIT_OPERATE_SET_BACKGROUND;
    }
}

