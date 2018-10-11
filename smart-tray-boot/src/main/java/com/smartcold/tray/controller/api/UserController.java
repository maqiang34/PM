package com.smartcold.tray.controller.api;


import com.smartcold.tray.entity.comm.UserEntity;
import com.smartcold.tray.mapper.UserMapper;
import com.smartcold.tray.util.EncodeUtil;
import com.smartcold.tray.util.ResponseData;
import com.smartcold.tray.util.StringUtil;
import com.smartcold.tray.util.TimeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Copyright (C) SmartCold 版权所有
 *
 * @author MaQiang34
 * @ClassName 手持终端使用
 * @Description: 用户-api
 * @createDate:2018-02-28 16:47:46
 * @version:V1.0
 */
@RestController
@RequestMapping(value = "i/user")
public class UserController {

    @Resource
    private UserMapper userMapper;
    /**
     * 备注：将使用spring session 统一托管
     *
     * @param request
     * @param token
     * @return
     */
    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    @ResponseBody
    public UserEntity findUser(HttpServletRequest request, String token) {
        return (UserEntity) request.getSession().getAttribute("user");
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Object logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.invalidate();//session失效
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return true;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token") || cookie.getName().equals("systoken")) {
                cookie.setMaxAge(0);
            }
        }
        return true;
    }

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<UserEntity> login(HttpServletRequest request, @RequestParam(value="username",required=true) String userName, @RequestParam(value="password",required=true)String password, String ipAddress, int sik, Boolean isAuto) {
        try {
            if (StringUtil.isNull(userName) || StringUtil.isNull(password) || sik != TimeUtil.getDay()) {
                return ResponseData.newFailure("异常登录！请联系管理员！");
            }
            password =isAuto == null || !isAuto? EncodeUtil.encodeByMD5(password):StringUtil.MD5pwd(null, password);
            UserEntity user = userMapper.userLogin(userName, password);
            if (user != null) {
                String cookie = StringUtil.getToken();
                user.setToken(cookie);
                user.setSystoken(StringUtil.MD5pwd(password, cookie));
                request.getSession().setAttribute("user", user);
                return ResponseData.newSuccess(user);
            }
            return ResponseData.newFailure("登录失败！用户名或密码错误！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.newFailure("网络异常！请稍后重试！");
        }
    }





}
