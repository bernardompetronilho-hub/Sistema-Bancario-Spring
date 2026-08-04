package com.sistema_bancarioSpring.SistemaBancarioSpring.cliente;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "pessoa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String endereco;
    private long telefoneContato;

    @Enumerated(EnumType.STRING) private TipoPessoa tipo;

}
