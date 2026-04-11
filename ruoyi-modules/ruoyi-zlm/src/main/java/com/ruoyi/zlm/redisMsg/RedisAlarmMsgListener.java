package com.ruoyi.zlm.redisMsg;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 监听 SUBSCRIBE alarm_receive
 * 发布 PUBLISH alarm_receive '{ "gbId": "", "alarmSn": 1, "alarmType": "111", "alarmDescription": "222", }'
 */
@Slf4j
@Component
public class RedisAlarmMsgListener implements MessageListener {

    private final ConcurrentLinkedQueue<Message> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void onMessage(@NotNull Message message, byte[] bytes) {
        log.info("[REDIS: ALARM]： {}", new String(message.getBody()));
        taskQueue.offer(message);
    }

//    @Scheduled(fixedDelay = 100)
//    public void executeTaskQueue() {
//
//    }
}

