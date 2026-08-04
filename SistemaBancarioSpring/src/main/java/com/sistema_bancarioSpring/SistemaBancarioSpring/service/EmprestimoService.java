package com.sistema_bancarioSpring.SistemaBancarioSpring.service;

import com.sistema_bancarioSpring.SistemaBancarioSpring.Emprestimo.Emprestimo;
import com.sistema_bancarioSpring.SistemaBancarioSpring.Emprestimo.StatusEmprestimo;
import com.sistema_bancarioSpring.SistemaBancarioSpring.cliente.Pessoa;
import com.sistema_bancarioSpring.SistemaBancarioSpring.cliente.TipoPessoa;
import com.sistema_bancarioSpring.SistemaBancarioSpring.repositorio.EmprestimoRepositorio;
import com.sistema_bancarioSpring.SistemaBancarioSpring.repositorio.PessoaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepositorio emprestimoRepositorio;
    private final PessoaRepositorio pessoaRepositorio;



    public Emprestimo solicitar(
            TipoPessoa tipoPessoa,
            Double valor,
            Integer meses) {

        if (meses > 24) {
            throw new RuntimeException(
                    "Prazo máximo permitido é 24 meses.");
        }

        double juros;

        if (tipoPessoa == TipoPessoa.PF) {

            if (valor <= 1000) {
                juros = 0.035;
            } else if (valor <= 5000) {
                juros = 0.030;
            } else {
                juros = 0.025;
            }

        } else {

            if (valor <= 10000) {
                juros = 0.025;
            } else if (valor <= 50000) {
                juros = 0.020;
            } else {
                juros = 0.015;
            }
        }

        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setValor(valor);
        emprestimo.setMeses(meses);
        emprestimo.setJuros(juros);
        emprestimo.setStatus(StatusEmprestimo.NEGADO);

        return emprestimo;
    }


    public Emprestimo salvar(Long pessoaId, Emprestimo emprestimo) {

        Pessoa pessoa = pessoaRepositorio.findById(pessoaId)
                .orElseThrow(() ->
                        new RuntimeException("Pessoa não encontrada"));

        emprestimo.setPessoa(pessoa);

        return emprestimoRepositorio.save(emprestimo);
    }

    public void atualizarStatus(Long emprestimoId,
                                StatusEmprestimo status) {

        Emprestimo emprestimo = emprestimoRepositorio.findById(emprestimoId)
                .orElseThrow(() ->
                        new RuntimeException("Empréstimo não encontrado"));

        emprestimo.setStatus(status);

        emprestimoRepositorio.save(emprestimo);
    }

    public Emprestimo consultarUltimoEmprestimo(Long pessoaId) {

        return emprestimoRepositorio
                .findFirstByPessoaIdOrderByIdDesc(pessoaId)
                .orElseThrow(() ->
                        new RuntimeException("Nenhum empréstimo encontrado"));
    }



    public String relatorioEmprestimo(Long pessoaId) {

        Emprestimo emprestimo = emprestimoRepositorio
                .findFirstByPessoaIdOrderByIdDesc(pessoaId)
                .orElseThrow(() ->
                        new RuntimeException("Nenhum empréstimo encontrado"));

        double valor = emprestimo.getValor();
        int meses = emprestimo.getMeses();
        double juros = emprestimo.getJuros();

        double valorParcelaBase = valor / meses;
        double jurosParcela = valorParcelaBase * juros;
        double totalParcela = valorParcelaBase + jurosParcela;

        double totalJuros = jurosParcela * meses;
        double valorTotal = valor + totalJuros;

        StringBuilder relatorio = new StringBuilder();

        relatorio.append("\n");
        relatorio.append("=====================================================\n");
        relatorio.append("                RELATORIO EMPRESTIMO\n");
        relatorio.append("=====================================================\n\n");

        relatorio.append("Status: ")
                .append(emprestimo.getStatus())
                .append("\n\n");

        relatorio.append(
                String.format("%-10s %-12s %-12s %-12s%n",
                        "Parcela",
                        "Valor",
                        "Juros",
                        "Total"));

        relatorio.append("-----------------------------------------------------\n");

        for (int i = 1; i <= meses; i++) {

            relatorio.append(
                    String.format(
                            "%-10d %-12.2f %-12.2f %-12.2f%n",
                            i,
                            valorParcelaBase,
                            jurosParcela,
                            totalParcela
                    )
            );
        }

        relatorio.append("-----------------------------------------------------\n");

        relatorio.append(
                String.format(
                        "Valor solicitado: R$ %.2f%n",
                        valor));

        relatorio.append(
                String.format(
                        "Valor total juros: R$ %.2f%n",
                        totalJuros));

        relatorio.append(
                String.format(
                        "Valor total pagar: R$ %.2f%n",
                        valorTotal));

        relatorio.append("=====================================================\n");

        return relatorio.toString();
    }


}
