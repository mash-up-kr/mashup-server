package kr.mashup.branding.facade.adminmember;

import kr.mashup.branding.domain.adminmember.AdminMemberSignInVo;

public interface AdminMemberFacadeService {
    SignInVo signIn(AdminMemberSignInVo adminMemberSignInVo);
}
