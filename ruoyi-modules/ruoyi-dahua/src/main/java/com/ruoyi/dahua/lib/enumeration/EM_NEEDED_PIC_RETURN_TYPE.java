package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 47081
 * @version 1.0
 * @description 查询结果返回图片的格式
 * @date 2021/4/26
 */
public enum EM_NEEDED_PIC_RETURN_TYPE {
  /** 未知类型 */
    EM_NEEDED_PIC_TYPE_UNKOWN(0, "未知类型"),
  /** 返回图片HTTP链接 */
    EM_NEEDED_PIC_TYPE_HTTP_URL(1, "返回图片HTTP链接"),
  /** 返回图片二进制数据 */
    EM_NEEDED_PIC_TYPE_BINARY_DATA(2, "返回图片二进制数据"),
  /** 返回二进制和HTTP链接 */
    EM_NEEDED_PIC_TYPE_HTTP_AND_BINARY(3, "返回二进制和HTTP链接");

  private int type;
  private String desc;

  EM_NEEDED_PIC_RETURN_TYPE(int type, String desc) {
    this.type = type;
    this.desc = desc;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}

