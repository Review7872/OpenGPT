package online.fadai.opengptproject.aspect;


import io.jsonwebtoken.Claims;
import online.fadai.opengptproject.dto.UserInfoRequest;
import online.fadai.opengptproject.utils.JWTUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Aspect
@Component
public class AuthFilter {

    @Around("execution(* online.fadai.opengptproject.controller.authController.*.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        ServerWebExchange exchange = (ServerWebExchange) joinPoint.getArgs()[0];
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        Claims claimsFromJwt = JWTUtil.getClaimsFromJwt(token);
        exchange.getAttributes().put("userData", new UserInfoRequest((String) claimsFromJwt.get("username"), null, (String) claimsFromJwt.get("email")));
        return joinPoint.proceed();

    }
}
