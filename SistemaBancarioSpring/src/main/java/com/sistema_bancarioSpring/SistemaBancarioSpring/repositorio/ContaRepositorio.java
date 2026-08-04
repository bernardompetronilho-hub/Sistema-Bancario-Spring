package com.sistema_bancarioSpring.SistemaBancarioSpring.repositorio;

import com.sistema_bancarioSpring.SistemaBancarioSpring.conta.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepositorio extends JpaRepository <Conta, Long> {
    Optional<Conta> findByPessoaId(Long pessoaId);
}
