package com.geradordenotas.gerador_de_notas.controller;

import com.geradordenotas.gerador_de_notas.entity.Despesa;
import com.geradordenotas.gerador_de_notas.entity.Receita;
import com.geradordenotas.gerador_de_notas.service.DespesaService;
import com.geradordenotas.gerador_de_notas.service.ReceitaService;
import com.geradordenotas.gerador_de_notas.service.RelatorioDespesaService;
import com.geradordenotas.gerador_de_notas.service.RelatorioReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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
    public Receita criar(@RequestBody Receita receita){

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
            return ResponseEntity.ok(receita.get()); // retorna 200 OK com o objeto
        } else {
            return ResponseEntity.notFound().build(); // retorna 404 se não encontrou
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma receita por ID")
    public ResponseEntity<Receita> atualizar(@PathVariable Long id, @RequestBody Receita receita) {
        return reService.buscarPorId(id)
                .map(r -> {
                    receita.setId(id); // mantém o mesmo ID
                    return ResponseEntity.ok(reService.criar(receita)); // salvar atualizando
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
        return relatorioReceitaService.gerarRelatorio(inicio, fim);
    }
}




