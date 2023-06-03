package kr.mashup.branding.ui.adminmember;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.domain.adminmember.vo.AdminLoginCommand;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberVo;
import kr.mashup.branding.ui.adminmember.vo.AdminMemberResponse;
import kr.mashup.branding.ui.adminmember.vo.LoginRequest;
import kr.mashup.branding.ui.adminmember.vo.LoginResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import kr.mashup.branding.facade.adminmember.AdminMemberFacadeService;
import kr.mashup.branding.facade.adminmember.LoginResponseVo;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

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
    public ApiResponse<List<AdminMemberResponse>> readAdminMembers(@PageableDefault Pageable pageable) {
        Page<AdminMemberVo> data = adminMemberFacadeService.readAdminMembers(pageable);

        return ApiResponse.success(data.map(AdminMemberResponse::from));
    }
}
