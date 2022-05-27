package cn.jxufe.controller;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.bean.EasyUIDataPageRequest;
import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.entity.Crop;
import cn.jxufe.serivce.CropService;
import cn.jxufe.utils.EasyUIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @create: 2022-05-05 12:36
 * @author: lwz
 * @description:
 **/
@Controller
@RequestMapping("/crop")
public class CropController {
    @Autowired
    CropService cropService;

    @ResponseBody
    @RequestMapping("/list")
    public EasyUIData<?> findAllPageable(EasyUIDataPageRequest pageRequest,
                                         @RequestParam(value = "name", defaultValue = "") String name) {
        return cropService.findAllByCropNameLike(name, EasyUIUtils.requestProcess(pageRequest));
    }

    @ResponseBody
    @RequestMapping("/save")
    public ResponseResult<?> save(@RequestBody Crop crop) {
        //TODO 修改
        return new ResponseResult<>(ResponseCode.SUCCESS, cropService.save(crop));
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ResponseResult<?> delete(@RequestBody Crop crop) {
        //TODO 修改
        cropService.delete(crop);
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }
}
