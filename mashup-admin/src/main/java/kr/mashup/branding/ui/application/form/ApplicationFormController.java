package kr.mashup.branding.ui.application.form;

import kr.mashup.branding.domain.application.form.ApplicationFormQueryVo;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.facade.application.form.AdminApplicationFormFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.application.form.vo.ApplicationFormQueryRequest;
import kr.mashup.branding.ui.application.form.vo.ApplicationFormResponse;
import kr.mashup.branding.ui.application.form.vo.CreateApplicationFormRequest;
import kr.mashup.branding.ui.application.form.vo.UpdateApplicationFormRequest;
import lombok.RequiredArgsConstructor;
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
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/v1/application-forms")
@RequiredArgsConstructor
public class ApplicationFormController {

    private final AdminApplicationFormFacadeService adminApplicationFormFacadeService;

    /**
     * 설문지 목록 조회
     * @param searchWord 설문지 이름 (검색)
     * @param pageable 페이지 정보
     * @return 설문지 목록
     */
    @GetMapping
    public ApiResponse<List<ApplicationFormResponse>> getApplicationForms(
        @RequestParam(defaultValue = "12", required = false) Integer generationNumber,
        @RequestParam(required = false) Long teamId,
        @RequestParam(required = false) String searchWord,
        Pageable pageable // TODO Pageable default
    ) {
        final ApplicationFormQueryRequest request
            = ApplicationFormQueryRequest.of(generationNumber, teamId, searchWord, pageable);

        final Page<ApplicationFormResponse> responses = adminApplicationFormFacadeService.getApplicationForms(request);
        return ApiResponse.success(responses);
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

        final ApplicationFormResponse response = adminApplicationFormFacadeService.getApplicationForm(applicationFormId);
        return ApiResponse.success(response);
    }

    /**
     * 설문지 생성
     * @param request 설문지 생성 요청 정보
     * @return 새로 생성된 설문지
     */
    @PostMapping
    public ApiResponse<ApplicationFormResponse> create(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @RequestBody CreateApplicationFormRequest request
    ) {

        final CreateApplicationFormVo createFormVo = request.toVo();
        final Long teamId = request.getTeamId();

        final ApplicationFormResponse response = adminApplicationFormFacadeService.create(adminMemberId,teamId, createFormVo);
        return ApiResponse.success(response);
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
        final ApplicationFormResponse response = adminApplicationFormFacadeService.update(
            adminMemberId,
            applicationFormId,
            updateApplicationFormRequest.toVo()
        );

        return ApiResponse.success(response);
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
        adminApplicationFormFacadeService.delete(adminMemberId, applicationFormId);
        return ApiResponse.success();
    }
}
