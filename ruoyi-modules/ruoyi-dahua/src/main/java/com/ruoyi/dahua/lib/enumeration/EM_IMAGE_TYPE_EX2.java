package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 图片类型
 *
 * @author ： 260611
 * @since ： Created in 2021/10/19 20:21
 */
public class EM_IMAGE_TYPE_EX2 extends NetSDKLib.SdkStructure {
	/**
	 *  未知
	 */
    public static final int   EM_IMAGE_TYPE_UNKNOWN = 0;
	/**
	 *  全景广角度
	 */
    public static final int   EM_IMAGE_TYPE_SCENE_IMAGE = 1;
	/**
	 *  大图
	 */
    public static final int   EM_IMAGE_TYPE_GLOBAL_SCENE = 2;
	/**
	 *  大图（全景图）的缩略图
	 */
    public static final int   EM_IMAGE_TYPE_THUM_IMAGE = 3;
	/**
	 *  人脸全景图
	 */
    public static final int   EM_IMAGE_TYPE_FACE_SCENE_IMAGE = 4;
	/**
	 *  人脸图
	 */
    public static final int   EM_IMAGE_TYPE_FACE_IMAGE = 5;
	/**
	 *  人体图
	 */
    public static final int   EM_IMAGE_TYPE_HUMAN_IMAGE = 6;
	/**
	 *  与最优人脸同画面的人体图
	 */
    public static final int   EM_IMAGE_TYPE_ALONG_WITH_FACE_HUMAN_IMAGE = 7;
	/**
	 *  与最优人脸同画面人体的全景图
	 */
    public static final int   EM_IMAGE_TYPE_ALONG_WITH_FACE_HUMAN_SCENE_IMAGE = 8;
	/**
	 *  车位抠图
	 */
    public static final int   EM_IMAGE_TYPE_PARKING_IMAGE = 9;
	/**
	 *  车身特写抠图
	 */
    public static final int   EM_IMAGE_TYPE_BINARIZED_PLATE = 10;
	/**
	 *  格口抠图
	 */
    public static final int   EM_IMAGE_TYPE_DEPOSIT_IMAGE_INFO = 11;
	/**
	 *  普通图，图片名称未定义情况使用
	 */
    public static final int   EM_IMAGE_TYPE_IMAGE_INFO = 12;
}

