package com.geradordenotas.gerador_de_notas.controller;

import com.geradordenotas.gerador_de_notas.entity.Despesa;
import com.geradordenotas.gerador_de_notas.service.DespesaService;
import com.geradordenotas.gerador_de_notas.service.RelatorioDespesaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despesas")
@RequiredArgsConstructor
public class DespesaController {

    private final DespesaService despesaService;
    private final RelatorioDespesaService relatorioService;

    @PostMapping
    @Operation(summary = "Criar despesas", description = "Essa rota cria no banco de dados uma despesa")
    public ResponseEntity<Despesa> criar(@RequestBody @Valid Despesa despesa) {
        Despesa criada = despesaService.criar(despesa);
        return ResponseEntity
                .status(201) // 201 Created
                .body(criada);
    }

    @GetMapping
    @Operation(summary = "Lista todas as despesas", description = "Essa rota puxa do banco de dados todas as despesa")
    public List<Despesa> listar() {
        return despesaService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista as despesas por ID", description = "Essa rota Lista do banco de dados as despesa por ID")
    public ResponseEntity<Despesa> buscar(@PathVariable Long id) {
        Optional<Despesa> despesa = despesaService.buscarPorId(id);
        if (despesa.isPresent()) {
            return ResponseEntity.ok(despesa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar despesa existente
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza a Despesa", description = "Essa rota atualiza as despesas")
    public ResponseEntity<Despesa> atualizar(@PathVariable Long id, @RequestBody @Valid Despesa despesa) {
        Optional<Despesa> despesaExistente = despesaService.buscarPorId(id);
        if (despesaExistente.isPresent()) {
            despesa.setId(id); // mantém o mesmo ID
            Despesa atualizada = despesaService.criar(despesa); // save atualiza se ID existe
            return ResponseEntity.ok(atualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta a despesa", description = "Essa rota deleta uma despesa por ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Despesa> despesaExistente = despesaService.buscarPorId(id);
        if (despesaExistente.isPresent()) {
            despesaService.deletar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/relatorio")
    @Operation(summary = "Gera um relatorio das despesas")
    public ResponseEntity<byte[]> gerarRelatorio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        if (inicio == null || fim == null){
            throw new RuntimeException("Parâmetros 'inicio' e 'fim' são obrigatórios.");
        }
        if (fim.isBefore(inicio)){
            throw new RuntimeException("A data fim nao pode ser anterior ao inicio");
        }

        return relatorioService.gerarRelatorio(inicio, fim);
    }



}

