package com.spring.project.service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {
    public byte[] generatePdf(List<String[]> data) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            if (data != null && !data.isEmpty()) {
                // Create a table with the number of columns matching the first row
                Table table = new Table(data.get(0).length);

                // Load a bold font
//                PdfFont boldFont = PdfFontFactory.createFont(PdfFontFactory.HELVETICA_BOLD);

                // Add rows to the table
                for (int i = 0; i < data.size(); i++) {
                    String[] row = data.get(i);
                    for (String cellData : row) {
                        Cell cell = new Cell().add(new Paragraph(cellData));
                        if (i == 0) {
//                            cell.setFont(boldFont);
                            cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                            cell.setTextAlignment(TextAlignment.CENTER);
                            cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                        }
                        table.addCell(cell);
                    }
                }

                document.add(table);
            } else {
                document.add(new Paragraph("No data available"));
            }

            document.close();
            return out.toByteArray();
        }
    }}
