package kr.mashup.branding.facade.member;

import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.member.exception.MemberInvalidInviteCodeException;
import kr.mashup.branding.dto.generation.GenerationDto;
import kr.mashup.branding.dto.invite.InviteDto;
import kr.mashup.branding.dto.member.MemberCreateDto;
import kr.mashup.branding.dto.member.MemberDto;
import kr.mashup.branding.security.JwtService;
import kr.mashup.branding.service.invite.InviteService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.member.request.LoginRequest;
import kr.mashup.branding.ui.member.request.SignUpRequest;
import kr.mashup.branding.ui.member.request.ValidInviteRequest;
import kr.mashup.branding.ui.member.response.LoginResponse;
import kr.mashup.branding.ui.member.response.MemberInfoResponse;
import kr.mashup.branding.ui.member.response.ValidInviteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberFacadeService {

    private final MemberService memberService;
    private final InviteService inviteService;
    private final JwtService jwtService;

    public MemberInfoResponse getMemberInfo(Long memberId) {
        Assert.notNull(memberId, "'memberId' must not be null");

        final MemberDto memberDto = memberService.getOrThrowById(memberId);

        return MemberInfoResponse.from(memberDto);
    }

    public LoginResponse login(LoginRequest request) {
        // Member 조회
        final String identification = request.getIdentification();
        final String password = request.getPassword();
        final MemberDto memberDto = memberService.getOrThrowByIdentificationAndPassword(identification, password);
        // Token 생성
        final Long memberId = memberDto.getId();
        final String token = jwtService.encode(memberId);

        return LoginResponse.of(
            memberId,
            token,
            memberDto.getName(),
            memberDto.getPlatform().name(),
            memberDto.getGeneration());
    }

    public void signUp(SignUpRequest request) {
        // 초대코드 조회
        final String inviteCode = request.getInviteCode();
        final InviteDto inviteDto = inviteService.getOrThrow(inviteCode);

        // 코드 검증
        final Platform platform = request.getPlatform();
        if (!inviteDto.getPlatform().equals(platform)) {
            throw new MemberInvalidInviteCodeException();
        }

        final GenerationDto generation = inviteDto.getGeneration();

        final MemberCreateDto memberCreateDto = MemberCreateDto.of(
            request.getName(),
            request.getIdentification(),
            request.getPassword(),
            platform,
            generation,
            request.getPrivatePolicyAgreed());

        memberService.save(memberCreateDto);
    }

    public ValidInviteResponse validateInviteCode(ValidInviteRequest request) {
        final Optional<InviteDto> inviteDto =
            inviteService.getOrNull(request.getInviteCode());

        if (inviteDto.isEmpty()) {
            return ValidInviteResponse.of(false);
        }

        final boolean isValidCode = inviteDto.get().getPlatform().equals(request.getPlatform());

        return ValidInviteResponse.of(isValidCode);
    }

    public void withdraw(Long memberId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        memberService.deleteMember(memberId);
    }

}
