package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;

public class NET_SCREEN_SHOW_INFO extends SdkStructure {
	/**
	 * 屏幕编号
	 */
    public int              nScreenNo;
	/**
	 *  显示文本(文本类型为EM_SCREEN_TEXT_TYPE_LOCAL_TIME时的时间格式,
	 *         %Y 年
	 *	       %M 月
	 *         %D 日
	 *         %H 时(24小时机制)
	 *			%h 时(12小时)
	 *			%m 分
	 *			%S 秒
	 *			%W 星期
	 *			%T 显示上午或下午
	 *			%X 表示显示普通文本内容
	 */
    public byte[]           szText = new byte[256];
	/**
	 * 文本类型EM_SCREEN_TEXT_TYPE
	 */
    public int              emTextType;
	/**
	 * 文本颜色EM_SCREEN_TEXT_COLOR
	 */
    public int              emTextColor;
	/**
	 * 文本滚动模式EM_SCREEN_TEXT_ROLL_MODE
	 */
    public int              emTextRollMode;
	/**
	 * 文本滚动速度由慢到快分为1~5
	 */
    public int              nRollSpeed;
    /**
     * 显示效果, 当emTextRollMode为EM_SCREEN_TEXT_ROLL_MODE_NO/EM_SCREEN_TEXT_ROLL_MODE_LEFT_RIGHT时, 取值为0：左移, 1：右移, 2、上移, 3、下移; ,  当emTextRollMode为EM_SCREEN_TEXT_ROLL_MODE_UP_DOWN时, 取值为0：上翻, 1：下翻;,  当emTextRollMode为EM_SCREEN_TEXT_ROLL_MODE_CUT_OUT时, 取值为0：左对齐, 1：居中, 2：右对齐
    */
    public int              nDisplayEffect;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[248];
}

