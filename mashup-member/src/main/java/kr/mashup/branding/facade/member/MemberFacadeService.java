package kr.mashup.branding.facade.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.invite.Invite;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.member.exception.MemberInvalidInviteCodeException;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import kr.mashup.branding.dto.member.MemberCreateDto;
import kr.mashup.branding.security.JwtService;
import kr.mashup.branding.service.invite.InviteService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import kr.mashup.branding.ui.member.request.LoginRequest;
import kr.mashup.branding.ui.member.request.PushNotificationRequest;
import kr.mashup.branding.ui.member.request.SignUpRequest;
import kr.mashup.branding.ui.member.request.ValidInviteRequest;
import kr.mashup.branding.ui.member.response.AccessResponse;
import kr.mashup.branding.ui.member.response.MemberInfoResponse;
import kr.mashup.branding.ui.member.response.TokenResponse;
import kr.mashup.branding.ui.member.response.ValidResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


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
        final Long memberId = member.getId();
        final String token = jwtService.encode(memberId);

        Platform latestPlatform = memberService.getLatestPlatform(member);

        // 로그인 시점에 푸시 알림을 위한 정보 업데이트
        memberService.updatePushNotificationInfo(request.getOsType(), request.getFcmToken(), member);

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
                request.getPrivatePolicyAgreed());

        final Member member = memberService.save(memberCreateDto);
        final String token = jwtService.encode(member.getId());
        Platform latestPlatform = memberService.getLatestPlatform(member);

        // 기본 활동 점수 부여
        ScoreHistory scoreHistory = ScoreHistory.of(ScoreType.ATTENDANCE, member, LocalDateTime.now(), "", generation, null);
        scoreHistoryService.save(scoreHistory);

        // 회원가입 시점에 푸시 알림을 위한 정보 업데이트
        memberService.updatePushNotificationInfo(request.getOsType(), request.getFcmToken(), member);

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

    @Transactional
    public Boolean updatePushNotificationAgreed(Long memberId, PushNotificationRequest request) {
        Member member = memberService.getActiveOrThrowById(memberId);
        memberService.updatePushNotificationAgreed(request.getPushNotificationAgreed(), member);
        return true;
    }
}
