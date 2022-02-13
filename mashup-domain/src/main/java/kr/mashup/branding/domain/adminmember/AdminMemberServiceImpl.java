package kr.mashup.branding.domain.adminmember;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminMemberServiceImpl implements AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;

    @Override
    public AdminMember signUp(AdminMemberVo adminMemberVo) {

        //TODO: 아아디 중복 검증
        AdminMember adminMember = AdminMember.of(
            adminMemberVo.getUsername(),
            adminMemberVo.getPassword(),
            adminMemberVo.getPhoneNumber(),
            adminMemberVo.getGroup(),
            adminMemberVo.getPosition()
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
