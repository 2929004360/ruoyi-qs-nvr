package com.ruoyi.qs.common;

import lombok.Data;

/**
 * 国标编码对象
 */
@Data
public class GbCode {

    /**
     * 中心编码,由监控中心所在地的行政区划代码确定,符合GB/T2260—2007的要求
     */
    private String centerCode;

    /**
     * 行业编码
     */
    private String industryCode;

    /**
     * 类型编码
     */
    private String typeCode;

    /**
     * 网络标识
     */
    private String netCode;

    /**
     * 序号
     */
    private String sn;

    /**
     * 解析国标编号
     */
    public static GbCode decode(String code) {
        if (code == null || code.trim().length() != 20) {
            return null;
        }
        code = code.trim();
        GbCode gbCode = new GbCode();
        gbCode.setCenterCode(code.substring(0, 8));
        gbCode.setIndustryCode(code.substring(8, 10));
        gbCode.setTypeCode(code.substring(10, 13));
        gbCode.setNetCode(code.substring(13, 14));
        gbCode.setSn(code.substring(14));
        return gbCode;
    }

    public String ecode() {
        return centerCode + industryCode + typeCode + netCode + sn;
    }
}
