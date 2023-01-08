package kr.mashup.branding.ui.adminmember;

import kr.mashup.branding.domain.adminmember.vo.AdminLoginCommand;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberVo;
import kr.mashup.branding.ui.adminmember.vo.AdminMemberResponse;
import kr.mashup.branding.ui.adminmember.vo.LoginRequest;
import kr.mashup.branding.ui.adminmember.vo.LoginResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.facade.adminmember.AdminMemberFacadeService;
import kr.mashup.branding.facade.adminmember.LoginResponseVo;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

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

}
