package online.fadai.opengptproject.controller;

import lombok.extern.slf4j.Slf4j;
import online.fadai.opengptproject.exception.*;
import online.fadai.opengptproject.utils.Result;
import online.fadai.opengptproject.utils.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler({ModelInvalidException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<String> modelInvalidExceptionHandler(ModelInvalidException e) {
        log.error(e.getMessage());
        return ResultUtil.success(e.getMessage());
    }

    @ExceptionHandler({NoCauseException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> noCauseExceptionHandler(NoCauseException e) {
        log.error(e.getMessage());
        return ResultUtil.failure(e.getMessage());
    }

    @ExceptionHandler({NotUniqueException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<String> usernameExceptionHandler(NotUniqueException e) {
        log.error(e.getMessage());
        return ResultUtil.success(e.getMessage());
    }

    @ExceptionHandler({PasswordException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<String> passwordExceptionHandler(PasswordException e) {
        log.error(e.getMessage());
        return ResultUtil.success(e.getMessage());
    }

    @ExceptionHandler({UsernameException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<String> usernameExceptionHandler(UsernameException e) {
        log.error(e.getMessage());
        return ResultUtil.success(e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> otherExceptionHandler(Exception e) {
        log.error(e.getMessage());
        return ResultUtil.failure("系统繁忙");
    }
}
