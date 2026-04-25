package com.ruoyi.jt1078.protocol.t808;

import com.ruoyi.jt1078.protocol.basics.JTMessage;
import com.ruoyi.jt1078.protocol.commons.JT808;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.终端鉴权)
public class T0102 extends JTMessage {

    /** 终端重连后上报鉴权码 */
    @Field(desc = "鉴权码", version = {-1, 0})
    @Field(lengthUnit = 1, desc = "鉴权码", version = 1)
    private String token;
    @Field(length = 15, desc = "终端IMEI", version = 1)
    private String imei;
    @Field(length = 20, desc = "软件版本号", version = 1)
    private String softwareVersion;

}