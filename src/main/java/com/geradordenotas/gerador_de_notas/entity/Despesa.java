package com.geradordenotas.gerador_de_notas.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "despesas")


public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Descriçao e obrigatória")
    @Size(min = 3, max = 50, message = "A descrição tem que ter entre 3 a 50 caracteres")
    private String descricao;


    @NotNull(message = "Valor e obrigatório")
    private BigDecimal valor;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private CategoriaDespesas categoria;

}
