package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.member.*;
import kr.mashup.branding.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //1. 회원 생성
    public MemberVo save(MemberCreateVo memberCreateVo) {

        Member member = memberCreateVo.toEntity(passwordEncoder);
        memberRepository.save(member);

        return MemberVo.from(member);
    }

    //2. 회원 조회
    public MemberVo getOrThrowById(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        return MemberVo.from(member);
    }

    public MemberVo getOrThrowByIdentification(String identification) {

        Member member = memberRepository.findByIdentification(identification).orElseThrow(MemberNotFoundException::new);

        return MemberVo.from(member);
    }

    //3. 회원 수정
    public MemberVo updateMemberInfo(Long memberId, MemberUpdateVo memberUpdateVo) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.updateInfo(memberUpdateVo);

        return MemberVo.from(member);
    }

    //4. 회원 삭제.
    public MemberVo deleteMember(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);

        return MemberVo.from(member);
    }
}
