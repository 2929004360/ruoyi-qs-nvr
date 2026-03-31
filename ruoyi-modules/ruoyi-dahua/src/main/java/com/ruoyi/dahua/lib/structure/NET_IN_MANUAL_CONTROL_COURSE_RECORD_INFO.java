package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * CLIENT_ManualControlCourseRecord 接口输入参数
*/
public class NET_IN_MANUAL_CONTROL_COURSE_RECORD_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 此结构体大小,必须赋值
    */
    public int              dwSize;
    /**
     * 预留对齐字节
    */
    public byte[]           szReserved = new byte[4];
    /**
     * 一键控制录播录像的信息,参见结构体定义 {@link NET_MANUAL_CONTROL_COURSE_RECORD_INFO}
    */
    public NET_MANUAL_CONTROL_COURSE_RECORD_INFO stuInfo = new NET_MANUAL_CONTROL_COURSE_RECORD_INFO();

    public NET_IN_MANUAL_CONTROL_COURSE_RECORD_INFO() {
        this.dwSize = this.size();
    }
}

