package com.mulsalasoft.interview.drones;

import com.mulsalasoft.interview.drones.models.BaseResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GenericExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseResponse> handleException(EntityNotFoundException ex) {
        log.error("Entity Not Found Exception ::: {}", ex);
        BaseResponse response = new BaseResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<BaseResponse> handleException(IllegalStateException ex) {
        log.error("Illegal State Exception ::: {}", ex);
        BaseResponse response = new BaseResponse("invalid document/data sent, ".concat(ex.getLocalizedMessage()));
        return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleException(RuntimeException ex) {
        log.error("Runtime Exception ::: {}", ex);
        BaseResponse response = new BaseResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handleException(ResourceAccessException ex) {
        log.error("Resource Access Exception ::: {}", ex);

        return new ResponseEntity<>("Error getting data", HttpStatus.OK);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<BaseResponse> handleException(UnsupportedOperationException ex) {
        log.error("Unsupported Operation Exception ::: {}", ex);
        BaseResponse response = new BaseResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> handleException(IllegalArgumentException ex) {
        log.error("Illegal Argument Exception ::: {}", ex);
        BaseResponse response = new BaseResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleException(MethodArgumentNotValidException ex) {
        log.error("Method Argument Not Valid Exception ::: {}", ex);

        BindingResult result = ex.getBindingResult();

        StringBuilder errorList = new StringBuilder();
        result.getFieldErrors().forEach(fieldError
                -> errorList.append(fieldError.getField()).append(" : ").append(fieldError.getDefaultMessage()).append(" : rejected value [").append(fieldError.getRejectedValue()).append("]"));
        result.getGlobalErrors().forEach(fieldError -> errorList.append(fieldError.getDefaultMessage()));

        String description = null;

        if (result.getFieldErrors().size() > 2) {

            StringBuilder fieldNames = new StringBuilder();
            result.getFieldErrors().forEach(fieldError -> fieldNames.append(fieldError.getField()).append(","));
            //remove last ,
            fieldNames.deleteCharAt(fieldNames.length() - 1);
            description = "kindly verify that all input fields contain valid information, check the following fields : ".concat(fieldNames.toString());
        } else {
            description = errorList.toString();
        }

        BaseResponse baseResponse = new BaseResponse(description);

        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<BaseResponse> handleException(NoSuchElementException ex) {
        log.error("No Such Element Exception ::: {}", ex);

        BaseResponse response = new BaseResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<BaseResponse> handleException(MissingServletRequestPartException ex) {
        log.error("Missing Servlet RequestPart Exception ::: {}", ex);

        BaseResponse response = new BaseResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponse> handleException(DataIntegrityViolationException ex, WebRequest request) {
        log.error("Data Integrity Violation Exception ::: {}", ex);

        String message = ex.getMostSpecificCause().getMessage();

        if (message != null) {
            message = message.split("for")[0];
        }

        BaseResponse baseResponse = new BaseResponse(message);

        return new ResponseEntity<>(baseResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse> handleException(ConstraintViolationException ex) {
        log.error("Constraint Violation Exception ::: {}", ex);
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append("\n").append(violation.getMessage()));
            errorMessage = builder.toString();
        }
        BaseResponse baseResponse = new BaseResponse(errorMessage);

        return new ResponseEntity<>(baseResponse, HttpStatus.CONFLICT);
    }
}
