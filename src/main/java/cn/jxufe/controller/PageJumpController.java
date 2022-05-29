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
    @RequestMapping("/cropPage")
    public String jump2CropPage() {
        return "/crop/crop";
    }

    @RequestMapping("/cropGrowPage")
    public String jump2CropGrowPage() {
        return "/crop/cropGrow";
    }

    @RequestMapping("/userManagerPage")
    public String jump2UserManagerPage() {
        return "/user/userManager";
    }

    @RequestMapping("/seedBagPage")
    public String jump2SeedBagPage() {
        return "/seedBag/seedBag";
    }

    @RequestMapping("/userLoginPage")
    public String jump2RegisterPage() {
        return "/user/userLogin";
    }

    @RequestMapping("/farmGamePage")
    public String jump2FarmGamePage() {
        return "/farmGame/farmGame";
    }
}
