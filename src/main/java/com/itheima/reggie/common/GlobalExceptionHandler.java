package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**Global exception handler
 * @author Diyang Li
 * @create 2022-06-29 12:04 AM
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody  // return json data
@Slf4j
public class GlobalExceptionHandler {
    /**
     * Exception handling method
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());

        if (ex.getMessage().contains("Duplicate")){
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + " already existed";
            return R.error(msg);
        }

        return R.error("Unknown Error");
    }
}
