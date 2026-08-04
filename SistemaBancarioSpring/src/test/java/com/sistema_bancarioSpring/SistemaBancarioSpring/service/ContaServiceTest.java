package com.sistema_bancarioSpring.SistemaBancarioSpring.service;

import com.sistema_bancarioSpring.SistemaBancarioSpring.conta.Conta;
import com.sistema_bancarioSpring.SistemaBancarioSpring.repositorio.ContaRepositorio;
import com.sistema_bancarioSpring.SistemaBancarioSpring.repositorio.PessoaRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @Mock
    private ContaRepositorio contaRepositorio;

    @Mock
    private PessoaRepositorio pessoaRepositorio;

    @InjectMocks
    private ContaService contaService;

    @Test
    void deveDepositarValorNaConta() {

        Conta conta = new Conta();
        conta.setSaldo(1000.0);

        when(contaRepositorio.findByPessoaId(1L))
                .thenReturn(Optional.of(conta));

        contaService.depositar(1L, 500.0);

        assertEquals(1500.0, conta.getSaldo());

        verify(contaRepositorio).save(conta);
    }

    @Test
    void deveSacarValorDaConta() {

        Conta conta = new Conta();
        conta.setSaldo(1000.0);

        when(contaRepositorio.findByPessoaId(1L))
                .thenReturn(Optional.of(conta));

        contaService.sacar(1L, 300.0);

        assertEquals(700.0, conta.getSaldo());

        verify(contaRepositorio).save(conta);
    }

    @Test
    void deveLancarErroQuandoSaldoInsuficiente() {

        Conta conta = new Conta();
        conta.setSaldo(100.0);

        when(contaRepositorio.findByPessoaId(1L))
                .thenReturn(Optional.of(conta));

        RuntimeException erro =
                assertThrows(
                        RuntimeException.class,
                        () -> contaService.sacar(
                                1L,
                                500.0));

        assertEquals(
                "Voce nao tem dinheiro",
                erro.getMessage());
    }
    @Test
    void deveConsultarSaldo() {

        Conta conta = new Conta();
        conta.setSaldo(2500.0);

        when(contaRepositorio.findByPessoaId(1L))
                .thenReturn(Optional.of(conta));

        Double saldo =
                contaService.ConsultarSaldo(1L);

        assertEquals(2500.0, saldo);
    }
}