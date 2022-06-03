package cn.jxufe.interceptor;

import cn.jxufe.bean.SystemCode;
import cn.jxufe.entity.User;
import cn.jxufe.utils.PrintUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @create: 2022-06-03 20:32
 * @author: lwz
 * @description:
 **/
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        User user = (User) request.getSession().getAttribute(SystemCode.USER_SESSION_NAME);
        PrintUtil.println("LoginInterceptor.user: " +user);
        if (user != null) {
            return true;
        } else {
            response.sendRedirect(request.getContextPath() + "/page/userLoginPage");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
