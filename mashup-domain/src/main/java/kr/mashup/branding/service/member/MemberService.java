package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.generation.GenerationVo;
import kr.mashup.branding.domain.generation.exception.GenerationNotFoundException;
import kr.mashup.branding.domain.member.*;
import kr.mashup.branding.repository.generation.GenerationRepository;
import kr.mashup.branding.repository.member.MemberRepository;
import kr.mashup.branding.service.member.dto.MemberCreateVo;
import kr.mashup.branding.service.member.dto.MemberUpdateVo;
import kr.mashup.branding.service.member.dto.MemberVo;
import kr.mashup.branding.domain.member.exception.MemberLoginFailException;
import kr.mashup.branding.domain.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final GenerationRepository generationRepository;
    private final PasswordEncoder passwordEncoder;

    //1. 회원 생성
    @Transactional
    public MemberVo save(MemberCreateVo memberCreateVo) {

        final String name = memberCreateVo.getName();
        final String identification = memberCreateVo.getIdentification();
        final Platform platform = memberCreateVo.getPlatform();
        final String password = memberCreateVo.getPassword();
        final Boolean privatePolicyAgreed = memberCreateVo.getPrivatePolicyAgreed();

        GenerationVo generationVo = memberCreateVo.getGeneration();
        Generation generation = generationRepository
                .findByNumber(generationVo.getNumber())
                .orElseThrow(GenerationNotFoundException::new);

        Member member = Member.of(name, identification, password, passwordEncoder, platform, generation, privatePolicyAgreed);
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

    public MemberVo getOrThrowByIdentificationAndPassword(String identification, String password){
        Member member = memberRepository.findByIdentification(identification).orElseThrow(MemberNotFoundException::new);
        if(!member.isMatchPassword(password, passwordEncoder)){
            throw new MemberLoginFailException();
        }
        return MemberVo.from(member);
    }

    //3. 회원 수정
    @Transactional
    public MemberVo updateMemberInfo(Long memberId, MemberUpdateVo memberUpdateVo) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.updateInfo(memberUpdateVo);

        return MemberVo.from(member);
    }
    @Transactional
    public MemberVo changePassword(Long memberId, String rawPassword, String newPassword){

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.changePassword(rawPassword, newPassword, passwordEncoder);

        return MemberVo.from(member);
    }


    //4. 회원 삭제.
    @Transactional
    public MemberVo deleteMember(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);

        return MemberVo.from(member);
    }


}
