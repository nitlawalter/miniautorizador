package com.walter.miniautorizadorapi.repositories;

import com.walter.miniautorizadorapi.entities.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    boolean existsByNumeroCartao(String numeroCartao);

    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
