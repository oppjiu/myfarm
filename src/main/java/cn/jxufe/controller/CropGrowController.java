package cn.jxufe.controller;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.bean.EasyUIDataPageRequest;
import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.entity.CropGrow;
import cn.jxufe.serivce.CropGrowService;
import cn.jxufe.utils.EasyUIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @create: 2022-05-05 12:38
 * @author: lwz
 * @description:
 **/
@Controller
@RequestMapping("/cropGrow")
public class CropGrowController {
    @Autowired
    CropGrowService cropGrowService;

    @ResponseBody
    @RequestMapping("/list")
    public EasyUIData<?> findAllPageable(EasyUIDataPageRequest pageRequest) {
        return cropGrowService.findAllPageable(EasyUIUtils.requestProcess(pageRequest));
    }

    @ResponseBody
    @RequestMapping("/save")
    public ResponseResult<?> save(CropGrow cropGrow) {
        //TODO
        return new ResponseResult<>(ResponseCode.SUCCESS, cropGrowService.save(cropGrow));
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ResponseResult<?> delete(@RequestBody CropGrow cropGrow) {
        //TODO
        cropGrowService.delete(cropGrow);
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }
}
