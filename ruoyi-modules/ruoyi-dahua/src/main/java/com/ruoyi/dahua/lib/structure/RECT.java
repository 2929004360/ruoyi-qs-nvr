package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.NativeLong;

/**
 * @author 251823
 * @description
 * @date 2022/06/17 11:14:34
 */
public class RECT extends NetSDKLib.SdkStructure {
    public NativeLong       left;
    public NativeLong       top;
    public NativeLong       right;
    public NativeLong       bottom;

	public RECT() {
	}
}

