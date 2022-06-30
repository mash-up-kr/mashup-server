package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.dto.generation.GenerationDto;
import kr.mashup.branding.domain.generation.exception.GenerationNotFoundException;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.member.exception.MemberLoginFailException;
import kr.mashup.branding.domain.member.exception.MemberNotFoundException;
import kr.mashup.branding.repository.member.MemberRepository;
import kr.mashup.branding.repository.generation.GenerationRepository;
import kr.mashup.branding.dto.member.MemberCreateDto;
import kr.mashup.branding.dto.member.MemberUpdateDto;
import kr.mashup.branding.dto.member.MemberDto;
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
    public MemberDto save(MemberCreateDto memberCreateDto) {

        final String name = memberCreateDto.getName();
        final String identification = memberCreateDto.getIdentification();
        final Platform platform = memberCreateDto.getPlatform();
        final String password = memberCreateDto.getPassword();
        final Boolean privatePolicyAgreed = memberCreateDto.getPrivatePolicyAgreed();

        GenerationDto generationDto = memberCreateDto.getGeneration();
        Generation generation = generationRepository
                .findByNumber(generationDto.getNumber())
                .orElseThrow(GenerationNotFoundException::new);

        Member member = Member.of(name, identification, password, passwordEncoder, platform, generation, privatePolicyAgreed);
        memberRepository.save(member);

        return MemberDto.from(member);
    }

    //2. 회원 조회
    public MemberDto getOrThrowById(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        return MemberDto.from(member);
    }

    public MemberDto getOrThrowByIdentification(String identification) {

        Member member = memberRepository.findByIdentification(identification).orElseThrow(MemberNotFoundException::new);

        return MemberDto.from(member);
    }

    public MemberDto getOrThrowByIdentificationAndPassword(String identification, String password){
        Member member = memberRepository.findByIdentification(identification).orElseThrow(MemberNotFoundException::new);
        if(!member.isMatchPassword(password, passwordEncoder)){
            throw new MemberLoginFailException();
        }
        return MemberDto.from(member);
    }

    //3. 회원 수정
    @Transactional
    public MemberDto updateMemberInfo(Long memberId, MemberUpdateDto memberUpdateDto) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.updateInfo(memberUpdateDto);

        return MemberDto.from(member);
    }
    @Transactional
    public MemberDto changePassword(Long memberId, String rawPassword, String newPassword){

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.changePassword(rawPassword, newPassword, passwordEncoder);

        return MemberDto.from(member);
    }


    //4. 회원 삭제.
    @Transactional
    public MemberDto deleteMember(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);

        return MemberDto.from(member);
    }


}
