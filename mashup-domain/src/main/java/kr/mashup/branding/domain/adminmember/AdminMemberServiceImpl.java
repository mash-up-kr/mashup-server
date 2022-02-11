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

        //TODO: 아아디 중복 검증
        AdminMember adminMember = AdminMember.of(
            adminMemberVo.getUsername(),
            adminMemberVo.getPassword(),
            adminMemberVo.getPhoneNumber(),
            team,
            adminMemberVo.getDescription()
        );

        return adminMemberRepository.save(adminMember);
    }

    @Override
    public AdminMember signIn(AdminMemberSignInVo adminMemberSignInVo) {
        AdminMember adminMember = adminMemberRepository.findByUsername(adminMemberSignInVo.getUsername())
            .orElseThrow(AdminMemberNotFoundException::new);
        return adminMember;
    }

    @Override
    public AdminMember getByAdminMemberId(Long adminMemberId) {
        return adminMemberRepository.findById(adminMemberId)
            .orElseThrow(AdminMemberNotFoundException::new);
    }
}
