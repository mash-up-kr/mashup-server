package kr.mashup.branding.ui.application;

import kr.mashup.branding.facade.application.AdminApplicationExcelFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications")
public class AdminApplicationExcelController {

    private final AdminApplicationExcelFacadeService adminApplicationExcelFacadeService;

    private static final String EXCEL_FILENAME_PREFIX = "applications_";
    private static final String EXCEL_FILENAME_DATETIME_PATTERN = "yyyyMMdd_HHmm";
    private static final String EXCEL_EXTENSION = ".xlsx";
    private static final String FILENAME_TEMPLATE = "attachment; filename=%s";

    @GetMapping("/excel")
    public ResponseEntity<Resource> downloadExcel(@ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId) {
        final String filename = generateFilename();
        final HttpHeaders headers = createHeaders(filename);
        final Resource resource = adminApplicationExcelFacadeService.generateApplicationExcel(adminMemberId);

        return ResponseEntity.ok()
            .headers(headers)
            .body(resource);
    }

    private String generateFilename() {
        return EXCEL_FILENAME_PREFIX +
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(EXCEL_FILENAME_DATETIME_PATTERN)) +
            EXCEL_EXTENSION;
    }

    private HttpHeaders createHeaders(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format(FILENAME_TEMPLATE, filename));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return headers;
    }
}
