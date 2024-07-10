package ch.zli.m223.service.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You are not authorized to perform this action")
public class UnauthorizedAccessException extends RuntimeException {
}
