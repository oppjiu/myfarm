package cn.jxufe.controller;

import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.bean.SystemCode;
import cn.jxufe.entity.User;
import cn.jxufe.utils.PrintUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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

    @RequestMapping("/userManagePage")
    public String jump2UserManagerPage() {
        return "/user/userManage";
    }

    @RequestMapping("/seedBagPage")
    public String jump2SeedBagPage() {
        return "/seedBag/seedBag";
    }

    @RequestMapping("/userLoginPage")
    public String jump2RegisterPage() {
        return "/user/userLogin";
    }

    @RequestMapping("/seedPurchasePage")
    public String jump2SeedPurchasePage() {
        return "/seedBag/seedPurchase";
    }

    @RequestMapping("/farmGamePage")
    public String jump2FarmGamePage() {
        return "/farmGame/farmGame";
    }
}
