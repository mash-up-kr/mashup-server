package kr.mashup.branding.service.adminmember;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.entity.Position;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
@Profile("production")
@Service
public class LeaderCheckServiceImpl implements LeaderCheckService {
    @Override
    public boolean isMashUpLeader(final AdminMember adminMember) {
        return adminMember.getPosition().equals(Position.MASHUP_LEADER);
    }

    @Override
    public boolean isMashUpSubLeader(final AdminMember adminMember) {
        return adminMember.getPosition().equals(Position.MASHUP_SUBLEADER);
    }
}
