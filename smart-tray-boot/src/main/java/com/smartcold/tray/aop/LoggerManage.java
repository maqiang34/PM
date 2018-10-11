package com.smartcold.tray.aop;

import java.lang.annotation.*;

/**
 * @Description: 日志注解
 * @author Leo maqiang34
 * @date 2018-9-6 12:17:53
 * @version 1.0
 */
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggerManage {

	public String description();
}
