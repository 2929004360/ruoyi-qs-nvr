package com.ruoyi.qs.config;

import com.ruoyi.qs.api.common.CivilCodePo;
import com.ruoyi.qs.api.utils.CivilCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * 启动时读取行政区划表
 */
@Slf4j
@Configuration
@Order(value = 15)
public class CivilCodeFileConf implements CommandLineRunner {

    /**
     * 行政区划信息文件,系统启动时会加载到系统里
     */
    private final String CIVILCODEFILE = "classpath:civilCode.csv";

    @Override
    public void run(String... args) throws Exception {
        if (ObjectUtils.isEmpty(CIVILCODEFILE)) {
            log.warn("[行政区划] 文件未设置，可能造成目录刷新结果不完整");
            return;
        }
        InputStream inputStream;
        if (CIVILCODEFILE.startsWith("classpath:")) {
            String filePath = CIVILCODEFILE.substring("classpath:".length());
            ClassPathResource civilCodeFile = new ClassPathResource(filePath);
            if (!civilCodeFile.exists()) {
                log.warn("[行政区划] 文件<{}>不存在，可能造成目录刷新结果不完整", civilCodeFile);
                return;
            }
            inputStream = civilCodeFile.getInputStream();

        } else {
            File civilCodeFile = new File(CIVILCODEFILE);
            if (!civilCodeFile.exists()) {
                log.warn("[行政区划] 文件<{}>不存在，可能造成目录刷新结果不完整", CIVILCODEFILE);
                return;
            }
            inputStream = Files.newInputStream(civilCodeFile.toPath());
        }

        BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        int index = -1;
        String line;
        while ((line = inputStreamReader.readLine()) != null) {
            index++;
            if (index == 0) {
                continue;
            }
            String[] infoArray = line.split(",");
            CivilCodePo civilCodePo = CivilCodePo.getInstance(infoArray);
            CivilCodeUtil.INSTANCE.add(civilCodePo);
        }
        inputStreamReader.close();
        inputStream.close();
        if (CivilCodeUtil.INSTANCE.isEmpty()) {
            log.warn("[行政区划] 文件内容为空，可能造成目录刷新结果不完整");
        } else {
            log.info("[行政区划] 加载成功，共加载数据{}条", CivilCodeUtil.INSTANCE.size());
        }
    }
}
