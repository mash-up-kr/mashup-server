package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.exception.GenerationIntegrityFailException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.generation.GenerationStatus;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.MemberStatus;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.member.exception.InactiveGenerationException;
import kr.mashup.branding.domain.member.exception.MemberLoginFailException;
import kr.mashup.branding.domain.member.exception.MemberNotFoundException;
import kr.mashup.branding.domain.member.exception.MemberPendingException;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.repository.attendance.AttendanceRepository;
import kr.mashup.branding.repository.danggn.DanggnNotificationMemberRecordRepository;
import kr.mashup.branding.repository.danggn.DanggnScoreRepository;
import kr.mashup.branding.repository.danggn.DanggnShakeLogRepository;
import kr.mashup.branding.repository.member.MemberGenerationRepository;
import kr.mashup.branding.repository.member.MemberRepository;
import kr.mashup.branding.repository.member.MemberRepositoryCustomImpl.MemberScoreQueryResult;
import kr.mashup.branding.repository.memberpopup.MemberPopupRepository;
import kr.mashup.branding.repository.scorehistory.ScoreHistoryRepository;
import kr.mashup.branding.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final AttendanceRepository attendanceRepository;

    private final MemberRepository memberRepository;
    @Qualifier("fourTimesRoundPasswordEncoder")
    private final PasswordEncoder passwordEncoder;
    private final MemberGenerationRepository memberGenerationRepository;
    private final ScoreHistoryRepository scoreHistoryRepository;
    private final DanggnScoreRepository danggnScoreRepository;
    private final DanggnNotificationMemberRecordRepository danggnNotificationMemberRecordRepository;
    private final DanggnShakeLogRepository danggnShakeLogRepository;
    private final MemberPopupRepository memberPopupRepository;

    public Member save(final MemberCreateDto memberCreateDto) {
        // 이미 존재하는 identification 인지 확인한다.
        final Boolean isExist = memberRepository.existsByIdentification(memberCreateDto.getIdentification());
        if (isExist) {
            throw new BadRequestException(ResultCode.MEMBER_DUPLICATED_IDENTIFICATION);
        }

        final Generation generation = memberCreateDto.getGeneration();
        final Member member = Member.of(
            memberCreateDto.getName(),
            memberCreateDto.getIdentification(),
            memberCreateDto.getPassword(),
            passwordEncoder,
            memberCreateDto.getPrivatePolicyAgreed(),
            memberCreateDto.getOsType(),
            memberCreateDto.getFcmToken()
        );
        memberRepository.save(member);

        final MemberGeneration memberGeneration = MemberGeneration.of(member, generation, memberCreateDto.getPlatform());

        memberGenerationRepository.save(memberGeneration);
        member.addMemberGenerations(memberGeneration);

        return member;
    }

    public MemberGeneration saveMemberGeneration(
        final Member member,
        final Generation generation,
        final Platform platform
    ) {
        return memberGenerationRepository.save(MemberGeneration.of(member, generation, platform));
    }

    public void deleteMemberGeneration(MemberGeneration memberGeneration) {
        memberGenerationRepository.delete(memberGeneration);
    }


    //2-1. 회원 조회 - active 상태만
    public Member getActiveOrThrowById(Long memberId) {
        final Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        checkActiveStatus(member);

        return member;
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    public boolean isDuplicatedIdentification(String identification) {
        return memberRepository.existsByIdentification(identification);
    }

    public Member getActiveOrThrowByIdentificationAndPassword(
        String identification,
        String password
    ) {
        final Member member = memberRepository.findByIdentification(identification)
            .orElseThrow(MemberNotFoundException::new);
        if (!member.isMatchPassword(password, passwordEncoder)) {
            throw new MemberLoginFailException();
        }
        checkActiveStatus(member);
        return member;
    }

    public Page<MemberScoreQueryResult> getActiveAllByGeneration(
        Generation generation,
        Platform platform,
        String searchName,
        Pageable pageable
    ) {

        return memberRepository.findAllByGeneration(generation, platform, searchName, pageable);
    }

    public List<Member> getAllByPlatformAndGeneration(
        Platform platform,
        Generation generation
    ) {
        return memberRepository.findActiveByPlatformAndGeneration(
            platform,
            generation
        );
    }


    //기수 조회
    public List<MemberGeneration> getMemberGenerations(Member member) {
        return memberGenerationRepository.findByMember(member);
    }

    public Platform getLatestPlatform(Member member) {
        final List<MemberGeneration> memberGenerations = getMemberGenerations(member);
        memberGenerations.sort(Comparator.comparingInt(it -> it.getGeneration().getNumber()));

        return memberGenerations.get(memberGenerations.size() - 1).getPlatform();
    }

    public void resetPassword(
        final String identification,
        final String newPassword
    ) {
        final Member member = memberRepository.findByIdentification(identification)
            .orElseThrow(MemberNotFoundException::new);

        member.setPassword(newPassword, passwordEncoder);
    }

    public void deleteMember(Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        member.getMemberGenerations().forEach(memberGeneration -> {
            danggnScoreRepository.deleteByMemberGeneration(memberGeneration);
            danggnNotificationMemberRecordRepository.deleteByMemberGeneration(memberGeneration);
            danggnShakeLogRepository.deleteByMemberGeneration(memberGeneration);
        });

        memberPopupRepository.deleteByMember(member);
        memberGenerationRepository.deleteByMember(member);
        scoreHistoryRepository.deleteByMember(member);
        attendanceRepository.deleteByMember(member);

        memberRepository.delete(member);
    }

    public boolean existsIdentification(String identification) {
        return memberRepository.existsByIdentification(identification);
    }

    private void checkActiveStatus(Member member) {
        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new MemberPendingException();
        }
    }

    public Platform getPlatform(Member member, Generation generation) {
        return memberGenerationRepository.findByMemberAndGeneration(member, generation).orElseThrow(MemberNotFoundException::new).getPlatform();
    }

    public List<Member> getAllPushNotiTargetableMembers() {
        return memberRepository.findAllByCurrentGenerationAt(LocalDate.now()).stream()
            .filter(Member::getNewsPushNotificationAgreed)
            .collect(Collectors.toList());
    }

    public List<Member> getPushNotiTargetableMembersBySchedule(Schedule schedule) {
        LocalDate now = LocalDate.now();
        return memberRepository.findAllByCurrentGenerationAt(now).stream()
            .filter(Member::getNewsPushNotificationAgreed)
            .filter(member -> schedule.checkAvailabilityByPlatform(getLatestPlatform(member)))
            .collect(Collectors.toList());
    }

    public List<Member> getPushNotiTargetableMembers(List<Long> memberIds) {
        return memberRepository.findAllByCurrentGenerationAt(LocalDate.now()).stream()
            .filter(member -> memberIds.contains(member.getId()))
            .filter(Member::getNewsPushNotificationAgreed)
            .collect(Collectors.toList());
    }

    public List<Member> getActiveAllByGeneration(Generation generation) {
        return memberRepository.findAllActiveByGeneration(generation);
    }

    public MemberGeneration findByMemberIdAndGenerationNumber(Long memberId, Integer generationNumber) {
        return memberGenerationRepository.findByMemberIdAndGenerationNumber(memberId, generationNumber)
            .orElseThrow(GenerationIntegrityFailException::new);
    }

    public Boolean existMemberGenerationByMemberAndGeneration(Member member, Generation generation) {
        return memberGenerationRepository.existsByMemberAndGeneration(member, generation);
    }

    public List<Member> getAllDanggnPushNotiTargetableMembers() {
        return memberRepository.findAllByCurrentGenerationAt(LocalDate.now()).stream()
            .filter(Member::getDanggnPushNotificationAgreed)
            .collect(Collectors.toList());
    }

    public MemberGeneration findByMemberGenerationId(Long memberGenerationId) {
        return memberGenerationRepository.findById(memberGenerationId).orElseThrow(GenerationIntegrityFailException::new);
    }

    public Boolean isActiveGeneration(MemberGeneration memberGeneration) {
        Generation generation = memberGeneration.getGeneration();
        return DateUtil.isInTime(generation.getStartedAt(), generation.getEndedAt(), LocalDate.now());
    }

    public List<MemberGeneration> findByGeneration(Generation generation) {
        return memberGenerationRepository.findByGeneration(generation);
    }

    public void updateStatus(MemberStatus memberStatus, Generation generation, Platform platform, List<Member> members) {
        switch (memberStatus) {
            case ACTIVE:
                for (final Member member : members) {
                    if (!existMemberGenerationByMemberAndGeneration(member, generation)) { // memberGeneration 없으면 생성
                        saveMemberGeneration(member, generation, platform);
                    }

                    member.setStatus(memberStatus);
                }
                break;
            case INACTIVE:
                for (final Member member : members) {
                    if (existMemberGenerationByMemberAndGeneration(member, generation)) { // memberGeneration 있을면 삭제

                        final MemberGeneration memberGeneration
                            = findByMemberIdAndGenerationNumber(member.getId(), generation.getNumber());

                        deleteMemberGeneration(memberGeneration);
                    }

                    member.setStatus(memberStatus);
                }
                break;
            case RUN:
                for (final Member member : members) {
                    member.setStatus(memberStatus);
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public List<MemberGeneration> findMemberGenerationByMember(Member member) {
        return memberGenerationRepository.findByMember(member);
    }

    public void updateMemberGeneration(
        Long memberGenerationId,
        String projectTeamName,
        String role
    ) {
        var memberGeneration = memberGenerationRepository.findById(memberGenerationId)
            .orElseThrow(GenerationIntegrityFailException::new);
        memberGeneration.updateProjectInfo(
            projectTeamName,
            role
        );
    }

    public List<Member> findAllByMemberIds(final List<Long> memberIds) {
        final List<Member> members = memberRepository.findAllById(memberIds);
        if (memberIds.size() != members.size()) {
            throw new MemberNotFoundException();
        }

        return members;
    }

    @Transactional
    public void transfer(final Generation oldGeneration, final Generation newGeneration, final List<Member> members) {

        final List<Long> memberIds =
            members.stream().map(BaseEntity::getId).collect(Collectors.toList());

        final List<MemberGeneration> newMemberGenerations =
            memberGenerationRepository
                .findByMemberIdsAndGenerationNumber(memberIds, oldGeneration.getNumber())
                .stream()
                .map(memberGeneration ->
                    MemberGeneration.of(memberGeneration.getMember(), newGeneration, memberGeneration.getPlatform()))
                .collect(Collectors.toList());

        memberGenerationRepository.saveAll(newMemberGenerations);
    }

    @Transactional
    public void dropOut(final Generation generation, final List<Member> members) {

        final List<Long> memberIds = members.stream().map(BaseEntity::getId).collect(Collectors.toList());

        memberGenerationRepository
            .findByMemberIdsAndGenerationNumber(memberIds, generation.getNumber())
            .forEach(MemberGeneration::dropOut);
    }

    public MemberGeneration getCurrentMemberGeneration(Member member) {
        return member.getMemberGenerations()
            .stream()
            .filter(mg -> GenerationStatus.ON_GOING.equals(mg.getGeneration().getStatus()))
            .max(Comparator.comparingInt(mg -> mg.getGeneration().getNumber()))
            .orElseThrow(InactiveGenerationException::new);
    }

    @Transactional(readOnly = true)
    public List<Member> getAllByBirthdayRecipient(Generation generation) {
        return memberRepository.retrieveByBirthDate(generation, LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<Member> getAllByBirthdaySender(Generation generation) {

        List<Member> senders = memberRepository.findAllActiveByGeneration(generation);
        List<Member> recipients = memberRepository.retrieveByBirthDate(generation, LocalDate.now());
        senders.removeAll(recipients);

        return senders;
    }


    @Transactional
    public void updatePushCheckTime(final Member member) {
        member.updatePushCheckTime(LocalDateTime.now());
    }
}
