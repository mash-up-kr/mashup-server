package kr.mashup.branding.domain.adminmember;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.domain.team.TeamService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminMemberServiceImpl implements AdminMemberService {

    private final TeamService teamService;
    private final AdminMemberRepository adminMemberRepository;

    @Override
    public AdminMember signUp(AdminMemberVo adminMemberVo) {
        Team team = teamService.getTeam(adminMemberVo.getTeamId());

        AdminMember adminMember = AdminMember.of(
            adminMemberVo.getUsername(),
            adminMemberVo.getPassword(),
            adminMemberVo.getPosition(),
            team,
            adminMemberVo.getDescription()
        );

        return adminMemberRepository.save(adminMember);
    }

    @Override
    public AdminMember signIn(AdminMemberSignInVo adminMemberSignInVo) {
        return adminMemberRepository.findByUsername(adminMemberSignInVo.getUsername())
            .orElseThrow(AdminMemberNotFoundException::new);
    }

    @Override
    public AdminMember getByAdminMemberId(Long adminMemberId) {
        return adminMemberRepository.findById(adminMemberId)
            .orElseThrow(AdminMemberNotFoundException::new);
    }
}
