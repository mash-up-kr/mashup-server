package kr.mashup.branding.facade.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.service.member.MemberProfileService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.member.request.MemberProfileRequest;
import kr.mashup.branding.ui.member.response.MemberProfileResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberProfileFacadeService {

    private final MemberProfileService memberProfileService;
    private final MemberService memberService;

    @Transactional
    public Boolean updateMyProfile(Long memberId, MemberProfileRequest request) {
        memberProfileService.updateOrSave(
                memberId,
                request.getBirthDate(),
                request.getJob(),
                request.getCompany(),
                request.getIntroduction(),
                request.getResidence(),
                request.getSocialNetworkServiceLink(),
                request.getGithubLink(),
                request.getPortfolioLink(),
                request.getBlogLink(),
                request.getLinkedInLink()
        );

        return true;
    }

    @Transactional
    public MemberProfileResponse getMyProfile(Long memberId) {
        Member member = memberService.findMemberById(memberId);

        var profile = memberProfileService.findOrSave(memberId);
        var generations = memberService.findMemberGenerationByMemberId(member);

        return MemberProfileResponse.from(profile, generations);
    }
}
