package com.getjavajob.training.timashovy.socialnetwork.web.controlleradvices;

import com.getjavajob.training.timashovy.socialnetwork.web.util.exceptions.WebException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebException.class)
    public String handleWebException() {
        return "error/500";
    }

}
