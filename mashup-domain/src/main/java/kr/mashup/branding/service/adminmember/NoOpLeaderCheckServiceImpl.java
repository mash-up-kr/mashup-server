package kr.mashup.branding.service.adminmember;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
@Profile("!production")
@Service
public class NoOpLeaderCheckServiceImpl implements LeaderCheckService {
    @Override
    public boolean isMashUpLeader(AdminMember adminMember) {
        return true;
    }

    @Override
    public boolean isMashUpSubLeader(AdminMember adminMember) {
        return true;
    }
}
