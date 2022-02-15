package kr.mashup.branding.ui.adminmember;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberLoginVo;
import kr.mashup.branding.facade.adminmember.AdminMemberFacadeService;
import kr.mashup.branding.facade.adminmember.LoginResponseVo;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin-members")
public class AdminMemberController {

    private final AdminMemberAssembler adminMemberAssembler;
    private final AdminMemberFacadeService adminMemberFacadeService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
        @RequestBody LoginRequest loginRequest
    ) {
        AdminMemberLoginVo adminMemberLoginVo = adminMemberAssembler.toAdminMemberLoginVo(loginRequest);
        LoginResponseVo loginResponseVo = adminMemberFacadeService.login(adminMemberLoginVo);
        return ApiResponse.success(adminMemberAssembler.toLoginResponse(loginResponseVo));
    }

    @GetMapping("/me")
    public ApiResponse<AdminMemberResponse> getMe(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId
    ) {
        AdminMember adminMember = adminMemberFacadeService.getAdminMember(adminMemberId);
        return ApiResponse.success(adminMemberAssembler.toAdminMemberResponse(adminMember));
    }

}
