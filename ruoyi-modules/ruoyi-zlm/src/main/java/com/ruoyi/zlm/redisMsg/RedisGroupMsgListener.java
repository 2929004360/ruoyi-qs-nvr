package com.ruoyi.zlm.redisMsg;

import com.ruoyi.zlm.constants.VideoManagerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 接收redis发送的推流设备列表更新通知
 * 监听： SUBSCRIBE VM_MSG_GROUP_LIST_RESPONSE
 * 发布 PUBLISH VM_MSG_GROUP_LIST_RESPONSE '[{"groupName":"研发AAS","topGroupGAlias":"6","groupAlias":"6"},{"groupName":"测试AAS","topGroupGAlias":"5","groupAlias":"5"},{"groupName":"研发2","topGroupGAlias":"4","groupAlias":"4"},{"groupName":"啊实打实的","topGroupGAlias":"4","groupAlias":"100000009"},{"groupName":"测试域","topGroupGAlias":"3","groupAlias":"3"},{"groupName":"结构1","topGroupGAlias":"3","groupAlias":"100000000"},{"groupName":"结构1-1","topGroupGAlias":"3","parentGAlias":"100000000","groupAlias":"100000001"},{"groupName":"结构2-2","topGroupGAlias":"3","groupAlias":"100000002"},{"groupName":"结构1-2","topGroupGAlias":"3","parentGAlias":"100000001","groupAlias":"100000003"},{"groupName":"结构1-3","topGroupGAlias":"3","parentGAlias":"100000003","groupAlias":"100000004"},{"groupName":"研发1","topGroupGAlias":"3","groupAlias":"100000005"},{"groupName":"研发3","topGroupGAlias":"3","parentGAlias":"100000005","groupAlias":"100000006"},{"groupName":"测试42","topGroupGAlias":"3","parentGAlias":"100000000","groupAlias":"100000010"},{"groupName":"测试2","topGroupGAlias":"3","parentGAlias":"100000000","groupAlias":"100000011"},{"groupName":"测试3","topGroupGAlias":"3","parentGAlias":"100000000","groupAlias":"100000007"},{"groupName":"测试4","topGroupGAlias":"3","parentGAlias":"100000007","groupAlias":"100000008"}]'
 */
@Slf4j
@Component
public class RedisGroupMsgListener implements MessageListener {


    private final ConcurrentLinkedQueue<Message> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("[REDIS: 业务分组同步回复] key： {}， ： {}", VideoManagerConstants.VM_MSG_GROUP_LIST_RESPONSE, new String(message.getBody()));
        taskQueue.offer(message);
    }

    @Scheduled(fixedDelay = 100)
    public void executeTaskQueue() {

    }
}
