package com.geradordenotas.gerador_de_notas.repository;

import com.geradordenotas.gerador_de_notas.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}
