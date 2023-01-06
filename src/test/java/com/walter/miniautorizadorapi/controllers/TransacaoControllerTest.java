package com.walter.miniautorizadorapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walter.miniautorizadorapi.dto.CartaoDTO;
import com.walter.miniautorizadorapi.dto.TransacaoDTORequest;
import com.walter.miniautorizadorapi.entities.Cartao;
import com.walter.miniautorizadorapi.services.CartaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = TransacaoController.class)
@AutoConfigureMockMvc
class TransacaoControllerTest {

    static String API = "/transacoes";

    @Autowired
    MockMvc mvc;

    @MockBean
    CartaoService service;



    @Test
    @DisplayName("Deve atualizar o saldo de um cartão por meio de uma transação")
    void deveAtualizarSaldo() throws Exception {

        TransacaoDTORequest dto = TransacaoDTORequest.builder()
                .numeroCartao("123456789")
                .senhaCartao("123")
                .valor(new BigDecimal(10.00))
                .build();

        Cartao cartao = Cartao.builder()
                .id(1l)
                .numeroCartao(dto.getNumeroCartao())
                .senha(dto.getSenhaCartao())
                .saldo(new BigDecimal(500.00))
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);

        Mockito.when( service.pesquisar("123456789") ).thenReturn(Optional.of(cartao));
        Mockito.when( service.atualizar( cartao, dto )).thenReturn(cartao);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(requestBuilder)
                .andExpect( MockMvcResultMatchers.status().isCreated() );

    }

    @Test
    @DisplayName("Deve retornar exceção de cartão inexistente")
    void deveRetornarExecaoCartaoInexistente() throws Exception {

        TransacaoDTORequest dto = TransacaoDTORequest.builder()
                .numeroCartao("123456789")
                .senhaCartao("123")
                .valor(new BigDecimal(10.00))
                .build();

        Cartao cartao = Cartao.builder()
                .id(1l)
                .numeroCartao(dto.getNumeroCartao())
                .senha(dto.getSenhaCartao())
                .saldo(new BigDecimal(500.00))
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);

        Mockito.when( service.atualizar( Mockito.any(Cartao.class), Mockito.any(TransacaoDTORequest.class) )).thenReturn(cartao);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(requestBuilder)
                .andExpect( MockMvcResultMatchers.status().isNotFound() )
                .andExpect( MockMvcResultMatchers.jsonPath("errors").value("Cartão inexistente!") );

    }

}