package com.geradordenotas.gerador_de_notas.repository;

import com.geradordenotas.gerador_de_notas.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByDataBetween(LocalDate inicio, LocalDate fim);

}
