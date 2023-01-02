package com.walter.miniautorizadorapi.controllers;

import com.walter.miniautorizadorapi.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleBusinessException(BusinessException ex) {
        return new ApiErros(ex);
    }

    @ExceptionHandler(CartaoJaCadastradoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErros handleCartaoJaCadastradoException(CartaoJaCadastradoException ex) {
        return new ApiErros(ex);
    }

    @ExceptionHandler(CartaoInexistenteException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErros handleCartaoInexistenteException(CartaoInexistenteException ex) {
        return new ApiErros(ex);
    }

    @ExceptionHandler(CartaoSaldoInsuficienteException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErros handleCartaoSaldoInsuficienteException(CartaoSaldoInsuficienteException ex) {
        return new ApiErros(ex);
    }

    @ExceptionHandler(CartaoSenhaInvalidaException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErros handleCartaoSenhaInvalidaException(CartaoSenhaInvalidaException ex) {
        return new ApiErros(ex);
    }
}
