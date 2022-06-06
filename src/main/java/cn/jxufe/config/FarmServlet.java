package cn.jxufe.config;

import cn.jxufe.serivce.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @create: 2022-06-06 11:58
 * @author: lwz
 * @description:
 **/
public class FarmServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    GameService gameService;

    //启动方法入口
    public void init() throws ServletException {
        super.init();
        WebApplicationContextUtils
                .getWebApplicationContext(getServletContext())
                .getAutowireCapableBeanFactory().autowireBean(this);
        System.out.println("/******************** 后台游戏服务开始启动 ***************************/");
        gameService.gameSeversInitiate();
        System.out.println("/******************** 后台游戏服务启动完成 ***************************/");
    }
}
