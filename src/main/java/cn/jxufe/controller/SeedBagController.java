package cn.jxufe.controller;

import cn.jxufe.bean.*;
import cn.jxufe.entity.SeedBag;
import cn.jxufe.entity.User;
import cn.jxufe.serivce.SeedBagService;
import cn.jxufe.utils.EasyUIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @create: 2022-05-18 10:48
 * @author: lwz
 * @description:
 **/
@Controller
@RequestMapping("/seedBag")
public class SeedBagController {
    @Autowired
    SeedBagService seedBagService;

    /**
     * 查询该用户拥有的所有种子数据
     *
     * @param pageRequest 请求
     * @param session    当前用户session
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIData<?> findAllPageable(EasyUIDataPageRequest pageRequest, HttpSession session) {
        User curUser = (User) session.getAttribute(SystemCode.USER_SESSION_NAME);
        return seedBagService.findAllByUsernamePageable(curUser.getUsername(), EasyUIUtils.requestProcess(pageRequest));
    }

    /**
     * 保存种子收纳袋信息（单条保存）
     *
     * @param seedBag 种子收纳袋信息
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseResult<?> save(@RequestBody SeedBag seedBag) {
        return new ResponseResult<>(ResponseCode.SUCCESS, seedBagService.save(seedBag));
    }

    /**
     * 删除种子收纳袋数据（单条删除）
     *
     * @param seedBag 种子收纳袋信息（需含有种子id和用户名信息）
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseResult<?> delete(@RequestBody SeedBag seedBag) {
        seedBagService.delete(seedBag);
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }
}
