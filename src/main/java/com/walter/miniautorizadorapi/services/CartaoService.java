package com.walter.miniautorizadorapi.services;

import com.walter.miniautorizadorapi.dto.TransacaoDTORequest;
import com.walter.miniautorizadorapi.entities.Cartao;
import com.walter.miniautorizadorapi.exceptions.*;
import com.walter.miniautorizadorapi.repositories.CartaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartaoService {

    private CartaoRepository repository;

    public CartaoService(CartaoRepository repository) {
        this.repository = repository;
    }

    public Cartao criar(Cartao cartao) {
        if ( this.repository.existsByNumeroCartao(cartao.getNumeroCartao()) ){
            throw new CartaoJaCadastradoException("Número de cartão já cadastrado!");
        }
        cartao.setSaldo(BigDecimal.valueOf(500.00));
        return this.repository.save(cartao);
    }

    public Optional<Cartao> pesquisar(String numeroCartao) {
        return this.repository.findByNumeroCartao(numeroCartao);
    }

    public Cartao atualizar(Cartao cartao, TransacaoDTORequest dto) {

        if ( !cartao.isSenhaIgual(dto.getSenhaCartao()) ) {
            throw new CartaoSenhaInvalidaException("Senha Inválida!");
        }

        if ( !cartao.isSaldoSuficiente(dto.getValor()) ){
            throw new CartaoSaldoInsuficienteException("Saldo Insuficiente!");
        }

        cartao.setSaldo( cartao.getSaldo().subtract(dto.getValor()) );

        return this.repository.save(cartao);
    }

}
