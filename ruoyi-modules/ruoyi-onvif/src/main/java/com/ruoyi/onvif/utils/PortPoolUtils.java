package com.ruoyi.onvif.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PortPoolUtils {
    // 静态列表，存放可用端口
    private static final List<Integer> availablePorts = new ArrayList<>();

    // 端口范围开始
    private static final int PORT_RANGE_START = 55000;

    // 端口范围结束
    private static final int PORT_RANGE_END = 60000;

    static {
        for (int i = PORT_RANGE_START; i <= PORT_RANGE_END; i++) {
            availablePorts.add(i);
        }
        // 打乱顺序，保证取出的顺序也是随机的
        Collections.shuffle(availablePorts);
    }

    /**
     * 获取一个不重复的端口
     * 如果池空了，可以重置或者抛出异常
     */
    public static synchronized int getUniquePort() {
        if (availablePorts.isEmpty()) {
            // 如果端口用完了，重新填充并打乱（或者根据业务需求抛出异常）
            for (int i = PORT_RANGE_START; i <= PORT_RANGE_END; i++) {
                availablePorts.add(i);
            }
            Collections.shuffle(availablePorts);
        }
        // 移除并返回最后一个元素
        return availablePorts.remove(availablePorts.size() - 1);
    }
}