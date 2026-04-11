package com.ruoyi.zlm.redisMsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 接收redis返回的推流结果
 *
 * @author lin
 * PUBLISH VM_MSG_STREAM_PUSH_RESPONSE '{"code":0,"msg":"失败","app":"1000","stream":"10000022"}'
 */
@Slf4j
@Component
public class RedisPushStreamResponseListener implements MessageListener {

    private ConcurrentLinkedQueue<Message> taskQueue = new ConcurrentLinkedQueue<>();


    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("[REDIS: 推流结果]： {}", new String(message.getBody()));
        taskQueue.offer(message);
    }

//    @Scheduled(fixedDelay = 100)
//    public void executeTaskQueue() {
//       
//    }
    
}
