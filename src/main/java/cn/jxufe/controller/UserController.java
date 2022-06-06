package cn.jxufe.controller;

import cn.jxufe.bean.*;
import cn.jxufe.entity.User;
import cn.jxufe.serivce.UserService;
import cn.jxufe.utils.EasyUIUtil;
import cn.jxufe.utils.PrintUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

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
     * 查询所有玩家数据
     *
     * @return List
     */
    @RequestMapping("/listAll")
    @ResponseBody
    public List<?> findAll() {
        return userService.findAll();
    }

    /**
     * 查询所有玩家数据
     * 模糊查询玩家数据
     *
     * @param pageRequest 请求
     * @param username    玩家名称 模糊查询所需信息
     * @return EasyUIData
     */
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIData<?> findAllPageable(EasyUIDataPageRequest pageRequest, @RequestParam(value = "username", defaultValue = "") String username) {
        return userService.findAllByUsernameLikePageable(username, EasyUIUtil.requestProcess(pageRequest));
    }

    /**
     * 注册用户
     *
     * @param user 用户信息（需含有用户名标识）
     * @return easyui onError方法需添加isError判断
     */
    @RequestMapping("/register")
    @ResponseBody
    public ResponseResult<?> register(User user) {
        User registerUser = userService.register(user);
        //判断注册用户是否重名
        if (registerUser != null) {
            return new ResponseResult<>(ResponseCode.SUCCESS, registerUser);
        } else {
            return new ResponseResult<>(ResponseCode.ERROR, true);
        }
    }

    /**
     * 修改用户
     *
     * @param user 用户信息（需含有用户名标识）
     * @return easyui onError方法需添加isError判断
     */
    @RequestMapping("/modify")
    @ResponseBody
    public ResponseResult<?> modify(User user) {
        User modifyUser = userService.modify(user);
        if (modifyUser != null) {

            return new ResponseResult<>(ResponseCode.SUCCESS, modifyUser);
        } else {
            return new ResponseResult<>(ResponseCode.ERROR, true);
        }
    }

    /**
     * 删除用户
     *
     * @param user 带有用户id信息
     * @return easyui onError方法需添加isError判断
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseResult<?> delete(User user) {
        PrintUtil.println("usr: " + user);
        userService.delete(user);
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }

    /**
     * 用户登录功能
     *
     * @param user    用户信息（需含有用户名标识）
     * @param session session
     * @return
     */
    @RequestMapping(value = "/setCurUser")
    @ResponseBody
    public ResponseResult<?> setCurUser(@RequestBody User user, HttpSession session) {
        User userByFind = userService.setCurUser(user, session);
        //检测是否登录成功
        ResponseResult<?> result;
        if (userByFind != null) {
            result = new ResponseResult<>(ResponseCode.SUCCESS, userByFind);
        } else {
            result = new ResponseResult<>(ResponseCode.ERROR);
        }
        return result;
    }

    /**
     * 购买种子
     *
     * @param jsonObject 数据
     * @param session    购买用户
     * @return
     */
    @RequestMapping(value = "/purchaseSeed")
    @ResponseBody
    public ResponseResult<?> purchaseSeed(@RequestBody JSONObject jsonObject, HttpSession session) {
        int cropId = (int) jsonObject.get("cropId");
        int seedNumber = (int) jsonObject.get("seedNumber");
        User curUser = (User) session.getAttribute(SystemCode.USER_SESSION_NAME);
        User user = userService.purchaseSeed(cropId, seedNumber, curUser);
        if (user != null) {
            return new ResponseResult<>(ResponseCode.SUCCESS, user);
        } else {
            return new ResponseResult<>(ResponseCode.ERROR);
        }
    }

    @RequestMapping(value = "/plantSeed")
    @ResponseBody
    public ResponseResult<?> plantSeed(@RequestBody JSONObject jsonObject, HttpSession session) {
        PrintUtil.println("plantSeed landId: " + jsonObject.get("landId"));
        FarmResponse farmResponse = userService.userActionPlantSeed((Integer) jsonObject.get("landId"), (Integer) jsonObject.get("cropId"), session);
        if (farmResponse != null) {
            return new ResponseResult<>(ResponseCode.SUCCESS, farmResponse);
        } else {
            return new ResponseResult<>(ResponseCode.ERROR);
        }
    }

    @RequestMapping(value = "/harvest")
    @ResponseBody
    public ResponseResult<?> harvest(@RequestBody JSONObject jsonObject, HttpSession session) {
        PrintUtil.println("harvest landId: " + jsonObject.get("landId"));
        FarmResponse farmResponse = userService.userActionHarvest((Integer) jsonObject.get("landId"), session);
        if (farmResponse != null) {
            return new ResponseResult<>(ResponseCode.SUCCESS, farmResponse);
        } else {
            return new ResponseResult<>(ResponseCode.ERROR);
        }
    }


    @RequestMapping(value = "/killWorm")
    @ResponseBody
    public ResponseResult<?> killWorm(@RequestBody JSONObject jsonObject, HttpSession session) {
        PrintUtil.println("killWorm landId: " + jsonObject.get("landId"));
        //TODO 修改
        FarmResponse farmResponse = userService.userActionKillWorm((Integer) jsonObject.get("landId"), session);
        if (farmResponse != null) {
            return new ResponseResult<>(ResponseCode.SUCCESS, farmResponse);
        } else {
            return new ResponseResult<>(ResponseCode.ERROR);
        }

    }


    @RequestMapping(value = "/cleanGrass")
    @ResponseBody
    public ResponseResult<?> cleanGrass(@RequestBody JSONObject jsonObject, HttpSession session) {
        //TODO 修改
        PrintUtil.println("cleanGrass landId: " + jsonObject.get("landId"));
        FarmResponse farmResponse = userService.userActionCleanGrass((Integer) jsonObject.get("landId"), session);
        if (farmResponse != null) {
            return new ResponseResult<>(ResponseCode.SUCCESS, farmResponse);
        } else {
            return new ResponseResult<>(ResponseCode.ERROR);
        }
    }
}