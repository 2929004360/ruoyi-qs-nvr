package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.NET_EM_CFG_OPERATE_TYPE;

/**
 * @author 47081
 * @version 1.0
 * @description 健康码使能,
 * 对应枚举类型{@link NET_EM_CFG_OPERATE_TYPE#NET_EM_CFG_HEALTH_CODE}
 * @date 2020/9/10
 */
public class NET_CFG_HEALTH_CODE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 健康码使能，1：使能；0：未使能
	 */
    public int              bEnable;
	/**
	 * 健康码离线使能，1：使能；0：未使能
	 */
    public int              bOfflineEnable;
	/**
	 * 核验健康码功能使能
	 */
    public int              bCheckHealthCode;
	/**
	 * 核酸显示使能
	 */
    public int              bHSVisible;
	/**
	 * 核验核酸功能使能
	 */
    public int              bHSCheckEnable;
	/**
	 * 核酸结果超时时间(小时) 0 默认不处理
	 */
    public int              nHSTimeout;
	/**
	 * 核酸采集超时时间(小时) 0 默认不处理
	 */
    public int              nHSCollectTimeout;
	/**
	 * 疫苗显示使能
	 */
    public int              bVaccineVisible;
	/**
	 * 疫苗校验使能
	 */
    public int              bVaccineCheckEnable;
	/**
	 * 行程码显示使能
	 */
    public int              bTravelCodeVisible;
	/**
	 * 行程码校验使能
	 */
    public int              bTravelCodeCheck;
	/**
	 * 加强针状态显示
	 */
    public int              bVaccineIntensifyVisible;
	/**
	 * 健康码鉴权方式组合 0:无组合方式 1:健康码单独存在 2:与现有鉴权方式组合，健康码鉴权优先
	 */
    public int              nOFFLineGreenCodeMethod;
	/**
	 * 二维码过期时间(秒)
	 */
    public int              nCodeFlushTimeout;
	/**
	 * 健康码请求超时时间(秒); 默认2秒
	 */
    public int              nHealthQueryTimeout;
	/**
	 * 红码禁止通行使能
	 */
    public int              bRedPermit;
	/**
	 * 黄码禁止通行使能
	 */
    public int              bYellowPermit;
	/**
	 * 健康码请求超时禁止通行使能
	 */
    public int              bQueryTimeoutPermit;

	public NET_CFG_HEALTH_CODE_INFO() {
		this.dwSize = this.size();
	}
}

