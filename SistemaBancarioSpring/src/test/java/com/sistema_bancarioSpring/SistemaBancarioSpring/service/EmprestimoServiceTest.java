package com.sistema_bancarioSpring.SistemaBancarioSpring.service;

import com.sistema_bancarioSpring.SistemaBancarioSpring.cliente.TipoPessoa;
import com.sistema_bancarioSpring.SistemaBancarioSpring.Emprestimo.Emprestimo;
import com.sistema_bancarioSpring.SistemaBancarioSpring.repositorio.EmprestimoRepositorio;
import com.sistema_bancarioSpring.SistemaBancarioSpring.repositorio.PessoaRepositorio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepositorio emprestimoRepositorio;

    @Mock
    private PessoaRepositorio pessoaRepositorio;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Test
    void deveAplicarJurosPfAte1000() {

        Emprestimo emprestimo =
                emprestimoService.solicitar(
                        TipoPessoa.PF,
                        1000.0,
                        12);

        assertEquals(
                0.035,
                emprestimo.getJuros());
    }

    @Test
    void deveAplicarJurosPfAte5000() {

        Emprestimo emprestimo =  emprestimoService.solicitar(TipoPessoa.PF, 3000.0, 12);

        assertEquals(0.030, emprestimo.getJuros());
    }

    @Test
    void deveAplicarJurosPfAcima5000() {

        Emprestimo emprestimo =
                emprestimoService.solicitar(
                        TipoPessoa.PF,
                        10000.0,
                        12);

        assertEquals(
                0.025,
                emprestimo.getJuros());
    }

    @Test
    void deveAplicarJurosPjAte10000() {

        Emprestimo emprestimo =
                emprestimoService.solicitar(
                        TipoPessoa.PJ,
                        10000.0,
                        12);

        assertEquals(
                0.025,
                emprestimo.getJuros());
    }

    @Test
    void deveAplicarJurosPjAte50000() {

        Emprestimo emprestimo =
                emprestimoService.solicitar(
                        TipoPessoa.PJ,
                        30000.0,
                        12);

        assertEquals(
                0.020,
                emprestimo.getJuros());
    }

    @Test
    void deveAplicarJurosPjAcima50000() {

        Emprestimo emprestimo =
                emprestimoService.solicitar(
                        TipoPessoa.PJ,
                        100000.0,
                        12);

        assertEquals(
                0.015,
                emprestimo.getJuros());
    }

    @Test
    void deveLancarErroPrazoMaiorQue24Meses() {

        assertThrows(
                RuntimeException.class,
                () -> emprestimoService.solicitar(
                        TipoPessoa.PF,
                        1000.0,
                        36)
        );
    }


}
