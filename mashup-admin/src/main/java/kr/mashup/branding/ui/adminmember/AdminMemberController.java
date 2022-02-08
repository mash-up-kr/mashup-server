package kr.mashup.branding.ui.adminmember;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberSignInVo;
import kr.mashup.branding.domain.adminmember.AdminMemberVo;
import kr.mashup.branding.facade.adminmember.AdminMemberFacadeService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AdminMemberController {

    private final AdminMemberAssembler adminMemberAssembler;
    private final AdminMemberFacadeService adminMemberFacadeService;

    @PostMapping("/signup")
    void signup(
        @RequestBody SignUpRequest signUpRequest
    ) {
        AdminMemberVo adminMemberVo = adminMemberAssembler.toAdminMemberVo(signUpRequest);
        AdminMember adminMember = adminMemberFacadeService.signUp(adminMemberVo);
    }

    @PostMapping("/signin")
    void signin(
        @RequestBody SignInRequest signInRequest
    ) {
        AdminMemberSignInVo adminMemberSignInVo = adminMemberAssembler.toAdminMemberSignInVo(signInRequest);
        adminMemberFacadeService.signIn(adminMemberSignInVo);
    }

}
