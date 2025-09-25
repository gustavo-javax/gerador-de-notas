package com.geradordenotas.gerador_de_notas.repository;

import com.geradordenotas.gerador_de_notas.entity.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}
