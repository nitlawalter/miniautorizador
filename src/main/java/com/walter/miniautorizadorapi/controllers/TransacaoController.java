package com.walter.miniautorizadorapi.controllers;

import com.walter.miniautorizadorapi.dto.TransacaoDTORequest;
import com.walter.miniautorizadorapi.entities.Cartao;
import com.walter.miniautorizadorapi.exceptions.CartaoInexistenteException;
import com.walter.miniautorizadorapi.services.CartaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String realizarTransacao(@RequestBody @Valid TransacaoDTORequest dto) {

        Cartao cartao = cartaoService.pesquisar(dto.getNumeroCartao())
                .orElseThrow(() -> new CartaoInexistenteException("Cartão inexistente!"));

        cartaoService.atualizar(cartao, dto);

        return String.valueOf("OK");
    }
}
