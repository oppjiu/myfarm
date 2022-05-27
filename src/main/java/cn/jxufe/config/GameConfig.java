//package cn.jxufe.config;
//
//import cn.jxufe.service.GameService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//
///**
// * @create: 2022-05-25 09:54
// * @author: lwz
// * @description:
// **/
//@Configuration
//public class GameConfig {
//    @Autowired
//    GameService gameService;
//
//    /**
//     * 初始化游戏服务
//     */
//    @PostConstruct
//    public void gameServiceInit() {
//        System.out.println("游戏初始化中");
//        gameService.gameSeversInitiate();
//        System.out.println("游戏初始化完成");
//    }
//}
