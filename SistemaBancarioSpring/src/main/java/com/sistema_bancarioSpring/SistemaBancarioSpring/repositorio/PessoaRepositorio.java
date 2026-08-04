package com.sistema_bancarioSpring.SistemaBancarioSpring.repositorio;

import com.sistema_bancarioSpring.SistemaBancarioSpring.cliente.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepositorio extends JpaRepository <Pessoa, Long> {
    Optional<Pessoa> findByNome(String nome);
}
