package com.sistema_bancarioSpring.SistemaBancarioSpring.service;

import com.sistema_bancarioSpring.SistemaBancarioSpring.cliente.Pessoa;
import com.sistema_bancarioSpring.SistemaBancarioSpring.conta.Conta;
import com.sistema_bancarioSpring.SistemaBancarioSpring.repositorio.ContaRepositorio;
import com.sistema_bancarioSpring.SistemaBancarioSpring.repositorio.PessoaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class ContaService {

    private final ContaRepositorio contaRepositorio;
    private final PessoaRepositorio pessoaRepositorio;


    public void depositar(Long pessoaId, Double valor) {

        Conta conta = contaRepositorio.findByPessoaId(pessoaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.setSaldo(conta.getSaldo() + valor);
        contaRepositorio.save(conta);

    }

    public void sacar(Long pessoaId, Double valor) {
        Conta conta = contaRepositorio.findByPessoaId(pessoaId).orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (valor > conta.getSaldo()) {
            throw new RuntimeException("Voce nao tem dinheiro");
        }

        conta.setSaldo(conta.getSaldo() - valor);
        contaRepositorio.save(conta);
    }

    public Double ConsultarSaldo(Long pessoaId) {

        Conta conta = contaRepositorio.findByPessoaId(pessoaId)
                .orElseThrow(() ->
                        new RuntimeException("Conta não encontrada"));

        return conta.getSaldo();
    }

    public Pessoa ConslutarPessoa (Long pessoaId) {
        Conta conta = contaRepositorio.findByPessoaId(pessoaId).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        return conta.getPessoa();
    }


}
