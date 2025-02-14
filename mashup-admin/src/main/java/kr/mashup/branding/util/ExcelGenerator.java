package kr.mashup.branding.util;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@UtilityClass
public class ExcelGenerator {

    public static <T> ByteArrayResource generate(
        String sheetName,
        List<String> headers,
        List<T> data,
        Function<T, List<String>> rowDataExtractor
    ) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet(sheetName);
            createHeaderRow(sheet, headers);
            fillData(sheet, data, rowDataExtractor);
            adjustColumnWidths(sheet, headers.size());

            return createExcelResource(workbook);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create excel file", e);
        }
    }

    private static void createHeaderRow(XSSFSheet sheet, List<String> headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            headerRow.createCell(i).setCellValue(headers.get(i));
        }
    }

    private static <T> void fillData(
        XSSFSheet sheet,
        List<T> data,
        Function<T, List<String>> rowDataExtractor
    ) {
        int rowNum = 1;
        for (T item : data) {
            Row row = sheet.createRow(rowNum++);
            List<String> rowData = rowDataExtractor.apply(item);

            for (int i = 0; i < rowData.size(); i++) {
                row.createCell(i).setCellValue(rowData.get(i));
            }
        }
    }

    private static void adjustColumnWidths(XSSFSheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static ByteArrayResource createExcelResource(XSSFWorkbook workbook) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            workbook.write(out);
            return new ByteArrayResource(out.toByteArray());
        }
    }
}
