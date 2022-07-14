package kr.mashup.branding.facade.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.invite.Invite;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.member.exception.MemberInvalidInviteCodeException;
import kr.mashup.branding.dto.member.MemberCreateDto;
import kr.mashup.branding.security.JwtService;
import kr.mashup.branding.service.invite.InviteService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.member.request.LoginRequest;
import kr.mashup.branding.ui.member.request.SignUpRequest;
import kr.mashup.branding.ui.member.request.ValidInviteRequest;
import kr.mashup.branding.ui.member.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class MemberFacadeService {

    private final MemberService memberService;
    private final InviteService inviteService;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long memberId) {

        final Member member = memberService.getOrThrowById(memberId);

        return MemberInfoResponse.from(member);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        // Member 조회
        final String identification = request.getIdentification();
        final String password = request.getPassword();
        final Member member =
            memberService.getOrThrowByIdentificationAndPassword(identification, password);
        // Token 생성
        final Long memberId = member.getId();
        final String token = jwtService.encode(memberId);

        return LoginResponse.of(
            memberId,
            token,
            member.getName(),
            member.getPlatform().name(),
            member.getGeneration().getNumber()
        );
    }


    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        // 초대코드 조회
        final String inviteCode = request.getInviteCode();
        final Invite invite = inviteService.getOrThrow(inviteCode);

        // 코드 검증
        final Platform platform = request.getPlatform();
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
        return SignUpResponse.of(token);
    }

    @Transactional(readOnly = true)
    public ValidInviteResponse validateInviteCode(ValidInviteRequest request) {
        boolean isValidCode = inviteService.getOrNull(request.getInviteCode())
            .map(invite -> invite.getPlatform().equals(request.getPlatform()))
            .orElse(false);

        return ValidInviteResponse.of(isValidCode);
    }

    @Transactional
    public void withdraw(Long memberId) {
        memberService.deleteMember(memberId);
    }

    public TokenResponse getAccessToken(Long memberId) {
        final String token = jwtService.encode(memberId);
        return TokenResponse.of(token);
    }
}
