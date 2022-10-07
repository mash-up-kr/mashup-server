package kr.mashup.branding.ui.adminmember;

import kr.mashup.branding.domain.adminmember.vo.AdminMemberLoginCommand;
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

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
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

        final AdminMemberLoginCommand loginCommand = loginRequest.toAdminMemberLoginVo();
        final LoginResponseVo loginResponseVo = adminMemberFacadeService.login(loginCommand);
        final LoginResponse loginResponse = LoginResponse.from(loginResponseVo);

        return ApiResponse.success(loginResponse);
    }

    @GetMapping("/me")
    public ApiResponse<AdminMemberResponse> getMe(
        @ApiIgnore @ModelAttribute("adminMemberId") Long adminMemberId // Model Attribute 는 AdminControllerAdvice 에서 주입
    ) {
        AdminMemberVo adminMemberVo = adminMemberFacadeService.getAdminMember(adminMemberId);

        return ApiResponse.success(AdminMemberResponse.from(adminMemberVo));
    }

}
