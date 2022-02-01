package kr.mashup.branding.domain.adminmember;

import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.domain.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminMemberServiceImpl implements AdminMemberService {

    private final TeamService teamService;
    private final AdminMemberRepository adminMemberRepository;

    @Override
    public AdminMember signUp(AdminMemberVo adminMemberVo) {
        Team team = teamService.getTeam(adminMemberVo.getTeamId());

        //TODO: providerUserId 검증
        AdminMember adminMember = AdminMember.of(
                adminMemberVo.getProviderUserId(),
                adminMemberVo.getName(),
                team,
                adminMemberVo.getDescription()
        );

        return adminMemberRepository.save(adminMember);
    }

    @Override
    public AdminMember signIn(AdminMemberSignInVo adminMemberSignInVo) {
        //TODO: providerUserId 검증
        return adminMemberRepository.findByProviderUserId(adminMemberSignInVo.getProviderUserId())
                .orElseThrow(AdminMemberNotFoundException::new);
    }
}
