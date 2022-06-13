package cn.jxufe.config;

import cn.jxufe.serivce.GameService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @create: 2022-05-25 09:54
 * @author: lwz
 * @description:
 **/
@Configuration
public class GameConfig implements ApplicationContextAware {
    @Autowired
    GameService gameService;

    /**
     * 会执行两次！！！
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("/******************** 后台游戏服务开始启动 ***************************/");
//        gameService.gameSeversInitiate();
        System.out.println("/******************** 后台游戏服务启动完成 ***************************/");
    }
}


