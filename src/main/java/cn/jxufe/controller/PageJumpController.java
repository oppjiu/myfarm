package cn.jxufe.controller;

import cn.jxufe.bean.ResponseCode;
import cn.jxufe.bean.ResponseResult;
import cn.jxufe.bean.SystemCode;
import cn.jxufe.entity.User;
import cn.jxufe.utils.PrintUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/seedPurchasePage")
    public String jump2SeedPurchasePage() {
        return "/seedBag/seedPurchase";
    }

    @RequestMapping("/seedPurchasePageConfirm")
    public ResponseResult<?> seedPurchasePageConfirm(HttpSession session) {
        PrintUtil.println("this is seedPurchasePageConfirm");
        User user = (User) session.getAttribute(SystemCode.USER_SESSION_NAME);
        if (user != null) {
            PrintUtil.println("this is true");
            return new ResponseResult<>(ResponseCode.SUCCESS);
        }else{
            PrintUtil.println("this is false");
            return new ResponseResult<>(ResponseCode.ERROR);
        }
    }

    @RequestMapping("/userLoginPage")
    public String jump2RegisterPage() {
        return "/user/userLogin";
    }

    @RequestMapping("/farmGamePage")
    public String jump2FarmGamePage() {
        //TODO 修改 重定向到GameController /initiateFarmView
//        return "redirect:/game/initiateFarmView";
        return "/farmGame/farmGame";
    }
}
