package kr.mashup.branding.service.adminmember;

import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberLoginVo;
import kr.mashup.branding.domain.adminmember.AdminMemberVo;

public interface AdminMemberService {
    AdminMember signUp(AdminMemberVo adminMemberVo);

    AdminMember signIn(AdminMemberLoginVo adminMemberLoginVo);

    AdminMember getByAdminMemberId(Long adminMemberId);
}
