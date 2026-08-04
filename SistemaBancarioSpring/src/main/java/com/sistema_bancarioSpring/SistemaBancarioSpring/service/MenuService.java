
package com.sistema_bancarioSpring.SistemaBancarioSpring.service;

import com.sistema_bancarioSpring.SistemaBancarioSpring.Emprestimo.Emprestimo;
import com.sistema_bancarioSpring.SistemaBancarioSpring.Emprestimo.StatusEmprestimo;
import com.sistema_bancarioSpring.SistemaBancarioSpring.cliente.Pessoa;
import com.sistema_bancarioSpring.SistemaBancarioSpring.repositorio.PessoaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final PessoaRepositorio pessoaRepositorio;
    private final ContaService contaService;
    private final EmprestimoService emprestimoService;

    private final Scanner scanner = new Scanner(System.in);

    public void iniciar() {

        System.out.println("=================================");
        System.out.println("      SISTEMA BANCARIO");
        System.out.println("=================================");

        System.out.print("Qual o nome do cliente? ");

        String nome = scanner.nextLine();

        Pessoa pessoa = pessoaRepositorio.findByNome(nome)
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado."));

        boolean executando = true;

        while (executando) {

            exibirMenu();

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1 -> consultarSaldo(pessoa.getId());

                case 2 -> depositar(pessoa.getId());

                case 3 -> sacar(pessoa.getId());

                case 4 -> solicitarEmprestimo(
                        pessoa.getId(),
                        pessoa.getNome());

                case 5 -> consultarUltimoEmprestimo(
                        pessoa.getId());

                case 6 -> relatorioEmprestimo(
                        pessoa.getId());

                case 7 -> {
                    executando = false;
                    System.out.println("Sistema encerrado.");
                }

                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void exibirMenu() {

        System.out.println();
        System.out.println("1 - Consultar saldo");
        System.out.println("2 - Depositar");
        System.out.println("3 - Sacar");
        System.out.println("4 - Solicitar empréstimo");
        System.out.println("5 - Consultar último empréstimo");
        System.out.println("6 - Relatório empréstimo");
        System.out.println("7 - Sair");
        System.out.print("Opção: ");
    }

    private void consultarSaldo(Long pessoaId) {

        Double saldo =
                contaService.ConsultarSaldo(pessoaId);

        System.out.println(
                "Saldo atual: R$ " + saldo);
    }

    private void depositar(Long pessoaId) {

        System.out.print(
                "Valor do depósito: R$ ");

        Double valor =
                scanner.nextDouble();

        scanner.nextLine();

        contaService.depositar(
                pessoaId,
                valor);

        System.out.println(
                "Depósito realizado com sucesso.");
    }

    private void sacar(Long pessoaId) {

        System.out.print(
                "Valor para saque: R$ ");

        Double valor =
                scanner.nextDouble();

        scanner.nextLine();

        contaService.sacar(
                pessoaId,
                valor);

        System.out.println(
                "Saque realizado com sucesso.");
    }

    private void solicitarEmprestimo(
            Long pessoaId,
            String nome) {

        System.out.print(
                "Valor desejado: R$ ");

        Double valor =
                scanner.nextDouble();

        System.out.print(
                "Prazo (meses): ");

        Integer meses =
                scanner.nextInt();

        scanner.nextLine();

        Pessoa pessoa =
                pessoaRepositorio.findById(pessoaId)
                        .orElseThrow(() ->
                                new RuntimeException("Pessoa não encontrada"));

        Emprestimo emprestimo =
                emprestimoService.solicitar(
                        pessoa.getTipo(),
                        valor,
                        meses);

        emprestimo =
                emprestimoService.salvar(
                        pessoaId,
                        emprestimo);

        System.out.println();
        System.out.println(
                "======= ANALISE GERENTE =======");

        System.out.println(
                "Cliente: " + nome);

        System.out.println(
                "Valor solicitado: R$ " + valor);

        String senhaAdmin = "lasanha";

        System.out.print(
                "Senha do administrador: ");

        String senhaDigitada =
                scanner.nextLine();

        if (!senhaAdmin.equals(senhaDigitada)) {

            emprestimoService.atualizarStatus(
                    emprestimo.getId(),
                    StatusEmprestimo.NEGADO);

            System.out.println(
                    "Senha inválida. Empréstimo negado.");

            return;
        }

        System.out.print(
                "Aprovar empréstimo? (S/N): ");

        String resposta =
                scanner.nextLine();

        if (resposta.equalsIgnoreCase("S")) {

            emprestimoService.atualizarStatus(
                    emprestimo.getId(),
                    StatusEmprestimo.APROVADO);

            contaService.depositar(
                    pessoaId,
                    valor);

            System.out.println(
                    "Empréstimo aprovado!");

        } else {

            emprestimoService.atualizarStatus(
                    emprestimo.getId(),
                    StatusEmprestimo.NEGADO);

            System.out.println(
                    "Empréstimo negado!");
        }
    }

    private void consultarUltimoEmprestimo(
            Long pessoaId) {

        Emprestimo emprestimo =
                emprestimoService
                        .consultarUltimoEmprestimo(
                                pessoaId);

        System.out.println();
        System.out.println(
                "===== ULTIMO EMPRESTIMO =====");

        System.out.println(
                "Valor: R$ " +
                        emprestimo.getValor());

        System.out.println(
                "Prazo: " +
                        emprestimo.getMeses() +
                        " meses");

        System.out.println(
                "Juros: " +
                        (emprestimo.getJuros() * 100)
                        + "%");

        System.out.println(
                "Status: " +
                        emprestimo.getStatus());
    }

    private void relatorioEmprestimo(
            Long pessoaId) {

        System.out.println(
                emprestimoService
                        .relatorioEmprestimo(
                                pessoaId));
    }
}