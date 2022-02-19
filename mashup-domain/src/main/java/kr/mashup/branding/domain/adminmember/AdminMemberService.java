package kr.mashup.branding.domain.adminmember;

public interface AdminMemberService {
    AdminMember signUp(AdminMemberVo adminMemberVo);

    AdminMember signIn(AdminMemberLoginVo adminMemberLoginVo);

    AdminMember getByAdminMemberId(Long adminMemberId);
}
