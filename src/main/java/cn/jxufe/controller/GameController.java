package cn.jxufe.controller;

import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.bean.SystemCode;
import cn.jxufe.entity.User;
import cn.jxufe.serivce.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @create: 2022-05-25 20:04
 * @author: lwz
 * @description:
 **/
@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    GameService gameService;

    /**
     * 初始化指定玩家的农场数据
     *
     * @param session 当前玩家
     * @return
     */
    @RequestMapping(value = "/initiateFarmView")
    @ResponseBody
    public ResponseResult<?> initiateFarmView(HttpSession session) {
        User curUser = (User) session.getAttribute(SystemCode.USER_SESSION_NAME);
        return new ResponseResult<>(ResponseCode.SUCCESS, gameService.initiateFarmView(curUser));
    }
}
