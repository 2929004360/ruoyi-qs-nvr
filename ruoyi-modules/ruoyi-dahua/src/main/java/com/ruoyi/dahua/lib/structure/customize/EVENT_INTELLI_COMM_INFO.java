package com.ruoyi.dahua.lib.structure.customize;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.structure.POSTION;

/**
 * 智能报警事件公共信息
 * <p>
 * ERR210428143 重型云台 水面预警事件 专用结构体
 * <p>
 * 注意: 其他项目不要使用, 通用的停车检测事件结构体见:
 * {@link NetSDKLib.EVENT_INTELLI_COMM_INFO}
 *
 * @author 47040
 * @since Created in 2021/5/13 23:17
 */
public class EVENT_INTELLI_COMM_INFO extends NetSDKLib.SdkStructure {
    /**
     * 智能事件所属大类 取值为 {@link NetSDKLib.EM_CLASS_TYPE } 中的值
     */
    public int emClassType;
    /**
     * 该事件触发的预置点，对应该设置规则的预置点
     */
    public int nPresetID;
    /**
     * 位置信息  (ERR210428143 专用, 其他项目不要使用)
     */
    public POSTION stuPostion;
    /**
     * 保留字节,留待扩展.
     */
    public byte[] bReserved = new byte[112];
}
