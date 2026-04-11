package com.ruoyi.zlm.redisMsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Auther: JiangFeng
 * @Date: 2022/8/16 11:32
 * @Description: 接收redis发送的推流设备列表更新通知
 * 监听：  SUBSCRIBE VM_MSG_PUSH_STREAM_LIST_CHANGE
 * 发布 PUBLISH VM_MSG_PUSH_STREAM_LIST_CHANGE '[{"app":1000,"stream":10000000,"gbId":"12345678901234567890","name":"A6","status":false},{"app":1000,"stream":10000021,"gbId":"24212345671381000021","name":"终端9273","status":false},{"app":1000,"stream":10000022,"gbId":"24212345671381000022","name":"终端9434","status":true},{"app":1000,"stream":10000025,"gbId":"24212345671381000025","name":"华为M10","status":false},{"app":1000,"stream":10000051,"gbId":"11111111111381111122","name":"终端9720","status":false}]'
 */
@Slf4j
@Component
public class RedisPushStreamListMsgListener implements MessageListener {

    private final ConcurrentLinkedQueue<Message> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("[REDIS: 推流设备列表更新]： {}", new String(message.getBody()));
        taskQueue.offer(message);
    }

    @Scheduled(fixedDelay = 100)
    public void executeTaskQueue() {

    }
}
