package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_MAIN_PAGE_STATE;
import com.ruoyi.dahua.lib.enumeration.NET_EM_CFG_OPERATE_TYPE;

/**
 * 门禁配置结构体
 * 枚举见 {@link NET_EM_CFG_OPERATE_TYPE#NET_EM_CFG_BSCCUSTOM}
 *
 * @author 47040
 * @since Created in 2021/4/27 19:15
 */
public class NET_CFG_BSCCUSTOM extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 间隔时间.在 n 秒内,相同人员不能连续开门.(0 表示无需限制)
     */
    public int              nSamePersonInterval;
    /**
     * 枚举值 设备界面图片所表示的状态
     * {@link EM_MAIN_PAGE_STATE}
     */
    public int              emMainPageState;
    /**
     * 字节对齐
     */
    public byte[]           szReserved = new byte[4];

    public NET_CFG_BSCCUSTOM() {
        this.dwSize = this.size();
    }
}

