package dev.innov8.set.security;

import dev.innov8.set.exceptions.ForbiddenRequestException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class SecurityAspect {

    @Around("@annotation(dev.innov8.set.security.Secured)")
    public Object secureEndpoint(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Secured securedAnno = method.getAnnotation(Secured.class);

        String secretKey = securedAnno.secretKey();
        String providedKey = (String) request.getSession().getAttribute("secret-key");

        if(!secretKey.equals(providedKey)) {
            throw new ForbiddenRequestException();
        }

        System.out.println("An authenticated request was made to a protected endpoint!");

        return pjp.proceed();

    }

}

