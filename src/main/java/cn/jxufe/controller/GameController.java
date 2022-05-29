package cn.jxufe.controller;

import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.bean.SystemCode;
import cn.jxufe.entity.User;
import cn.jxufe.serivce.GameService;
import cn.jxufe.serivce.LandService;
import cn.jxufe.serivce.UserService;
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
@RequestMapping("/socket")
public class GameController {

    @Autowired
    GameService gameService;
    @Autowired
    LandService landService;

    @Autowired
    UserService userService;

    /**
     * 初始化该玩家的农场数据
     * @param session 当前玩家
     * @return
     */
    @RequestMapping(value = "/initiateFarmView")
    @ResponseBody
    public ResponseResult<?> initiateFarmView(HttpSession session) {
        User curUser = (User) session.getAttribute(SystemCode.USER_SESSION_NAME);
        return new ResponseResult<>(ResponseCode.SUCCESS, landService.findAllByUsername(curUser));
    }

    @RequestMapping(value = "/plantSeed")
    @ResponseBody
    public ResponseResult<?> serverActionPlantSeed() {
        //TODO
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }


    @RequestMapping(value = "/killWorm")
    @ResponseBody
    public ResponseResult<?> serverActionKillWorm() {
        //TODO
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }

    @RequestMapping(value = "/harvest")
    @ResponseBody
    public ResponseResult<?> serverActionHarvest() {
        //TODO
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }

    @RequestMapping(value = "/cleanGrass")
    @ResponseBody
    public ResponseResult<?> serverActionCleanGrass() {
        //TODO
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }


}
