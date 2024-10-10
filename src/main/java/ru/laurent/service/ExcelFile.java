package ru.laurent.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Data
public class ExcelFile {

    private Workbook workbook;
    private Sheet sheet;
    private boolean isActive = false;
    private int counter = 2;

    public ExcelFile(Workbook workbook, Sheet sheet) {
        this.workbook = workbook;
        this.sheet = sheet;
    }

    public void createHeaders(Workbook workbook) {
        Row row0 = sheet.createRow(0);
        Row row1 = sheet.createRow(1);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 7));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));
        row0.createCell(0).setCellValue("Дата");
        row0.createCell(1).setCellValue("Гос. номер");
        row0.createCell(2).setCellValue("ФИО");
        row0.createCell(3).setCellValue("Маршрут");
        row0.createCell(4).setCellValue("Фирма");
        row0.createCell(5).setCellValue("Ставка");
        row1.createCell(5).setCellValue("Начальное показание од.");
        row1.createCell(6).setCellValue("Конечное показание од.");
        row1.createCell(7).setCellValue("Топливо");
        row0.createCell(8).setCellValue("Комментарии");
        for (int i = 0; i < 11; i++) {
            sheet.setColumnWidth(i, 25*150);
        }
    }

    public void createNewRow(String[] data) {
        int cellCounter = 0;
        Row row = sheet.createRow(counter++);
        for (String datum : data) {
            row.createCell(cellCounter++).setCellValue(datum);
        }
        try {
            workbook.write(new FileOutputStream("wb.xlsx"));
        } catch (IOException e) {
          log.error(String.valueOf(e));
        }
    }
}
