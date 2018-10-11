package com.smartcold.tray.controller.admin;

import com.smartcold.tray.aop.LoggerManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面资源设置
 * 管理页面入口
 * 404/500跳转
 *
 *
 */
@Controller
public class PageController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/")
    public String index() {
        return "/admin/login.html";
    }




}
