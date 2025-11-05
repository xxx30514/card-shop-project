package com.myproject.cardshop.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
	// *跟包名間一定要有空格 指定此包下的所有方法
	@Pointcut("execution(* com.myproject.cardshop.controller..*(..))")
	public void controllerMethods() {
	}

	@Pointcut("execution(* com.myproject.cardshop.service..*(..))")
	public void serviceMethods() {
	}

	@Around("controllerMethods() || serviceMethods()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long startNano = System.nanoTime();
		String declaringType = safeDeclaringType(joinPoint);// 原始類名 父類名
		String targetType = safeTargetType(joinPoint);// 子類 代理類名
		String methodName = joinPoint.getSignature().getName();
		String argsStr = safeArgs(joinPoint.getArgs());

		// example: 在 MDC 放入 traceId（可用於日誌串流）
		// String traceId = UUID.randomUUID().toString();
		// MDC.put("traceId", traceId);
		log.info("執行開始: [聲明類型:{}], [實際執行類型:{}], [方法名稱:{}()], [方法參數:{}]", declaringType, targetType, methodName, argsStr);

		Object result;
		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			log.error("方法: {}.{}(), [執行時發生錯誤：{}]", targetType, methodName, e.getMessage(), e);
			throw e;
		} finally {
			// 清除 MDC，避免污染下一個 thread/request
			// MDC.remove("traceId");
		}

		long elapsedMs = (System.nanoTime() - startNano) / 1_000_000;
		if (log.isDebugEnabled()) {
			log.debug("執行完畢: [實際執行類型:{}], [方法名稱:{}()], [耗時:{}ms], [回傳結果:{}]", targetType, methodName, elapsedMs,
					safeResult(result));
		} else {
			log.info("執行完畢: [實際執行類型:{}], [方法名稱:{}()], [耗時:{}ms]", targetType, methodName, elapsedMs);
		}

		return result;
	}

	private String safeDeclaringType(ProceedingJoinPoint joinPoint) {
		try {
			return joinPoint.getSignature().getDeclaringTypeName();
		} catch (Exception e) {
			return "UnknownDeclaringType";
		}
	}

	private String safeTargetType(ProceedingJoinPoint joinPoint) {
		try {
			Object type = joinPoint.getTarget();
			return type != null ? type.getClass().getName() : "null";
		} catch (Exception e) {
			return "UnknownTarget";
		}
	}

	private String safeArgs(Object[] args) {
		try {
			if (args == null || args.length == 0)
				return "[]";
			return Arrays.toString(args);
		} catch (Exception e) {
			return "[unserializable args]";
		}
	}

	private String safeResult(Object result) {
		try {
			if (result == null)
				return "null";
			return result.toString();
		} catch (Exception e) {
			return "[unserializable result]";
		}
	}
}
