package com.geradordenotas.gerador_de_notas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "receitas")

public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Descriçao é obrigatória")
    private String descricao;

    @NotNull(message = "Valor não pode ser vazio")
    @Positive(message = "Valor não pode ser negativo")
    private BigDecimal valor;

    @NotNull(message = "A data e Obrigatória")
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private FonteReceita fonte;



}
