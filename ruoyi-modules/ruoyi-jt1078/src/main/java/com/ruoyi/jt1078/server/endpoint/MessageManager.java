package com.ruoyi.jt1078.server.endpoint;

import com.ruoyi.jt1078.commons.model.APIException;
import com.ruoyi.jt1078.commons.util.Exceptions;
import com.ruoyi.jt1078.protocol.basics.JTMessage;
import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.netmc.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Slf4j
@Component
public class MessageManager {

    private static final Mono OFFLINE = Mono.error(new APIException(4000, "离线的客户端（请检查设备是否注册或者鉴权）"));
    private static final Mono TIMEOUT = Mono.error(new APIException(4002, "消息发送成功,客户端响应超时（至于设备为什么不应答，请联系设备厂商）"));

    private SessionManager sessionManager;

    public MessageManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Mono<Void> notify(String sessionId, JTMessage request) {
        Session session = sessionManager.get(sessionId);
        if (session == null) return OFFLINE;
        return session.notify(request);
    }

    public <T> Mono<T> request(String sessionId, JTMessage request, Class<T> responseClass) {
        Session session = sessionManager.get(sessionId);
        if (session == null) return OFFLINE;

        return session.request(request, responseClass)
                .timeout(Duration.ofSeconds(10), TIMEOUT)
                .onErrorResume(new Function<Throwable, Mono>() {
                    @Override
                    public Mono apply(Throwable throwable) {
                        return Mono.error(new APIException(4001, "消息发送失败").setDetails(Exceptions.getStackTrace(throwable)));
                    }
                });
    }

    public <T> Mono<T> request(JTMessage request, Class<T> responseClass) {
        return request(request.getClientId(), request, responseClass);
    }
}