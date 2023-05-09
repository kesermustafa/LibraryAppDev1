package com.lib.controller;

import com.lib.dto.response.BookResponse;
import com.lib.dto.response.ReportResponse;
import com.lib.service.ReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {


    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<ReportResponse> getSomeStatistics() {

        ReportResponse response = reportService.getReportAboutAllData();

        return ResponseEntity.ok(response);
    }



    @GetMapping("/unreturned-books")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<Page<BookResponse>> getReportsWithPage(@RequestParam("page") int page,
                                                                 @RequestParam("size") int size,
                                                                 @RequestParam("sort") String prop,
                                                                 @RequestParam(value = "direction",
                                                                         required = false, defaultValue = "DESC")
                                                                     Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<BookResponse> bookResponse = reportService.findReportsWithPage(pageable);
        return ResponseEntity.ok(bookResponse);

    }



    @GetMapping("/expired-books")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<Page<BookResponse>> getReportsWithPageExpiredBooks(@RequestParam("page") int page,
                                                                             @RequestParam("size") int size,
                                                                             @RequestParam("sort") String prop,
                                                                             @RequestParam(value = "direction",
                                                                                     required = false, defaultValue = "DESC")
                                                                                 Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<BookResponse> bookResponse = reportService.findReportsWithPageExpiredBooks(pageable);
        return ResponseEntity.ok(bookResponse);

    }


    @GetMapping("/most-borrowers")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<Page> getReportMostBorrowers(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                       @RequestParam(required = false,value = "size", defaultValue = "20") int size ){

        Pageable pageable = PageRequest.of(page, size);
        Page mostBorrowers = reportService.findReportMostBorrowers(pageable);

        return ResponseEntity.ok(mostBorrowers);

    }



    @GetMapping("/most-popular-books")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity <List<Object>> getReportMostPopularBooks(@RequestParam(required = false,value = "amount", defaultValue = "10") int amount,
                                                                   @RequestParam(required = false,value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(required = false,value = "size", defaultValue = "20") int size ){

        Pageable pageable = PageRequest.of(page, size);
        List<Object> bookResponse = reportService.findReportMostPopularBooks(amount,pageable);

        return ResponseEntity.ok(bookResponse);

    }


    @GetMapping("/excel/download/books")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<Resource> getBookReport() {
        String fileName = "books.xlsx";
        ByteArrayInputStream bais = reportService.getBookReport();
        InputStreamResource file = new InputStreamResource(bais);

        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName).
                contentType(MediaType.parseMediaType("application/vmd.ms-excel")).
                body(file);
    }






}
