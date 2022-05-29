package cn.jxufe.controller;

import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.entity.SeedBag;
import cn.jxufe.serivce.SeedBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * 查询用户拥有的所有种子数据
     *
     * @param username
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResponseResult<?> findAllPageable(@RequestParam("username") String username) {
        //TODO EasyUIData<?> 返回类型
        return new ResponseResult<>(ResponseCode.SUCCESS, seedBagService.findAllByUsername(username));
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
