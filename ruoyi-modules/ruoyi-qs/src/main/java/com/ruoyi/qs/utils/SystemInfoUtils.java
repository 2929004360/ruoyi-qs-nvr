package com.ruoyi.qs.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.OperatingSystem;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 实现参考自xiaozhangnomoney原创文章，
 * 版权声明：本文为xiaozhangnomoney原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明
 * 原文出处链接：https://blog.csdn.net/xiaozhangnomoney/article/details/107769147
 */
@Slf4j
public class SystemInfoUtils {

    /**
     * 获取cpu信息
     *
     * @return
     * @throws InterruptedException
     */
    public static double getCpuInfo() throws InterruptedException {
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        TimeUnit.SECONDS.sleep(1);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        return 1.0 - (idle * 1.0 / totalCpu);
    }

    /**
     * 获取内存使用率
     *
     * @return
     */
    public static double getMemInfo() {
        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        //总内存
        long totalByte = memory.getTotal();
        //剩余
        long acaliableByte = memory.getAvailable();
        return (totalByte - acaliableByte) * 1.0 / totalByte;
    }

    /**
     * 获取网络上传和下载
     *
     * @return
     */
    public static Map<String, Double> getNetworkInterfaces() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        List<NetworkIF> beforeRecvNetworkIFs = hal.getNetworkIFs();
        HashMap<String, Double> map = new HashMap<>();
        
        // 默认值
        map.put("in", 0.0);
        map.put("out", 0.0);
        
        if (beforeRecvNetworkIFs == null || beforeRecvNetworkIFs.isEmpty()) {
            log.warn("[系统信息] 未找到网络接口");
            return map;
        }
        
        NetworkIF beforeBet = beforeRecvNetworkIFs.get(beforeRecvNetworkIFs.size() - 1);
        long beforeRecv = beforeBet.getBytesRecv();
        long beforeSend = beforeBet.getBytesSent();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("[线程休眠失败] : {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        List<NetworkIF> afterNetworkIFs = hal.getNetworkIFs();
        
        if (afterNetworkIFs == null || afterNetworkIFs.isEmpty()) {
            log.warn("[系统信息] 未找到网络接口(第二次)");
            return map;
        }
        
        NetworkIF afterNet = afterNetworkIFs.get(afterNetworkIFs.size() - 1);

        // 速度单位: Mbps
        map.put("in", formatUnits(afterNet.getBytesRecv() - beforeRecv, 1048576L));
        map.put("out", formatUnits(afterNet.getBytesSent() - beforeSend, 1048576L));
        return map;
    }

    /**
     * 获取带宽总值
     *
     * @return
     */
    public static long getNetworkTotal() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        List<NetworkIF> recvNetworkIFs = hal.getNetworkIFs();
        
        if (recvNetworkIFs == null || recvNetworkIFs.isEmpty()) {
            log.warn("[系统信息] 未找到网络接口");
            return 0L;
        }
        
        NetworkIF networkIF = recvNetworkIFs.get(recvNetworkIFs.size() - 1);

        return networkIF.getSpeed() / 1048576L / 8L;
    }

    public static double formatUnits(long value, long prefix) {
        return (double) value / (double) prefix;
    }

    /**
     * 获取进程数
     *
     * @return
     */
    public static int getProcessesCount() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();

        int processCount = os.getProcessCount();
        return processCount;
    }

    public static List<Map<String, Object>> getDiskInfo() {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            // 使用 Java NIO 的 FileStore 获取所有挂载的文件系统，这种方式在所有系统上都适用
            for (FileStore store : FileSystems.getDefault().getFileStores()) {
                try {
                    Map<String, Object> infoMap = new HashMap<>();
                    // 获取文件系统的路径
                    String path = store.toString();
                    // 处理 Windows 盘符，格式通常是 "C:\ (NTFS)"，需要提取盘符
                    if (path.contains(":\\")) {
                        path = path.substring(0, path.indexOf(":\\")) + ":";
                    } else if (path.contains(" (")) {
                        // 处理其他格式，比如 "/dev/sda1 (ext4)"，提取挂载点
                        path = path.substring(0, path.indexOf(" ("));
                    }
                    infoMap.put("path", path);
                    
                    // 获取总空间和可用空间（单位：GB）
                    long total = store.getTotalSpace();
                    long usable = store.getUsableSpace();
                    infoMap.put("use", (total - usable) / 1024 / 1024 / 1024D);
                    infoMap.put("free", usable / 1024 / 1024 / 1024D);
                    
                    result.add(infoMap);
                } catch (Exception e) {
                    // 某些文件系统可能无法访问（比如网络磁盘），跳过即可
                    log.debug("[系统信息] 无法访问文件系统: {}", store, e);
                }
            }
        } catch (Exception e) {
            log.error("[系统信息] 获取磁盘信息失败", e);
            // 如果 NIO 方式失败，回退到原有的方法
            return getDiskInfoFallback();
        }
        return result;
    }
    
    /**
     * 备用方法，当 NIO 方式失败时使用
     */
    private static List<Map<String, Object>> getDiskInfoFallback() {
        List<Map<String, Object>> result = new ArrayList<>();
        String osName = System.getProperty("os.name");
        List<String> pathArray = new ArrayList<>();
        
        if (osName.startsWith("Windows")) {
            // Windows - 获取所有盘符
            File[] roots = File.listRoots();
            if (roots != null) {
                for (File root : roots) {
                    if (root.exists()) {
                        pathArray.add(root.getPath());
                    }
                }
            }
        } else {
            // Mac 和 Linux
            pathArray.add("/");
        }
        
        for (String path : pathArray) {
            try {
                Map<String, Object> infoMap = new HashMap<>();
                infoMap.put("path", path);
                File partitionFile = new File(path);
                infoMap.put("use", (partitionFile.getTotalSpace() - partitionFile.getFreeSpace()) / 1024 / 1024 / 1024D);
                infoMap.put("free", partitionFile.getFreeSpace() / 1024 / 1024 / 1024D);
                result.add(infoMap);
            } catch (Exception e) {
                log.debug("[系统信息] 无法访问路径: {}", path, e);
            }
        }
        return result;
    }

    public static String getHardwareId() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        // CPU ID
        String cpuId = hardware.getProcessor().getProcessorIdentifier().getProcessorID();
        // 主板序号
        String serialNumber = hardware.getComputerSystem().getSerialNumber();

        return DigestUtils.md5DigestAsHex((DigestUtils.md5DigestAsHex(cpuId.getBytes(StandardCharsets.UTF_8)) + DigestUtils.md5DigestAsHex(serialNumber.getBytes(StandardCharsets.UTF_8))).getBytes(StandardCharsets.UTF_8));
    }
}
