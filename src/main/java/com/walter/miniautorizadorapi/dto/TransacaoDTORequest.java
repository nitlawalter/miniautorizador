package com.walter.miniautorizadorapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTORequest {

    @NotEmpty
    private String numeroCartao;

    @NotEmpty
    private String senhaCartao;

    @NotNull
    private BigDecimal valor;

}
