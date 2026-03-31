 package com.ruoyi.dahua.lib.structure;

 import com.ruoyi.dahua.lib.NetSDKLib;
 import com.sun.jna.Pointer;

 /**
  * @author 291189
  * @version 1.0
  * @description CLIENT_NET_API BOOL CALL_METHOD CLIENT_UploadUnpackingCheckInfo 入参
  * @date 2021/9/9
  */
public class NET_IN_UPLOAD_UPPACKING_CHECK_INFO extends NetSDKLib.SdkStructure {
/** 结构体大小*/
    public			int            dwSize;
/**  图片二进制数据缓存大小*/
    public			int            nImageDataLen;
/** 图片二进制数据块*/
    public Pointer          pImageData;
/** 开包检查信息*/
    public			UNPACKING_CHECK_INFO stuCheckInfo = new UNPACKING_CHECK_INFO();

public NET_IN_UPLOAD_UPPACKING_CHECK_INFO(){
    this.dwSize=this.size();
}
}

