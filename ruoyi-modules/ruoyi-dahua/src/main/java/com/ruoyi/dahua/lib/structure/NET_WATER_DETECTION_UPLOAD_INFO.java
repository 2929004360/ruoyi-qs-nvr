package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  水质检测上报数据信息 
* @date 2022/08/22 16:51:44
*/
public class NET_WATER_DETECTION_UPLOAD_INFO extends NetSDKLib.SdkStructure {
/** 
PH值 范围(1-14)
*/
    public			float          fPH;
/** 
浊度值 范围(0-500NTU)
*/
    public			float          fNTU;
/** 
氨氮值 范围(0-50mg/l)
*/
    public			float          fNH3_N;
/** 
总氮值 范围(0-50mg/l)
*/
    public			float          fTN;
/** 
透明度值 范围(0-30 m)
*/
    public			float          fSD;
/** 
化学需氧量 范围(0-100mg/l)
*/
    public			float          fCOD;
/** 
亚硝酸盐氮 范围(0-500mg/l)
*/
    public			float          fNN;
/** 
溶解氧 范围(0-10 mg/l)
*/
    public			float          fDO;
/** 
叶绿素a 范围(0-300 ug/l)
*/
    public			float          fChl_a;
/** 
总磷 范围0-5mg/L
*/
    public			float          fTP;
/** 
高锰酸盐指数范围(0-100mg/l)
*/
    public			float          fCODMn;
/** 
悬浮物 范围(0-1000mg/l)
*/
    public			float          fSS;
/** 
五日生化需氧量 范围(0-50mg/l)
*/
    public			float          fBOD_5;
/** 
硝酸盐 范围(0-500mg/l)
*/
    public			float          fNO3_N;
/** 
富营养状况指数 范围无
*/
    public			float          fTSI;
/** 
黑臭等级 {@link com.ruoyi.dahua.lib.enumeration.EM_SMELLY_LEVEL}
*/
    public			int            emSmellyLevel;
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[512];

public NET_WATER_DETECTION_UPLOAD_INFO(){
}

    @Override
    public String toString() {
        return "NET_WATER_DETECTION_UPLOAD_INFO{" +
                "fPH=" + fPH +
                ", fNTU=" + fNTU +
                ", fNH3_N=" + fNH3_N +
                ", fTN=" + fTN +
                ", fSD=" + fSD +
                ", fCOD=" + fCOD +
                ", fNN=" + fNN +
                ", fDO=" + fDO +
                ", fChl_a=" + fChl_a +
                ", fTP=" + fTP +
                ", fCODMn=" + fCODMn +
                ", fSS=" + fSS +
                ", fBOD_5=" + fBOD_5 +
                ", fNO3_N=" + fNO3_N +
                ", fTSI=" + fTSI +
                ", emSmellyLevel=" + emSmellyLevel +
                '}';
    }
}

