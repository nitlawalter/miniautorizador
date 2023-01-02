package com.walter.miniautorizadorapi.exceptions;

public class CartaoInexistenteException extends BusinessException {
    public CartaoInexistenteException(String s) {
        super(s);
    }
}
