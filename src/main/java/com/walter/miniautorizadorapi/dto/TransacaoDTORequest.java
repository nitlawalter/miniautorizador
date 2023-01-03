package com.walter.miniautorizadorapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
