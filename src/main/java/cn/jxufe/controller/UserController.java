package cn.jxufe.controller;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.bean.EasyUIDataPageRequest;
import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.entity.User;
import cn.jxufe.serivce.UserService;
import cn.jxufe.utils.EasyUIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @create: 2022-05-10 19:51
 * @author: lwz
 * @description:
 **/
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 查询玩家列表
     *
     * @param pageRequest easyui请求
     * @param username    玩家名称 模糊查询数据
     * @return EasyUIData
     */
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIData<?> findAllPageable(EasyUIDataPageRequest pageRequest,
                                         @RequestParam(value = "username", defaultValue = "") String username) {
        return userService.findAllByUsernameLike(username, EasyUIUtils.requestProcess(pageRequest));
    }

    @RequestMapping("/register")
    @ResponseBody
    public ResponseResult<?> register(@RequestBody User user) {
        //TODO 修改
        User registerUser = userService.register(user);
        return new ResponseResult<>(ResponseCode.SUCCESS, registerUser);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public ResponseResult<?> modify(@RequestBody User user) {
        //TODO 修改
        User modifyUser = userService.modify(user);
        return new ResponseResult<>(ResponseCode.SUCCESS, modifyUser);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseResult<?> delete(@RequestBody User user) {
        //TODO 修改
        userService.delete(user);
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }

    /**
     * 用户登录功能
     *
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "/setCurUser")
    @ResponseBody
    public ResponseResult<?> setCurUser(HttpSession session, @RequestBody User user) {
        //TODO 是否需要增加shiro
        boolean isTure = userService.setCurUser(session, user);
        //检测是否登录成功
        ResponseResult<?> result;
        if (isTure) {
            result = new ResponseResult<>(ResponseCode.SUCCESS, user);
        } else {
            result = new ResponseResult<>(ResponseCode.ERROR, user);
        }
        return result;
    }

    @RequestMapping(value = "/purchaseSeed")
    @ResponseBody
    public ResponseResult<?> purchaseSeed(HttpSession session,
                                          @RequestParam("seedId") int seedId) {
        User curUser = (User) session.getAttribute("curUser");
        userService.purchaseSeed(seedId, curUser, 1);
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }
}