package cn.jxufe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @create: 2022-05-05 10:23
 * @author: lwz
 * @description:
 **/
@Controller
@RequestMapping("/page")
public class PageJumpController {
    @RequestMapping("/cropList")
    public String jump2CropList() {
        return "/crop/grid";
    }

    @RequestMapping("/cropsGrowList")
    public String jump2CropsGrowList() {
        return "/cropsGrow/grid";
    }

}
