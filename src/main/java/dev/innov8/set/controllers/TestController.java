package dev.innov8.set.controllers;

import dev.innov8.set.exceptions.ForbiddenRequestException;
import dev.innov8.set.security.Secured;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class TestController {

    @GetMapping("/auth")
    public void auth(HttpServletRequest req) {
        HttpSession userSession = req.getSession();
        userSession.setAttribute("secret-key", "revature");
    }

    @Secured(secretKey = "revature")
    @GetMapping("/protected")
    public String protectedEndpoint() {
        return "This endpoint is protected!";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleForbiddenRequest(ForbiddenRequestException e) {

    }

}
