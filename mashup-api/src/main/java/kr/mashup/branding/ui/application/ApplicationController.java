package kr.mashup.branding.ui.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    @ApiOperation("내 지원서 임시 저장")
    @PutMapping
    public ApplicationResponse update(
            @RequestBody ApplicationUpdateRequest applicationUpdateRequest
    ) {
        return null;
    }

    /**
     * 임시저장 상태일때만 삭제 가능, 제출하고나서는 삭제 못함
     */
    @ApiOperation("내 지원서 삭제")
    @DeleteMapping
    public ApplicationResponse delete() {
        return null;
    }

    /**
     * 지원서가 없으면? 오류
     * 임시저장이면 성공
     * 이미 제출했어도 성공
     */
    @ApiOperation("내 지원서 제출")
    @PostMapping("/submit")
    public ApplicationResponse submit() {
        return null;
    }

    /**
     * 0개 or 1개
     */
    @ApiOperation("내 지원서 목록 조회")
    @GetMapping
    public List<ApplicationResponse> getApplications() {
        return Collections.emptyList();
    }
}
