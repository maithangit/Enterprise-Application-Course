package edu.mum.cs544;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Date;

@Aspect
@Component
public class LogAspect {
    @Around("target(edu.mum.cs544.ICustomerDAO)")
    public Object invoke(ProceedingJoinPoint call ) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start(call.getSignature().getName());
        Object retVal = call.proceed();
        sw.stop();
        long totaltime = sw.getLastTaskTimeMillis();
        // print the time to the console
        System.out.println("Time to execute save = "+ totaltime);
        return retVal;
    }

    //@After("target(edu.mum.cs544.IEmailSender) && args(String,String)")
    @After("execution(* edu.mum.cs544.EmailSender.sendEmail(..)) && args(String,String)")
    public void logAfter(JoinPoint joinpoint) {

        System.out.println(new Date() + " method= " +  joinpoint.getSignature().getName()
                                    +"address = " + joinpoint.getArgs()[0] +" message= " + joinpoint.getArgs()[1] +"\n"
                                    + "outgoing email server=  " + joinpoint.getTarget());
    }
}
