package dev.waterticket.jobdemo.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Parameter is not valid.")
public class ParameterException extends RuntimeException{
}
