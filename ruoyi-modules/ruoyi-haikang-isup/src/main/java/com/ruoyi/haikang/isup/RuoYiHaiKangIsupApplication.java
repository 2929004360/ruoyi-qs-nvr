package com.ruoyi.haikang.isup;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 海康isup模块
 * 
 * @author fengcheng
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiHaiKangIsupApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiHaiKangIsupApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  海康isup模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
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
