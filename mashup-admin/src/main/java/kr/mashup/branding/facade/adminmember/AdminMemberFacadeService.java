package kr.mashup.branding.facade.adminmember;

import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberSignInVo;
import kr.mashup.branding.domain.adminmember.AdminMemberVo;

public interface AdminMemberFacadeService {
    AdminMember signUp(AdminMemberVo adminMemberVo);

    SignInVo signIn(AdminMemberSignInVo adminMemberSignInVo);
}
