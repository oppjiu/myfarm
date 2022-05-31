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
import org.springframework.web.bind.annotation.*;

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

    /**
     * 查询作物的所有生长阶段数据
     *
     * @param pageRequest 请求
     * @param cropId      种子id
     * @return
     */
    @ResponseBody
    @RequestMapping("/list/{cropId}")
    public EasyUIData<?> findAllPageable(EasyUIDataPageRequest pageRequest,
                                         @PathVariable("cropId") int cropId) {
        return cropGrowService.findAllPageableByCropId(cropId, EasyUIUtils.requestProcess(pageRequest));
    }

    /**
     * 保存作物生长阶段信息（单条保存）
     *
     * @param cropGrow 作物生长阶段信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public ResponseResult<?> save(CropGrow cropGrow) {
        return new ResponseResult<>(ResponseCode.SUCCESS, cropGrowService.save(cropGrow));
    }

    /**
     * 删除作物生长阶段数据（单条删除）
     *
     * @param cropGrow 作物生长阶段信息（需含有作物生长阶段id和id）
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public ResponseResult<?> delete(CropGrow cropGrow) {
        cropGrowService.delete(cropGrow);
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }
}
