package kr.mashup.branding.ui.adminmember;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.domain.adminmember.vo.AdminLoginCommand;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberSignUpCommand;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberVo;
import kr.mashup.branding.ui.adminmember.vo.AdminMemberResponse;
import kr.mashup.branding.ui.adminmember.vo.LoginRequest;
import kr.mashup.branding.ui.adminmember.vo.LoginResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import kr.mashup.branding.facade.adminmember.AdminMemberFacadeService;
import kr.mashup.branding.facade.adminmember.LoginResponseVo;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin-members")
public class AdminMemberController {

    private final AdminMemberFacadeService adminMemberFacadeService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
        @RequestBody LoginRequest loginRequest
    ) {

        final AdminLoginCommand loginCommand = loginRequest.toAdminMemberLoginVo();
        final LoginResponseVo loginResponseVo = adminMemberFacadeService.login(loginCommand);

        return ApiResponse.success(LoginResponse.from(loginResponseVo));
    }

    @GetMapping("/me")
    public ApiResponse<AdminMemberResponse> getMe(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId // Model Attribute 는 AdminControllerAdvice 에서 주입
    ) {
        final AdminMemberVo adminMemberVo = adminMemberFacadeService.getAdminMember(adminMemberId);

        return ApiResponse.success(AdminMemberResponse.from(adminMemberVo));
    }

    @PostMapping("/{adminId}/password/reset")
    public ApiResponse<EmptyResponse> resetPassword(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @PathVariable("adminId") Long targetAdminId,
        @RequestBody AdminPasswordResetRequest request
    ){
        adminMemberFacadeService.resetPassword(adminMemberId, targetAdminId, request.getResetPassword());

        return ApiResponse.success();
    }

    @DeleteMapping("/{adminId}")
    public ApiResponse<EmptyResponse> deleteAdminMember(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId,
        @PathVariable("adminId") Long targetAdminId
    ){
        adminMemberFacadeService.deleteAdminMember(adminMemberId, targetAdminId);

        return ApiResponse.success();
    }

    @PostMapping("/{adminId}/password/change")
    public ApiResponse<EmptyResponse> changePassword(
        @ApiIgnore @ModelAttribute("adminId") Long adminMemberId,
        @RequestBody AdminPasswordChangeRequest request
    ){
        adminMemberFacadeService.changePassword(adminMemberId, request);

        return ApiResponse.success();
    }

    /** 어드민 멤버 리스트 조회 */
    @ApiOperation("어드민 멤버 리스트 조회")
    @GetMapping
    public ApiResponse<List<AdminMemberResponse>> readAdminMembers() {
        List<AdminMemberVo> data = adminMemberFacadeService.readAdminMembers();

        return ApiResponse.success(data.stream().map(AdminMemberResponse::from).collect(Collectors.toList()));
    }

    /** 어드민 멤버 생성 */
    @ApiOperation("어드민 멤버 생성")
    @PreAuthorize("hasAnyAuthority('MASHUP_LEADER', 'MASHUP_SUBLEADER')")
    @PostMapping
    public ApiResponse<AdminMemberResponse> createAdminMember(@RequestBody AdminMemberSignUpCommand signUpCommand) {
        AdminMemberVo data = adminMemberFacadeService.createAdminMember(signUpCommand);

        return ApiResponse.success(AdminMemberResponse.from(data));
    }
}
