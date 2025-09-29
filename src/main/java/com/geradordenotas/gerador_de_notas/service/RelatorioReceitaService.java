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
public class RelatorioReceitaService {

    private final ReceitaRepository receitaRepository;

    public ResponseEntity<byte[]> gerarRelatorio(LocalDate inicio, LocalDate fim) {
        try {
            // 🔹 Buscar despesas no período
            List<Receita> receitas = receitaRepository.findByDataBetween(inicio, fim);

            // 🔹 Calcular total
            BigDecimal total = receitas.stream()
                    .map(Receita::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // 🔹 Criar documento PDF em memória
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();

            // 🔹 Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("Relatório de Receita", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("Período: " + inicio + " até " + fim));
            document.add(new Paragraph("Total das Receitas: R$ " + total));
            document.add(new Paragraph(" ")); // espaço em branco

            // 🔹 Tabela
            PdfPTable table = new PdfPTable(3); // 3 colunas
            table.setWidthPercentage(100);
            table.addCell("Descrição");
            table.addCell("Valor");
            table.addCell("Data");

            for (Receita d : receitas) {
                table.addCell(d.getDescricao());
                table.addCell("R$ " + d.getValor().toString());
                table.addCell(d.getData().toString());
            }

            document.add(table);
            document.close();

            // 🔹 Retornar como resposta HTTP
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório PDF: " + e.getMessage(), e);
        }
    }
}
