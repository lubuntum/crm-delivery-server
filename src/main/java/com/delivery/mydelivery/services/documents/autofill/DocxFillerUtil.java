package com.delivery.mydelivery.services.documents.autofill;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class DocxFillerUtil {
    public static XWPFDocument fillDocx(String templatePath, Map<String, String> fields) throws IOException {
        XWPFDocument document ;
        try (FileInputStream fis = new FileInputStream(templatePath)) {
            document = new XWPFDocument(fis);
        }
        for(XWPFParagraph paragraph: document.getParagraphs()) {
            for (XWPFRun run: paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null) {
                    for (Map.Entry<String, String> entry: fields.entrySet())
                        text = text.replace(entry.getKey(), entry.getValue());
                    run.setText(text, 0);
                }
            }
        }
        return document;
    }
    public static XWPFDocument fillDocxWithBuffer(String templatePath, Map<String, String> fields) throws IOException {
        XWPFDocument document;
        try (FileInputStream fis = new FileInputStream(templatePath)){
            document = new XWPFDocument(fis);
        }
        for (XWPFParagraph paragraph: document.getParagraphs()){
            StringBuilder paragraphText = new StringBuilder();

            for (XWPFRun run: paragraph.getRuns())
                paragraphText.append(run.getText(0));
            String updatedText = paragraphText.toString();
            for(Map.Entry<String, String> entry: fields.entrySet()) {
                String placeholder = entry.getKey();
                if (updatedText.contains(placeholder))
                    updatedText = updatedText.replace(placeholder, entry.getValue());
            }
            paragraph.getRuns().clear();
            XWPFRun newRun = paragraph.createRun();
            newRun.setText(updatedText);
        }
        return document;
    }

}
