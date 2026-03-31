 package com.ruoyi.dahua.lib.structure;
 import com.ruoyi.dahua.lib.NetSDKLib;

 /**
  * @author 291189
  * @version 1.0
  * @description CLIENT_NET_API BOOL CALL_METHOD CLIENT_UploadUnpackingCheckInfo 出参
  * @date 2021/9/9
  */

public class NET_OUT_UPLOAD_UPPACKING_CHECK_INFO extends NetSDKLib.SdkStructure {
/** 结构体大小*/
    public			int            dwSize;

 public NET_OUT_UPLOAD_UPPACKING_CHECK_INFO(){
  this.dwSize=this.size();
 }
}

