package com.geradordenotas.gerador_de_notas.service;

import com.geradordenotas.gerador_de_notas.entity.Despesa;
import com.geradordenotas.gerador_de_notas.entity.Receita;
import com.geradordenotas.gerador_de_notas.repository.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ReceitaService {

    private final ReceitaRepository receitarepo;

    public Receita criar(Receita receita){
        return receitarepo.save(receita);
    }

    public List<Receita> listar() {
        return receitarepo.findAll();
    }

    public Optional<Receita> buscarPorId(Long id){
        return receitarepo.findById(id);
    }


    public Receita atualizar(Receita receita){
        return receitarepo.save(receita);
    }

    public void deletar(Long id){
        receitarepo.deleteById(id);
    }



}
