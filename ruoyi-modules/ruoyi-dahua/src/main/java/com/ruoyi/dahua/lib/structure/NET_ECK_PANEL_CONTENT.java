package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 显示内容
 * @origin autoTool
 * @date 2023/06/27 16:54:41
 */
public class NET_ECK_PANEL_CONTENT extends NetSDKLib.SdkStructure {
	/**
	 * 显示内容的类型,必选 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_ECK_PANEL_TYPE}
	 */
    public int              emType;
	/**
	 * 是否加粗，必选 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_ECK_PANEL_ISBOLD}
	 */
    public int              emBold;
	/**
	 * 显示颜色，必选 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_ECK_PANEL_COLOR}
	 */
    public int              emColor;
	/**
	 * 箭头指向，箭头专用,可选,Type为2时有效
	 * {@link com.ruoyi.dahua.lib.enumeration.EM_NET_ECK_PANEL_DIRECTION}
	 */
    public int              emDirection;
	/**
	 * 图标，图标专用，可选，Type为1时有效 {@link com.ruoyi.dahua.lib.enumeration.EM_NET_ECK_PANEL_ICON}
	 */
    public int              emIcon;
	/**
	 * 文本,当前只支持一个字符,限Unicode编码,字符限为:数字、字母、空格（不支持汉字），可选，Type为0时有效
	 */
    public short            nText;
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved = new byte[2];
	/**
	 * 是否使用szIcon字段下发
	 */
    public int              bUserIcon;
	/**
	 * 图标，图标专用 Type为1时有效
	 */
    public byte[]           szIcon = new byte[16];
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[1000];

	public NET_ECK_PANEL_CONTENT() {
	}
}

