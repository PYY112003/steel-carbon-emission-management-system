package com.steel.carbon.service;

import com.steel.carbon.entity.EmissionRecord;
import com.steel.carbon.mapper.EmissionRecordMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExportService {

    @Autowired
    private EmissionRecordMapper emissionRecordMapper;

    public byte[] exportMonthlyReport(Integer year, Integer month) throws IOException {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        
        List<EmissionRecord> records = emissionRecordMapper.selectByDateRange(startDate, endDate);
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("月度排放统计");
        
        Row headerRow = sheet.createRow(0);
        String[] headers = {"日期", "工序", "原料/能源", "消耗量", "排放因子", "碳排放量"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderStyle(workbook));
        }
        
        int rowNum = 1;
        for (EmissionRecord record : records) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(record.getRecordDate().toString());
            row.createCell(1).setCellValue(record.getProcessName() != null ? record.getProcessName() : "");
            row.createCell(2).setCellValue(record.getMaterialEnergyName() != null ? record.getMaterialEnergyName() : "");
            row.createCell(3).setCellValue(record.getConsumeValue() != null ? record.getConsumeValue().doubleValue() : 0);
            row.createCell(4).setCellValue(record.getEmissionFactor() != null ? record.getEmissionFactor().doubleValue() : 0);
            row.createCell(5).setCellValue(record.getEmissionValue() != null ? record.getEmissionValue().doubleValue() : 0);
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        
        return outputStream.toByteArray();
    }
    
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }
}
