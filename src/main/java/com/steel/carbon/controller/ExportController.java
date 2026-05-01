package com.steel.carbon.controller;

import com.steel.carbon.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private ExportService exportService;

    @GetMapping("/monthly-report")
    public ResponseEntity<byte[]> exportMonthlyReport(@RequestParam(required = false) Integer year,
                                                      @RequestParam(required = false) Integer month) throws IOException {
        if (year == null) year = LocalDate.now().getYear();
        if (month == null) month = LocalDate.now().getMonthValue();
        
        byte[] data = exportService.exportMonthlyReport(year, month);
        
        String filename = year + "年" + month + "月碳排放统计报表.xlsx";
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
    }
}
