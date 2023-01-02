package com.walter.miniautorizadorapi.controllers;

import com.walter.miniautorizadorapi.dto.CartaoDTO;
import com.walter.miniautorizadorapi.dto.CartaoDTOResponse;
import com.walter.miniautorizadorapi.entities.Cartao;
import com.walter.miniautorizadorapi.exceptions.CartaoInexistenteException;
import com.walter.miniautorizadorapi.services.CartaoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private CartaoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartaoDTOResponse criar(@RequestBody @Valid CartaoDTO dto) {
        Cartao cartao  = service.criar(modelMapper.map(dto, Cartao.class));
        return modelMapper.map(cartao, CartaoDTOResponse.class);
    }

    @GetMapping("{numeroCartao}")
    public BigDecimal pesquisar(@PathVariable String numeroCartao) {
        CartaoDTO cartaoDTO = service.pesquisar(numeroCartao)
                .map(cartao -> modelMapper.map(cartao, CartaoDTO.class))
                .orElseThrow(() -> new CartaoInexistenteException("Cartão inexistente!"));

        return cartaoDTO.getSaldo();
    }

}
