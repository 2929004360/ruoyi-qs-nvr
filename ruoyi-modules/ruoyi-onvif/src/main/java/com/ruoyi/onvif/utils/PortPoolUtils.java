package com.ruoyi.onvif.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PortPoolUtils {
    // 静态列表，存放可用端口
    private static final List<Integer> availablePorts = new ArrayList<>();
    
    // 已分配的端口
    private static final Set<Integer> allocatedPorts = ConcurrentHashMap.newKeySet();

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
     * 获取一个不重复的可用端口
     */
    public static synchronized int getUniquePort() {
        while (!availablePorts.isEmpty()) {
            int port = availablePorts.remove(availablePorts.size() - 1);
            if (isPortAvailable(port)) {
                allocatedPorts.add(port);
                return port;
            }
        }
        
        // 如果端口用完了，重置并重新尝试
        resetPool();
        return getUniquePort();
    }
    
    /**
     * 释放端口
     */
    public static synchronized void releasePort(int port) {
        if (allocatedPorts.remove(port)) {
            availablePorts.add(port);
        }
    }
    
    /**
     * 检查端口是否可用
     */
    private static boolean isPortAvailable(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * 重置端口池
     */
    private static void resetPool() {
        availablePorts.clear();
        allocatedPorts.clear();
        for (int i = PORT_RANGE_START; i <= PORT_RANGE_END; i++) {
            availablePorts.add(i);
        }
        Collections.shuffle(availablePorts);
    }
}