package com.rfq.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogerAop {

	private Logger logger = LogManager.getLogger(LogerAop.class);
	
	
	//@Around("execution(* com.szprism.service.impl.*ServiceImpl.add*(..)) OR execution(* com.szprism.service.impl.*ServiceImpl.delete*(..)) OR execution(* com.szprism.service.impl.*ServiceImpl.update*(..))")
	@Around("execution(* cn.healthmanage.controller.*Controller.*(..))")
    public Object method(ProceedingJoinPoint joinPoint) throws Throwable {
		
		
        //Object[] args = joinPoint.getArgs();
		String methodName = joinPoint.getSignature().getName();
		//Class<?> c = joinPoint.getSignature().getDeclaringType();
		//if (c.isAssignableFrom(UserService.class))
        
		logger.debug("您调用了方法:"+methodName);
		
        Object object = joinPoint.proceed();
        return object;
    }
	
}
