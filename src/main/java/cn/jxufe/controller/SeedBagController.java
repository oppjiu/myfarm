package cn.jxufe.controller;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.bean.EasyUIDataPageRequest;
import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.serivce.SeedBagService;
import cn.jxufe.utils.EasyUIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIData<?> findAllPageable(EasyUIDataPageRequest pageRequest) {
        //修改UserSeed为UserSeedView
        return seedBagService.findAllPageable(EasyUIUtils.requestProcess(pageRequest));
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseResult<?> save() {
        //TODO
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseResult<?> delete() {
        //TODO
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }
}
