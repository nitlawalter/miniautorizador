package com.walter.miniautorizadorapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walter.miniautorizadorapi.dto.CartaoDTO;
import com.walter.miniautorizadorapi.entities.Cartao;
import com.walter.miniautorizadorapi.exceptions.CartaoInexistenteException;
import com.walter.miniautorizadorapi.exceptions.CartaoJaCadastradoException;
import com.walter.miniautorizadorapi.services.CartaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CartaoController.class)
@AutoConfigureMockMvc
class CartaoControllerTest {

    static String API = "/cartoes";

    @Autowired
    MockMvc mvc;

    @MockBean
    CartaoService service;

    @Test
    @DisplayName("Deve criar um cartão")
    void deveCriarUmCartao() throws Exception {

        CartaoDTO dto = CartaoDTO.builder()
                .numeroCartao("123456789")
                .senha("123")
                .build();

        Cartao cartao = Cartao.builder()
                .id(1l)
                .numeroCartao(dto.getNumeroCartao())
                .senha(dto.getSenha())
                .saldo(new BigDecimal(500.00))
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);

        Mockito.when( service.criar(Mockito.any(Cartao.class)) ).thenReturn(cartao);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(requestBuilder)
                .andExpect( MockMvcResultMatchers.status().isCreated() )
                .andExpect( MockMvcResultMatchers.jsonPath("numeroCartao").value(dto.getNumeroCartao()) )
                .andExpect( MockMvcResultMatchers.jsonPath("senha").value(dto.getSenha()) );

    }

    @Test
    @DisplayName("Deve lançar um erro ao cadastrar um cartão sem os campos obrigatórios")
    void deveLancarErroCartaoSemCamposObrigatorios() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new CartaoDTO());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(requestBuilder)
                .andExpect( MockMvcResultMatchers.status().isBadRequest() );

    }

    @Test
    @DisplayName("Deve retornar exceção de cartão já cadastrado")
    void deveLancarErroCartaoJaCadastrado() throws Exception {

        CartaoDTO dto = CartaoDTO.builder()
                .numeroCartao("123456789")
                .senha("123")
                .build();

        Cartao cartao = Cartao.builder()
                .id(1l)
                .numeroCartao(dto.getNumeroCartao())
                .senha(dto.getSenha())
                .saldo(new BigDecimal(500.00))
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);

        Mockito.when( service.criar(Mockito.any(Cartao.class)) ).thenThrow(new CartaoJaCadastradoException("Número de cartão já cadastrado!"));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(requestBuilder)
                .andExpect( MockMvcResultMatchers.status().isUnprocessableEntity() )
                .andExpect( MockMvcResultMatchers.jsonPath("errors[0]").value("Número de cartão já cadastrado!"));

    }

    @Test
    @DisplayName("Deve pesquisar com sucesso")
    void devePesquisarComSucesso() throws Exception {
        String numeroCartao = "123456789";

        Cartao cartao = Cartao.builder()
                .id(1l)
                .numeroCartao("123456789")
                .senha("123")
                .saldo(new BigDecimal(500.00))
                .build();

        Optional<Cartao> cartaoRetorno = Optional.of(cartao);

        Mockito.when( service.pesquisar(numeroCartao) ).thenReturn(cartaoRetorno);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(API.concat("/" + numeroCartao))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect( MockMvcResultMatchers.status().is2xxSuccessful() );

    }

    @Test
    @DisplayName("Deve retornar exeção com erro de cartão inexistente")
    void devePesquisarComErro() throws Exception {
        String numeroCartao = "123456789";

        Mockito.when( service.pesquisar(numeroCartao) ).thenThrow(new CartaoInexistenteException("Cartão inexistente!"));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(API.concat("/" + numeroCartao))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect( MockMvcResultMatchers.status().isNotFound() )
                .andExpect( MockMvcResultMatchers.jsonPath("errors[0]").value("Cartão inexistente!"));

    }
}