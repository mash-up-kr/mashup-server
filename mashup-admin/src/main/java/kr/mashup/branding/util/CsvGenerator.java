package kr.mashup.branding.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.ByteArrayResource;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class CsvGenerator {

    private static final String DELIMITER = ",";
    private static final String NEW_LINE = "\n";
    private static final String UTF8_BOM = "\uFEFF"; // Excel에서 한글 인코딩을 위한 BOM

    public static <T> ByteArrayResource generate(
        List<String> headers,
        List<T> data,
        Function<T, List<String>> rowDataExtractor
    ) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(UTF8_BOM); // Add BOM for Excel UTF-8 support

        // Headers
        csvContent.append(String.join(DELIMITER,
                headers.stream()
                    .map(CsvGenerator::escapeCsvField)
                    .collect(Collectors.toList())))
            .append(NEW_LINE);

        // Data rows
        data.forEach(item -> {
            List<String> rowData = rowDataExtractor.apply(item);
            csvContent.append(rowData.stream()
                .map(CsvGenerator::escapeCsvField)
                .collect(Collectors.joining(DELIMITER)))
                .append(NEW_LINE);
        });

        return new ByteArrayResource(csvContent.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static String escapeCsvField(String field) {
        if (field == null) return "";

        // 필드에 쉼표, 큰따옴표, 개행이 포함된 경우 큰따옴표로 감싸고 이스케이프 처리
        if (field.contains(DELIMITER) || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}

