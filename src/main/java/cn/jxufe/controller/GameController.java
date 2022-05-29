package cn.jxufe.controller;

import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.serivce.GameService;
import cn.jxufe.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    UserService userService;

    /**
     * 进入农场初始化农场数据
     *
     * @return
     */
    @RequestMapping(value = "/initiateFarmView")
    @ResponseBody
    public ResponseResult<?> initiateFarmView() {
        //TODO
        return new ResponseResult<>(ResponseCode.SUCCESS);
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
