package com.geradordenotas.gerador_de_notas.controller;

import com.geradordenotas.gerador_de_notas.entity.Despesa;
import com.geradordenotas.gerador_de_notas.entity.Receita;
import com.geradordenotas.gerador_de_notas.service.DespesaService;
import com.geradordenotas.gerador_de_notas.service.ReceitaService;
import com.geradordenotas.gerador_de_notas.service.RelatorioDespesaService;
import com.geradordenotas.gerador_de_notas.service.RelatorioReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receitas")
@RequiredArgsConstructor

public class ReceitaController {

    private final ReceitaService reService;
    private final RelatorioReceitaService relatorioReceitaService;

    @PostMapping
    @Operation(summary = "Cria uma receita")
    public Receita criar(@RequestBody @Valid  Receita receita){

        return reService.criar(receita);

    }

    @GetMapping
    @Operation(summary = "Lista todas as receitas")
    public List<Receita> listar(){
        return reService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista uma receita por ID")
    public ResponseEntity<Receita> buscar(@PathVariable Long id) {
        Optional<Receita> receita = reService.buscarPorId(id);

        if (receita.isPresent()) {
            return ResponseEntity.ok(receita.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma receita por ID")
    public ResponseEntity<Receita> atualizar(@PathVariable Long id, @RequestBody @Valid Receita receita) {
        return reService.buscarPorId(id)
                .map(r -> {
                    receita.setId(id);
                    return ResponseEntity.ok(reService.criar(receita));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma receita")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Receita> receitaExistente = reService.buscarPorId(id);
        if (receitaExistente.isPresent()) {
            reService.deletar(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/relatorio")
    @Operation(summary = "Cria um relatorio das receita")
    public ResponseEntity<byte[]> gerarRelatorio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        if(inicio == null || fim == null){
            throw new IllegalArgumentException("Parâmetros 'inicio' e 'fim' são obrigatórios.");
        }
        if(fim.isBefore(inicio)){
            throw new IllegalArgumentException("A data final nao pode ser anterior a data inicio");
        }

        return relatorioReceitaService.gerarRelatorio(inicio, fim);
    }
}




