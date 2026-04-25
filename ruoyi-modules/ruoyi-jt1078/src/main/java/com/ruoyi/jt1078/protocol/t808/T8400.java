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
@Message(JT808.电话回拨)
public class T8400 extends JTMessage {

    /** 通话 */
    public static final int Normal = 0;
    /** 监听 */
    public static final int Listen = 1;

    @Field(length = 1, desc = "类型：0.通话 1.监听")
    private int type;
    @Field(length = 20, desc = "电话号码")
    private String phoneNumber;

}