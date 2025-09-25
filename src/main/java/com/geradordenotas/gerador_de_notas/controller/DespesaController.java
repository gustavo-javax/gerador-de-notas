package com.geradordenotas.gerador_de_notas.controller;

import com.geradordenotas.gerador_de_notas.entity.Despesa;
import com.geradordenotas.gerador_de_notas.service.DespesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despesas")
@RequiredArgsConstructor
public class DespesaController {

    private final DespesaService despesaService;

    // Criar nova despesa
    @PostMapping
    public Despesa criar(@RequestBody Despesa despesa) {
        return despesaService.criar(despesa);
    }

    // Listar todas as despesas
    @GetMapping
    public List<Despesa> listar() {
        return despesaService.listar();
    }

    // Buscar despesa por ID
    @GetMapping("/{id}")
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
    public ResponseEntity<Despesa> atualizar(@PathVariable Long id, @RequestBody Despesa despesa) {
        Optional<Despesa> despesaExistente = despesaService.buscarPorId(id);
        if (despesaExistente.isPresent()) {
            despesa.setId(id); // mantém o mesmo ID
            Despesa atualizada = despesaService.criar(despesa); // save atualiza se ID existe
            return ResponseEntity.ok(atualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar despesa por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Despesa> despesaExistente = despesaService.buscarPorId(id);
        if (despesaExistente.isPresent()) {
            despesaService.deletar(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

