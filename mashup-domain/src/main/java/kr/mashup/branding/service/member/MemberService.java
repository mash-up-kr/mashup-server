package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.MemberStatus;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.member.exception.MemberLoginFailException;
import kr.mashup.branding.domain.member.exception.MemberNotFoundException;
import kr.mashup.branding.domain.member.exception.MemberPendingException;
import kr.mashup.branding.dto.member.MemberCreateDto;
import kr.mashup.branding.repository.member.MemberGenerationRepository;
import kr.mashup.branding.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberGenerationRepository memberGenerationRepository;

    public Member save(MemberCreateDto memberCreateDto) {

        Boolean isExist = memberRepository.existsByIdentification(memberCreateDto.getIdentification());
        if(isExist){
            throw new BadRequestException(ResultCode.MEMBER_DUPLICATED_IDENTIFICATION);
        }
        Generation generation = memberCreateDto.getGeneration();

        Member member = Member.of(
            memberCreateDto.getName(),
            memberCreateDto.getIdentification(),
            memberCreateDto.getPassword(),
            passwordEncoder,
            memberCreateDto.getPrivatePolicyAgreed()
        );
        memberRepository.save(member);

        MemberGeneration memberGeneration = MemberGeneration.of(member, generation, memberCreateDto.getPlatform() );
        memberGenerationRepository.save(memberGeneration);
        member.addMemberGenerations(memberGeneration);

        return member;
    }


    //2-1. 회원 조회 - active 상태만
    public Member getActiveOrThrowById(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        checkActiveStatus(member);
        return member;
    }

    public Member getActiveOrThrowByIdentification(String identification) {
        Member member = memberRepository.findByIdentification(identification)
            .orElseThrow(MemberNotFoundException::new);
        checkActiveStatus(member);
        return member;
    }

    public boolean isDuplicatedIdentification(String identification){
        return memberRepository.existsByIdentification(identification);
    }

    public Member getActiveOrThrowByIdentificationAndPassword(
        String identification,
        String password
    ) {
        Member member = memberRepository.findByIdentification(identification)
            .orElseThrow(MemberNotFoundException::new);
        if (!member.isMatchPassword(password, passwordEncoder)) {
            throw new MemberLoginFailException();
        }
        checkActiveStatus(member);
        return member;
    }

    public Page<Member> getActiveAllByGeneration(
        Generation generation,
        Pageable pageable
    ){
        return memberRepository.findAllActiveByGeneration(generation, pageable);
    }

    public List<Member> getAllByPlatformAndGeneration(
        Platform platform,
        Generation generation
    ) {
        return memberRepository.findAllActiveByPlatformAndGeneration(
            platform,
            generation
        );
    }

    public Page<Member> getAllByPlatformAndGeneration(
        Platform platform,
        Generation generation,
        Pageable pageable
    ){
        return memberRepository.findAllActiveByPlatformAndGeneration(
            platform,
            generation,
            pageable
        );
    }

    //2-2 회원 조회 - pending 상태만
    public Page<Member> getPendingMembers(Pageable pageable){
        return memberRepository.findAllByStatus(MemberStatus.PENDING, pageable);
    }


    //기수 조회
    public List<MemberGeneration> getMemberGenerations(Member member){
        return memberGenerationRepository.findByMember(member);
    }

    public Platform getLatestPlatform(Member member){
        List<MemberGeneration> memberGenerations = getMemberGenerations(member);
        memberGenerations.sort(Comparator.comparingInt(it->it.getGeneration().getNumber()));
        return memberGenerations.get(memberGenerations.size() - 1).getPlatform();
    }

    public Member changePassword(
        Long memberId,
        String rawPassword,
        String newPassword
    ) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        checkActiveStatus(member);
        member.changePassword(rawPassword, newPassword, passwordEncoder);

        return member;
    }

    public Member activate(Long memberId){

        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        member.activate();

        return member;
    }


    //4. 회원 삭제
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        memberGenerationRepository.deleteByMember(member);
        memberRepository.delete(member);
    }

    public Long getTotalCountByPlatformAndGeneration(
        Platform platform,
        Generation generation
    ) {
        return memberRepository.countActiveByPlatformAndGeneration(platform, generation);
    }


    private void checkActiveStatus(Member member){
        if(member.getStatus() != MemberStatus.ACTIVE){
            throw new MemberPendingException();
        }
    }

    public Platform getPlatform(Member member, Generation generation) {
        return memberGenerationRepository.findByMemberAndGeneration(member, generation).get().getPlatform();
    }
}
