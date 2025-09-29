package com.geradordenotas.gerador_de_notas.service;

import com.geradordenotas.gerador_de_notas.entity.Despesa;
import com.geradordenotas.gerador_de_notas.repository.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class DespesaService {

    private final DespesaRepository despesarepo;

    public Despesa criar(Despesa despesa){
        return despesarepo.save(despesa);
    }

    public List<Despesa> listar(){
        return despesarepo.findAll();
    }

    public Optional<Despesa> buscarPorId(Long id){
        return despesarepo.findById(id);
    }

    public Despesa atualizar(Despesa despesa){
        return despesarepo.save(despesa);
    }

    public void deletar(Long id){
        despesarepo.deleteById(id);
    }

//    public List<Despesa> buscarPorPeriodo(LocalDate inicio, LocalDate fim){
//        return despesarepo.findByDataBetween(inicio, fim);
//    }




}
