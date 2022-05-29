package cn.jxufe.controller;

import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.serivce.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @create: 2022-05-19 10:09
 * @author: lwz
 * @description:
 **/
@Controller
@RequestMapping("/common")
public class CommonController {
    @Autowired
    CommonService commonService;

    /**
     * 查询所有土地种类数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/landType")
    public ResponseResult<?> soilTypeFindAll() {
        return new ResponseResult<>(ResponseCode.SUCCESS, commonService.soilTypeFindAll());
    }

    /**
     * 查询所有种子种类数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/seedType")
    public ResponseResult<?> seedTypeFindAll() {
        return new ResponseResult<>(ResponseCode.SUCCESS, commonService.seedTypeFindAll());
    }

    /**
     * 查询所有作物生长状态数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/cropState")
    public ResponseResult<?> cropStatusFindAll() {
        return new ResponseResult<>(ResponseCode.SUCCESS, commonService.cropStatusFindAll());
    }
}
