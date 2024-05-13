package online.fadai.opengptproject.aop;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthFilter {
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;
    @Around("execution(* online.fadai.opengptproject.controller.authController.*.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute("user");
        if (user==null||user.isEmpty()){
            response.sendRedirect("/login.html");
        }
        return joinPoint.proceed();
    }
}
