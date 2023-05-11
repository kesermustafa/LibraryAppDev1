package com.lib.report;

import com.lib.domain.Role;
import com.lib.domain.User;
import com.lib.dto.response.BookExcelReport;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;


public class ExcelReporter {

    //  ---- LIBRARY ----
    static String SHEET_LIBRARY = "Library";

    static String[] LIBRARY_HEADERS={"id", "Name", "ISBN", "PageCount", "publishDate", "shelfCode", "createDate",
            "categoryName", "publisherName", "authorName", "active", "featured", "loanable"  };



    //  ---- USER ----
    static String SHEET_USER = "Users";

    static String[] USER_HEADERS={"id", "FirstName", "LastName", "Score", "PhoneNumber", "Email", "Address",  "Roles"};




    //*******************LIBRARYREPORT***********************

    public static ByteArrayInputStream getLibraryExcelReport(List<BookExcelReport> bookExcelReports) throws IOException {
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
        for(BookExcelReport report : bookExcelReports) {
            Row row = sheet.createRow(rowId++);
            row.createCell(0).setCellValue(report.getId());
            row.createCell(1).setCellValue(report.getName());
            row.createCell(2).setCellValue(report.getIsbn());
            row.createCell(3).setCellValue(report.getPageCount());
            row.createCell(4).setCellValue(report.getPublishDate());
            row.createCell(5).setCellValue(report.getShelfCode());
            row.createCell(6).setCellValue(report.getCreateDate().toString());
            row.createCell(7).setCellValue(report.getCategoryName());
            row.createCell(8).setCellValue(report.getPublisherName());
            row.createCell(9).setCellValue(report.getAuthorName());
            row.createCell(10).setCellValue(report.isActive() ? "True" : "False");
            row.createCell(11).setCellValue(report.isFeatured() ? "True" : "False");
            row.createCell(12).setCellValue(report.isLoanable() ? "True" : "False");

        }
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }




    public static ByteArrayInputStream getUserExcelReport(List<User> users) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet(SHEET_USER);
        Row headerRow =  sheet.createRow(0);

        // header row dolduruluyor
        for(int i=0; i< USER_HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(USER_HEADERS[i]);
        }


// static String[] USER_HEADERS={"id", "FirstName", "LastName", "Score", "PhoneNumber", "Email", "Address",  "Roles"};
        // dataları dolduruyoruz
        int rowId = 1;
        for(User user : users) {
            Row row = sheet.createRow(rowId++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getFirstName());
            row.createCell(2).setCellValue(user.getLastName());
            row.createCell(3).setCellValue(user.getScore());
            row.createCell(4).setCellValue(user.getPhone());
            row.createCell(5).setCellValue(user.getEmail());
            row.createCell(6).setCellValue(user.getAddress());



            // Customer , Administrator
            StringJoiner sj = new StringJoiner(",");

            for(Role role : user.getRoles()) {
                sj.add(role.getRoleType().getName());
            }

            row.createCell(7).setCellValue(sj.toString());
        }
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
