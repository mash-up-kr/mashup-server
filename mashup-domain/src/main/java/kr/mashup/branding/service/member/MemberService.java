package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.member.exception.MemberLoginFailException;
import kr.mashup.branding.domain.member.exception.MemberNotFoundException;
import kr.mashup.branding.dto.member.MemberCreateDto;
import kr.mashup.branding.dto.member.MemberUpdateDto;
import kr.mashup.branding.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //1. 회원 생성
    public Member save(MemberCreateDto memberCreateDto) {
        Member member = Member.of(
            memberCreateDto.getName(),
            memberCreateDto.getIdentification(),
            memberCreateDto.getPassword(),
            passwordEncoder,
            memberCreateDto.getPlatform(),
            memberCreateDto.getGeneration(),
            memberCreateDto.getPrivatePolicyAgreed()
        );
        return memberRepository.save(member);
    }

    //2. 회원 조회
    public Member getOrThrowById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
    }

    public Member getOrThrowByIdentification(String identification) {
        return memberRepository.findByIdentification(identification)
            .orElseThrow(MemberNotFoundException::new);
    }

    public Member getOrThrowByIdentificationAndPassword(
        String identification,
        String password
    ) {
        Member member = memberRepository.findByIdentification(identification)
            .orElseThrow(MemberNotFoundException::new);
        if (!member.isMatchPassword(password, passwordEncoder)) {
            throw new MemberLoginFailException();
        }
        return member;
    }

    //3. 회원 수정
    public Member updateMemberInfo(
        Long memberId,
        MemberUpdateDto memberUpdateDto
    ) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        member.updateInfo(memberUpdateDto);

        return member;
    }

    public Member changePassword(
        Long memberId,
        String rawPassword,
        String newPassword
    ) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        member.changePassword(rawPassword, newPassword, passwordEncoder);

        return member;
    }


    //4. 회원 삭제.
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);
    }

    public Long getTotalCountByPlatform(Platform platform) {
        return memberRepository.countByPlatform(platform);
    }

    public List<Member> getAllByPlatform(Platform platform) {
        return memberRepository.findAllByPlatform(platform);
    }

}
