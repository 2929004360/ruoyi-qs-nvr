package com.ruoyi.zlm.redisMsg;

import com.ruoyi.zlm.constants.VideoManagerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Auther: JiangFeng
 * @Date: 2022/8/16 11:32
 * @Description: 接收redis发送的推流设备列表更新通知
 * 监听：  SUBSCRIBE VM_MSG_GROUP_LIST_CHANGE
 * 发布 PUBLISH VM_MSG_GROUP_LIST_CHANGE  '[{"groupName":"测试域修改新","topGroupGAlias":3,"messageType":"update","groupAlias":3}]'
 */
@Slf4j
@Component
public class RedisGroupChangeListener implements MessageListener {

    private final ConcurrentLinkedQueue<Message> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("[REDIS-分组信息改变] key： {}， ： {}", VideoManagerConstants.VM_MSG_GROUP_LIST_CHANGE, new String(message.getBody()));
        taskQueue.offer(message);
    }

//    @Scheduled(fixedDelay = 100)
//    public void executeTaskQueue() {
//
//
//    }
}
