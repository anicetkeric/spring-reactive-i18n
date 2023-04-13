package com.bootlabs.springreactivei18n.exception;

import com.bootlabs.springreactivei18n.config.Translator;
import com.bootlabs.springreactivei18n.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class BaseControllerAdvice {

    @ExceptionHandler({WebExchangeBindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponseDTO> handleRequestBodyError(WebExchangeBindException ex, ServerWebExchange exchange){

        var error = ex.getBindingResult().getAllErrors().stream()
                .map(errorMessage -> MessageFormat.format("{0} {1}", ((FieldError) errorMessage).getField(), errorMessage.getDefaultMessage()))
                .sorted()
                .collect(Collectors.joining(","));
        log.error("errorList : {} - Exception caught in handleRequestBodyError :  {} ", error, ex.getMessage());
        return Mono.just(new ErrorResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), Translator.getMessage("validation.failed.entity", exchange), Translator.getMessage(error, exchange)));
    }

    @ExceptionHandler({Exception.class, BaseException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponseDTO> handleAllExceptions(Exception ex, ServerWebExchange exchange) {
        log.debug(ex.getMessage(), ex.getCause());
        return Mono.just(new ErrorResponseDTO(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), Translator.getMessage("internal.server.exception", exchange), Translator.getMessage(ex.getMessage(), exchange)));
    }

    @ExceptionHandler({ IllegalArgumentException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponseDTO> handleBadRequestException(Exception ex, ServerWebExchange exchange) {
        log.debug(ex.getMessage(), ex.getCause());
        return Mono.just(new ErrorResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), Translator.getMessage("bad.request.exception", exchange), Translator.getMessage(ex.getMessage(), exchange)));
    }

    @ExceptionHandler({DataNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponseDTO> noDataFoundException(DataNotFoundException ex, ServerWebExchange exchange) {
        log.debug(ex.getMessage(), ex.getCause());
        return getErrorResponse(ex, String.valueOf(HttpStatus.NOT_FOUND.value()), exchange);
    }

    /**
     * @param ex exception type ServiceException
     * @param statusCodeValue status code
     * @param exchange server exchange
     * @return Mono ErrorResponseDTO
     */
    private Mono<ErrorResponseDTO> getErrorResponse(BaseException ex, String statusCodeValue, ServerWebExchange exchange){
        String errorMessage = (ObjectUtils.isNotEmpty(ex.args) && CollectionUtils.isNotEmpty(Arrays.asList(ex.args))) ? Translator.getMessage(ex.getMessage(),ex.args, exchange)
                                                                                        : Translator.getMessage(ex.getMessage(), exchange);
        return Mono.just(new ErrorResponseDTO(statusCodeValue, errorMessage, errorMessage));
    }


}
