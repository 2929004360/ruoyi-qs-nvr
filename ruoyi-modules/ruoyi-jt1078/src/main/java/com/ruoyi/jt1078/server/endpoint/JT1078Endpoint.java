package com.ruoyi.jt1078.server.endpoint;

import com.ruoyi.jt1078.protocol.t1078.T1003;
import com.ruoyi.jt1078.protocol.t1078.T1005;
import com.ruoyi.jt1078.protocol.t1078.T1205;
import com.ruoyi.jt1078.protocol.t1078.T1206;
import com.ruoyi.jt1078.server.service.IRedisCatchStorage;
import io.github.yezhihao.netmc.core.annotation.Endpoint;
import io.github.yezhihao.netmc.core.annotation.Mapping;
import io.github.yezhihao.netmc.session.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.ruoyi.jt1078.protocol.commons.JT1078.*;

@Endpoint
@Component
@RequiredArgsConstructor
public class JT1078Endpoint {

    private final IRedisCatchStorage redisCatchStorage;

    @Mapping(types = 终端上传音视频资源列表, desc = "终端上传音视频资源列表")
    public void T1205(T1205 message, Session session) {
        String clientId = session.getClientId();
        redisCatchStorage.setRecordList(clientId, message);
        session.response(message);
    }

    @Mapping(types = 终端上传音视频属性, desc = "终端上传音视频属性")
    public void T1003(T1003 message, Session session) {
        session.response(message);
    }

    @Mapping(types = 文件上传完成通知, desc = "文件上传完成通知")
    public void T1206(T1206 message, Session session) {
        session.response(message);
    }

    @Mapping(types = 终端上传乘客流量, desc = "终端上传乘客流量")
    public void T1005(T1005 message, Session session) {

    }
}