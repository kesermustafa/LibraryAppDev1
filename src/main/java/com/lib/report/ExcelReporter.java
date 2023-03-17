package com.lib.report;

import com.lib.dto.response.ReportResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


public class ExcelReporter {

    //  ---- LIBRARY ----
    static String SHEET_LIBRARY = "Library";

    static String[] LIBRARY_HEADERS={"books", "authors", "publishers", "categories", "loans", "unReturnedBooks", "expiredBooks", "members"};

    //*******************LIBRARYREPORT***********************

    public static ByteArrayInputStream getLibraryExcelReport(List<ReportResponse> reportResponses) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet(SHEET_LIBRARY);
        Row headerRow =  sheet.createRow(0);

        // header row dolduruluyor
        for(int i=0; i< LIBRARY_HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(LIBRARY_HEADERS[i]);
        }


        // dataları dolduruyoruz
        int rowId = 1;
        for(ReportResponse report : reportResponses) {
            Row row = sheet.createRow(rowId++);
            row.createCell(0).setCellValue(report.getBooks());
            row.createCell(1).setCellValue(report.getAuthors());
            row.createCell(2).setCellValue(report.getPublishers());
            row.createCell(3).setCellValue(report.getCategories());
            row.createCell(4).setCellValue(report.getLoans());
            row.createCell(5).setCellValue(report.getUnReturnedBooks());
            row.createCell(6).setCellValue(report.getExpiredBooks());
            row.createCell(6).setCellValue(report.getMembers());

        }
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }


}
