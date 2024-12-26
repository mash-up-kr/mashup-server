package kr.mashup.branding.facade.member;

import kr.mashup.branding.domain.exception.GenerationIntegrityFailException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.invite.Invite;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.member.exception.MemberInvalidInviteCodeException;
import kr.mashup.branding.security.JwtService;
import kr.mashup.branding.service.invite.InviteService;
import kr.mashup.branding.service.member.MemberCreateDto;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import kr.mashup.branding.ui.member.request.*;
import kr.mashup.branding.ui.member.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberFacadeService {

    private final MemberService memberService;
    private final InviteService inviteService;
    private final JwtService jwtService;
    private final ScoreHistoryService scoreHistoryService;

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long memberId) {

        final Member member = memberService.getActiveOrThrowById(memberId);
        Platform latestPlatform = memberService.getLatestPlatform(member);
        return MemberInfoResponse.from(member,latestPlatform);
    }

    @Transactional
    public AccessResponse login(LoginRequest request) {
        // Member 조회
        final String identification = request.getIdentification();
        final String password = request.getPassword();
        final Member member =
            memberService.getActiveOrThrowByIdentificationAndPassword(identification, password);
        // Token 생성
        final String token = getToken(member);

        final Platform latestPlatform = memberService.getLatestPlatform(member);

        // 로그인 시점에 푸시 알림을 위한 정보 업데이트
        member.updatePushNotificationInfo(request.getOsType(), request.getFcmToken());

        return AccessResponse.of(member,latestPlatform,token);
    }


    @Transactional
    public AccessResponse signUp(SignUpRequest request) {
        // 초대코드 조회
        final Platform platform = request.getPlatform();
        final String inviteCode = request.getInviteCode();
        final Invite invite = inviteService.getOrThrow(inviteCode);

        // 코드 검증
        if (!invite.getPlatform().equals(platform)) {
            throw new MemberInvalidInviteCodeException();
        }

        final Generation generation = invite.getGeneration();

        final MemberCreateDto memberCreateDto = MemberCreateDto.of(
                request.getName(),
                request.getIdentification(),
                request.getPassword(),
                platform,
                generation,
                request.getPrivatePolicyAgreed(),
                request.getOsType(),
                request.getFcmToken());

        final Member member = memberService.save(memberCreateDto);
        final String token = getToken(member);
        Platform latestPlatform = memberService.getLatestPlatform(member);

        return AccessResponse.of(member,latestPlatform, token);
    }

    @Transactional(readOnly = true)
    public ValidResponse validateInviteCode(ValidInviteRequest request) {
        boolean isValidCode = inviteService.getOrNull(request.getInviteCode())
            .map(invite -> invite.getPlatform().equals(request.getPlatform()))
            .orElse(false);

        return ValidResponse.of(isValidCode);
    }

    @Transactional
    public void withdraw(Long memberId) {
        memberService.deleteMember(memberId);
    }

    public TokenResponse getAccessToken(Long memberId) {
        final String token = jwtService.encode(memberId);
        return TokenResponse.of(token);
    }

    @Transactional(readOnly = true)
    public ValidResponse checkDuplicatedIdentification(String identification) {
        boolean isExist = memberService.isDuplicatedIdentification(identification);
        return ValidResponse.of(!isExist);
    }

    @Transactional(readOnly = true)
    public ExistsResponse existsIdentification(String identification) {
        boolean isExist = memberService.existsIdentification(identification);
        return ExistsResponse.of(isExist);
    }

    @Transactional
    public Boolean updatePushNotificationAgreed(Long memberId, PushNotificationRequest request) {
        Member member = memberService.getActiveOrThrowById(memberId);
        member.updatePushNotificationAgreed(
                request.getNewsPushNotificationAgreed(),
                request.getDanggnPushNotificationAgreed()
        );
        return true;
    }

    @Transactional(readOnly = true)
    public MemberGenerationsResponse findMemberGenerationByMemberId(Long memberId) {
        Member member = memberService.getActiveOrThrowById(memberId);
        return MemberGenerationsResponse.of(memberService.findMemberGenerationByMember(member));
    }

    @Transactional
    public Boolean updateMemberGenerationById(
            Long memberId,
            Long memberGenerationId,
            MemberGenerationRequest request
    ) {
        Member member = memberService.getActiveOrThrowById(memberId);
        checkMemberGenerationIsIn(memberGenerationId, member.getMemberGenerations());

        memberService.updateMemberGeneration(memberGenerationId, request.getProjectTeamName(), request.getRole());
        return true;
    }

    @Transactional
    public void changePassword(
        String identification,
        MemberPasswordChangeRequest request
    ) {
        memberService.resetPassword(identification, request.getNewPassword());
    }

    private String getToken(Member member) {
        return jwtService.encode(member.getId());
    }

    private void checkMemberGenerationIsIn(Long memberGenerationId, List<MemberGeneration> memberGenerations) {
        var result = memberGenerations.stream()
                .anyMatch(memberGeneration -> memberGeneration.getId().equals(memberGenerationId));
        if (!result) {
            throw new GenerationIntegrityFailException();
        }
    }
}
