package com.smartcold.tray.aop;


import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description: 日志管理
 * @author maqiang34
 * @date 2018-9-6 12:18:04
 * @version 1.0
 */
//@Aspect
//@Service
public class LoggerAdvice {

   private Gson gson = new Gson();

	protected Logger logger =  LoggerFactory.getLogger(this.getClass());

	@Before("within(com.smartcold.tray.controller.*) && @annotation(loggerManage)")
	public void addBeforeLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
		logger.info("执行 " + loggerManage.description() + " 开始");
		logger.info(joinPoint.getSignature().toString());
		logger.info(parseParames(joinPoint.getArgs()));
	}
	
	@AfterReturning("@annotation(loggerManage)")
	public void addAfterReturningLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
		logger.info("执行 " + loggerManage.description() + " 结束");
	}
	
	@AfterThrowing(pointcut = "@annotation(loggerManage)", throwing = "ex")
	public void addAfterThrowingLogger(JoinPoint joinPoint, LoggerManage loggerManage, Exception ex) {
		logger.error("执行 " + loggerManage.description() + " 异常", ex);
	}

	private String parseParames(Object[] parames) {
		if (null == parames || parames.length <= 0 || parames.length >1024) {
			return "";
		}
		StringBuffer param = new StringBuffer("传入参数[{}] ");
		for (Object obj : parames) {
			param.append(gson.toJson(obj)).append("  ");
		}
		return param.toString();
	}
	
}
