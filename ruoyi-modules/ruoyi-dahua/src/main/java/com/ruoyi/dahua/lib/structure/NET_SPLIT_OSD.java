package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 分割窗口OSD信息
 * @date 2022/06/22 09:56:21
 */
public class NET_SPLIT_OSD extends NetSDKLib.SdkStructure {
    public int              dwSize;
	/**
	 * 使能
	 */
    public int              bEnable;
	/**
	 * 前景颜色
	 */
    public NET_COLOR_RGBA   stuFrontColor = new NET_COLOR_RGBA();
	/**
	 * 背景颜色
	 */
    public NET_COLOR_RGBA   stuBackColor = new NET_COLOR_RGBA();
	/**
	 * 前景区域
	 */
    public NetSDKLib.DH_RECT stuFrontRect = new NetSDKLib.DH_RECT();
	/**
	 * 背景区域
	 */
    public NetSDKLib.DH_RECT stuBackRect = new NetSDKLib.DH_RECT();
	/**
	 * 是否滚动显示, 只对文本有效
	 */
    public int              bRoll;
	/**
	 * 滚动模式, 只对文本有效, 0-从左往右, 1-从右往左, 2-从上往下滚动, 3-从下往上滚动
	 */
    public byte             byRollMode;
	/**
	 * 滚动速率, 只对文本有效, 0~4, 数值越大滚动越快
	 */
    public byte             byRoolSpeed;
	/**
	 * 字体大小, 只对文本有效
	 */
    public byte             byFontSize;
	/**
	 * 对齐方式, 0-靠左, 1-居中, 2-靠右
	 */
    public byte             byTextAlign;
	/**
	 * OSD类型, 0-文本, 1-图标, 2-时间, 3-星期， 4-日期， 5-日期+星期, 6-日期+时间, 7-星期+时间, 8-日期+星期+时间
	 */
    public byte             byType;
	/**
	 * 保留字节
	 */
    public byte[]           Reserved = new byte[3];
	/**
	 * OSD内容 若类型为图标, 内容为图标名称
	 * 若类型为Time，内容为”Date”(日期),”Week”(星期),”Time”(时间),”\n”(换行)的自由组合。
	 * 例如：不同行显示，需要将此字段填为"Date\nTime",在屏幕上就会显示 2018年4月23日 16:49:15
	 * 同行显示，需要将此字段填为"DateTime",在屏幕上就会显示 2018年4月23日16:49:15
	 */
    public byte[]           szContent = new byte[260];
	/**
	 * null
	 */
    public float            fPitch;
	/**
	 * 字体类型
	 */
    public byte[]           szFontType = new byte[64];
	/**
	 * 文本显示模式 Row:横排(默认) Column:竖排
	 */
    public byte[]           szPattern = new byte[8];
	/**
	 * OSD内容
	 */
    public byte[]           szContentEx = new byte[1024];

	public NET_SPLIT_OSD() {
		this.dwSize = this.size();
	}
}

