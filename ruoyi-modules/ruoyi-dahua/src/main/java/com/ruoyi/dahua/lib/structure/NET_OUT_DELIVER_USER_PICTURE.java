package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * className：NET_OUT_DELIVER_USER_PICTURE
 * description： 下发人脸数据 <—— 出参
 * author：251589
 * createTime：2020/12/22 22:01
 *
 * @version v1.0
 */
public class NET_OUT_DELIVER_USER_PICTURE extends NetSDKLib.SdkStructure {
    public 	int             dwSize;                               // 结构体大小

    public NET_OUT_DELIVER_USER_PICTURE() {
        dwSize = this.size();
    }
}

