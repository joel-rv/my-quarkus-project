package com.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
public class WordDocumentService {

    public void modifyWordDocument(String inputFilePath, String outputFilePath, String name) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFilePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                replacePlaceholder(paragraph, "{date}", LocalDate.now().format(DateTimeFormatter.ISO_DATE));
                replacePlaceholder(paragraph, "{name}", name);
            }

            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                document.write(fos);
            }
        }
    }

    private void replacePlaceholder(XWPFParagraph paragraph, String placeholder, String replacement) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs != null) {
            for (XWPFRun run : runs) {
                String text = run.getText(0);
                if (text != null && text.contains(placeholder)) {
                    text = text.replace(placeholder, replacement);
                    run.setText(text, 0);
                }
            }
        }
    }
}