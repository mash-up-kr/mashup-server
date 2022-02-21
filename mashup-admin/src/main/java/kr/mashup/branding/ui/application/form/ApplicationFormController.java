package kr.mashup.branding.ui.application.form;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormQueryVo;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.facade.application.form.ApplicationFormFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/application-forms")
@RequiredArgsConstructor
public class ApplicationFormController {
    private final ApplicationFormFacadeService applicationFormFacadeService;
    private final ApplicationFormAssembler applicationFormAssembler;

    /**
     * 설문지 목록 조회
     * @param searchWord 설문지 이름 (검색)
     * @param pageable 페이지 정보
     * @return 설문지 목록
     */
    @GetMapping
    public ApiResponse<List<ApplicationFormResponse>> getApplicationForms(
        @RequestParam(required = false) Long teamId,
        @RequestParam(required = false) String searchWord,
        Pageable pageable
    ) {
        Page<ApplicationForm> applicationFormsPage = applicationFormFacadeService.getApplicationForms(
            ApplicationFormQueryVo.of(teamId, searchWord, pageable));
        return ApiResponse.success(
            applicationFormsPage
                .stream()
                .map(applicationFormAssembler::toApplicationFormResponse)
                .collect(Collectors.toList()),
            ApiResponse.PageResponse.from(applicationFormsPage)
        );
    }

    /**
     * 설문지 상세 조회
     * @param applicationFormId 설문지 식별자
     * @return 설문지 정보
     */
    @GetMapping("/{applicationFormId}")
    public ApiResponse<ApplicationFormResponse> getApplicationForm(
        @PathVariable Long applicationFormId
    ) {
        ApplicationForm applicationForm = applicationFormFacadeService.getApplicationForm(applicationFormId);
        return ApiResponse.success(applicationFormAssembler.toApplicationFormResponse(applicationForm));
    }

    /**
     * 설문지 생성
     * @param createApplicationFormRequest 설문지 생성 요청 정보
     * @return 새로 생성된 설문지
     */
    @PostMapping
    public ApiResponse<ApplicationFormResponse> create(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestBody CreateApplicationFormRequest createApplicationFormRequest
    ) {
        CreateApplicationFormVo createApplicationFormVo = applicationFormAssembler.toCreateApplicationFormVo(
            createApplicationFormRequest);
        ApplicationForm applicationForm = applicationFormFacadeService.create(adminMemberId, createApplicationFormVo);
        return ApiResponse.success(
            applicationFormAssembler.toApplicationFormResponse(applicationForm)
        );
    }

    /**
     * 설문지 수정
     * @param applicationFormId 설문지 식별자
     * @param updateApplicationFormRequest 설문지 수정 요청 정보
     * @return 수정된 설문지
     */
    @PutMapping("/{applicationFormId}")
    public ApiResponse<ApplicationFormResponse> update(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @PathVariable Long applicationFormId,
        @RequestBody UpdateApplicationFormRequest updateApplicationFormRequest
    ) {
        ApplicationForm applicationForm = applicationFormFacadeService.update(
            adminMemberId,
            applicationFormId,
            applicationFormAssembler.toUpdateApplicationFormVo(updateApplicationFormRequest)
        );
        return ApiResponse.success(
            applicationFormAssembler.toApplicationFormResponse(applicationForm)
        );
    }

    /**
     * 설문지 삭제 요청
     * @param applicationFormId 설문지 식별자
     */
    @DeleteMapping("/{applicationFormId}")
    public ApiResponse<?> delete(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @PathVariable Long applicationFormId
    ) {
        applicationFormFacadeService.delete(adminMemberId, applicationFormId);
        return ApiResponse.success();
    }
}
