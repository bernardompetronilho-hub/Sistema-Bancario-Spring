package com.sistema_bancarioSpring.SistemaBancarioSpring.repositorio;

import com.sistema_bancarioSpring.SistemaBancarioSpring.Emprestimo.Emprestimo;
import com.sistema_bancarioSpring.SistemaBancarioSpring.conta.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmprestimoRepositorio extends JpaRepository <Emprestimo, Long> {
    Optional<Emprestimo> findFirstByPessoaIdOrderByIdDesc(Long pessoaId);
}
