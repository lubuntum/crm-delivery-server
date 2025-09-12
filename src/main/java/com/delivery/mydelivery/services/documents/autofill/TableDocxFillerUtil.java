package com.delivery.mydelivery.services.documents.autofill;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.productmanage.Item;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class TableDocxFillerUtil {

    public XWPFDocument fillDocxTable(ClientOrder clientOrder, XWPFDocument document, int tableIndex) throws IOException {
        XWPFTable table = document.getTables().get(tableIndex);
        for (int i = 0; i < clientOrder.getItems().size();i++)
            addTableRow(table, clientOrder.getItems().get(i), i+1);
        return document;

    }
    private void addTableRow(XWPFTable table, Item item, int columnNumber) {
        XWPFTableRow row = table.createRow();
        setCellValue(row, 0, String.valueOf(columnNumber));
        if (item.getMaterial() != null)
            setCellValue(row, 1, item.getMaterial().getName());
        setCellValue(row, 2, String.valueOf(item.getWidth()));
        setCellValue(row, 3, String.valueOf(item.getHeight()));
        setCellValue(row, 4, String.valueOf(item.getSize()));
        if (item.getPricePerUnit() != null)
            setCellValue(row, 5, item.getPricePerUnit().toString());
        if (item.getAdditionalPrice() != null)
            setCellValue(row, 6, item.getAdditionalPrice().toString());
        setCellValue(row, 7, item.getComment());
        if (item.getPrice() != null)
            setCellValue(row, 8, item.getPrice().toString());

    }
    private void setCellValue(XWPFTableRow row, int cellIndex, String value){
        XWPFTableCell cell = row.getCell(cellIndex);
        if (cell == null) cell = row.addNewTableCell();
        cell.removeParagraph(0);
        XWPFParagraph paragraph = cell.addParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(value != null ? value : "");
    }
}
