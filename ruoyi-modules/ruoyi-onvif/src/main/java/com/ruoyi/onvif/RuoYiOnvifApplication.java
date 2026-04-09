package com.ruoyi.onvif;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * onvif模块
 * 
 * @author fengcheng
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
@EnableScheduling
public class RuoYiOnvifApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiOnvifApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  onvif模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }
}
