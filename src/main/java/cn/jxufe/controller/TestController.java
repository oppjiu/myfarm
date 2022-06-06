package cn.jxufe.controller;

import cn.jxufe.bean.FarmResponse;
import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.entity.Land;
import cn.jxufe.repository.view.CropGrowViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @create: 2022-05-30 10:57
 * @author: lwz
 * @description:
 **/
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    CropGrowViewRepository cropGrowViewRepository;

    @ResponseBody
    @RequestMapping("/testFarmResponse")
    public ResponseResult<?> testFarmResponse(@RequestBody Land landByFind) {
        FarmResponse farmResponse = new FarmResponse(100, landByFind, cropGrowViewRepository.findByStageIdAndCropId(landByFind.getNowCropGrowStage(), landByFind.getCropId()));
        return new ResponseResult<>(ResponseCode.SUCCESS, farmResponse);
    }
}
