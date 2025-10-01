package com.geradordenotas.gerador_de_notas.service;

import com.geradordenotas.gerador_de_notas.entity.Despesa;
import com.geradordenotas.gerador_de_notas.entity.Receita;
import com.geradordenotas.gerador_de_notas.repository.DespesaRepository;
import com.geradordenotas.gerador_de_notas.repository.ReceitaRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class RelatorioGlobalService {

    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;

    public ResponseEntity<byte[]> gerarRelatorio(LocalDate inicio, LocalDate fim){

        try {
            // 🔹 Buscar despesas no período
            List<Receita> receitas = receitaRepository.findByDataBetween(inicio, fim);
            List<Despesa> despesas = despesaRepository.findByDataBetween(inicio, fim);

            // 🔹 Calcular total
            BigDecimal totalReceitas = receitas.stream()
                    .map(Receita::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalDespesas = despesas.stream()
                    .map(Despesa::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);


            // 🔹 Criar documento PDF em memória
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();

            // 🔹 Título
            Font fontTituloReceita = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph tituloReceita = new Paragraph("Relatório de Receita", fontTituloReceita);
            tituloReceita.setAlignment(Element.ALIGN_CENTER);
            document.add(tituloReceita);

            document.add(new Paragraph("Período: " + inicio + " até " + fim));
            document.add(new Paragraph("Total das Receitas: R$ " + totalReceitas));
            document.add(new Paragraph(" ")); // espaço em branco
//**************************************************************************
            Font fontTituloDespesa = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph tituloDespesa = new Paragraph("Relatório de Despesas", fontTituloDespesa);
            tituloDespesa.setAlignment(Element.ALIGN_CENTER);
            document.add(tituloDespesa);

            document.add(new Paragraph("Período: " + inicio + " até " + fim));
            document.add(new Paragraph("Total das Despesas: R$ " + totalDespesas));
            document.add(new Paragraph(" ")); // espaço em branco
//*****************************************************************************
            Font fontTituloTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.BLACK);
            Paragraph tituloTotal = new Paragraph("Relatório TOTAL", fontTituloTotal); // aqui usa a fonte correta
            tituloTotal.setAlignment(Element.ALIGN_CENTER);
            document.add(tituloTotal);
            BigDecimal saldo = totalReceitas.subtract(totalDespesas);
            Paragraph periodo = new Paragraph("Período: " + inicio + " até " + fim);
            periodo.setAlignment(Element.ALIGN_CENTER);
            document.add(periodo);
            Font fontSaldo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 30, BaseColor.BLUE);
            Paragraph saldoParagrafo = new Paragraph("Saldo Final: R$ " + saldo, fontSaldo);
            saldoParagrafo.setAlignment(Element.ALIGN_CENTER);
            document.add(saldoParagrafo);

            document.add(new Paragraph(" "));

            //*********************************************
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.addCell("Tipo");
            table.addCell("Descrição");
            table.addCell("Categoria");
            table.addCell("Valor");
            table.addCell("Data");

            for (Receita d : receitas) {
                table.addCell("Receita");
                table.addCell(d.getDescricao());
                table.addCell(d.getFonte().toString());
                table.addCell("R$ " + d.getValor().toString());
                table.addCell(d.getData().toString());
            }

            for (Despesa d : despesas) {
                table.addCell("Despesa");
                table.addCell(d.getDescricao());
                table.addCell(d.getCategoria().toString());
                table.addCell("R$ " + d.getValor().toString());
                table.addCell(d.getData().toString());
            }

            document.add(table);
            document.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório PDF: " + e.getMessage(), e);
        }
    }
}

