package com.geradordenotas.gerador_de_notas.controller;

import com.geradordenotas.gerador_de_notas.service.DespesaService;
import com.geradordenotas.gerador_de_notas.service.ReceitaService;
import com.geradordenotas.gerador_de_notas.service.RelatorioGlobalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatoriototal")
@RequiredArgsConstructor


public class RelatorioController {

    private final RelatorioGlobalService relatorioGlobalService;
    private final ReceitaService receitaService;
    private final DespesaService despesaService;

    @GetMapping
    @Operation(summary = "Gera um relatorio total", description = "Essa rota gera um relatorio total das receitas e despesas")
    public ResponseEntity<byte[]> gerarRelatorioGlobal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fim){
            return relatorioGlobalService.gerarRelatorio(inicio,fim);
    }





}
