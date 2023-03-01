package dev.waterticket.jobdemo.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Same ID exists.")
public class SameIDExistsException extends RuntimeException{
}
