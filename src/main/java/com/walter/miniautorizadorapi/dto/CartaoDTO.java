package com.walter.miniautorizadorapi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoDTO {

    private Long id;

    @NotEmpty
    private String numeroCartao;

    @NotEmpty
    private String senha;

    private BigDecimal saldo;

}
