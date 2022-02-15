package kr.mashup.branding.facade.adminmember;

import kr.mashup.branding.domain.adminmember.AdminMemberLoginVo;

public interface AdminMemberFacadeService {
    LoginResponseVo login(AdminMemberLoginVo adminMemberLoginVo);
}
