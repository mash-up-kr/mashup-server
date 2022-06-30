package kr.mashup.branding.facade.member;

import kr.mashup.branding.domain.generation.GenerationVo;
import kr.mashup.branding.domain.invite.InviteVo;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.member.exception.MemberInvalidInviteCodeException;
import kr.mashup.branding.service.invite.InviteService;
import kr.mashup.branding.service.member.dto.MemberCreateVo;
import kr.mashup.branding.service.member.dto.MemberVo;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.security.JwtService;
import kr.mashup.branding.ui.member.dto.request.LoginRequest;
import kr.mashup.branding.ui.member.dto.request.ValidInviteRequest;
import kr.mashup.branding.ui.member.dto.request.SignUpRequest;
import kr.mashup.branding.ui.member.dto.response.LoginResponse;
import kr.mashup.branding.ui.member.dto.response.MemberInfoResponse;
import kr.mashup.branding.ui.member.dto.response.ValidInviteResponse;
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

        final MemberVo memberVo = memberService.getOrThrowById(memberId);

        return MemberInfoResponse.from(memberVo);
    }

    public LoginResponse login(LoginRequest request) {
        // Member 조회
        final String identification = request.getIdentification();
        final String password = request.getPassword();
        final MemberVo memberVo = memberService.getOrThrowByIdentificationAndPassword(identification, password);
        // Token 생성
        final Long memberId = memberVo.getId();
        final String token = jwtService.encode(memberId);

        return LoginResponse.of(
                memberId,
                token,
                memberVo.getName(),
                memberVo.getPlatform().name(),
                memberVo.getGeneration());
    }

    public void signUp(SignUpRequest request) {
        // 초대코드 조회
        final String inviteCode = request.getInviteCode();
        final InviteVo inviteVo = inviteService.getOrThrow(inviteCode);

        // 코드 검증
        final Platform platform = request.getPlatform();
        if (!inviteVo.getPlatform().equals(platform)) {
            throw new MemberInvalidInviteCodeException();
        }

        final GenerationVo generation = inviteVo.getGeneration();

        final MemberCreateVo memberCreateVo = MemberCreateVo.of(
                request.getName(),
                request.getIdentification(),
                request.getPassword(),
                platform,
                generation,
                request.getPrivatePolicyAgreed());

        memberService.save(memberCreateVo);
    }

    public ValidInviteResponse validateInviteCode(ValidInviteRequest request) {
        final Optional<InviteVo> inviteVo = inviteService.getOrNull(request.getInviteCode());

        if(inviteVo.isEmpty()){
            return ValidInviteResponse.of(false);
        }

        final boolean isValidCode = inviteVo.get().getPlatform().equals(request.getPlatform());

        return ValidInviteResponse.of(isValidCode);
    }

    public void withdraw(Long memberId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        memberService.deleteMember(memberId);
    }

}
