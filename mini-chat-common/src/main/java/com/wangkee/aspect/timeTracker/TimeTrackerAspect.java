package com.wangkee.aspect.timeTracker;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Slf4j
@Component
public class TimeTrackerAspect {

    public static final String POINT_CUT =
            "@annotation(com.wangkee.aspect.timeTracker.TimeTracker) || " +
            "@within(com.wangkee.aspect.timeTracker.TimeTracker)";


    @Around(POINT_CUT)
    public Object trackExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();

        Object proceed = joinPoint.proceed();

        stopwatch.stop();

        String pointName = joinPoint.getTarget().getClass().getSimpleName()
                + "."
                + joinPoint.getSignature().getName();

        long executionTime = stopwatch.getTotalTimeMillis();
        if(executionTime > 2000){
            log.warn("{}执行时间过长，耗费时间{}ms", pointName, executionTime);
        }else{
            log.info("{}执行耗时，耗费时间{}ms", pointName, executionTime);
        }

        return proceed;
    }
}
