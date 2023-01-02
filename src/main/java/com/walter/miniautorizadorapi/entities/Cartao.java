package com.walter.miniautorizadorapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Cartao {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cartao")
    private String numeroCartao;

    @Column
    private String senha;

    @Column
    private BigDecimal saldo;

    public static boolean isCartaoInvalido(Cartao cartao) {
        return cartao == null || cartao.getId() == null;
    }

    public boolean isSenhaIgual(String senha) {
        return this.senha.equals(senha);
    }

    public boolean isSaldoSuficiente(BigDecimal valor) {
        return this.saldo.compareTo(valor) == 1;
    }
}
