package kr.mashup.branding.ui.application;

import kr.mashup.branding.facade.application.AdminApplicationExcelFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications")
public class AdminApplicationExcelController {

    private static final String FILENAME_PREFIX = "applications_";
    private static final String FILENAME_DATETIME_PATTERN = "yyyyMMdd_HHmmss";
    private static final String CSV_EXTENSION = ".csv";
    private static final String CSV_CONTENT_TYPE = "text/csv;charset=UTF-8";

    private final AdminApplicationExcelFacadeService adminApplicationExcelFacadeService;

    @GetMapping("/csv")
    public ResponseEntity<Resource> downloadCsv(@ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId) {
        String filename = FILENAME_PREFIX +
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(FILENAME_DATETIME_PATTERN)) +
            CSV_EXTENSION;

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.add(HttpHeaders.CONTENT_TYPE, CSV_CONTENT_TYPE);

        Resource resource = adminApplicationExcelFacadeService.generateCsv(adminMemberId);

        return ResponseEntity.ok()
            .headers(headers)
            .body(resource);
    }
}
