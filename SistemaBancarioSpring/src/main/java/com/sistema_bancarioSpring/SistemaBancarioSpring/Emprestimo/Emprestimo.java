package com.sistema_bancarioSpring.SistemaBancarioSpring.Emprestimo;

import com.sistema_bancarioSpring.SistemaBancarioSpring.cliente.Pessoa;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emprestimo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;

    private Integer meses;

    private Double juros;

    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
}