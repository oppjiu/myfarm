package cn.jxufe.controller;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.bean.EasyUIDataPageRequest;
import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.entity.Crop;
import cn.jxufe.serivce.CropService;
import cn.jxufe.utils.EasyUIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    /**
     * 查询所有种子数据
     * 模糊查询种子数据
     *
     * @param pageRequest 请求
     * @param cropName    种子名称
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public EasyUIData<?> findAllPageable(EasyUIDataPageRequest pageRequest,
                                         @RequestParam(value = "cropName", defaultValue = "") String cropName) {
        return cropService.findAllPageableByCropNameLike(cropName, EasyUIUtil.requestProcess(pageRequest));
    }

    /**
     * 保存种子信息（单条保存）
     *
     * @param crop 种子信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public ResponseResult<?> save(Crop crop) {
        return new ResponseResult<>(ResponseCode.SUCCESS, cropService.save(crop));
    }

    /**
     * 删除种子数据（单条删除）
     *
     * @param crop 种子信息（需含有id）
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public ResponseResult<?> delete(Crop crop) {
        cropService.delete(crop);
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }
}
