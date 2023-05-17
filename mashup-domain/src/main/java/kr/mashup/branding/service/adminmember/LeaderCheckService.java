package kr.mashup.branding.service.adminmember;


import kr.mashup.branding.domain.adminmember.entity.AdminMember;

public interface LeaderCheckService {

    boolean isMashUpLeader(AdminMember adminMember);
    boolean isMashUpSubLeader(AdminMember adminMember);

}
