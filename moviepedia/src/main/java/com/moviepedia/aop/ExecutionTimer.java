package com.moviepedia.aop;
// 메서드 실행 시간 측정 AOP

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ExecutionTimer {

	
	// 조인트포인트를 어노테이션으로 지정
	@Pointcut("@annotation(com.moviepedia.aop.ExeTimer)")
	private void timer() {};
	
	@Around("timer()")
		// 메서드 실행 전후로 시간을 공유해야하기 때문에 @Around 사용
	public void AssumeExcutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		
		StopWatch stopWatch = new StopWatch();
		
		stopWatch.start();
		
		joinPoint.proceed(); // 조인포인트의 메서드 실행
		
		stopWatch.stop();
		
		long totalTimeMillis = stopWatch.getTotalTimeMillis();
		
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String methodName = signature.getMethod().getName();
		
		log.info("실행 메서드: {}, 실행시간 = {}ms", methodName, totalTimeMillis);
		
	}
}
