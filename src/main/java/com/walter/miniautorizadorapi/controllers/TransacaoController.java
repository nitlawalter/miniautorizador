package com.walter.miniautorizadorapi.controllers;

import com.walter.miniautorizadorapi.dto.TransacaoDTORequest;
import com.walter.miniautorizadorapi.entities.Cartao;
import com.walter.miniautorizadorapi.exceptions.CartaoInexistenteException;
import com.walter.miniautorizadorapi.services.CartaoService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/transacoes")
@ApiModel("Trasacao API")
public class TransacaoController {

    private CartaoService cartaoService;

    public TransacaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Realiza Transação no Cartão")
    public String realizarTransacao(@RequestBody @Valid TransacaoDTORequest dto) {

        Cartao cartao = cartaoService.pesquisar(dto.getNumeroCartao())
                .orElseThrow(() -> new CartaoInexistenteException("Cartão inexistente!"));

        cartaoService.atualizar(cartao, dto);

        return String.valueOf("OK");
    }
}
