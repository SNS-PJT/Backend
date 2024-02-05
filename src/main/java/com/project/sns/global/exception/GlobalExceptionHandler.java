package com.project.sns.global.exception;

import com.project.sns.global.dto.HttpResponseDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e) {
        return HttpResponseDto.fail(HttpStatus.valueOf(e.getStatusCode()), e.getMessage());
    }

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다. HttpMessageConverter 에서 등록한
     * HttpMessageConverter binding 못할경우 발생 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return HttpResponseDto.fail(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
    }

    /**
     * @ModelAttribute 으로 binding error 발생시 BindException 발생한다. ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<?> handleBindException(BindException e) {
        final String errorMessage = getBindingResultErrorMessage(e.getBindingResult());
        return HttpResponseDto.fail(HttpStatus.BAD_REQUEST, errorMessage);
    }

    private String getBindingResultErrorMessage(BindingResult bindingResult) {
        ObjectError objectError = bindingResult.getAllErrors()
                                               .stream()
                                               .findFirst()
                                               .get();
        return objectError.getDefaultMessage();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        return HttpResponseDto.fail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        return HttpResponseDto.fail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handlerHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        return HttpResponseDto.fail(HttpStatus.BAD_REQUEST, e.getMessage());
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleUploadSizeExceeded(MaxUploadSizeExceededException e) {
        return HttpResponseDto.fail(HttpStatus.PAYLOAD_TOO_LARGE, "20MB 이하의 이미지를 등록해주세요.");
    }

}
